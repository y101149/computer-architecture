
public class MyIR {//instruction register
	public static String value;

	public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		MyIR.value = value;
	}
	
	public static void initMyIR()
	{
		setValue("0000000000000000");
	}

}
