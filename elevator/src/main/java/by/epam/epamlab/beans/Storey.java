package by.epam.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Storey extends Location {
	
	private List<Passenger> dispatchStoryContainer = new ArrayList<Passenger>();
	private List<Passenger> arrivalStoryContainer = new ArrayList<Passenger>();
	
	
	//
	// private ElevatorShaft elevatorShaft;
	//
	// public Storey(String name) {
	// setLocationName(name);
	// }
	//
	// @Override
	// public Button getButton() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public Door getDoor() {
	// // TODO Auto-generated method stub
	// return null;
	// }
}
