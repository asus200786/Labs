package by.epam.epamlab.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.beans.Passenger;
import by.epam.epamlab.beans.Storey;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationState;
import by.epam.epamlab.events.PassengerMovingEvent;
import by.epam.epamlab.interfaces.IGraphics2DUI;

public class BuildingUI extends JPanel {

	private static final long serialVersionUID = 201404181850L;

	private Building building;

	private List<IGraphics2DUI> graphics2DList = new ArrayList<IGraphics2DUI>();
	private List<StoreyUI> storeysUI = new ArrayList<StoreyUI>();
	private BufferedImage bufferedImage;

	private ContainerUI elevatorUI;

	public BuildingUI(Building building) {
		super();
		this.building = building;
		init();
	}

	private void init() {
		// Removes all of the elements from this list. The list will be empty
		// after this call returns.
		graphics2DList.clear();

		int widthElevator = building.getConfigReader().getElevatorCapacity()
				* (PassengerUI.WIDTH + Constants.SPACE) + Constants.SPACE;
		Storey storey;
		for (int i = building.LAST_STOREY; i >= building.FIRST_STOREY; i--) {
			storey = building.getStoreys().get(i);
			StoreyUI storeyUI = new StoreyUI(storey, widthElevator);
			graphics2DList.add(storeyUI);
			storeysUI.add(storeyUI);
		}
		Point stationingElevator = new Point(Constants.SPACE * 3
				+ StoreyUI.WIDTH + StoreyUI.CONTAINERS_WIDTH, 0);
		elevatorUI = new ContainerUI(building.getElevator()
				.getElevatorContainer(), stationingElevator, widthElevator);
	}

	private void rebuildBuffer() {
		int widthImage = getWidth();
		int heightImage = getHeight();
		bufferedImage = new BufferedImage(widthImage, heightImage,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2d = bufferedImage.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (IGraphics2DUI graphics2dui : graphics2DList) {
			graphics2dui.painting(graphics2d);
		}
		elevatorUI.changeAxisY(getPositionElevator());
		elevatorUI.painting(graphics2d);
		graphics2d.dispose();
	}

	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		rebuildBuffer();
		graphics.drawImage(bufferedImage, 0, 0, this);
	}

	private int getPositionElevator() {
		int currentStorey = building.getElevator().getCurrentNumberStorey();
		int y = 0;
		StoreyUI storeyUI = getStoreyUI(currentStorey);
		if (storeyUI != null) {
			y = storeyUI.offsetAxisY;
		}
		return y;
	}

	private StoreyUI getStoreyUI(int currentStorey) {
		for (StoreyUI storeyUI : storeysUI) {
			if (storeyUI.getStorey().getNumberStorey() == currentStorey) {
				return storeyUI;
			}
		}
		return null;
	}

	public List<StoreyUI> getStoreysUI() {
		return storeysUI;
	}

	public ContainerUI getElevatorUI() {
		return elevatorUI;
	}

	public void movingPassengersAnimation(
			PassengerMovingEvent passengerMovingEvent) {
		ContainerUI from;
		ContainerUI to;
		int currentStorey = passengerMovingEvent.getCurrentStorey();
		Passenger passenger = passengerMovingEvent.getPassenger();
		Point fromPoint = null;
		Point toPoint = null;
		switch (passengerMovingEvent.getTransportationActions()) {
		case BOARDING_OF_PASSENGER:
			from = getStoreyUI(currentStorey).getDispatchContainer();
			to = elevatorUI;
			break;
		case DEBOARDING_OF_PASSENGER:
			from = elevatorUI;
			to = getStoreyUI(currentStorey).getDestinationContainer();
			break;
		default:
			throw new IllegalArgumentException();
		}
		PassengerUI passengerUI = from.getPassengerUI(passenger);
		fromPoint = passengerUI.getPoint();
		from.removePAssengerUI(passengerUI);
		to.addPassengerUI(passengerUI);
		toPoint = passengerUI.getPoint();
		passengerUI.setPoint(fromPoint);

		while (fromPoint.x <= toPoint.x
				&& building.getTransportationState() != TransportationState.ABORTED) {
			fromPoint.x++;
			try {
				Thread.sleep(building.getAnimationBoost() / 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}
