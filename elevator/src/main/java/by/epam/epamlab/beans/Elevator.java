package by.epam.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

import by.epam.epamlab.constants.Constants;

public class Elevator {

	private final int elevatorCapacity;
	private final int maxNumberStorey;
	private int currentNumberStorey;
	// maximum floor moving elevator. elevator can carry traffic on certain
	// segments of the building, if it is a skyscraper.
	private List<Passenger> elevatorContainer = new ArrayList<Passenger>();
	private Directions directionElevator = Directions.UP;

	public Elevator(int elevatorCapacity, int maxNumberStorey) {
		super();
		this.elevatorCapacity = elevatorCapacity;
		this.maxNumberStorey = maxNumberStorey - 1;
		// Need revision, if not lift goes from the first floor
		currentNumberStorey = Constants.FIRST_NUMBER_STOREY;
	}

	public Directions getDirectionElevator() {
		return directionElevator;
	}

	public void setDirectionElevator(Directions directionElevator) {
		this.directionElevator = directionElevator;
	}

	public int getElevatorCapacity() {
		return elevatorCapacity;
	}

	public int getCurrentNumberStorey() {
		return currentNumberStorey;
	}

	public void setCurrentNumberStorey(int currentNumberStorey) {
		this.currentNumberStorey = currentNumberStorey;
	}

	public List<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}

	public void setElevatorContainer(List<Passenger> elevatorContainer) {
		this.elevatorContainer = elevatorContainer;
	}

	public int getPassengersInElevator() {
		return elevatorContainer.size();
	}

	private void changeDirection() {
		if (currentNumberStorey == 0) {
			directionElevator = Directions.UP;
		} else {
			if (currentNumberStorey == maxNumberStorey)
				directionElevator = Directions.DOWN;
		}
	}

	public int toNextStorey() {
		switch (directionElevator) {
		case UP:
			upWard();
			break;
		case DOWN:
			downWard();
			break;
		default:
			break;
		}
		changeDirection();
		return currentNumberStorey;
	}

	private void downWard() {
		currentNumberStorey--;
	}

	private void upWard() {
		currentNumberStorey++;
	}

	public boolean isConsilienceDirectionMove(Passenger passenger) {
		Directions directionPassenger = passenger.getDirections();
		return directionPassenger == getDirectionElevator();
	}

	public boolean isAvailablePlace() {
		return elevatorContainer.size() < elevatorCapacity ? true : false;
	}

}
