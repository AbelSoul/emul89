import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * Defines a GUI that displays details of the emulator and contains buttons
 * enabling access to the required functionality.
 * 
 * @author Robert Wilson
 * 
 */
public class EmuGUI extends JFrame implements ActionListener {

	/** GUI JButtons */
	private JButton loadButton, runButton, saveButton, stepButton, clearButton,
			stopButton, modifyMemoryButton;

	/** GUI JTextAreas for code input and display */
	private JTextArea codeIn, displayOutput;

	/** GUI JMenuBar */
	private JMenuBar menuBar;

	/** GUI JMenu */
	private JMenu fileMenu, helpMenu, recentMenu;

	/** GUI JMenuItems */
	private JMenuItem openMenuItem, openUserGuide;

	/** An instance of the Memory class */
	private Memory mem, resetVect1, resetVect2;
	private Memory[] memLocList;

	/** An instance of the MemoryArray class */
	private MemoryArray memArray;

	/** An instance of the Instructions class */
	private Instructions instructionsClass;

	/** An instance of the CCR class */
	private CCR ccrFlags;

	/** An instance of the userGuide class */
	private UserGuide guideWindow;

	/** An instance of the Processor class */
	private Processor proc;

	/** An instance of the S_Record class */
	private S_Record sRec;

	/** class constant representing the number of memory locations */
	private final int MEM_LOCATIONS = 65536;

	/** String representing memory address to modify */
	private String addressToModify;

	/** Integers representing the OpCode and Address */
	private int opCode, address, memAddress;

	/** StringBuilder object for displaying memory */
	private StringBuilder mList;

	/** Tab pane for display */
	private JTabbedPane tabs;

	/** Text fields for registers */
	private JTextField pcField, xField, yField, aField, bField, dField,
			usField, hsField, dpField;

	private String text;

	/** focus listener for code entry area */
	private FocusListener focusListener;

	public EmuGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Motorola MC6809 emulator");
		setSize(1024, 680);
		setLocation(10, 0);

		// layoutTop();
		layoutRight();
		layoutLeft();
		layoutBottom();
		layoutCenter();

		// Menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu bar headings and sub-menus
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		recentMenu = new JMenu("Recently opened files");
		fileMenu.add(recentMenu);

		// Menu items
		openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		openMenuItem.addActionListener(this);
		openUserGuide = new JMenuItem("User Guide");
		helpMenu.add(openUserGuide);
		openUserGuide.addActionListener(this);

		initEmu();
		// updateDisplay();
	}

	/**
	 * method to initialise emulator
	 */
	public void initEmu() {

		// create new memory object
		mem = new Memory();

		// create new memory array
		memArray = new MemoryArray();
	}

	/**
	 * adds top elements to top of GUI
	 */
	// public void layoutTop() {
	// JPanel top = new JPanel();
	// top.setBackground(new java.awt.Color(90, 244, 255));
	// JLabel code = new JLabel("Machine code   ");
	// top.add(code, BorderLayout.WEST);
	//
	// JLabel displayArea = new JLabel("   Display area   ");
	// top.add(displayArea, BorderLayout.CENTER);
	//
	// JLabel status = new JLabel("   CCR status flags");
	// top.add(status, BorderLayout.EAST);
	// this.add(top, BorderLayout.NORTH);
	// }

	/**
	 * adds right hand side elements to right of GUI
	 */
	public void layoutRight() {
		JPanel right = new JPanel();
		right.setBackground(new java.awt.Color(200, 244, 255));
		add(right, BorderLayout.EAST);

		// Group layout
		GroupLayout layout = new GroupLayout(right);
		right.setLayout(layout);

		// automatic gap insertion
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// define components
		JLabel statusLabel = new JLabel("Status register");
		JLabel pcLabel = new JLabel("Program counter");
		pcField = new JTextField(16);
		pcField.setText("-0-0-0-0-0-0-0-0-");
		JLabel xLabel = new JLabel("Index X");
		xField = new JTextField(16);
		xField.setText("-0-0-0-0-0-0-0-0-");
		JLabel yLabel = new JLabel("Index Y");
		yField = new JTextField(16);
		yField.setText("-0-0-0-0-0-0-0-0-");
		JLabel aLabel = new JLabel("Accumulator A");
		aField = new JTextField(16);
		aField.setText("-0-0-0-0-0-0-0-0-");
		JLabel bLabel = new JLabel("Accumulator B");
		bField = new JTextField(16);
		bField.setText("-0-0-0-0-0-0-0-0-");
		JLabel dLabel = new JLabel("Accumulator D");
		dField = new JTextField(24);
		dField.setText("-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-");
		JLabel hsLabel = new JLabel("Hardware Stack");
		hsField = new JTextField(16);
		hsField.setText("-0-0-0-0-0-0-0-0-");
		JLabel usLabel = new JLabel("User Stack");
		usField = new JTextField(16);
		usField.setText("-0-0-0-0-0-0-0-0-");
		JLabel dpLabel = new JLabel("Direct Page");
		dpField = new JTextField(16);
		dpField.setText("-0-0-0-0-0-0-0-0-");

		/**
		 * JPanel for CCR status flags
		 */
		JPanel ccrPanel = new JPanel();
		ccrPanel.setBackground(Color.LIGHT_GRAY);
		JTextField E = new JTextField(1);
		E.setEditable(false);
		JLabel Elabel = new JLabel("E");
		ccrPanel.add(Elabel);
		ccrPanel.add(E);
		JTextField F = new JTextField(1);
		F.setEditable(false);
		JLabel Flabel = new JLabel("F");
		ccrPanel.add(Flabel);
		ccrPanel.add(F);
		JTextField H = new JTextField(1);
		H.setEditable(false);
		JLabel Hlabel = new JLabel("H");
		ccrPanel.add(Hlabel);
		ccrPanel.add(H);
		JTextField I = new JTextField(1);
		I.setEditable(false);
		JLabel Ilabel = new JLabel("I");
		ccrPanel.add(Ilabel);
		ccrPanel.add(I);
		JTextField N = new JTextField(1);
		JLabel Nlabel = new JLabel("N");
		N.setEditable(false);
		ccrPanel.add(Nlabel);
		ccrPanel.add(N);
		JTextField Z = new JTextField(1);
		JLabel Zlabel = new JLabel("Z");
		Z.setEditable(false);
		ccrPanel.add(Zlabel);
		ccrPanel.add(Z);
		JTextField V = new JTextField(1);
		JLabel Vlabel = new JLabel("V");
		V.setEditable(false);
		ccrPanel.add(Vlabel);
		ccrPanel.add(V);
		JTextField C = new JTextField(1);
		JLabel Clabel = new JLabel("C");
		C.setEditable(false);
		ccrPanel.add(Clabel);
		ccrPanel.add(C);
		ccrPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("CCR flags"));

		// condition of flags
		String flagSet = "1";
		String unset = "0";
		ccrFlags = new CCR();

		// E bit flag
		if (ccrFlags.eBit() == false) {
			E.setText(unset);
		} else
			E.setText(flagSet);

		// F bit flag
		if (ccrFlags.fBit() == false) {
			F.setText(unset);
		} else
			F.setText(flagSet);

		// H bit flag
		if (ccrFlags.hBit() == false) {
			H.setText(unset);
		} else
			H.setText(flagSet);

		// I bit flag
		if (ccrFlags.iBit() == false) {
			I.setText(unset);
		} else
			I.setText(flagSet);

		// N bit flag
		if (ccrFlags.nBit() == false) {
			N.setText(unset);
		} else
			N.setText(flagSet);

		// Z bit flag
		if (ccrFlags.zBit() == false) {
			Z.setText(unset);
		} else if (ccrFlags.zBit()) {
			Z.setText(flagSet);
			System.out.println("Z flag is set");
		}

		// V bit flag
		if (ccrFlags.cBit() == false) {
			V.setText(unset);
		} else
			V.setText(flagSet);

		// C bit flag
		if (ccrFlags.cBit() == false) {
			C.setText(unset);
		} else {
			C.setText(flagSet);
			System.out.println("C flag is set");
		}

		/**
		 * JPanel for Program Counter display
		 */
		JPanel PCPanel = new JPanel();
		PCPanel.setBackground(new java.awt.Color(0, 204, 255));
		PCPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("PC"));

		// define groups and add components
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addComponent(statusLabel)
				.addComponent(ccrPanel)
				.addGroup(
						layout.createSequentialGroup().addComponent(pcLabel)
								.addComponent(pcField).addComponent(xLabel)
								.addComponent(xField).addComponent(yLabel)
								.addComponent(yField).addComponent(aLabel)
								.addComponent(aField).addComponent(bLabel)
								.addComponent(bField).addComponent(dLabel)
								.addComponent(dField).addComponent(hsLabel)
								.addComponent(hsField).addComponent(usLabel)
								.addComponent(usField).addComponent(dpLabel)
								.addComponent(dpField)));

		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.LEADING, false)
				.addComponent(statusLabel).addComponent(ccrPanel)
				.addComponent(pcLabel).addComponent(pcField)
				.addComponent(xLabel).addComponent(xField).addComponent(yLabel)
				.addComponent(yField).addComponent(aLabel).addComponent(aField)
				.addComponent(bLabel).addComponent(bField).addComponent(dLabel)
				.addComponent(dField).addComponent(hsLabel)
				.addComponent(hsField).addComponent(usLabel)
				.addComponent(usField).addComponent(dpLabel)
				.addComponent(dpField));

	}

	/**
	 * adds left hand side elements to left of GUI
	 */
	public void layoutLeft() {
		JPanel left = new JPanel();
		left.setBackground(new java.awt.Color(200, 244, 255));
		add(left, BorderLayout.WEST);
		codeIn = new JTextArea(32, 38);
		codeIn.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Machine Code Area"));
		JScrollPane codeScroll = new JScrollPane(codeIn);
		left.add(codeIn);
		add(codeScroll, BorderLayout.CENTER);
		codeIn.setFont(new Font("Courier", Font.PLAIN, 14));
		codeIn.addFocusListener(focusListener);
	}

	/**
	 * adds bottom elements to bottom of GUI
	 */
	public void layoutBottom() {
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.LIGHT_GRAY);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		// loadButton = new JButton("Load");
		// loadButton.addActionListener(this);
		bottom.add(clearButton);
		// bottom.add(loadButton);
		runButton = new JButton("Run");
		runButton.setEnabled(false);
		runButton.addActionListener(this);
		bottom.add(runButton);
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setEnabled(false);
		bottom.add(saveButton);
		add(bottom, BorderLayout.SOUTH);
		modifyMemoryButton = new JButton("Load into Memory");
		modifyMemoryButton.addActionListener(this);
		stepButton = new JButton("Step");
		bottom.add(modifyMemoryButton);
		bottom.add(stepButton);

		// Combo box to select type of break
		JComboBox breakSelector = new JComboBox();
		breakSelector.addItem("Break Disabled");
		breakSelector.addItem("Program Line No.");
		bottom.add(breakSelector);

		// Spinner to set break line number
		JSpinner breakAt = new JSpinner();
		JLabel lineNo = new JLabel("Break at:");
		bottom.add(lineNo);
		bottom.add(breakAt);

	}

	/**
	 * method to add central elements to GUI
	 */
	public void layoutCenter() {
		JPanel central = new JPanel();
		central.setBackground(Color.BLUE);
		add(central, BorderLayout.CENTER);

		// JTabbedPane for switching between Memory, Display and Reference
		tabs = new JTabbedPane();
		this.add(tabs);

		// memory tab
		mList = new StringBuilder();
		memLocList = new Memory[MEM_LOCATIONS];
		mem = new Memory();
		for (int i = 0; i < memLocList.length; i++) {
			memLocList[i] = mem;
			memLocList[i].setOpCode(00);

			// reset vector at addresses $FFFE & $FFFF
			resetVect1 = new Memory();
			resetVect1.setOpCode(160);
			memLocList[65534] = resetVect1;
			resetVect2 = new Memory();
			resetVect2.setOpCode(39);
			memLocList[65535] = resetVect2;

			mList.append(String.format("%10s %04x %10s %6x", "Address:   ", i,
					"Value:  ", memLocList[i].getOpCode()));
			mList.append("\n");
		}

		// updateDisplay();

		JComponent memTab = makeTextPanel(mList.toString());
		tabs.addTab("Memory", new JScrollPane(memTab));
		JComponent displayTab = makeTextPanel("\nOutput \n of\n  emulator\n   operations\n   to\n    be\n      Displayed\n       Here");
		tabs.addTab("Display", displayTab);
		JComponent refTab = makeTextPanel("Reference");
		tabs.addTab("Reference", refTab);
	}

	/**
	 * Method to set central panel text
	 * 
	 * @param t - the string for display
	 * @return - the panel
	 */
	protected JComponent makeTextPanel(String t) {
		text = t;
		JPanel panel = new JPanel(false);
		JTextPane filler = new JTextPane();
		filler.setFont(new Font("Courier", Font.PLAIN, 14));
		filler.setText(text);
		filler.setAlignmentX(LEFT_ALIGNMENT);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	/**
	 * method to update the display
	 */
	public void updateDisplay() {

		// modify displayed Sting of memory
		// StringBuilder newList = new StringBuilder();
		//
		// for (int i = 0; i < memLocList.length; i++) {
		//
		// newList.append(String.format("%10s %04x %10s %6x", "Address:   ",
		// i, "Value:  ", memLocList[i].getOpCode()));
		// newList.append("\n");
		// }
		// System.out.println(newList);
	}

	/**
	 * method to handle loading of code into memory locations
	 */
	public void modifyMemory() {

		// show dialog to retrieve entered address
		addressToModify = (String) JOptionPane
				.showInputDialog("At which memory address? \n "
						+ "(if hexadecimal please signify\n with a '$' symbol, e.g. $1000)");

		// confirm if a string was entered
		if ((addressToModify != null) && (addressToModify.length() > 0)) {

			// check if entered address is hexadecimal
			if (addressToModify.charAt(0) == '$') {

				// create substring to remove $ symbol
				addressToModify = addressToModify.substring(1);

				// convert to integer if hexadecimal address entered
				memAddress = Integer.parseInt(addressToModify, 16);
			}

			else {
				// convert to integer if decimal address entered
				memAddress = Integer.parseInt(addressToModify);
			}
			System.out.println("address as hex String = " + addressToModify
					+ "\naddress as decimal int = " + memAddress);

			// declare new memory array and memory object
			MemoryArray MA = new MemoryArray();
			Memory mem = new Memory();

			// set the memory address and OpCode
			mem.setAddress(memAddress);
			// mem.setOpCode(opCode);

			// pass entered memory location to Memory array as memory object
			MA.addMemoryObjects(memAddress, mem);

			processInput(memAddress);

			// enable run and save buttons
			runButton.setEnabled(true);
			saveButton.setEnabled(true);

			// call update display method to refresh the GUI
			// updateDisplay(memAddress);

		} else
			JOptionPane.showMessageDialog(clearButton,
					"Sorry, not a valid address.");
	}

	/**
	 *  Method to read text from code input area
	 *  
	 * @param addr - the memory address
	 */
	public void processInput(int addr) {

		String enteredCode = codeIn.getText();
		System.out.println("Entered Code:\n" + enteredCode);

		// pass entered code as String parameter to Instructions class
		Instructions instructionsClass = new Instructions(enteredCode, addr);
	}

	/**
	 * Method to process input of S-record file
	 */
	public void processInputFile() {

		// create S_Record object
		sRec = new S_Record();

		// add file chooser to "open" menu
		JFileChooser chooser = new JFileChooser();
		JLabel resultLabel = new JLabel("");
		int returnVal = chooser.showOpenDialog(getParent());

		// if file chosen then confirm details
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// create the file
			File file = chooser.getSelectedFile();
			System.out.println(file);

			// pass to readRecord method in S_Record class
			sRec.readRecord(file);

			resultLabel.setText("You chose to open this file: "
					+ file.getName());
		} else
			resultLabel.setText("You did not choose a file to open");
	}

	/**
	 * method to process run button operation
	 */
	public void processRun() {
		// start main emulator loop
		System.out.println("\nStart running from hex address "
				+ addressToModify + " or decimal address " + memAddress);
		proc = new Processor(memAddress);

	}

	/**
	 * method to process user guide menu operation
	 */
	public void displayGuide() {
		// create new guide window object
		guideWindow = new UserGuide();
		// display the guide window
		guideWindow.setVisible(true);
	}

	/**
	 * Process button clicks.
	 * 
	 * @param ae
	 *            the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {

		// action if load into memory clicked
		if (ae.getSource() == modifyMemoryButton) {

			// first check if any code entered
			if (codeIn.getText().trim().length() != 0) {

				// call modifyMemory() method
				modifyMemory();

			} else
				JOptionPane.showMessageDialog(null,
						"Please enter some machine code first.");
		}

		if (ae.getSource() == openMenuItem) {
			// call processInputFile method
			processInputFile();
		}

		if (ae.getSource() == openUserGuide) {
			// call display guide method
			displayGuide();
		}

		if (ae.getSource() == runButton) {
			// call method to process running of code
			processRun();
		}

		if (ae.getSource() == clearButton) {
			// clear code entry area
			codeIn.setText("");
		}

	}

}
