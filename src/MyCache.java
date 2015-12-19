import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class MyCache {
	//design cache as following: fully associative, unified cache
	//16 cache line, each line with 4 16 bits word
	//using fifo, write back;
	//cacheline is like: lineID, tag(14bits), dirtybit(1bit)
	// word1(16bits), word2(16bits), word3(16bits)word4(16bits)
	public static String[] cacheline = new String[16];
	public static String id = "";
	public static String tag="";
	public static String offset = "";
	public static String dirtybit = "";
	public static String word1 = "";
	public static String word2 = "";
	public static String word3 = "";
	public static String word4 = "";
	public static int cacheseq = 0;
	
	public static void initMyCache()
	{
		for(int i = 0; i < 16; i++)
		{
			cacheline[i] = new String();
		}
		cacheseq = 0;
	}
	
	public static String buling(String str)//make the binary number  16 bit long
	{
		String temp = str;
		for(int i = temp.length();i < 16;i++)
		{
			temp = "0" + temp;
		}
		return temp;
	}	
	public static boolean check(String addr)
	{
		String temp = buling(addr);
		setTag(temp.substring(0, 14));
		String temptag = "";
		for(int i = 0; i < 16; i++)
		{	
			if (!cacheline[i].isEmpty()) {
				temptag = cacheline[i].substring(0, 14);
				if (temptag.equals(tag))
					return true;
			}
		}
		return false;
	}
	
	public static int getCacheLineNumber(String addr)
	{
		String temp = buling(addr);
		setTag(temp.substring(0, 14));
		String temptag = "";
		for(int i = 0; i < 16; i++)
		{	
			if (!cacheline[i].isEmpty()) {
				temptag = cacheline[i].substring(0, 14);
				if (temptag.equals(tag))
					return i;
			}
		}
		return -1;
	}
	public static void SeqSelfIncrease()
	{
		if(cacheseq != 15)
			cacheseq = cacheseq + 1;
		else
			cacheseq = 0;
	}
	public static void updateToMemory(String cache)
	{
		dirtybit = cache.substring(14, 15);
		if(dirtybit.equals("0"))
		{
			MyGUI.cacheconsole.append("DIRTY BIT IS 0,NO CHANGE\n");
		}
		else if(dirtybit.equals("1"))
		{
			tag = cache.substring(0, 14);
			String addr1 = tag + "00";
			String addr2 = tag + "01";
			String addr3 = tag + "10";
			String addr4 = tag + "11";
			String word1 = cache.substring(15, 31);
			String word2 = cache.substring(31, 47);
			String word3 = cache.substring(47, 63);
			String word4 = cache.substring(63);
			MyMemory.insertByAddr(addr1, word1);
			MyMemory.insertByAddr(addr2, word2);
			MyMemory.insertByAddr(addr3, word3);
			MyMemory.insertByAddr(addr4, word4);
			MyGUI.cacheconsole.append("DIRTY BIT IS 1,UPDATE TO MEMORY SUCCESSFUL\n");
		}
	}
	public static void insertToCacheFromMemory(String addr)//读入：在判断缓存中不存在要找的内容时调用该函数，并从内存加载内容
	{
		String temp = buling(addr);
		if (cacheline[cacheseq].isEmpty()) {
			tag = temp.substring(0, 14);
			offset = temp.substring(14);
			word1 = MyMemory.getValue(tag + "00");
			word2 = MyMemory.getValue(tag + "01");
			word3 = MyMemory.getValue(tag + "10");
			word4 = MyMemory.getValue(tag + "11");
			dirtybit = "0";
			cacheline[cacheseq] = tag + dirtybit + word1 + word2 + word3
					+ word4;
			MyGUI.cacheconsole.append("MISS,SO PUT IN CACHELINE " + cacheseq + "\n");
			SeqSelfIncrease();
		}
		else if(!cacheline[cacheseq].isEmpty())
		{
			updateToMemory(cacheline[cacheseq]);
			tag = temp.substring(0, 14);
			offset = temp.substring(14);
			word1 = MyMemory.getValue(tag + "00");
			word2 = MyMemory.getValue(tag + "01");
			word3 = MyMemory.getValue(tag + "10");
			word4 = MyMemory.getValue(tag + "11");
			dirtybit = "0";
			cacheline[cacheseq] = tag + dirtybit + word1 + word2 + word3
					+ word4;
			MyGUI.cacheconsole.append("MISS,SO PUT IN LINE " + cacheseq + " AND UPDATE THE OLD ONE\n");
			SeqSelfIncrease();
			
		}

	}
	
	public static String getValueFromCache(String addr)//读入：在判断缓存中存在要找的内容时调用该函数
	{
		String temp = buling(addr);
		int linenumber = getCacheLineNumber(temp);
		offset = temp.substring(14);
		word1 = cacheline[linenumber].substring(15, 31);
		word2 = cacheline[linenumber].substring(31, 47);
		word3 = cacheline[linenumber].substring(47, 63);
		word4 = cacheline[linenumber].substring(63);
		MyGUI.cacheconsole.append("HIT!!!CACHE HAS THE CONTENTS\n");
		if(offset.equals("00"))
			return word1;
		else if(offset.equals("01"))
			return word2;
		else if (offset.equals("10"))
			return word3;
		else if(offset.equals("11"))
			return word4;
		else 
		{
			return "";
		}

		
	}
	
	public static void writeBack(String addr, String value)//写回，若在缓存则写回缓存，若不在则先取到缓存再写入
	{
		String temp = buling(addr);
		int linenumber = getCacheLineNumber(temp);
		tag = temp.substring(0, 14);
		dirtybit = "1";
		offset = temp.substring(14);
		word1 = cacheline[linenumber].substring(15, 31);
		word2 = cacheline[linenumber].substring(31, 47);
		word3 = cacheline[linenumber].substring(47, 63);
		word4 = cacheline[linenumber].substring(63);
		if (offset.equals("00"))
			word1 = value;
		else if (offset.equals("01"))
			word2 = value;
		else if (offset.equals("10"))
			word3 = value;
		else if (offset.equals("11"))
			word4 = value;
		cacheline[linenumber] = tag + dirtybit + word1 + word2 + word3 + word4;
		MyGUI.cacheconsole.append("WRITE BACKE TO CACHE FIRST\n");

	}
	
	public static void finalwriteback()//当程序结束时将dirty bit为1的cacheline写回内存并更新文件
	{
		for(int i = 0; i < 16; i++)
		{
			if(cacheline[i].equals(""))
				break;
			else if(cacheline[i].substring(14, 15).equals("1"))
			{
				//System.out.println(i);
				tag = cacheline[i].substring(0, 14);
				String addr1 = tag + "00";
				String addr2 = tag + "01";
				String addr3 = tag + "10";
				String addr4 = tag + "11";
				String word1 = cacheline[i].substring(15, 31);
				String word2 = cacheline[i].substring(31, 47);
				String word3 = cacheline[i].substring(47, 63);
				String word4 = cacheline[i].substring(63);
				MyMemory.insertByAddr(addr1, word1);
				MyMemory.insertByAddr(addr2, word2);
				MyMemory.insertByAddr(addr3, word3);
				MyMemory.insertByAddr(addr4, word4);
				
			}
		}
		try {
			File filePath = new File("MyMemory.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(
					filePath));
			bw.write("				THE CONTENTS OF MYMEMORY\n");
			bw.write("							GROUP 5\n");
			for(int i = 0;i< 2048; i++)
			{
				bw.write("THE CONTNETS OF ADDRESS "+Integer.toBinaryString(i) + " IS ");
				for(int j = 0 ; j < 16; j++)
				{
					bw.write(""+MyMemory.memory[i][j]);
				}
				bw.write("\n");
			}
			bw.flush();
			bw.close();
		} catch (Exception e2) {
			System.out.println("Exception:" + e2);
		}
	
	}
	
	public static void print()
	{
		MyGUI.cacheprinter.setText("");
		MyGUI.cacheprinter.append("ID" + "            TAG       " + "      DIRTY BIT" + "          WORD1         " + "          "
				+ "  WORD2    " + "   "
				+ "              WORD3      " + "              WORD4       \n");
		for(int i = 0; i < 16; i++)
		{
			String istr = "";
			if(i < 10)
			{
				istr = "0" + Integer.toString(i);
			}
			else 
				istr = Integer.toString(i);
			if (!cacheline[i].isEmpty()) {
				MyGUI.cacheprinter.append(istr +"  "+ cacheline[i].substring(0, 14) + "       "
						+ cacheline[i].substring(14, 15) + "      "
						+ cacheline[i].substring(15, 31) + "  "
						+ cacheline[i].substring(31, 47) + "  "
						+ cacheline[i].substring(47, 63) + "  "
						+ cacheline[i].substring(63));
				MyGUI.cacheprinter.append("\n");
			}
		}
	}
	
//	public static void main(String[] args) {
//		tag = "01000000000001";
//		word1 = "0100000000000100";
//		word2 = "0100000000000101";
//		word3 = "0100000000000110";
//		word4 = "0100000000000111";
//		dirtybit = "0";
//		String aaa = tag + dirtybit + word1 + word2 + word3 + word4;
//		initMyCache();
//		String temp = "0100000000000101";
//		String a = "00000000101";
//		cacheline[0] = aaa;
//		//System.out.println(getCacheLineNumber(temp));
//		print();
//		
//	}
	public static String[] getCacheline() {
		return cacheline;
	}
	public static void setCacheline(String[] cacheline) {
		MyCache.cacheline = cacheline;
	}
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		MyCache.id = id;
	}
	public static String getTag() {
		return tag;
	}
	public static void setTag(String tag) {
		MyCache.tag = tag;
	}
	public static String getDirtybit() {
		return dirtybit;
	}
	public static void setDirtybit(String dirtybit) {
		MyCache.dirtybit = dirtybit;
	}
	public static String getWord1() {
		return word1;
	}
	public static void setWord1(String word1) {
		MyCache.word1 = word1;
	}
	public static String getWord2() {
		return word2;
	}
	public static void setWord2(String word2) {
		MyCache.word2 = word2;
	}
	public static String getWord3() {
		return word3;
	}
	public static void setWord3(String word3) {
		MyCache.word3 = word3;
	}
	public static String getWord4() {
		return word4;
	}
	public static void setWord4(String word4) {
		MyCache.word4 = word4;
	}
	public static String getOffset() {
		return offset;
	}
	public static void setOffset(String offset) {
		MyCache.offset = offset;
	}
	

	

}
