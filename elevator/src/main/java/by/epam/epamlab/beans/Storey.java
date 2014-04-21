package by.epam.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Storey { 
	private final int numberStorey;

	private List<Passenger> dispatchStoryContainer = new ArrayList<Passenger>();
	private List<Passenger> arrivalStoryContainer = new ArrayList<Passenger>();

	public Storey(int numberStorey) {
		super();
		this.numberStorey = numberStorey;
	}

	public int getNumberStorey() {
		return numberStorey;
	}

	public List<Passenger> getDispatchStoryContainer() {
		return dispatchStoryContainer;
	}

	public void setDispatchStoryContainer(List<Passenger> dispatchStoryContainer) {
		this.dispatchStoryContainer = dispatchStoryContainer;
	}

	public List<Passenger> getArrivalStoryContainer() {
		return arrivalStoryContainer;
	}

	public void setArrivalStoryContainer(List<Passenger> arrivalStoryContainer) {
		this.arrivalStoryContainer = arrivalStoryContainer;
	}

}
