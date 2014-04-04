package by.epam.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
	private int elevatorCapacity;
	private int currentNumberStorey;
	// maximum floor moving elevator. elevator can carry traffic on certain
	// segments of the building, if it is a skyscraper.
	private int maxNumberStorey;
	private List<Passenger> elevatorContainer = new ArrayList<Passenger>();
	private boolean downWard = false;

	private final int MIN_NUMER_STOREY = 1;

	public Elevator(int elevatorCapacity, int lastNumberStorey) {
		super();
		this.elevatorCapacity = elevatorCapacity;
		this.maxNumberStorey = maxNumberStorey - MIN_NUMER_STOREY;
		currentNumberStorey = 1;
		downWard = false;
	}
	
	

}
