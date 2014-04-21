package by.epam.epamlab.events;

import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationActions;

public class AbstractEvent {
	private TransportationActions transportationActions;
	private String infoEvent;

	public AbstractEvent(TransportationActions transportationActions,
			String infoEvent) {
		super();
		this.transportationActions = transportationActions;
		this.infoEvent = infoEvent;
	}

	public AbstractEvent(TransportationActions transportationActions) {
		this(transportationActions, Constants.EMPTY_STRING);
	}

	public TransportationActions getTransportationActions() {
		return transportationActions;
	}

	public String getInfoEvent() {
		return infoEvent;
	}

}
