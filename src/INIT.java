
public class INIT {
	public static void main(String[] args) {//initial everything we need include
		MyMemory.initMemory();//memory and all kinds of registers and set opcode 000000(temporary)
		MyR0.initR0();
		MyR1.initR1();
		MyR2.initR2();
		MyR3.initR3();
		IX1.initIX1();
		IX2.initIX2();
		IX3.initIX3();
		MyCCR.initial();
		MyMAR.initMyMAR();
		MyMDR.initMyMDR();
		MyIR.initMyIR();
		MyFR0.initMyFR0();
		MyFR1.initMyFR1();
		MyCache.initMyCache();
		MyInstruction.setOpcode("000000");//initial the gui
		MyGUI gui = new MyGUI();
		gui.run();
		
	}

}
