
public class MyR0 {//register0
	public static String value;

	public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		MyR0.value = value;
	}
	
	public static void initR0()
	{
		setValue("0000000000000000");
	}
	

}
