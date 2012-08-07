import javax.swing.JOptionPane;

/**
 * This class contains the code for each instruction used by the emulator
 * 
 * @author Robert Wilson
 * 
 */
public class Instructions {

	/**
	 * This method takes in a string passed through from the GUI and splits it
	 * into chunks of two characters in length for interpretation as hexadecimal
	 * 
	 * @param codeIn
	 */

	/**Instance variables */

	/** instance of CCR class */
	private CCR ccrFlagState;

	/** instance of GUI class */
	private EmuGUI gui;

	/** instance of MemoryArray class */
	private MemoryArray[] memArrayList;

	public Instructions(String codeIn, int addr) {

		String code = codeIn.trim();
		code = code.replaceAll("\\s+", "");
		System.out.println("Code In:\n" + code);

		int len = code.length();
		int chunkLength = 2; // the length of each chunk of code

		// traverse entered code and split into 2 digit chunks
		for (int i = 0; i < len; i += chunkLength) {

			String chunk = code.substring(i, Math.min(len, i + chunkLength));
			System.out.println("code chunk no. " + (i / 2) + " " + chunk);

			// opCode(chunk); //TODO - need to call this from the memory class I
			// think

			MemoryArray MA = new MemoryArray();
			Memory mem = new Memory();

			// set the memory address and OpCode
			mem.setAddress(addr);
			// convert to decimal
			mem.setOpCode(Integer.parseInt(chunk, 16));

			// add the memory object to the memory array
			MA.addMemoryObjects(addr, mem);
			// System.out.println("New memory = " + MA.getAddress() + " "
			// + MA.getOpCode());
			addr++;
		}

	}

	/**
	 * There are 268 OpCodes, most of which are described below
	 * 
	 * Key ===
	 * 
	 * a Affected. - Unaffected. u Undefined. d Changed directly. s Contains the
	 * carry from a shift operation. c Affected only if CC register selected. n
	 * Unaffected by LSL, undefined by ASL (according to Motorola)!
	 * 
	 * Undocumented + Not an instruction, but a prefix to page 1 or page 2
	 * instructions. ^ Illegal instructions not listed.
	 * 
	 * 
	 * @param oc
	 * @throws NumberFormatException
	 */
	public void opCode(String oc) throws NumberFormatException {

		CCR ccrFlagState = new CCR();
		// gui = new EmuGUI();

		try {

			// parse hexadecimal codes to integers for page 0 instructions
			switch (Integer.parseInt(oc, 16)) {
			case 0x00:
				System.out.println("NEG, direct, 6 cycles, 2 Bytes, uaaaa");
				break;
			case 0x01:
				System.out.println("ILLEGAL, 1 cycle, 1 byte, uuuuu");
				break;
			case 0x02:
				System.out.println("ILLEGAL, 1 cycle, 1 byte, uuuuu");
				break;
			case 0x03:
				System.out.println("COM, direct, 6 cycles, 2 Bytes, -aa01");
				break;
			case 0x04:
				System.out.println("LSR, direct, 6 cycles, 2 Bytes, -0a-s");
				break;
			case 0x06:
				System.out.println("ROR, direct, 6 cycles, 2 Bytes, -aa-s");
				break;
			case 0x07:
				System.out.println("ASR, direct, 6 cycles, 2 Bytes, uaa-s");
				break;
			case 0x08:
				System.out.println("LSL/ASL, dir, 6 cycles, 2 Bytes, naaas");
				break;
			case 0x09:
				System.out.println("ROL, direct, 6 cycles, 2 bytes, -aaas");
				break;
			case 0x0A:
				System.out.println("DEC, direct, 6 cycles, 2 Bytes, -aaa-");
				break;
			case 0x0C:
				System.out.println("INC, direct, 6 cycles, 2 Bytes, -aaa-");
				break;
			case 0x0D:
				System.out.println("TST, direct, 6 cycles, 2 Bytes, -aa0-");
				break;
			case 0x0E:
				System.out.println("JMP, direct, 3 cycles, 2 Bytes, -----");
				break;
			case 0x0F:
				System.out.println("CLR, direct, 6 cycles, 2 Bytes, -0100");
				// sets Z flag, clears N, V & C
				ccrFlagState.setZBit(true);
				System.out.println(ccrFlagState.zBit() + " z bit");
				break;
			case 0x10:
				System.out.println("PAGE1+, variant, 1 cycle, 1 Byte, +++++");
				p1Codes(oc);
				break;
			case 0x11:
				System.out.println("PAGE2+, variant, 1 cycle, 1 Byte, +++++");
				p2Codes(oc);
				break;
			case 0x12:
				System.out.println("NOP, inherent, 2 cycles, 1 Byte, -----");
				break;
			case 0x13:
				System.out.println("SYNC, inherent, 2 cycles, 1 Byte, -----");
				break;
			case 0x16:
				System.out.println("LBRA, relative, 5 cycles, 3 Bytes, -----");
				break;
			case 0x17:
				System.out.println("LBSR, relative, 9 cycles, 3 Bytes, -----");
				break;
			case 0x19:
				System.out.println("DAA, inherent, 2 cycles, 1 Bytes, -aa0a");
				break;
			case 0x1A:
				System.out.println("ORCC, immediate, 3 cycles, 2 Bytes, ddddd");
				break;
			case 0x1C:
				System.out
						.println("ANDCC, immediate, 3 cycles, 2 Bytes, ddddd");
				break;
			case 0x1D:
				System.out.println("SEX, inherent, 2 cycles, 1 Bytes, -aa0-");
				break;
			case 0x1E:
				System.out.println("EXG, inherent, 8 cycles, 2 Bytes, ccccc");
				break;
			case 0x1F:
				System.out.println("TFR, inherent, 7 cycles, 2 Bytes, -aa0a");
				break;
			case 0x20:
				System.out.println("BRA, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x21:
				System.out.println("BRN, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x22:
				System.out.println("BHI, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x23:
				System.out.println("BLS, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x24:
				System.out
						.println("BHS/BCC, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x25:
				System.out
						.println("BLO/BCS, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x26:
				System.out.println("BNE, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x27:
				System.out.println("BEQ, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x28:
				System.out.println("BVC, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x29:
				System.out.println("BVS, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x2A:
				System.out.println("BPL, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x2B:
				System.out.println("BMI, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x2C:
				System.out.println("BGE, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x2D:
				System.out.println("BLT, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x2E:
				System.out.println("BGT, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x2F:
				System.out.println("BLE, relative, 3 cycles, 2 Bytes, -----");
				break;
			case 0x30:
				System.out.println("LEAX, relative, 4 cycles, 2 Bytes, --a--");
				break;
			case 0x31:
				System.out.println("LEAY, indexed, 4 cycles, 2 Bytes, --a--");
				break;
			case 0x32:
				System.out.println("LEAS, indexed, 4 cycles, 2 Bytes, -----");
				break;
			case 0x33:
				System.out.println("LEAU, indexed, 4 cycles, 2 Bytes, -----");
				break;
			case 0x34:
				System.out.println("PSHS, inherent, 5 cycles, 2 Bytes, -----");
				break;
			case 0x35:
				System.out.println("PULS, inherent, 5 cycles, 2 Bytes, ccccc");
				break;
			case 0x36:
				System.out.println("PSHU, inherent, 5 cycles, 2 Bytes, -----");
				break;
			case 0x37:
				System.out.println("PULU, inherent, 5 cycles, 2 Bytes, ccccc");
				break;
			case 0x39:
				System.out.println("RTS, inherent, 5 cycles, 1 Bytes, -----");
				break;
			case 0x3A:
				System.out.println("ABX, inherent, 3 cycles, 1 Bytes, -----");
				break;
			case 0x3B:
				System.out
						.println("RTI, inherent, 6/15 cycles, 1 Bytes, -----");
				break;
			case 0x3C:
				System.out.println("CWAI, inherent, 21 cycles, 2 Bytes, ddddd");
				break;
			case 0x3D:
				System.out.println("MUL, inherent, 11 cycles, 1 Bytes, --a-a");
				break;
			case 0x3E:
				System.out
						.println("RESET*, inherent, * cycles, 1 Bytes, *****");
				break;
			case 0x3F:
				System.out.println("SWI, inherent, 19 cycles, 1 Bytes, -----");
				break;
			case 0x40:
				System.out.println("NEGA, inherent, 2 cycles, 1 Bytes, uaaaa");
				break;
			case 0x43:
				System.out.println("COMA, inherent, 2 cycles, 1 Bytes, -aa01");
				break;
			case 0x44:
				System.out.println("LSRA, inherent, 2 cycles, 1 Bytes, -0a-s");
				break;
			case 0x46:
				System.out.println("RORA, inherent, 2 cycles, 1 Bytes, -aa-s");
				break;
			case 0x47:
				System.out.println("ASRA, inherent, 2 cycles, 1 Bytes, uaa-s");
				break;
			case 0x48:
				System.out
						.println("LSLA/ASLA, inherent, 2 cycles, 1 Bytes, naaas");
				break;
			case 0x49:
				System.out.println("ROLA, inherent, 2 cycles, 1 Bytes, -aaas");
				break;
			case 0x4A:
				System.out.println("DECA, inherent, 2 cycles, 1 Bytes, -aaa-");
				break;
			case 0x4C:
				System.out.println("INCA, inherent, 2 cycles, 1 Bytes, -aaa-");
				break;
			case 0x4D:
				System.out.println("TSTA, inherent, 2 cycles, 1 Bytes, -aa0-");
				break;
			case 0x4F:
				System.out.println("CLRA, inherent, 2 cycles, 1 Bytes, -0100");
				break;
			case 0x50:
				System.out.println("NEGB, inherent, 2 cycles, 1 Bytes, uaaaa");
				break;
			case 0x53:
				System.out.println("COMB, inherent, 2 cycles, 1 Bytes, -aa01");
				break;
			case 0x54:
				System.out.println("LSRB, inherent, 2 cycles, 1 Bytes, -0a-s");
				break;
			case 0x56:
				System.out.println("RORB, inherent, 2 cycles, 1 Bytes, -aa-s");
				break;
			case 0x57:
				System.out.println("ASRB, inherent, 2 cycles, 1 Bytes, uaa-s");
				break;
			case 0x58:
				System.out
						.println("LSLB/ASLB, inherent, 2 cycles, 1 Bytes, naaas");
				break;
			case 0x59:
				System.out.println("ROLB, inherent, 2 cycles, 1 Bytes, -aaas");
				break;
			case 0x5A:
				System.out.println("DECB, inherent, 2 cycles, 1 Bytes, -aaa-");
				break;
			case 0x5C:
				System.out.println("INCB, inherent, 2 cycles, 1 Bytes, -aaa-");
				break;
			case 0x5D:
				System.out.println("TSTB, inherent, 2 cycles, 1 Bytes, -aa0-");
				break;
			case 0x5F:
				System.out.println("CLRB, inherent, 2 cycles, 1 Bytes, -0100");
				break;
			case 0x60:
				System.out.println("NEG, INDEXED, 6 cycles, 2 Bytes, uaaaa");
				break;
			case 0x63:
				System.out.println("COM, INDEXED, 6 cycles, 2 Bytes, -aa01");
				break;
			case 0x64:
				System.out.println("LSR, INDEXED, 6 cycles, 2 Bytes, -0a-s");
				break;
			case 0x66:
				System.out.println("ROR, INDEXED, 6 cycles, 2 Bytes, -aa-s");
				break;
			case 0x67:
				System.out.println("ASR, INDEXED, 6 cycles, 2 Bytes, uaa-s");
				break;
			case 0x68:
				System.out
						.println("LSL/ASL, INDEXED, 6 cycles, 2 Bytes, naaas");
				break;
			case 0x69:
				System.out.println("ROL, INDEXED, 6 cycles, 2 Bytes, -aaas");
				break;
			case 0x6A:
				System.out.println("DEC, INDEXED, 6 cycles, 2 Bytes, -aaa-");
				break;
			case 0x6C:
				System.out.println("INC, INDEXED, 6 cycles, 2 Bytes, -aaa-");
				break;
			case 0x6D:

				break;
			case 0x6E:

				break;
			case 0x6F:

				break;
			case 0x70:

				break;
			case 0x73:

				break;
			case 0x74:

				break;
			case 0x76:

				break;
			case 0x77:

				break;
			case 0x78:

				break;
			case 0x79:

				break;
			case 0x7A:

				break;
			case 0x7C:

				break;
			case 0x7D:

				break;
			case 0x7E:

				break;
			case 0x7F:

				break;
			case 0x80:

				break;
			case 0x81:

				break;
			case 0x82:

				break;
			case 0x83:

				break;
			case 0x84:

				break;
			case 0x85:

				break;
			case 0x86:

				break;
			case 0x88:

				break;
			case 0x89:

				break;
			case 0x8A:

				break;
			case 0x8B: // immediate ADDA
				System.out.println("ADDA, immediate, 2 cycles, 2 Bytes, aaaaa");
				break;
			case 0x8C:
				System.out.println("CMPX, immediate, 4 cycles, 3 Bytes, -aaaa");
				break;
			case 0x8D:
				System.out.println("BSR, RELATIVE, 7 cycles, 2 Bytes, -aa0-");
				break;
			case 0x8E:
				System.out.println("LDX, immediate, 3 cycles, 3 Bytes, aaaaa");
				break;
			case 0x90:
				System.out.println("SUBA, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0x91:
				System.out.println("CMPA, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0x92:
				System.out.println("SBCA, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0x93:
				System.out.println("SUBD, DIRECT, 6 cycles, 2 Bytes, -aaaa");
				break;
			case 0x94:
				System.out.println("ANDA, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0x95:
				System.out.println("BITA, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0x96:
				System.out.println("LDA, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0x97:
				System.out.println("STA, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0x98:
				System.out.println("EORA, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0x99:
				System.out.println("ADCA, DIRECT, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0x9A:
				System.out.println("ORA, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;

			case 0x9B: // direct ADDA
				System.out.println("ADDA, direct, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0x9C:
				System.out.println("CMPX, DIRECT, 6 cycles, 2 Bytes, -aaaa");
				break;
			case 0x9D:
				System.out.println("JSR, DIRECT, 7 cycles, 2 Bytes, -----");
				break;
			case 0x9E:
				System.out.println("LDX, DIRECT, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0x9F:
				System.out.println("STX, DIRECT, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0xA0:
				System.out.println("SUBA, INDEXED, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xA1:
				System.out.println("CMPA, INDEXED, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xA2:
				System.out.println("SBCA, INDEXED, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xA3:
				System.out.println("SUBD, INDEXED, 6 cycles, 2 Bytes, -aaaa");
				break;
			case 0xA4:
				System.out.println("ANDA, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xA5:
				System.out.println("BITA, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xA6:
				System.out.println("LDA, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xA7:
				System.out.println("STA, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xA8:
				System.out.println("EORA, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xA9:
				System.out.println("ADCA, INDEXED, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0xAA:
				System.out.println("ORA, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xAB: // indexed ADDA
				System.out.println("ADAA, INDEXED, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0xAC:
				System.out.println("CMPX, INDEXED, 6 cycles, 2 Bytes, -aaaa");
				break;
			case 0xAD:
				System.out.println("JSR, INDEXED, 7 cycles, 2 Bytes, -----");
				break;
			case 0xAE:
				System.out.println("LDX, INDEXED, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0xAF:
				System.out.println("STX, INDEXED, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0xB0:
				System.out.println("SUBA, EXTENDED, 5 cycles, 3 Bytes, uaaaa");
				break;
			case 0xB1:
				System.out.println("CMPA, EXTENDED, 5 cycles, 3 Bytes, uaaaa");
				break;
			case 0xB2:
				System.out.println("SBCA, EXTENDED, 5 cycles, 3 Bytes, uaaaa");
				break;
			case 0xB3:
				System.out.println("SUBD, EXTENDED, 7 cycles, 3 Bytes, -aaaa");
				break;
			case 0xB4:
				System.out.println("ANDA, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xB5:
				System.out.println("BITA, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xB6:
				System.out.println("LDA, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xB7:
				System.out.println("STA, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xB8:
				System.out.println("EORA, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xB9:
				System.out.println("ADCA, EXTENDED, 5 cycles, 3 Bytes, aaaaa");
				break;
			case 0xBA:
				System.out.println("ORA, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xBB: // extended ADDA
				System.out.println("DAA, EXTENDED, 2 cycles, 1 Bytes, -aa0a");
				break;
			case 0xBC:
				System.out.println("CMPX, EXTENDED, 7 cycles, 3 Bytes, -aaaa");
				break;
			case 0xBD:
				System.out.println("JSR, EXTENDED, 8 cycles, 3 Bytes, -----");
				break;
			case 0xBE:
				System.out.println("LDX, EXTENDED, 6 cycles, 3 Bytes, -aa0-");
				break;
			case 0xBF:
				System.out.println("STX, EXTENDED, 6 cycles, 3 Bytes, -aa0-");
				break;
			case 0xC0:
				System.out.println("SUBB, IMMEDIATE, 2 cycles, 2 Bytes, uaaaa");
				break;
			case 0xC1:
				System.out.println("SUBB, IMMEDIATE, 2 cycles, 2 Bytes, uaaaa");
				break;
			case 0xC2:
				System.out.println("SUBB, IMMEDIATE, 2 cycles, 2 Bytes, uaaaa");
				break;
			case 0xC3: // immediate ADDD
				System.out.println("ADDD, IMMEDIATE, 4 cycles, 3 Bytes, -aaaa");
				break;
			case 0xC4:
				System.out.println("ANDB, IMMEDIATE, 2 cycles, 2 Bytes, -aa0-");
				break;
			case 0xC5:
				System.out.println("BITB, IMMEDIATE, 2 cycles, 2 Bytes, -aa0-");
				break;
			case 0xC6:
				System.out.println("LDB, IMMEDIATE, 2 cycles, 2 Bytes, -aa0-");
				break;
			case 0xC8:
				System.out.println("EORB, IMMEDIATE, 2 cycles, 2 Bytes, -aa0-");
				break;
			case 0xC9:
				System.out.println("ADCB, IMMEDIATE, 2 cycles, 2 Bytes, aaaaa");
				break;
			case 0xCA:
				System.out.println("ORB, IMMEDIATE, 2 cycles, 2 Bytes, -aa0-");
				break;
			case 0xCB: // immediate ADDB
				System.out.println("ADDB, IMMEDIATE, 2 cycles, 2 Bytes, aaaaa");
				break;
			case 0xCC:
				System.out.println("LDD, IMMEDIATE, 3 cycles, 3 Bytes, -aa0-");
				break;
			case 0xCE:
				System.out.println("LDU, IMMEDIATE, 3 cycles, 3 Bytes, -aa0-");
				break;
			case 0xD0:
				System.out.println("SUBB, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xD1:
				System.out.println("CMPB, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xD2:
				System.out.println("SBCB, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xD3:
				System.out.println("ADDD, DIRECT, 6 cycles, 2 Bytes, -aaaa");
				break;
			case 0xD4:
				System.out.println("ANDB, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xD5:
				System.out.println("BITB, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xD6:
				System.out.println("LDB, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xD7:
				System.out.println("STB, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xD8:
				System.out.println("EORB, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xD9:
				System.out.println("ADCB, DIRECT, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0xDA:
				System.out.println("ORB, DIRECT, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xDB: // direct ADDB
				System.out.println("ADDB, DIRECT, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0xDC:
				System.out.println("LDD, DIRECT, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0xDD:
				System.out.println("STD, DIRECT, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0xDE:
				System.out.println("LDU, DIRECT, 5 cycles, 2 Bytes, -aa0-");
				break;
			case 0xDF:
				System.out.println("STU, DIRECT, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xE0:
				System.out.println("SUBB, INDEXED, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xE1:
				System.out.println("CMPB, INDEXED, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xE2:
				System.out.println("SBCB, INDEXED, 4 cycles, 2 Bytes, uaaaa");
				break;
			case 0xE3:
				System.out.println("ADDD, INDEXED, 6 cycles, 2 Bytes, -aaaa");
				break;
			case 0xE4:
				System.out.println("ANDB, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xE5:
				System.out.println("BITB, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xE6:
				System.out.println("LDB, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xE7:
				System.out.println("STB, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xE8:
				System.out.println("EORB, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xE9:
				System.out.println("ADCB, INDEXED, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0xEA:
				System.out.println("ORB, INDEXED, 4 cycles, 2 Bytes, -aa0-");
				break;
			case 0xEB:
				System.out.println("ADDB, INDEXED, 4 cycles, 2 Bytes, aaaaa");
				break;
			case 0xEC:
				System.out.println("DAA, INDEXED, 5 cycles, 2 Bytes, -aa0a");
				break;
			case 0xED:
				System.out.println("STD, INDEXED, 5 cycles, 2 Bytes, -aa0a");
				break;
			case 0xEE:
				System.out.println("LDU, INDEXED, 5 cycles, 2 Bytes, -aa0a");
				break;
			case 0xEF:
				System.out.println("STU, INDEXED, 5 cycles, 2 Bytes, -aa0a");
				break;
			case 0xF0:
				System.out.println("SUBB, EXTENDED, 5 cycles, 3 Bytes, uaaaa");
				break;
			case 0xF1:
				System.out.println("CMPB, EXTENDED, 5 cycles, 3 Bytes, uaaaa");
				break;
			case 0xF2:
				System.out.println("SBCB, EXTENDED, 5 cycles, 3 Bytes, uaaaa");
				break;
			case 0xF3:
				System.out.println("ADDD, EXTENDED, 7 cycles, 3 Bytes, -aaaa");
				break;
			case 0xF4:
				System.out.println("ANDB, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xF5:
				System.out.println("BITB, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xF6:
				System.out.println("LDB, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xF7:
				System.out.println("STB, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xF8:
				System.out.println("EORB, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xF9:
				System.out.println("ADCB, EXTENDED, 5 cycles, 3 Bytes, aaaaa");
				break;
			case 0xFA:
				System.out.println("ORB, EXTENDED, 5 cycles, 3 Bytes, -aa0-");
				break;
			case 0xFB: // extended ADDB
				System.out.println("ADDB, EXTENDED, 5 cycles, 3 Bytes, aaaaa");
				break;
			case 0xFC:
				System.out.println("LDD, EXTENDED, 6 cycles, 3 Bytes, -aa0-");
				break;
			case 0xFD:
				System.out.println("STD, EXTENDED, 6 cycles, 3 Bytes, -aa0-");
				break;
			case 0xFE:
				System.out.println("LDU, EXTENDED, 6 cycles, 3 Bytes, -aa0-");
				break;
			case 0xFF:
				System.out.println("STU, EXTENDED, 6 cycles, 3 Bytes, -aa0-");
				break;

			default:
				System.out.println("Not a valid Op Code");

			}

		} catch (NumberFormatException e) {

			JOptionPane
					.showMessageDialog(
							null,
							"Incorrect syntax. Please enter only decimal or hexadecimal values.",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("incorrect syntax entered");
		}
	}

	public void p1Codes(String oc2) {

		System.out.println("Expanding OpCode - Page 1");
		switch (Integer.parseInt(oc2, 16)) {
		// integer oc2 to be created from concatenation of first two chunks
		case 0x1021:
			System.out.println("LBRN, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1022:
			System.out.println("LBHI, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1023:
			System.out.println("LBLS, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1024:
			System.out
					.println("LBHS/LBCC, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1025:
			System.out
					.println("LBLO/LBCS, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1026:
			System.out.println("LBNE, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1027:
			System.out.println("LBEQ, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1028:
			System.out.println("LBVC, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x1029:
			System.out.println("LBVS, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x102A:
			System.out.println("LBPL, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x102B:
			System.out.println("LBMI, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x102C:
			System.out.println("LBGE, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x102D:
			System.out.println("LBLT, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x102E:
			System.out.println("LBGT, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x102F:
			System.out.println("LBLE, RELATIVE, 5(6) cycles, 4 Bytes, -----");
		case 0x103F:
			System.out.println("SWI2, INHERENT, 20 cycles, 2 Bytes, -----");
		case 0x1083:
			System.out.println("CMPD, IMMEDIATE, 5 cycles, 4 Bytes, -aaaa");
		case 0x108C:
			System.out.println("CMPY, IMMEDIATE, 5 cycles, 4 Bytes, -aaaa");
		case 0x108E:
			System.out.println("LDY, IMMEDIATE, 4 cycles, 4 Bytes, -aa0-");
		case 0x1093:
			System.out.println("CMPD, DIRECT, 7 cycles, 3 Bytes, -aaaa");
		case 0x109C:
			System.out.println("CMPY, DIRECT, 7 cycles, 3 Bytes, -aaaa");
		case 0x109E:
			System.out.println("LDY, DIRECT, 6 cycles, 3 Bytes, -aa0-");
		case 0x109F:
			System.out.println("STY, DIRECT, 6 cycles, 3 Bytes, -aa0-");
		case 0x10A3:
			System.out.println("CMPD, INDEXED, 7 cycles, 3 Bytes, -aaaa");
		case 0x10AC:
			System.out.println("CMPY, INDEXED, 7 cycles, 3 Bytes, -aaaa");
		case 0x10AE:
			System.out.println("LDY, INDEXED, 6 cycles, 3 Bytes, -aa0-");
		case 0x10AF:
			System.out.println("STY, INDEXED, 6 cycles, 3 Bytes, -aa0-");
		case 0x10B3:
			System.out.println("CMPD, EXTENDED, 8 cycles, 4 Bytes, -aaaa");
		case 0x10BC:
			System.out.println("CMPY, EXTENDED, 8 cycles, 4 Bytes, -aaaa");
		case 0x10BE:
			System.out.println("LDY, EXTENDED, 7 cycles, 4 Bytes, -aa0-");
		case 0x10BF:
			System.out.println("STY, EXTENDED, 7 cycles, 4 Bytes, -aa0-");
		case 0x10CE:
			System.out.println("LDS, IMMEDIATE, 4 cycles, 4 Bytes, -aa0-");
		case 0x10DE:
			System.out.println("LDS, DIRECT, 6 cycles, 3 Bytes, -aa0-");
		case 0x10DF:
			System.out.println("STS, DIRECT, 6 cycles, 3 Bytes, -aa0-");
		case 0x10EE:
			System.out.println("LDS, INDEXED, 6 cycles, 3 Bytes, -aa0-");
		case 0x10EF:
			System.out.println("STS, INDEXED, 6 cycles, 3 Bytes, -aa0-");
		case 0x10FE:
			System.out.println("LDS, EXTENDED, 7 cycles, 4 Bytes, -aa0-");
		case 0x10FF:
			System.out.println("STS, EXTENDED, 7 cycles, 4 Bytes, -aa0-");
		}

	}

	public void p2Codes(String oc3) {

		System.out.println("Expanding OpCode - Page 2");

		switch (Integer.parseInt(oc3, 16)) {

		case 0x113F:
			System.out.println("SWI3, INHERENT, 20 cycles, 2 Bytes, -----");
		case 0x1183:
			System.out.println("STS, IMMEDIATE, 5 cycles, 4 Bytes, -aaaa");
		case 0x118C:
			System.out.println("STS, IMMEDIATE, 5 cycles, 4 Bytes, -aaaa");
		case 0x1193:
			System.out.println("STS, DIRECT, 7 cycles, 3 Bytes, -aaaa");
		case 0x119C:
			System.out.println("STS, DIRECT, 7 cycles, 3 Bytes, -aaaa");
		case 0x11A3:
			System.out.println("STS, INDEXED, 7 cycles, 3 Bytes, -aaaa");
		case 0x11AC:
			System.out.println("STS, INDEXED, 7 cycles, 3 Bytes, -aaaa");
		case 0x11B3:
			System.out.println("STS, EXTENDED, 8 cycles, 4 Bytes, -aaaa");
		case 0x11BC:
			System.out.println("STS, EXTENDED, 8 cycles, 4 Bytes, -aaaa");

		}
	}
}
