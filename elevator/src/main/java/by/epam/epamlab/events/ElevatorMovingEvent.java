package by.epam.epamlab.events;

import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationActions;

public class ElevatorMovingEvent extends AbstractEvent {
	private final static String MESSAGE_HEADER = "Moving elevator";
	private final static String MESSAGE_BODY_FROM = "from storey #: ";
	private final static String MESSAGE_BODY_TO = "to storey #: ";
	private final static String MESSAGE_TAIL = ". Passenger's count:";

	public ElevatorMovingEvent(TransportationActions transportationActions,
			int countPassengers, int fromStorey, int toStorey) {
		super(transportationActions, MESSAGE_HEADER + MESSAGE_BODY_FROM
				+ fromStorey + MESSAGE_BODY_TO + toStorey + MESSAGE_TAIL
				+ countPassengers + Constants.DOT);
	}
}
