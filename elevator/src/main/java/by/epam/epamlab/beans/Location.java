package by.epam.epamlab.beans;

public abstract class Location {
	
	private String locationName;
	
	protected void setLocationName (String name){
		locationName = name;
	}
	
	public String getLocationName() {
		return locationName;
	}
//
//	public abstract Button getButton();
//	
//	public abstract Door getDoor();
//	
}
