import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * This class contains the user guide for the emulator
 * 
 * @author Robert Wilson
 *
 */
public class UserGuide extends JFrame {

	/** Instance variables */
	private JTextArea guideText;

	public UserGuide() {

		// set title and dimensions of report frame
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("MC6809 Emulator - User Guide");
		setSize(790, 560);
		setLocation(160, 150);

		// add text area to frame
		guideText = new JTextArea();
		guideText.setBackground(Color.LIGHT_GRAY);
		guideText.setFont(new Font("Comic sans", Font.PLAIN, 14));
		add(guideText, BorderLayout.CENTER);
		guideText.setEditable(false);
		guideText
				.setText(" Starting the emulator:\n"
						+ "\n To start the emulator double click the icon.\n\n\n"
						+ " Entering code:\n\n"
						+ " The emulator reads code entered in hexadecimal."
						+ "\n Code is typed into the text entry panel on the left hand side of the user interface.\n"
						+ " Clicking anywhere within the text entry panel will select it and code can then be typed in.\n\n\n"
						+ " Loading a program:\n\n"
						+ " Clicking the Load button allows saved code to loaded.\n"
						+ " Programs can be loaded in Motorola's S-Record format.\n\n\n"
						+ " Modifying memory:\n\n"
						+ " Once code has been typed in it can be loaded into memory by clicking the Modify Memory button. \n"
						+ " This will bring up a requestor asking for a memory address to be specified.\n\n\n"
						+ " Running code:\n\n"
						+ " Once code has been loaded into a memory address, clicking the Run button will run the code from that address.");

	}

}
