import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


public class MyProLoader {
	public  static String programid;
	public static void Load(String id)
	{
		int pc = 100;
		String pcstr = "000001100100";//100
		if(id.equals("01"))
		{
			MyMemory.initMemory();
			MyMemory.insertByAddr("1011", "0000000010000000");//boot			
			MyPC.setPcval(pcstr);//start from pc = 100
			IX1.setIxval("0000000111110100");//DATA START 500
			IX2.setIxval("0000000001100100");//PC START 100
			IX3.setIxval("0000000111110100");//DATA START 500

			//存入前n个数
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001100000010100");//AIR R0 20    100
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111010100000011");//IN R1 3
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111100000000001");//OUT R0 1
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111100100000001");//OUT R1 1
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000100101000000");//STR R1 X1 0 0
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010100100011111");//STX X1 0 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000011100011111");//LDR R3 0 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001101100000001");//AIR R3 1
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000101100011111");//STR R3 0 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010010100011111");//LDX X1 0 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0100000010000001");//SOB R0 X2 1 
//			//输入要比较的数
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111010100000011");//IN R1 3
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000100101000000");//STR R1 X1 0 0
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111100000000001");//OUT R0 3
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111100100000001");//OUT R1 3
//			//开始处理
//			//把要比较的数和那20个数各自相减
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010100100011110");//STX X1 30 ADDRESS OF GOOD NUMBER
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010101100011101");//STX X3 29 ADDRESS OF COMPARE NUMBER
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000010101000000");//LDR R1 X1  0 GET GOOD NUMBER
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001010111000000");//SMR R1 X3  0 MINUS FROM MEMORY
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0100010110010111");//JGE R1 X2 23 >=0 then jump
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000100100011100");//STR R1 28
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000110100000000");//LDA R1 0
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001010100011100");//SMR R1 28     122
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000100100011011");//STR R1 27
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000011011000000");//LDR R2 X3 0
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000101000011010");//STR R2 26
//			
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010011000001011");//LDX X2 11
//
//			//将第一个数和要比的数的差的绝对值存在27 第一个数存在26已完成，此后需循环比较
//			
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001100000010011");//AIR R0 19
//	/*128*/	MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010101100011111");//STX X3 0 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000011100011111");//LDR R3 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001101100000001");//AIR R3 1
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000101100011111");//STR R3 0 31
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1010011100011111");//LDX X3 0 31
//
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000010101000000");//LDR R1 X1  0 GET GOOD NUMBER
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001010111000000");//SMR R1 X3  0 MINUS FROM MEMORY
//	/*135*/	MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0100010110001011");//JGE R1 X2 139 >=0 then jump
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000100100011100");//STR R1 28
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000110100000000");//LDA R1 0
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001010100011100");//SMR R1 28 
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000011011000000");//LDR R2 X3 0
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000100100011001");//STR R1 0 25
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000011100011001");//LDR R3 0 25
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0001010100011011");//SMR R1 27
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0100010110010010");//JGE R1 X2 146
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000101100011011");//STR R3 27
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000101000011010");//STR R2 26
//			
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0100000010000000");//SOB R0 X2 0
//			//output
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "0000010000011010");//LDR R0 26
//			MyMemory.insertByAddr(Integer.toBinaryString(pc++), "1111100000000001");//OUT R0 1*/
//
//
//			
			CodeTransfer.ReaderTransfer("program1.txt");
			for(int i = pc; i < pc + CodeTransfer.line; i++)
			{
				MyMemory.insertByAddr(Integer.toBinaryString(i), CodeTransfer.instruction.get(Integer.toBinaryString(i)));
			}
			//System.out.println(CodeTransfer.line);

		}
		else if (id.equals("02"))
		{
			MyPC.setPcval(pcstr);//start from pc = 100
			MyMemory.initMemory();
			MyMemory.insertByAddr("10100", "0000000001100100");//boot	20 100		
			MyMemory.insertByAddr("10101", "0000000110010000");//boot   21 400
			MyMemory.insertByAddr("10110", "0000000111110100");//boot	22 500
			MyMemory.insertByAddr("1111", "0000000000100000");//boot	15 SPACE
			MyMemory.insertByAddr("10000", "0000000000001010");//boot	16 /N
			MyMemory.insertByAddr("1001", "0000000001111110");//boot	9  126索引
			MyMemory.insertByAddr("11001", "0000000010000111");//boot	25 135
			MyMemory.insertByAddr("11000", "0000000011000111");//boot	24 199
			MyMemory.insertByAddr("10111", "0000000010101001");//boot	23 169
			MyMemory.insertByAddr("10", "0000000100000100");//boot	    2 260
			MyMemory.insertByAddr("11", "0000000011100110");//boot	    3  230







			MyMemory.insertByAddr("1010", "0000001111101000");//boot	10  1000
			MyMemory.insertByAddr("1011", "0000010001001100");//boot	11  1100
			MyMemory.insertByAddr("1100", "0000010010110000");//boot	12  1200
			MyMemory.insertByAddr("1101", "0000010100010100");//boot	13  1300
			
			MyMemory.insertByAddr("100", "0000001111101000");//boot	4  1000
			MyMemory.insertByAddr("101", "0000010001001100");//boot	5  1100
			MyMemory.insertByAddr("110", "0000010010110000");//boot	6  1200
			MyMemory.insertByAddr("111", "0000010100010100");//boot	7  1300

			



			IX1.setIxval("0000000111110100");//DATA START 500
			IX2.setIxval("0000000001100100");//PC START 100
			IX3.setIxval("0000000110010000");//SET 400
			//CodeTransfer.ReaderTransfer("program2.txt");
			CodeTransfer.ReaderTransfer("program2.txt");

			
			for(int i = pc; i < pc + CodeTransfer.line; i++)
			{
				MyMemory.insertByAddr(Integer.toBinaryString(i), CodeTransfer.instruction.get(Integer.toBinaryString(i)));
			}

			
		}
		else if(id.equals("03"))
		{
			MyPC.setPcval(pcstr);//start from pc = 100
			MyMemory.initMemory();
			
			CodeTransfer.ReaderTransfer("floattest.txt");
			MyMemory.insertByAddr("1", "0000001011001000"); //put 6.25 into address 1
			MyMemory.insertByAddr("10", "0000000111100000");//put 3.5 into address 2
			

			
			for(int i = pc; i < pc + CodeTransfer.line; i++)
			{
				MyMemory.insertByAddr(Integer.toBinaryString(i), CodeTransfer.instruction.get(Integer.toBinaryString(i)));
			}
		}
		else if(id.equals("04"))
		{
			MyPC.setPcval(pcstr);//start from pc = 100
			MyMemory.initMemory();
			CodeTransfer.ReaderTransfer("convert.txt");
			MyMemory.insertByAddr("01", "0000001010101000");
			MyMemory.insertByAddr("10", "0000000000001001");
			for(int i = pc; i < pc + CodeTransfer.line; i++)
			{
				MyMemory.insertByAddr(Integer.toBinaryString(i), CodeTransfer.instruction.get(Integer.toBinaryString(i)));
			}

			
		}
		else if (id.equals("05"))
		{
			MyPC.setPcval(pcstr);//start from pc = 100
			MyMemory.initMemory();
			CodeTransfer.ReaderTransfer("vectoradd.txt");
			MyMemory.insertByAddr("01", "0000000000001010");
			MyMemory.insertByAddr("10", "0000000000010100");
			MyMemory.insertByAddr("11",	"0000000000000011");
			MyMemory.insertByAddr("1010",	"0000000000000010");
			MyMemory.insertByAddr("1011",	"0000000000000100");
			MyMemory.insertByAddr("1100",	"0000000000000110");
			
			MyMemory.insertByAddr("10100",	"0000000000000001");
			MyMemory.insertByAddr("10101",	"0000000000000011");
			MyMemory.insertByAddr("10110",	"0000000000000101");




			for(int i = pc; i < pc + CodeTransfer.line; i++)
			{
				MyMemory.insertByAddr(Integer.toBinaryString(i), CodeTransfer.instruction.get(Integer.toBinaryString(i)));
			}

		}
		else if (id.equals("06"))
		{
			MyPC.setPcval(pcstr);//start from pc = 100
			MyMemory.initMemory();
			CodeTransfer.ReaderTransfer("vectorsub.txt");
			MyMemory.insertByAddr("01", "0000000000001010");
			MyMemory.insertByAddr("10", "0000000000010100");
			MyMemory.insertByAddr("11",	"0000000000000011");
			MyMemory.insertByAddr("1010",	"0000000000000010");
			MyMemory.insertByAddr("1011",	"0000000000000100");
			MyMemory.insertByAddr("1100",	"0000000000000110");
			
			MyMemory.insertByAddr("10100",	"0000000000000001");
			MyMemory.insertByAddr("10101",	"0000000000000011");
			MyMemory.insertByAddr("10110",	"0000000000000101");




			for(int i = pc; i < pc + CodeTransfer.line; i++)
			{
				MyMemory.insertByAddr(Integer.toBinaryString(i), CodeTransfer.instruction.get(Integer.toBinaryString(i)));
			}

		}
	
	}
	

}
