package by.epam.epamlab.views;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import by.epam.epamlab.beans.Passenger;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.interfaces.IGraphics2DUI;

public class ContainerUI implements IGraphics2DUI {

	public static final int HEIGHT = StoreyUI.HEIGHT;
	public final int width;
	private Point point;
	private int offsetAxisX;

	private List<Position> pointsPositions = new LinkedList<Position>();
	private List<PassengerUI> passengersListUI = new ArrayList<PassengerUI>();

	public ContainerUI(List<Passenger> main, Point stationing, int width) {
		super();
		this.point = stationing;
		offsetAxisX = point.x + Constants.SPACE;
		this.width = width;
		// Calculation of the required width of containers for passengers
		int countPoint = width / (PassengerUI.WIDTH + Constants.SPACE);
		for (int i = 1; i <= countPoint; i++) {
			Position position = new Position(new Point(getOffsetAxisX(),
					point.y + Constants.SPACE));
			pointsPositions.add(position);
		}
		// Adding passenger UI in the content
		Passenger passenger;
		for (int i = 0; i < main.size(); i++) {
			passenger = main.get(i);
			addPassengerUI(new PassengerUI(passenger));
		}
	}

	public PassengerUI getPassengerUI(Passenger passenger) {
		for (PassengerUI passengerUI : passengersListUI) {
			if (passengerUI.getPassenger().equals(passenger)) {
				return passengerUI;
			}
		}
		return null;
	}

	// obtaining X Offset
	private int getOffsetAxisX() {
		int thisOffsetAxisX = offsetAxisX;
		offsetAxisX += Constants.SPACE + PassengerUI.WIDTH;
		return thisOffsetAxisX;
	}

	public void changeAxisY(int y) {
		for (PassengerUI passengerUI : passengersListUI) {
			passengerUI.getPoint().y = y + Constants.SPACE;
		}
		point.y = y;
	}

	public void addPassengerUI(PassengerUI passengerUI) {
		passengersListUI.add(passengerUI);
		Point pointPosition = null;
		for (Position position : pointsPositions) {
			if (position.getPassengerUI() == null) {
				pointPosition = position.getPoint();
				passengerUI.setPoint(new Point(pointPosition));
				position.setPassengerUI(passengerUI);
				break;
			}
		}
		if (pointPosition == null) {
			pointPosition = pointsPositions.get(0).getPoint();
			pointPosition.x = 24;
			passengerUI.setPoint(new Point(pointPosition));
		}
	}

	public void removePAssengerUI(PassengerUI passengerUI) {
		passengersListUI.remove(passengerUI);
		for (Position position : pointsPositions) {
			if (position.getPassengerUI() == passengerUI) {
				position.cleaning();
			}
		}
	}

	public void painting(Graphics2D graphics2d) {
		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.drawRect(point.x, point.y, width, HEIGHT);
		for (PassengerUI passengerUI : passengersListUI) {
			passengerUI.painting(graphics2d);
		}
	}

}
