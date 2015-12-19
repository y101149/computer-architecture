
public class MyInstruction {//identify the instructions, now split them into the following attributes
	public static String opcode;
	public static String R;
	public static String IX;
	public static String I;
	public static String ADDR;
	public static String immed;
	public static String cc;
	public static String DevID;//not use yet
	public static String rx;//not use yet
	public static String ry;//not use yet
	public static String FRF;//do not use yet
	public static String AL;
	public static String LR;
	public static String count;
	
	
	public static String getAL() {
		return AL;
	}
	public static void setAL(String aL) {
		AL = aL;
	}
	public static String getLR() {
		return LR;
	}
	public static void setLR(String lR) {
		LR = lR;
	}
	public static String getCount() {
		return count;
	}
	public static void setCount(String count) {
		MyInstruction.count = count;
	}
	public static String getOpcode() {
		return opcode;
	}
	public static void setOpcode(String opcode) {
		MyInstruction.opcode = opcode;
	}
	public static String getR() {
		return R;
	}
	public static void setR(String r) {
		R = r;
	}
	public static String getIX() {
		return IX;
	}
	public static void setIX(String iX) {
		IX = iX;
	}
	public static String getI() {
		return I;
	}
	public static void setI(String i) {
		I = i;
	}
	public static String getADDR() {
		return ADDR;
	}
	public static void setADDR(String aDDR) {
		ADDR = aDDR;
	}
	public static String getImmed() {
		return immed;
	}
	public static void setImmed(String immed) {
		MyInstruction.immed = immed;
	}
	public static String getCc() {
		return cc;
	}
	public static void setCc(String cc) {
		MyInstruction.cc = cc;
	}
	public static String getDevID() {
		return DevID;
	}
	public static void setDevID(String devID) {
		DevID = devID;
	}
	public static String getRx() {
		return rx;
	}
	public static void setRx(String rx) {
		MyInstruction.rx = rx;
	}
	public static String getRy() {
		return ry;
	}
	public static void setRy(String ry) {
		MyInstruction.ry = ry;
	}
	public static String getFRF() {
		return FRF;
	}
	public static void setFRF(String fRF) {
		FRF = fRF;
	}
	
	
}
