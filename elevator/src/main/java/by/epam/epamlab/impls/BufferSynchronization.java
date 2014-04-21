package by.epam.epamlab.impls;

import by.epam.epamlab.events.AbstractEvent;

public class BufferSynchronization {

	private AbstractEvent event;
	private boolean waitingRepaint = true;
	private boolean empty = true;

	public synchronized AbstractEvent get() throws InterruptedException {
		while (empty) {
			wait();
		}
		empty = true;
		return event;
	}

	public synchronized void set(AbstractEvent event)
			throws InterruptedException {
		while (!empty) {
			wait();
		}
		empty = false;
		this.event = event;
		notify();
		while (waitingRepaint) {
			wait();
		}
		waitingRepaint = true;
	}

	public synchronized void produce() {
		waitingRepaint = false;
		notify();
	}
}
