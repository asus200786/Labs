package by.epam.epamlab.controllers;

import by.epam.epamlab.conctants.Constants;

public class RandomInitTaskElevator extends InitTaskElevator {

	private final int STORIES_NUMBER_DEFAULT_INT = Integer
			.parseInt(Constants.STORIES_NUMBER_DEFAULT);
	private final int PASSENGERS_NUMBER_DEFAULT_INT = Integer
			.parseInt(Constants.PASSENGERS_NUMBER_DEFAULT);
	private final int ELEVATOR_CAPACITY_DEFAULT_INT = Integer
			.parseInt(Constants.ELEVATOR_CAPACITY_DEFAULT);

	public RandomInitTaskElevator(int maxNumberStorey, int passengerNumber,
			int elevatorCapacity) {
		super(maxNumberStorey, passengerNumber, elevatorCapacity);

		checkSourceDataAndSetUp(maxNumberStorey, passengerNumber,
				elevatorCapacity);

	}

	// Checking the source data. Application to work in case of incorrect
	// parameter value assignment, I assigned the default value.
	private void checkSourceDataAndSetUp(int maxNumberStorey,
			int passengerNumber, int elevatorCapacity) {
		if (maxNumberStorey < STORIES_NUMBER_DEFAULT_INT) {
			maxNumberStorey = STORIES_NUMBER_DEFAULT_INT;
		}
		if (passengerNumber < PASSENGERS_NUMBER_DEFAULT_INT) {
			passengerNumber = PASSENGERS_NUMBER_DEFAULT_INT;
		}

		if (elevatorCapacity < ELEVATOR_CAPACITY_DEFAULT_INT) {
			elevatorCapacity = ELEVATOR_CAPACITY_DEFAULT_INT;
		}
	}
	
	
}
