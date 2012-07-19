/**
 * This class implements the main processor routine
 * 
 * @author robertwilson
 * 
 */
public class Processor {

	/** instance variable representing number of CPU cycles to next interrupt */
	private int interruptCounter = 0;

	/** Instance variable to represent the Program Counter */
	private int progCounter;

	/** Instance of the MemoryArray class */
	private Memory[] memList;

	/** class constant representing the number of memory locations */
	private final int MEM_LOCATIONS = 65536;

	/** integer representing the opCode from memory */
	private int opCode;

	/** this method takes the array of memory objects as a parameter */
	public void memPass(Memory[] memLocList) {

		memList = memLocList;
		System.out.println("aye");
		// System.out.println("OK " + memLocList[0].getAddress().);
	}

	/** 
	 * this method takes an integer representing the starting
	 * memory location for the program
	 * 
	 * @param mem
	 */
	public Processor(int mem) {

		progCounter = mem;
		System.out
				.println("Address for program counter to start from in decimal = "
						+ progCounter);
		// memList = new MemoryArray[MEM_LOCATIONS];
		// System.out.println(memList[progCounter].getOpCode());
		// infinite loop whilst emulator is running a program
		// for (;;) {
		// fetch bytes from memory
		// opCode = memList[progCounter].getOpCode();
		// System.out.println("Starting opCode = " + opCode);
		// interruptCounter -= Cycles(opCode); // check for interrupts after
		// executing each instruction

		// decode instruction via instruction class

		// execute instruction

		if (interruptCounter <= 0) {
			// check for interrupts etc

		}
		// }

	}

}
