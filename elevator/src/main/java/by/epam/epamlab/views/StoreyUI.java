package by.epam.epamlab.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import by.epam.epamlab.beans.Storey;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.interfaces.IGraphics2DUI;

public class StoreyUI implements IGraphics2DUI {

	public static final int CONTAINERS_WIDTH = 300;
	public static final int HEIGHT = 40;
	public static final int WIDTH = 30;
	private static int OFFSET_AXIS_Y = Constants.SPACE;

	private static final Font FONT = new Font("Serif", Font.CENTER_BASELINE,
			HEIGHT);
	private Storey storey;

	public final int offsetAxisY;
	private ContainerUI dispatchContainer;
	private ContainerUI destinationContainer;

	public StoreyUI(Storey storey, int widthElevator) {
		super();
		this.storey = storey;
		this.offsetAxisY = OFFSET_AXIS_Y;
		OFFSET_AXIS_Y += HEIGHT + Constants.SPACE;
		Point stationingDispatchContainer = new Point(2 * Constants.SPACE
				+ WIDTH, offsetAxisY);
		this.dispatchContainer = new ContainerUI(
				storey.getDispatchStoryContainer(),
				stationingDispatchContainer, CONTAINERS_WIDTH);
		Point stationingDestinationContainer = new Point(4 * Constants.SPACE
				+ WIDTH + CONTAINERS_WIDTH + widthElevator, offsetAxisY);
		this.destinationContainer = new ContainerUI(
				storey.getArrivalStoryContainer(),
				stationingDestinationContainer, CONTAINERS_WIDTH);
	}

	public Storey getStorey() {
		return storey;
	}

	public ContainerUI getDispatchContainer() {
		return dispatchContainer;
	}

	public ContainerUI getDestinationContainer() {
		return destinationContainer;
	}

	public void painting(Graphics2D graphics2d) {
		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.setFont(FONT);
		graphics2d.drawRect(Constants.SPACE, offsetAxisY, WIDTH, HEIGHT);
		graphics2d.drawString(String.valueOf(storey.getNumberStorey()),
				Constants.SPACE, offsetAxisY + HEIGHT - Constants.SPACE);
		dispatchContainer.painting(graphics2d);
		destinationContainer.painting(graphics2d);
	}
}
