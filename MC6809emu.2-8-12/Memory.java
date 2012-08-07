/**
 * This class defines objects representing the memory locations
 * 
 * @author Robert Wilson
 * 
 */
public class Memory {

	/** Instance variables */
	private int address, opCode;

	// private String opCode;

	/**
	 * This default constructor method defines an object representing a single
	 * memory location
	 */
	public Memory() {

		address = 00;
		opCode = 00;

	}

	public Memory(int add, int op) {

		address = add;
		opCode = op;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getOpCode() {
		return opCode;
	}

	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}

}
