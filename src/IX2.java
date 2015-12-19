
public class IX2 {//index register2
	public static String ixval;

	public static String getIxval() {
		return ixval;
	}

	public static void setIxval(String ixval) {
		IX2.ixval = ixval;
	}
	public static void initIX2()
	{
		setIxval("0000000000000000");
	}

}
