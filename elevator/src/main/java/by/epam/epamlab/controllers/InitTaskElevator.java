package by.epam.epamlab.controllers;

import java.util.ArrayList;
import java.util.List;

import by.epam.epamlab.beans.Elevator;
import by.epam.epamlab.beans.Storey;

public class InitTaskElevator {
	protected int maxNumberStorey;
	protected int passengerNumber;
	protected int animationBoost;

	private Elevator elevator;
	private List<Storey> storeys = null;

	public int getMaxNumberStorey() {
		return maxNumberStorey;
	}

	public int getPassengerNumber() {
		return passengerNumber;
	}

	public int getAnimationBoost() {
		return animationBoost;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public List<Storey> getStoreys() {
		return storeys;
	}

	public InitTaskElevator(int maxNumberStorey, int passengerNumber,
			int elevatorCapacity) {
		super();
		this.maxNumberStorey = maxNumberStorey;
		this.passengerNumber = passengerNumber;

		elevator = new Elevator(elevatorCapacity, maxNumberStorey);
		storeys = new ArrayList<Storey>();

		for (int i = 0; i < maxNumberStorey; i++) {
			Storey storey = new Storey();
			storeys.add(storey);
		}
	}

	

}
