package by.epam.epamlab.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import by.epam.epamlab.beans.Passenger;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.interfaces.IGraphics2DUI;

public class PassengerUI implements IGraphics2DUI {

	private static final Font FONT_1 = new Font("Serif", Font.BOLD, 14);
	private static final Font FONT_2 = new Font("Serif", Font.BOLD, 12);
	public static final int HEIGTH = 30;
	public static final int WIDTH = 20;

	private Passenger passenger;
	private int passengerId;
	private int destinationStorey;
	private Point point;

	public PassengerUI(Passenger passenger, Point point) {
		super();
		this.passenger = passenger;
		this.passengerId = passenger.getPassengerID();
		this.destinationStorey = passenger.getDestinationStorey();
		this.point = point;
	}

	public PassengerUI(Passenger passenger) {
		this(passenger, null);
	}
	
	public Passenger getPassenger() {
		return passenger;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void painting(Graphics2D graphics2d) {
		graphics2d.setColor(Color.GRAY);
		graphics2d.drawRect(point.x, point.y, WIDTH, HEIGTH);
		graphics2d.setColor(Color.RED);
		graphics2d.setFont(FONT_1);
		graphics2d.drawString(String.valueOf(passengerId), point.x + 2, point.y
				+ StoreyUI.HEIGHT - 3 * Constants.SPACE);
		graphics2d.setColor(Color.GREEN);
		graphics2d.setFont(FONT_2);
		graphics2d.drawString(String.valueOf(destinationStorey), point.x + 2,
				point.y + StoreyUI.HEIGHT / 2 - 2 * Constants.SPACE);

	}

}
