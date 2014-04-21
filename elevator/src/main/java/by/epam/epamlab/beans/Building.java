package by.epam.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.Controller;
import by.epam.epamlab.controllers.TransportationActions;
import by.epam.epamlab.controllers.TransportationState;
import by.epam.epamlab.events.AbstractEvent;
import by.epam.epamlab.factories.DAOFactory;
import by.epam.epamlab.multithreading.ElevatorTask;
import by.epam.epamlab.multithreading.TransportationTask;
import by.epam.epamlab.readers.ConfigReader;

public class Building {
	private static final String INTERRUPTED_EXCEPTION = "InterruptedException";
	private static final String START_ELEVATION = "Start elevation";
	private static final String START_ELEVATION_THREAD = "StartElevationThread";
	private static final String ELEVATOR_THREAD = "ElevatorThread:";
	private static final String START = "START!";
	private static final String PASSENGER2 = "Passenger: # ";
	private static final String PASSENGER_THREAD = "PassengerThread :";
	private static final String PASSENGER_CREATED_ID = "Passenger created: id# ";
	private static final String HIGHT_BUILDING = "Hight building:";

	private static Logger logger = Logger.getLogger(Building.class.getName());

	public final int FIRST_STOREY = Constants.FIRST_NUMBER_STOREY;
	public final int LAST_STOREY;

	private ConfigReader configReader;
	private List<Storey> storeys;
	private Elevator elevator;
	private Passenger passenger;
	private Controller controller;

	private TransportationState transportationState;
	private int animationBoost;

	private int arrivedPassengers;
	private int maxNumberStorey;
	private List<Thread> groupThread = new ArrayList<Thread>();

	public Building(ConfigReader configReader) {
		super();
		this.configReader = configReader;
		setAnimationBoost(configReader.getAnimationBoost());
		setTransportationState(TransportationState.NOT_STARTED);

		this.storeys = new ArrayList<Storey>(configReader.getStoreysNumber());
		maxNumberStorey = configReader.getStoreysNumber();
		// in ArrayList number[0] => in Building numberStorey [0]
		for (int numberStorey = 0; numberStorey < maxNumberStorey; numberStorey++) {
			storeys.add(numberStorey, new Storey(numberStorey));
		}
		// for BuildingUI.
		LAST_STOREY = configReader.getStoreysNumber() - 1;

		logger.info(HIGHT_BUILDING + storeys.size());

		this.elevator = new Elevator(configReader.getElevatorCapacity(),
				configReader.getStoreysNumber());

		// Created Controller
		controller = new Controller(this);

		// placement of passengers on the floors
		for (int countPassengers = 0; countPassengers < configReader
				.getPassengersNumber(); countPassengers++) {

			this.passenger = new Passenger(maxNumberStorey);
			getStoreys().get(passenger.getDispatchStorey())
					.getDispatchStoryContainer().add(passenger);
			logger.info(PASSENGER_CREATED_ID + passenger.getPassengerID());
		}
	}

	public int getAnimationBoost() {
		return animationBoost;
	}

	public void setAnimationBoost(int value) {
		int delay = 500;

		if (value > 0) {
			animationBoost = delay / value;
		}
		if (value == 0)
			animationBoost = 3000;
	}

	public ConfigReader getConfigReader() {
		return configReader;
	}

	public List<Storey> getStoreys() {
		return storeys;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public void setStoreys(List<Storey> storeys) {
		this.storeys = storeys;
	}

	public Controller getController() {
		return controller;
	}

	public int getMaxNumberStorey() {
		return maxNumberStorey;
	}

	public boolean isDispatchContainerEmpty(int storey) {
		return storeys.get(storey).getDispatchStoryContainer().isEmpty();
	}

	public Storey getStorey(int storey) {
		return storeys.get(storey);
	}

	public synchronized void arrived() {
		arrivedPassengers++;
	}

	public synchronized boolean allArrived() {
		return arrivedPassengers == configReader.getPassengersNumber();
	}

	public TransportationState getTransportationState() {
		return transportationState;
	}

	public void setTransportationState(TransportationState transportationState) {
		this.transportationState = transportationState;
	}

	public void startTask() {
		startTaskView();
		final Building building = this;
		// start task-thread in other thread
		Thread startTask = new Thread(new Runnable() {
			public void run() {
				for (int storey = 0; storey < building.maxNumberStorey; storey++) {
					List<Passenger> dispatchStoryContainer = building
							.getStoreys().get(storey)
							.getDispatchStoryContainer();
					synchronized (dispatchStoryContainer) {
						if (!dispatchStoryContainer.isEmpty()) {
							for (Passenger passenger : dispatchStoryContainer) {
								TransportationTask transportationTask = new TransportationTask(
										building, passenger);
								// passenger
								// .setTransportationState(transportationState.IN_PROGRESS);
								Thread threadPassenger = new Thread(
										transportationTask);
								groupThread.add(threadPassenger);
								threadPassenger.setName(PASSENGER_THREAD
										+ passenger.getPassengerID());
								threadPassenger.start();
								logger.info(PASSENGER2
										+ passenger.getPassengerID()
										+ threadPassenger.getName() + START
										+ passenger.getTransportationState());
							}
						}
					}
				}
				ElevatorTask elevatorTask = new ElevatorTask(building);
				Thread elevatorThread = new Thread(elevatorTask);
				groupThread.add(elevatorThread);
				elevatorThread.setName(ELEVATOR_THREAD);
				elevatorThread.start();
				logger.info(elevatorThread.getName() + START);
			}
		});
		startTask.setName(START_ELEVATION_THREAD);
		startTask.start();
		logger.info(START_ELEVATION + startTask.getName());
	}

	private void startTaskView() {
		transportationState = TransportationState.IN_PROGRESS;
		Thread presentation = new Thread(new Runnable() {

			public void run() {
				boolean progress = true;
				AbstractEvent event = null;
				try {
					while (progress) {
						event = DAOFactory.eventsSynchro.get();
						if (event.getTransportationActions() == TransportationActions.VALIDATION
								|| event.getTransportationActions() == TransportationActions.ABORTING_TRANSPORTATION) {
							progress = false;
						}
						DAOFactory.showerDAO.show(event);
						if (configReader.getAnimationBoost() > 0
								&& animationBoost != 0) {
							Thread.sleep(animationBoost);
						}
						DAOFactory.eventsSynchro.produce();
					}
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					logger.error(ex + INTERRUPTED_EXCEPTION);
				}
				transportationState = TransportationState.COMPLETED;
			}
		});
		presentation.setName("Presentation");
		presentation.start();
		logger.info(presentation.getName() + "start!");

		try {
			DAOFactory.eventsSynchro.set(new AbstractEvent(
					TransportationActions.STARTING_TRANSPORTATION));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			logger.error(ex + INTERRUPTED_EXCEPTION);
		}
	}

	public synchronized void cancel() {
		transportationState = TransportationState.ABORTED;

		try {
			DAOFactory.eventsSynchro.set(new AbstractEvent(
					TransportationActions.ABORTING_TRANSPORTATION));
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e + INTERRUPTED_EXCEPTION);
		}

		for (Thread thread : groupThread) {
			thread.interrupt();
		}
	}

}
