/**
 * This class implements the main processor routine
 * 
 * @author Robert Wilson
 * 
 */
public class ProcessorLoop {

	/** instance variable representing number of CPU cycles to next interrupt */
	private int interruptCounter = 0;

	/** Instance variable to represent the Program Counter */
	private int progCounter;

	/** Instance of the Memory class */
	// private MemoryArray[] memoryArray;
	public Memory[] memList;

	/** class constant representing the number of memory locations */
	private final int MEM_LOCATIONS = 65536;

	/** integer representing the opCode from memory */
	private int opCode;

	/** this method takes the array of memory objects as a parameter */
	public void memPass(Memory[] memLocList) {

		memList = memLocList;
		System.out.println(memList.length);
		// // System.out.println("OK " + memLocList[0].getAddress().);
		// // print first few elements to check
		// for (int i = 0; i < 10; i++) {
		// System.out.println(memList[i].getOpCode() + " = op code");
		// }

		System.out.println(this);
	}

	/** 
	 * this method takes an integer representing the starting
	 * memory location for the program
	 * 
	 * @param mem - the starting memory address
	 */
	public void procLoop() {

		// print small portion of memory array to console for checking
		// for (int i = 0; i < memList.length / 10000; i++)
		// System.out.println("address in PLoop: " + i + " opCode: "
		// + memList[i].getOpCode());

		// boolean representing emulator state
		// boolean emuRunning = true;
		//
		// progCounter = mem;
		// System.out
		// .println("Address for program counter to start from in decimal = "
		// + progCounter);

		// System.out.println(memList.length);

		System.out.println(this);

		// while loop is running
		// while (emuRunning) {
		// // fetch first opCode (byte)
		// opCode = memList[progCounter].getOpCode();
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

	}

}
