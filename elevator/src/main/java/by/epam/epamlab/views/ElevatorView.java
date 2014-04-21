package by.epam.epamlab.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationState;
import by.epam.epamlab.impls.FrameImpl;
import by.epam.epamlab.readers.ConfigReader;

public class ElevatorView extends JFrame {
	private static final long serialVersionUID = 201404181646L;

	private static final String FONT_TIMES_NEW_ROMAN = "Times New Roman";
	private static final String ELEVATOR = "Elevator";
	private static final String POWS_MIG_LAYOUT = "[242.00,grow][38.00,grow][37.00][][70.00,grow]";
	private static final String COLUMNS_MIG_LAYOUT = "[146.00px,grow][157.00px,grow][147.00px,grow]";
	private static final String BUILDING_AREA = "cell 0 0 3 1,grow";
	private static final String NUMBER_PASSENGERS = "Number Passengers";
	private static final String PASSENGERS_NUMBER_LABEL_FIELD_AREA = "flowx,cell 0 1,alignx left,aligny center";
	private static final String PASSENGERS_NUMBER_TEXT_FIELD_AREA = "cell 0 1";
	private static final String NUMBER_STORIES = "Number Stories";
	private static final String STOREYS_NUMBER_LABEL_FIELD_AREA = "flowx,cell 1 1,alignx left,aligny center";
	private static final String STOREYS_NUMBER_TEXT_FIELD_AREA = "cell 1 1";
	private static final String ELEVATOR_CAPACITY_LABEL_FIELD_AREA = "flowx,cell 0 2,aligny center";
	private static final String ELEVATOR_CAPACITY_TEXT_FIELD_AREA = "cell 0 2";
	private static final String ANIMATION_BOOST = "Animation Boost";
	private static final String ANIMATION_BOOST_LABEL_FIELD_AREA = "flowx,cell 1 2,aligny center";
	private static final String ANIMATION_BOOST_TEXT_FIELD_AREA = "cell 1 2";
	private static final String FONT_NIRMALA_UI = "Nirmala UI";
	private static final String BUTTON_AREA = "cell 2 1 1 2,growx,aligny center";
	private static final String BUTTON_START = "Start";
	private static final String BUTTON_ABORT = "Abort";
	private static final String SLIDER_AREA = "cell 1 3,alignx left,aligny center";
	private static final String LOGS_PANEL_AREA_SCROLL = "cell 0 4 3 1,grow";

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	private BuildingUI buildingUI;
	private JButton btnStartButton;
	private JTextField textField_ElevatorCapacity;
	private JTextField textField_AnimationBoost;
	private JTextField textField_StoreysNumber;
	private JTextField textField_PassengersNumber;
	private JTextArea logs;

	private Building building;

	/**
	 * Create the frame.
	 */
	public ElevatorView(final Building building) {
		super();
		this.building = building;
		getContentPane().setForeground(SystemColor.textHighlight);
		getContentPane().setFont(
				new Font(FONT_TIMES_NEW_ROMAN, Font.BOLD | Font.ITALIC, 13));
		getContentPane().setEnabled(false);
		getContentPane().setBackground(SystemColor.controlHighlight);
		setTitle(ELEVATOR);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				new Thread(new Runnable() {

					public void run() {
						building.cancel();
					}
				}).start();
				;
			}
		});
		createAndShowGUI();
	}

	public BuildingUI getBuildingUI() {
		return buildingUI;
	}

	/*
	 * Create the GUI and show it.For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		addComponentsToPane(getContentPane());

		// Display the window.
		pack();
		setVisible(true);
	}

	public void addComponentsToPane(Container pane) {

		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}

		getContentPane().setLayout(
				new MigLayout(Constants.EMPTY_STRING, COLUMNS_MIG_LAYOUT,
						POWS_MIG_LAYOUT));

		// Building GUI
		buildingUI = new BuildingUI(building);

		buildingUI.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0,
				0, 0)));
		buildingUI.setBackground(Color.WHITE);
		buildingUI.setPreferredSize(new Dimension(900, 500));
		JScrollPane buildingJScrollPane = new JScrollPane(buildingUI);
		getContentPane().add(buildingJScrollPane, BUILDING_AREA);

		// config info
		ConfigReader configReader = building.getConfigReader();

		JLabel lblNumberPassengers = new JLabel(NUMBER_PASSENGERS);
		getContentPane().add(lblNumberPassengers,
				PASSENGERS_NUMBER_LABEL_FIELD_AREA);
		textField_PassengersNumber = new JTextField(String.valueOf(configReader
				.getPassengersNumber()));
		textField_PassengersNumber.setEditable(false);
		getContentPane().add(textField_PassengersNumber,
				PASSENGERS_NUMBER_TEXT_FIELD_AREA);
		textField_PassengersNumber.setColumns(5);

		JLabel lblStoreysNumber = new JLabel(NUMBER_STORIES);
		getContentPane().add(lblStoreysNumber, STOREYS_NUMBER_LABEL_FIELD_AREA);
		textField_StoreysNumber = new JTextField(String.valueOf(configReader
				.getStoreysNumber()));
		textField_StoreysNumber.setEditable(false);
		getContentPane().add(textField_StoreysNumber,
				STOREYS_NUMBER_TEXT_FIELD_AREA);
		textField_StoreysNumber.setColumns(3);

		JLabel lblElevatorCapacity = new JLabel("Elevator Capacity");
		getContentPane().add(lblElevatorCapacity,
				ELEVATOR_CAPACITY_LABEL_FIELD_AREA);
		textField_ElevatorCapacity = new JTextField(String.valueOf(configReader
				.getElevatorCapacity()));
		textField_ElevatorCapacity.setEditable(false);
		getContentPane().add(textField_ElevatorCapacity,
				ELEVATOR_CAPACITY_TEXT_FIELD_AREA);
		textField_ElevatorCapacity.setColumns(2);

		JLabel lblAnimationBoost = new JLabel(ANIMATION_BOOST);
		getContentPane().add(lblAnimationBoost,
				ANIMATION_BOOST_LABEL_FIELD_AREA);
		textField_AnimationBoost = new JTextField(String.valueOf(configReader
				.getAnimationBoost()));
		textField_AnimationBoost.setEditable(false);
		getContentPane().add(textField_AnimationBoost,
				ANIMATION_BOOST_TEXT_FIELD_AREA);
		textField_AnimationBoost.setColumns(2);
		// Button
		btnStartButton = new JButton(BUTTON_START);
		btnStartButton.setForeground(Color.RED);
		btnStartButton.setBackground(SystemColor.windowBorder);
		btnStartButton.setFont(new Font(FONT_NIRMALA_UI, Font.BOLD
				| Font.ITALIC, 11));
		getContentPane().add(btnStartButton, BUTTON_AREA);
		btnStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (BUTTON_START.equals(btnStartButton.getText())) {
					btnStartButton.setText(BUTTON_ABORT);
					building.startTask();
				} else {
					if (building.getTransportationState() == TransportationState.IN_PROGRESS) {
						building.cancel();
					}
				}
			}
		});

		getContentPane()
				.setFocusTraversalPolicy(
						new FocusTraversalOnArray(new Component[] {
								lblNumberPassengers, lblStoreysNumber,
								btnStartButton }));

		JSlider slider = new JSlider();
		slider.setForeground(Color.DARK_GRAY);
		slider.setBorder(new LineBorder(Color.WHITE));
		slider.setValue(configReader.getAnimationBoost());
		slider.setMaximum(10);
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				if (building != null) {
					int value = ((JSlider) changeEvent.getSource()).getValue();
					building.setAnimationBoost(value);
				}
			}
		});
		getContentPane().add(slider, SLIDER_AREA);
		// logs area
		logs = new JTextArea(5, 60);
		// text component should not be editable.
		logs.setEditable(false);
		JScrollPane logsPanel = new JScrollPane(logs);
		getContentPane().add(logsPanel, LOGS_PANEL_AREA_SCROLL);
		LogsUI logsAppend = new LogsUI(logs);
		logsAppend.setScrollPane(logsPanel);
		Logger logger = Logger.getLogger(FrameImpl.class);
		logger.addAppender(logsAppend);
	}
}
