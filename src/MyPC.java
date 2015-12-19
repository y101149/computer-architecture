
public class MyPC {//design for pc, 12 bits long
	public static String pcval = "000000000000";
	private static int length = 12;

	public static String getPcval() {
		return pcval;
	}

	public static void setPcval(String pcval) {
		MyPC.pcval = pcval;
	}
	
	public static void selfIncrease()
	{
		pcval = Integer.toBinaryString((Integer.parseInt(pcval, 2) + 1));
		for(int i = pcval.length(); i < length; i++)
		{
			pcval = '0' + pcval;
		}
			
	}
	public static void initPC()//12 bit pc
	{
		setPcval("000000000000");
	}
	/*public static void main(String[] args) {
		for(int i = 0; i<10; i++)
		{
			System.out.println(MyPC.getPcval());
			selfIncrease();
		}
	}*/

}
