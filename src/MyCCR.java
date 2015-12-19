
public class MyCCR {

	public static int[] CCR = new int[4];	
	public static int[] getCCR() {
		return CCR;
	}
	public static void setCCR(int[] cCR) {
		CCR = cCR;
	}
	public static void initial(){
		for(int i=0; i<4;i++){
			CCR[i] = 0;
		}
	}
	

}
