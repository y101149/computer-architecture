
public class MyMDR {//have not used many in part 1
	public static String value;

	public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		MyMDR.value = value;
	}
	
	public static void initMyMDR()
	{
		setValue("0000000000000000");
	}

}
