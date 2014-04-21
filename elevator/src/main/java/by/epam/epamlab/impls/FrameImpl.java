package by.epam.epamlab.impls;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.events.AbstractEvent;
import by.epam.epamlab.events.PassengerMovingEvent;
import by.epam.epamlab.interfaces.IShowerDAO;
import by.epam.epamlab.views.ElevatorView;

public class FrameImpl implements IShowerDAO {
	private static Logger logger = Logger.getLogger(FrameImpl.class.getName());
	private ElevatorView elevatorView;

	public FrameImpl(final Building building) {
		super();
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				elevatorView = new ElevatorView(building);
				elevatorView.setVisible(true);
			}
		});
	}
		
	public void show(AbstractEvent event) {
		if (event != null) {
			if (logger.isInfoEnabled()) {
				logger.info(event.getTransportationActions()
						+ Constants.SEPARATOR + event.getInfoEvent());
			}
		}
		switch (event.getTransportationActions()) {
		case BOARDING_OF_PASSENGER:
			PassengerMovingEvent boardingPassenger = (PassengerMovingEvent) event;
			elevatorView.getBuildingUI().movingPassengersAnimation(
					boardingPassenger);
			break;
		case DEBOARDING_OF_PASSENGER:
			PassengerMovingEvent deboardingPassenger = (PassengerMovingEvent) event;
			elevatorView.getBuildingUI().movingPassengersAnimation(
					deboardingPassenger);
			break;
		default:
			elevatorView.repaint();
			break;
		}
	}
	
}
