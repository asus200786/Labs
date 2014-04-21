package by.epam.epamlab.views;

import java.awt.Point;

public class Position {

	private Point point;
	private PassengerUI passengerUI;

	public Position(Point point, PassengerUI passengerUI) {
		super();
		this.point = point;
		this.passengerUI = passengerUI;
	}

	public Position(Point point) {
		super();
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public PassengerUI getPassengerUI() {
		return passengerUI;
	}

	public void setPassengerUI(PassengerUI passengerUI) {
		this.passengerUI = passengerUI;
	}

	public void cleaning() {
		this.passengerUI = null;
	}
}
