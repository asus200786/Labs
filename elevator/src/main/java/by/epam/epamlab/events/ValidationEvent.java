package by.epam.epamlab.events;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.beans.Passenger;
import by.epam.epamlab.beans.Storey;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationActions;
import by.epam.epamlab.controllers.TransportationState;

public class ValidationEvent extends AbstractEvent {
	private static Logger logger = Logger.getLogger(ValidationEvent.class
			.getName());

	private Building building;

	private static final String MESSAGE_OK_VALIDATION = "Validation was successful.";
	private static final String MESSAGE_FALSE_VALIDATION = "Validation are unsuccessful.";
	private static final String STOREY = "Storey №";
	private static final String PASSENGER = "Arrived passenger # ";
	private static final String DISPATCH_CONTAINER = "Dispatch container is ";
	private static final String ELEVATOR_CONTAINER = "Elevator container is ";
	private static final String DESTINATION_CONTAINER = "The number of passengers in the arriving container: ";
	private static final String EMPTY = "empty. ";
	private static final String NOT = "not";
	private static final String VALIDATION_STATE = "Validation state is ";

	public ValidationEvent(TransportationActions transportationActions,
			Building building) {
		super(transportationActions, Constants.EMPTY_STRING);
		this.building = building;
	}

	// For the validation I use type StringBuffer as it is thread safe and
	// synchronized.
	public StringBuffer validation() {
		boolean validation = true;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Starting validation.\n");

		// variable "count" - To summarize arriving passengers
		int count = 0;
		for (Storey storey : building.getStoreys()) {
			// Verification 1. All containers of departure
			// (dispatchStoryContainer)
			// must be empty.
			if (storey.getDispatchStoryContainer().size() == 0) {
				stringBuffer.append(STOREY + storey.getNumberStorey()
						+ DISPATCH_CONTAINER + EMPTY + MESSAGE_OK_VALIDATION);
			} else {
				validation = false;
				stringBuffer.append(STOREY + storey.getNumberStorey()
						+ DISPATCH_CONTAINER + NOT + EMPTY
						+ MESSAGE_FALSE_VALIDATION);
			}
			// Verification 2. Containers arriving (arrivalStoryContainer) in
			// all people who are there,
			// transportationState must be COMPLETED and destination floor
			// (destinationStory) must coincide with
			// floor room, with which is associated a corresponding
			// arrivalStoryContainer
			if (storey.getArrivalStoryContainer().size() > 0) {
				count += storey.getArrivalStoryContainer().size();
				stringBuffer.append(DESTINATION_CONTAINER + count
						+ Constants.DOT);
				// Validation coincidences storey arrival and current storey,
				// and passenger's state.
				for (Passenger passenger : storey.getArrivalStoryContainer()) {
					if (passenger.getTransportationState() == TransportationState.COMPLETED
							| passenger.getDestinationStorey() == storey
									.getNumberStorey()) {
						stringBuffer.append(PASSENGER
								+ passenger.getPassengerID()
								+ MESSAGE_OK_VALIDATION);
					} else {
						validation = false;
						stringBuffer.append(MESSAGE_FALSE_VALIDATION + NOT
								+ PASSENGER);
					}
				}
				stringBuffer.append("\n");
			}
		}
		// Verification 3. Container elevator must be empty.
		if (building.getElevator().getElevatorContainer().size() == 0) {
			stringBuffer.append(ELEVATOR_CONTAINER + EMPTY + "\n");
			stringBuffer.append(MESSAGE_OK_VALIDATION + "\n");
		} else {
			validation = false;
			stringBuffer.append(ELEVATOR_CONTAINER + NOT + EMPTY + "\n");
			stringBuffer.append(MESSAGE_FALSE_VALIDATION);
		}
		// Result of the validation.
		if (validation) {
			stringBuffer.append(VALIDATION_STATE + validation);
		}
		// Add info in the logfile.
		if (logger.isInfoEnabled()) {
			logger.info(stringBuffer.toString());
		}
		return stringBuffer;
	}

}
