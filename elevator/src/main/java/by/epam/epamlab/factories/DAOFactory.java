package by.epam.epamlab.factories;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.impls.BufferSynchronization;
import by.epam.epamlab.impls.ConsoleImpl;
import by.epam.epamlab.impls.FrameImpl;
import by.epam.epamlab.interfaces.IShowerDAO;

public class DAOFactory {
	public static IShowerDAO showerDAO;

	public final static BufferSynchronization eventsSynchro = new BufferSynchronization();

	public static void initShower(Building building) {
		IShowerDAO iShowerDAO;
		int animationBoost = building.getConfigReader().getAnimationBoost();
		switch (animationBoost) {
		case 0:
			iShowerDAO = new ConsoleImpl(building);
			break;
		default:
			iShowerDAO = new FrameImpl(building);
			break;
		}
		showerDAO = iShowerDAO;
	}
}
