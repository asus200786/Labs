package by.epam.epamlab.impls;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.events.AbstractEvent;
import by.epam.epamlab.interfaces.IShowerDAO;

public class ConsoleImpl implements IShowerDAO {
	private static Logger logger = Logger
			.getLogger(ConsoleImpl.class.getName());

	public void show(AbstractEvent event) {
		if (event != null) {
			switch (event.getTransportationActions()) {
			default:
				if (logger.isInfoEnabled()) {
					logger.info(event.getTransportationActions()
							+ Constants.SEPARATOR + event.getInfoEvent());
				}
				break;
			}
		}
	}

	public ConsoleImpl(final Building building) {
		super();
		new Thread (new Runnable() {
			
			public void run() {
				building.startTask();
			}
		}).start();
	}
	
	

}
