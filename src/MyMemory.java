
public class MyMemory {//i have not limit the range of instruction and data. because at part one we only need to run a single instruction.
	public static int [][] memory = new int [2048][16];//use int[][] to reprensent memory
	public static int curaddr = 0;
	public static int instrulengh = 16;
	public static String getValue(String addr)
	{
		String tempstr = "";
		for(int i = 0; i < instrulengh; i++)
		{
			tempstr = tempstr + memory[Integer.parseInt(addr,2)][i];
		}
		return tempstr;
	}
	
	public static void addValue(String val)
	{
		char c;
		for(int i = 0; i < instrulengh; i++)
		{
			c = val.charAt(i);
			memory[curaddr][i] = Integer.parseInt(Character.toString(c));
			curaddr++;
		}
	}
	
	public static void insertByAddr(String addr, String val)//insert data into memory according to address
	{
		int index = Integer.parseInt(addr,2);
		char c;
		//System.out.println(index);
		for(int i = 0; i < instrulengh; i++)
		{
			c = val.charAt(i);
			memory[index][i] = Integer.parseInt(Character.toString(c));
		}
	}
	public static void initMemory()//initial memory by 0
	{
		for(int i = 0; i < 2048; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				memory[i][j] = 0;
			}
		}
	}
	
	
	

}
