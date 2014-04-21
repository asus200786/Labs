package by.epam.epamlab.views;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import by.epam.epamlab.constants.Constants;

public class LogsUI extends AppenderSkeleton {

	private JTextArea jTextArea;
	private JScrollPane jScrollPane;
	
	public LogsUI(JTextArea jTextArea) {
		super();
		this.jTextArea = jTextArea;
		setLayout(new PatternLayout(Constants.PATTERN_LAYOUT));
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}
	
	public void setScrollPane(JScrollPane jScrollPane){
		this.jScrollPane = jScrollPane;
	}

	@Override
	protected void append(LoggingEvent event) {
		jTextArea.append(layout.format(event));
		int maxPane = jScrollPane.getVerticalScrollBar().getMaximum();
		// Sets the scrollbar's value.
		jScrollPane.getVerticalScrollBar().setValue(maxPane);		
	}

}
