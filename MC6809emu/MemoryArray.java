import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

/**
 * This class maintains a list of objects representing the 64k memory locations
 * 
 * @author Robert Wilson
 * 
 */
public class MemoryArray implements Runnable {

	/** Swing Property Change Support Object */
	private SwingPropertyChangeSupport swingPcSupport = new SwingPropertyChangeSupport(
			this);

	/** class constant representing the number of memory locations */
	private final int MEM_LOCATIONS = 65536;

	/** Instance variables */
	private int address;
	private int opCode;
	private Memory[] memoryList;

	/** Instance of Memory class */
	private Memory memClass;

	@Override
	public void run() {

	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		swingPcSupport.addPropertyChangeListener(listener);
	}

	/**
	 * default constructor to instantiate array of empty memory objects
	 */
	public MemoryArray() {

		memoryList = new Memory[MEM_LOCATIONS];
		for (int i = 0; i < memoryList.length; i++) {
			memoryList[i] = null;
			// System.out.println(i + " " + memoryList[i]);
		}

		// reset vector at addresses $FFFE & $FFFF
		// resetVect1 = new Memory();
		// resetVect1.setOpCode(160);
		// memLocList[65534] = resetVect1;
		// resetVect2 = new Memory();
		// resetVect2.setOpCode(39);
		// memLocList[65535] = resetVect2;

		// FIRQ vector at addresses $FFF6 & $FFF7

	}

	/**
	 * method to add memory objects to the array
	 */
	public void addMemoryObjects(int a, Memory mo) {

		memoryList[a] = mo;
		// System.out.println("\nAddress = " + a + "  OpCode = " +
		// mo.getOpCode());

		System.out.println("Memory modified at decimal address "
				+ memoryList[a].getAddress());

	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = 00;
		this.address = address;
		System.out.println("addressToModify = " + address);
	}

	public int getOpCode() {
		return opCode;
	}

	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}

}
