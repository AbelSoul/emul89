/**
 * This is the main class of an emulator for the Motorola MC6809 microprocessor
 * 
 * @author Robert Wilson
 * 
 */
public class MC6809emuMain {
	/**
	 * 
	 * The main method sets the GUI class to visible
	 * 
	 * @param arg
	 *            the default parameter
	 */
	public static void main(String[] arg) {
		EmuGUI display = new EmuGUI();
		display.setVisible(true);

	}
}
