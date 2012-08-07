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
	private Memory[] memoryList, memoryListNew;
	private Memory mem, resetVect1, resetVect2, reserveVect1, reserveVect2,
			swi3Vect1, swi3Vect2, swi2Vect1, swi2Vect2, swi1Vect1, swi1Vect2;

	/** Instance variable to represent the Program Counter */
	private int progCounter;

	/** StringBuilder object for displaying memory */
	private StringBuilder mList;

	/** Instance of Memory class */
	private Memory memClass;

	/** Instance of Processor class */
	private ProcessorLoop proc;

	@Override
	public void run() {

	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		swingPcSupport.addPropertyChangeListener(listener);
	}

	/**
	 * default constructor to instantiate array of empty 
	 * memory objects and set reserved memory locations
	 */
	public MemoryArray() {

		proc = new ProcessorLoop();

		memoryList = new Memory[MEM_LOCATIONS];
		// for (int i = 0; i < memoryList.length; i++) {
		mem = new Memory();
		for (int i = 0; i < memoryList.length; i++) {
			memoryList[i] = mem;
			memoryList[i].setOpCode(00);
		}

		// reset vector at addresses $FFFE & $FFFF
		resetVect1 = new Memory();
		resetVect1.setOpCode(160);
		memoryList[65534] = resetVect1;
		resetVect2 = new Memory();
		resetVect2.setOpCode(39);
		memoryList[65535] = resetVect2;

		// FFF0H to FFF1H |Reserved by Motorola |
		reserveVect1 = new Memory();
		reserveVect1.setOpCode(255); // TODO - need to find out actual values
		memoryList[65520] = reserveVect1;
		reserveVect2 = new Memory();
		reserveVect2.setOpCode(255);
		memoryList[65521] = reserveVect2;

		// | FFF2H to FFF3H |SWI3 instruction interrupt vector |
		swi3Vect1 = new Memory();
		swi3Vect1.setOpCode(254);
		memoryList[65522] = swi3Vect1;
		swi3Vect2 = new Memory();
		swi3Vect2.setOpCode(254);
		memoryList[65523] = swi3Vect2;

		// | FFF4H to FFF5H |SWI2 instruction interrupt vector |
		swi2Vect1 = new Memory();
		swi2Vect1.setOpCode(253);
		memoryList[65524] = swi2Vect1;
		swi2Vect2 = new Memory();
		swi2Vect2.setOpCode(253);
		memoryList[65525] = swi2Vect2;

		// | FFF6H to FFF7H |Fast hardware interrupt vector (FIRQ) |

		// | FFF8H to FFF9H |Hardware interrupt vector (IRQ) |

		// | FFFAH to FFFBH |SWI instruction interrupt vector |
		swi1Vect1 = new Memory();
		swi1Vect1.setOpCode(250);
		memoryList[65530] = swi1Vect1;
		swi1Vect2 = new Memory();
		swi1Vect2.setOpCode(250);
		memoryList[65531] = swi1Vect2;

		// | FFFCH to FFFDH |Non-maskable interrupt vector (NMI)|
		// | FFFEH to FFFFH |Reset vector
		// FIRQ vector at addresses $FFF6 & $FFF7

		setMemoryDisplayString();
	}

	/**	  
	 * method to add memory objects to the array
	 *
	 * @param a - the memory address
	 * @param mo - the memory object
	 */
	public void addMemoryObjects(int a, Memory mo) {
		memoryList[a] = mo;

		System.out.println("Memory modified at decimal address "
				+ memoryList[a].getAddress() + " with op code "
				+ memoryList[a].getOpCode());
		// print small portion of memory array to console for checking
		for (int i = 0; i < memoryList.length / 10000; i++) {
			System.out.println("address: " + i + " opCode: "
					+ memoryList[i].getOpCode());
		}
		// setMemoryDisplayString(); // TODO does nothing
		// proc = new ProcessorLoop();
		proc.memPass(memoryList);
	}

	/**
	 * This method begins the main emulator loop with the 
	 * program counter set to the memory address passed as
	 * a parameter from the GUI
	 *  
	 * @param mem - the address for the Program Counter to start
	 */
	public void runLoop() {

		// // print small portion of memory array to console for checking
		// for (int i = 0; i < memoryList.length / 10000; i++)
		// System.out.println("address in PLoop: " + i + " opCode: "
		// + memoryList[i].getOpCode());
		//
		// // boolean representing emulator state
		// boolean emuRunning = true;
		//
		// progCounter = mem;
		// System.out
		// .println("Address for program counter to start from in decimal = "
		// + progCounter);
		//
		// // while loop is running
		// while (emuRunning) {
		// // fetch first opCode (byte)
		// opCode = memoryList[progCounter].getOpCode();
		// System.out.println("first op code " + opCode
		// + " fetched from address " + progCounter);
		// // interpret and establish addressing mode
		//
		// // fetch further bytes if required
		//
		// // execute
		//
		// // check for interrupts
		//
		// // exit loop
		// emuRunning = false;
		// }
		proc.procLoop();
	}

	public int getArrayAddress() {
		return address;
	}

	public void setArrayAddress(int address) {
		this.address = address;
		System.out.println("addressToModify = " + address);
	}

	public int getArrayOpCode() {
		return opCode;
	}

	public void setArrayOpCode(int opCode) {
		this.opCode = opCode;
	}

	/** 
	 * method to create formatted string of memory array
	 * for display in the GUI
	 */
	public void setMemoryDisplayString() {

		// create StringBuilder for display in memory tab
		mList = new StringBuilder();
		for (int i = 0; i < memoryList.length; i++) {

			mList.append(String.format("%10s %04x %10s %02x", "Address:   ", i,
					"Value:  ", memoryList[i].getOpCode()));
			mList.append("\n");
		}
	}

	public StringBuilder getMemroyDisplayString() {

		return mList;
	}
}
