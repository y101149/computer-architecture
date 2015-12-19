
public class MyMAR {//have not used many in part1
	public static String value;

	public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		MyMAR.value = value;
	}
	
	public static void initMyMAR()
	{
		setValue("0000000000000000");
	}

}
