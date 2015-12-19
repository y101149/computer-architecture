import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;



public class MyALU {
	static int bootstart = 500;
	static int characterstore = 400;
	public static boolean process()//main part of the program
	{
		
		String r = MyInstruction.getR();//decode the instructions and split the instructions
		String ix = MyInstruction.getIX();
		String i = MyInstruction.getI();
		String addr = MyInstruction.getADDR();
		String imm = MyInstruction.getImmed();
		String cc = MyInstruction.getCc();//codition code
		String rx = MyInstruction.getRx();
		String ry = MyInstruction.getRy();
		String al = MyInstruction.getAL();
		String lr = MyInstruction.getLR();
		String count = MyInstruction.getCount();
		String devid = MyInstruction.getDevID();
		
		
		if(MyInstruction.getOpcode().equals("000001"))//LDR
		{
			String ea = getEffectiveAddr(ix,i,addr);
			
			//System.out.println(ea);
			String data = "";
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("LDR ERROR\n");
				return false;
			}
			if(MyCache.check(ea))//with cache
			{
				data = MyCache.getValueFromCache(ea);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data = MyMemory.getValue(ea);
			}
//			if(i.equals("1") && ix.equals("01"))
//				System.out.println(data);
			setValueToRegById(r,data);	
			MyPC.selfIncrease();
			return true;
		}
		else if(MyInstruction.getOpcode().equals("110010"))//LDFR
		{
			String ea = getEffectiveAddr(ix, i, addr);
			String data = "";
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("LDFR ERROR\n");
				return false;
			}
			if(MyCache.check(ea))//with cache
			{
				data = MyCache.getValueFromCache(ea);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data = MyMemory.getValue(ea);
			}
//			if(i.equals("1") && ix.equals("01"))
//				System.out.println(data);
			setValueToFRById(r, data);
			//setValueToRegById(r,data);	
			MyPC.selfIncrease();
			return true;
		}
		else if(MyInstruction.getOpcode().equals("100101"))//CNVRT
		{
			//System.out.println(MyInstruction.IX +"  " + MyInstruction.I +"  " + MyInstruction.ADDR);
			String ea = getEffectiveAddr(ix, i, addr);
			String data1 = "";
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("CNVRT ERROR\n");
				return false;
			}
			if(MyCache.check(ea))//with cache
			{
				data1 = MyCache.getValueFromCache(ea);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data1 = MyMemory.getValue(ea);
			}
			
			String f = getValueFromRegById(r);
			if(f.equals("0000000000000000"))
			{
				int flag1 = 1,sign1 = 1;
				if(data1.charAt(0) == '1')
				{
					flag1 = -1;
				}
				
				if(data1.charAt(1) == '1')
					sign1 = -1;
				
				String exp1, mant1;
				int expnum1, mantnum1tmp;
				double mantnum1;
				
				exp1 = data1.substring(2, 8);
				mant1 = data1.substring(8);
				expnum1 = sign1 * Integer.parseInt(exp1,2);
				mantnum1tmp = Integer.parseInt(mant1);
//				mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//				mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
				
				mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 


				String addbin1;
				
				DecimalFormat decimalFormat = new DecimalFormat("#.###");//格式化设置  
				
		       // addbin1 = Double.toString(mantnum1);
				//addbin2 = Double.toString(mantnum2);
		        addbin1 = decimalFormat.format(mantnum1);

//				
				double adddec1,adddec2;
				String resultdec, resultbin;
				//System.out.println(addbin2);
				adddec1 = Double.parseDouble(binaryToDecimal(addbin1));
				int good = (int)adddec1;
				String goodstr;
				if(good >= 0)
				{
					goodstr = buling(Integer.toBinaryString(good));
					
				}
				else
				{
					String temp = Integer.toBinaryString(good - 1).substring(16);//transfer to binary string
					goodstr = "1";
					for (int j = 1; j < 16; j++) //make it a 16 bit long string
					{
						if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
							goodstr = goodstr + '1';
						else if(temp.charAt(j) == '1')
							goodstr = goodstr + '0';

					}
					
				}
				System.out.println(  "FLOAT--->FIXED" );
				setValueToRegById(r, goodstr);
				//System.out.println(good);
				
			}
			else if(f.equals("0000000000000001"))
			{
				String result = "";
				int sign1 = 1;
				int exp = 0, start = 16;
				String mant = "";
				String expstr = "";
				if(data1.charAt(0) == '0')
					sign1 = 1;
				else if(data1.charAt(0) == '1')
					sign1 = -1;
				if(sign1 == 1)
				{
					for(int j = 0; j < 16; j++)
					{
						if(data1.charAt(j) != '0')
						{
							exp = 15 - j;
							start = j;
							break;
						}
						
					}
					expstr = Integer.toBinaryString(exp);
					for (int j = expstr.length(); j < 7; j++)
					{
						expstr = '0' + expstr;
					}
					mant = data1.substring(start);
					if(mant.length() > 8)
						mant = mant.substring(0, 8);
					for(int j = mant.length(); j < 8; j++)
					{
						mant = mant + '0';
					}
					result = '0' + expstr + mant;
					
					
				}
				else if (sign1 == -1)
				{
					for(int j = 1; j < 16; j++)
					{
						if(data1.charAt(j) != '0')
						{
							exp = 15 - j;
							start = j;
							break;
						}
						
					}
					expstr = Integer.toBinaryString(exp);
					for (int j = expstr.length(); j < 7; j++)
					{
						expstr = '0' + expstr;
					}
					mant = data1.substring(start);
					if(mant.length() > 8)
						mant = mant.substring(0, 8);
					for(int j = mant.length(); j < 8; j++)
					{
						mant = mant + '0';
					}
					result = '1' + expstr + mant;
				}
				System.out.println(  "FIXED--->FLOAT" );
				setValueToFRById("00", result);
		
				
					
			}
			MyPC.selfIncrease();
			return true;
		}
		else if(MyInstruction.getOpcode().equals("000010"))//STR
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("STR ERROR\n");
				return false;
			}
			String data = "";
			data = getValueFromRegById(r);

			if(MyCache.check(ea))
			{
				MyCache.writeBack(ea, data);///////////////////
			}
			else{
				MyCache.insertToCacheFromMemory(ea);//if not in cache, fetch it from memory and write back
				MyCache.writeBack(ea, data);
				//MyMemory.insertByAddr(ea, data);
			}
			MyPC.selfIncrease();

			return true;
		}
		else if(MyInstruction.getOpcode().equals("110011"))//STFR
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("STFR ERROR\n");
				return false;
			}
			String data = "";
			//data = getValueFromRegById(r);
			data = getValueFromFRById(r);

			if(MyCache.check(ea))
			{
				MyCache.writeBack(ea, data);///////////////////
			}
			else{
				MyCache.insertToCacheFromMemory(ea);//if not in cache, fetch it from memory and write back
				MyCache.writeBack(ea, data);
				//MyMemory.insertByAddr(ea, data);
			}
			MyPC.selfIncrease();

			return true;
		}
		else if (MyInstruction.getOpcode().equals("100011"))//VADD
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("VADD ERROR\n");
				return false;
			}
			String ea2 = Integer.toBinaryString(Integer.parseInt(ea,2) + 1);

			if(ea.equals("") || Integer.parseInt(ea2, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("VADD ERROR\n");
				return false;
			}
			String len = getValueFromRegById(r);
			String location1,location2;
			String val1,val2;
			if(MyCache.check(ea))//with cache
			{
				location1 = MyCache.getValueFromCache(ea);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				location1 = MyMemory.getValue(ea);
			}
		
			if(MyCache.check(ea2))//with cache
			{
				location2 = MyCache.getValueFromCache(ea2);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea2);
				location2 = MyMemory.getValue(ea2);
			}
			for (int j = 0; j < Integer.parseInt(len,2); j++)
			{
				int locationstr1,locationstr2;
				locationstr1 = Integer.parseInt(location1,2) + j;
				locationstr2 = Integer.parseInt(location2,2) + j;
				if(MyCache.check(Integer.toBinaryString(locationstr1)))//with cache
				{
					val1 = MyCache.getValueFromCache(Integer.toBinaryString(locationstr1));////////////////////
				}
				else {
					MyCache.insertToCacheFromMemory(Integer.toBinaryString(locationstr1));
					val1 = MyMemory.getValue(Integer.toBinaryString(locationstr1));
				}
				if(MyCache.check(Integer.toBinaryString(locationstr2)))//with cache
				{
					val2 = MyCache.getValueFromCache(Integer.toBinaryString(locationstr2));////////////////////
				}
				else {
					MyCache.insertToCacheFromMemory(Integer.toBinaryString(locationstr2));
					val2 = MyMemory.getValue(Integer.toBinaryString(locationstr2));
				}
				int temp;
				System.out.println(val1+"  "+ val2);
				temp = Integer.parseInt(val1,2)+Integer.parseInt(val2,2);
				val1 = buling(Integer.toBinaryString(temp));
				System.out.println(val1);
				if(MyCache.check(Integer.toBinaryString(locationstr1)))
				{
					MyCache.writeBack(Integer.toBinaryString(locationstr1), val1);///////////////////
				}
				else{
					MyCache.insertToCacheFromMemory(Integer.toBinaryString(locationstr1));//if not in cache, fetch it from memory and write back
					MyCache.writeBack(Integer.toBinaryString(locationstr1), val1);
					//MyMemory.insertByAddr(ea, data);
				}
				//MyMemory.insertByAddr(location1, val1);
				
			}
			
			MyPC.selfIncrease();
			return true;
			
		}
		else if (MyInstruction.getOpcode().equals("100100"))//VSUB
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("VSUB ERROR\n");
				return false;
			}
			String ea2 = Integer.toBinaryString(Integer.parseInt(ea,2) + 1);

			if(ea.equals("") || Integer.parseInt(ea2, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("VSUB ERROR\n");
				return false;
			}
			String len = getValueFromRegById(r);
			String location1,location2;
			String val1,val2;
			if(MyCache.check(ea))//with cache
			{
				location1 = MyCache.getValueFromCache(ea);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				location1 = MyMemory.getValue(ea);
			}
		
			if(MyCache.check(ea2))//with cache
			{
				location2 = MyCache.getValueFromCache(ea2);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea2);
				location2 = MyMemory.getValue(ea2);
			}
			for (int j = 0; j < Integer.parseInt(len,2); j++)
			{
				int locationstr1,locationstr2;
				locationstr1 = Integer.parseInt(location1,2) + j;
				locationstr2 = Integer.parseInt(location2,2) + j;
				if(MyCache.check(Integer.toBinaryString(locationstr1)))//with cache
				{
					val1 = MyCache.getValueFromCache(Integer.toBinaryString(locationstr1));////////////////////
				}
				else {
					MyCache.insertToCacheFromMemory(Integer.toBinaryString(locationstr1));
					val1 = MyMemory.getValue(Integer.toBinaryString(locationstr1));
				}
				if(MyCache.check(Integer.toBinaryString(locationstr2)))//with cache
				{
					val2 = MyCache.getValueFromCache(Integer.toBinaryString(locationstr2));////////////////////
				}
				else {
					MyCache.insertToCacheFromMemory(Integer.toBinaryString(locationstr2));
					val2 = MyMemory.getValue(Integer.toBinaryString(locationstr2));
				}
				int temp;
				System.out.println(val1+"  "+ val2);
				temp = Integer.parseInt(val1,2) - Integer.parseInt(val2,2);
				val1 = buling(Integer.toBinaryString(temp));
				System.out.println(val1);
				if(MyCache.check(Integer.toBinaryString(locationstr1)))
				{
					MyCache.writeBack(Integer.toBinaryString(locationstr1), val1);///////////////////
				}
				else{
					MyCache.insertToCacheFromMemory(Integer.toBinaryString(locationstr1));//if not in cache, fetch it from memory and write back
					MyCache.writeBack(Integer.toBinaryString(locationstr1), val1);
					//MyMemory.insertByAddr(ea, data);
				}
				//MyMemory.insertByAddr(location1, val1);
				
			}
			MyPC.selfIncrease();
			return true;
		}
		else if(MyInstruction.getOpcode().equals("000011"))//LDA
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("LDA ERROR\n");
				return false;
			}
			String data = ea;
			data = buling(data);
			setValueToRegById(r, data);
			MyPC.selfIncrease();

			return true;
		}
		else if (MyInstruction.getOpcode().equals("001010"))//JZ
		{
			String judge = getValueFromRegById(r);
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("")||Integer.parseInt(ea,2) > 2048)
			{
				MyGUI.console.append("JZ ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			if(Integer.parseInt(judge,2) == 0)//make pc 12 bits long, because ea can be 5 bit or 16 bit
			{
				MyPC.setPcval(buling(ea).substring(4));
				
			}
			else
			{
				MyPC.selfIncrease();
			}
			return true;
		}
		
		else if (MyInstruction.opcode.equals("001011")) //JNE
		{
			String judge = getValueFromRegById(r);
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("")||Integer.parseInt(ea,2) > 2048)
			{
				MyGUI.console.append("JNE ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			if(Integer.parseInt(judge,2) != 0)//make pc 12 bits long, because ea can be 5 bit or 16 bit
			{
				MyPC.setPcval(buling(ea).substring(4));
				
			}
			else
			{
				MyPC.selfIncrease();
			}
			return true;
		}
		//等ccr设置了来检查
		else if(MyInstruction.opcode.equals("001100"))//JCC
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("")||Integer.parseInt(ea,2) > 2048)
			{
				MyGUI.console.append("JCC ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			if(MyCCR.getCCR()[Integer.parseInt(cc, 2)] == 1)
			{
				MyPC.setPcval(buling(ea).substring(4));

			}
			else
			{
				MyPC.selfIncrease();
			}
			return true;
		}
		else if(MyInstruction.opcode.equals("001101"))//JMA
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("")||Integer.parseInt(ea,2) > 2048)
			{
				MyGUI.console.append("JMA ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			MyPC.setPcval(buling(ea).substring(4));

			return true;
		}
		else if(MyInstruction.opcode.equals("001110"))//JSR
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("")||Integer.parseInt(ea,2) > 2048)
			{
				MyGUI.console.append("JSR ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			int temp = Integer.parseInt(MyPC.getPcval(),2) + 1;
			MyR3.setValue(buling(Integer.toBinaryString(temp)));
			MyPC.setPcval(buling(ea).substring(4));
			return true;
			
		}
		else if(MyInstruction.opcode.equals("001111"))//RFS
		{
			MyR0.setValue(buling(imm));
			String temp = MyR3.getValue();
			if(temp.equals("") || Integer.parseInt(temp, 2) > 2048)
			{
				MyGUI.console.append("RFS ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			MyPC.setPcval(buling(MyR3.getValue()).substring(4));
			return true;
		}
		else if(MyInstruction.opcode.equals("010000"))//SOB
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if (ea.equals("") || Integer.parseInt(ea, 2) > 2048) {
				MyGUI.console.append("SOB ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			StringBuffer data = new StringBuffer(getValueFromRegById(r));
			int flag1 = 1;
			if (data.charAt(0) == '1')// judge whether the number is a positive
										// or a negative
			{
				flag1 = -1;// if flag == -1 then it is a negative
				data.setCharAt(0, '0');
			}

			int sum;
			sum = Integer.parseInt(data.toString(), 2) * flag1 - 1;// calculate
																	// the sum
																	// according
																	// to flag
			// REG = REG - 1
			if (sum < 0) {
				if (sum <= -Math.pow(2, 16)) {
					MyGUI.console.setText("NUMBER TOO SMALL OVERFLOW\n");
					return false;
				}
				String temp = Integer.toBinaryString(sum - 1).substring(16);// 因为反码需要转换
				String result = "1";
				for (int j = 1; j < 16; j++) {
					if (temp.charAt(j) == '0')
						result = result + '1';
					else if (temp.charAt(j) == '1')
						result = result + '0';

				}
				setValueToRegById(r, result);
				MyPC.selfIncrease();

				return true;
			} else if (sum > 0) {
				if (sum > Math.pow(2, 16)) {
					MyGUI.console.setText("NUMBER TOO LARGE OVERFLOW\n");
					return false;
				}
				setValueToRegById(r, buling(Integer.toBinaryString(sum)));
				MyPC.setPcval(buling(ea).substring(4));
				return true;
			}
			else if(sum == 0)
			{
				setValueToRegById(r, buling(Integer.toBinaryString(sum)));
				MyPC.selfIncrease();
				return true;
			}

		}
		else if(MyInstruction.opcode.equals("010001"))//JGE
		{

			String ea = getEffectiveAddr(ix, i, addr);
			if (ea.equals("") || Integer.parseInt(ea, 2) > 2048) {
				MyGUI.console.append("SOB ERROR, NO SUCH ADDRESS\n");
				return false;
			}
			StringBuffer data = new StringBuffer(getValueFromRegById(r));
			int flag1 = 1;
			if (data.charAt(0) == '1')// judge whether the number is a positive
										// or a negative
			{
				flag1 = -1;// if flag == -1 then it is a negative
				data.setCharAt(0, '0');
			}

			int sum;
			sum = Integer.parseInt(data.toString(), 2) * flag1;// calculate
																// the sum
																// according
																// to flag
			if (sum < 0)
			{
				if (sum <= -Math.pow(2, 16)) {
					MyGUI.console.setText("NUMBER TOO SMALL OVERFLOW\n");
					return false;
				}
				String temp = Integer.toBinaryString(sum - 1).substring(16);// 因为反码需要转换
				String result = "1";
				for (int j = 1; j < 16; j++) {
					if (temp.charAt(j) == '0')
						result = result + '1';
					else if (temp.charAt(j) == '1')
						result = result + '0';

				}

				MyPC.selfIncrease();
				return true;
			} 
			else if (sum >= 0) {
				if (sum > Math.pow(2, 16)) {
					MyGUI.console.setText("NUMBER TOO LARGE OVERFLOW\n");
					return false;
				}
				MyPC.setPcval(buling(ea).substring(4));
				return true;
			}

		}
		
		else if(MyInstruction.getOpcode().equals("010100"))//MLT
		{
			if(!rx.equals("00")&&!rx.equals("10") || !ry.equals("00")&&!ry.equals("10"))
			{
				MyGUI.console.append("RX AND RY MUST BE 0 OR 2");
				return false;
			}
			StringBuffer data1 = new StringBuffer(getValueFromRegById(rx));
			StringBuffer data2 = new StringBuffer(getValueFromRegById(ry));
			int flag1 = 1, flag2 = 1;
			int data1int,data2int,signbit = 1;
			
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
				data1.setCharAt(0, '0');
			}
			if(data2.charAt(0) == '1')
			{
				flag2 = -1;
				data2.setCharAt(0, '0');
			}
			signbit = flag1*flag2;
			data1int = Integer.parseInt(data1.toString(),2);
			data2int = Integer.parseInt(data2.toString(),2);
			int value = data1int * data2int;
			String temp = Integer.toBinaryString(value);
			if(value > Math.pow(2, 32)-1)
			{
				MyGUI.console.append("OVERFLOW TOO BIG\n");
				MyCCR.CCR[0] = 1;//set overflow bit
				return true;
			}
			for(int j = temp.length(); j < 31;j++)
			{
				temp = '0' + temp;
			}
			if(signbit == 1)
				temp = '0' + temp;
			else if(signbit == -1)
				temp = '1' + temp;
			String next = "";
			if(rx.equals("00"))
				next = "01";
			else if(rx.equals("10"))
				next = "11";
			setValueToRegById(rx, temp.substring(0, 16));
			setValueToRegById(next, temp.substring(16));
			//System.out.println(next);
			System.out.println(data1int*flag1+" * " +data2int*flag2);
			System.out.println(value*signbit);
			System.out.println(temp);
		
			MyPC.selfIncrease();
			return true;			
		}
		else if(MyInstruction.getOpcode().equals("010101"))//DVD余数可以为负数 -7/3 = -2 and -1?!!!!!
		{//it is remainder not mod

			if(!rx.equals("00")&&!rx.equals("10") || !ry.equals("00")&&!ry.equals("10"))
			{
				MyGUI.console.append("RX AND RY MUST BE 0 OR 2\n");
				return false;
			}
			StringBuffer data1 = new StringBuffer(getValueFromRegById(rx));
			StringBuffer data2 = new StringBuffer(getValueFromRegById(ry));
			int flag1 = 1, flag2 = 1;
			int data1int,data2int;
			
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
				data1.setCharAt(0, '0');
			}
			if(data2.charAt(0) == '1')
			{
				flag2 = -1;
				data2.setCharAt(0, '0');
			}
			data1int = Integer.parseInt(data1.toString(),2);
			data2int = Integer.parseInt(data2.toString(),2);
			if(data2int == 0)
			{
				MyGUI.console.append("RY CAN NOT BE ZERO\n");
				MyCCR.CCR[2] = 1;
				return true;
			}
			int quotient = (data1int* flag1)/(data2int*flag2);
			int remainder = (data1int*flag1) % (data2int*flag2);
			String temp1 = "1";
			String temp = "";
			if(quotient < 0)
			{
				temp = Integer.toBinaryString((quotient-1)).substring(16);
				for(int j = 1; j < 16; j++)
				{
					if(temp.charAt(j) == '0')
					{
						temp1 = temp1 + '1';
					}
					else if(temp.charAt(j) == '1')
						temp1 = temp1 + '0';	
				}
			}
			else if(quotient >= 0)
			{
				temp1 = Integer.toBinaryString(quotient);
				temp1 = buling(temp1);
			}
			temp = "";
			String temp2 = "1";
			if(remainder < 0)
			{
				temp = Integer.toBinaryString((remainder-1)).substring(16);
				for(int j = 1; j < 16; j++)
				{
					if(temp.charAt(j) == '0')
					{
						temp2 = temp2 + '1';
					}
					else if(temp.charAt(j) == '1')
						temp2 = temp2 + '0';	
				}
			}
			else if(remainder >= 0)
			{
				 temp2 = Integer.toBinaryString(remainder);
				 temp2 = buling(temp2);

			}
			
			
			
			String next = "";
			if(rx.equals("00"))
				next = "01";
			else if(rx.equals("10"))
				next = "11";
			setValueToRegById(rx, temp1);
			setValueToRegById(next, temp2);
			//System.out.println(next);
			System.out.println(data1int*flag1+" / " +data2int*flag2);
			System.out.println(quotient+" and "+remainder);
			System.out.println(temp1+" and "+temp2);
			
			MyPC.selfIncrease();
			return true;			
		
		}
		else if(MyInstruction.getOpcode().equals("010110"))//TRR
		{
			String temp1 = getValueFromRegById(rx);
			String temp2 = getValueFromRegById(ry);
			if(temp1.equals(temp2))
				MyCCR.CCR[3] = 1;
			else
				MyCCR.CCR[3] = 0;
			MyPC.selfIncrease();
			/*for(int j = 0 ;j <= 3;j++)
			{
				System.out.println(MyCCR.CCR[j]);
			}*/
			return true;
		}
		else if(MyInstruction.getOpcode().equals("010111"))//AND
		{
			String temp1 = getValueFromRegById(rx);
			String temp2 = getValueFromRegById(ry);
			String temp = "";
			for(int j = 0;j < 16; j++)
			{
				int andleft = Integer.parseInt(Character.toString(temp1.charAt(j)));
				int andright = Integer.parseInt(Character.toString(temp2.charAt(j)));
				if((andleft & andright) == 1)
				{
					temp = temp + '1';
				}
				else if((andleft & andright) == 0)
				{
					temp = temp + '0';
				}
			}
			setValueToRegById(rx, temp);
			//System.out.println(temp);
			MyPC.selfIncrease();
			return true;
		}
		
		else if(MyInstruction.getOpcode().equals("011000"))//ORR
		{
			String temp1 = getValueFromRegById(rx);
			String temp2 = getValueFromRegById(ry);
			String temp = "";
			for(int j = 0;j < 16; j++)
			{
				int andleft = Integer.parseInt(Character.toString(temp1.charAt(j)));
				int andright = Integer.parseInt(Character.toString(temp2.charAt(j)));
				if((andleft | andright) == 1)
				{
					temp = temp + '1';
				}
				else if((andleft | andright) == 0)
				{
					temp = temp + '0';
				}
			}
			setValueToRegById(rx, temp);
			//System.out.println(temp);
			MyPC.selfIncrease();
			return true;
		}
		else if(MyInstruction.getOpcode().equals("011001"))//NOT
		{
			String temp1 = getValueFromRegById(rx);
			String not = "";
			for(int j = 0; j < 16; j++)
			{
				if(temp1.charAt(j) == '0')
					not = not + '1';
				else if(temp1.charAt(j) == '1')
					not = not + '0';
			}
			setValueToRegById(rx, not);
			MyPC.selfIncrease();
			return true;
		}
		
		else if(MyInstruction.getOpcode().equals("011111"))//SRC补码问题需要解决
		{
			String lrtemp = lr;
			String altemp = al;
			int counttemp = Integer.parseInt(count,2);
			String valtemp = getValueFromRegById(r);
			int []buffer = new int[16];
			String result = "";
			for(int j = 0; j < 16; j++)
			{
				buffer[j] = Integer.parseInt(Character.toString(valtemp.charAt(j)));
				//System.out.println(buffer[j]);
			}
			
			if(altemp.equals("0"))//arithmetically
			{
				if(lrtemp.equals("0"))//right算术右移
				{
					for(int k = counttemp; k > 0; k--)
					{
						if(buffer[15] == 1)
						{
							MyGUI.console.append("ARITHMETICALLY RIGHT SHIFT UNDERFLOW\n");
							MyCCR.CCR[1] = 1;
							//System.out.println(MyCCR.CCR[1]);
							MyPC.selfIncrease();
							return true;//underflow when left shift
						}
						for(int m = 15; m > 1; m--)
						{
							buffer[m] = buffer[m-1];
						}
						buffer[1] = 0;
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					System.out.println(result);
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
				else if(lrtemp.equals("1"))//left算术左移
				{
					for(int k = counttemp; k > 0; k--)
					{
						if(buffer[1] == 1)
						{
							MyGUI.console.append("ARITHMETICALLY LEFT SHIFT OVERFLOW\n");
							MyCCR.CCR[0] = 1;
							MyPC.selfIncrease();
							return true;
						}
						for(int m = 1; m < 15;m++)
						{
							buffer[m] = buffer[m + 1];
						}
						buffer[15] = 0;
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
				
			}
			else if(altemp.equals("1"))//logically
			{
				if(lrtemp.equals("0"))//logically right
				{
					for(int k = counttemp; k > 0; k--)
					{
						if(buffer[15] == 1)
						{
							MyGUI.console.append("LOGICALLY RIGHT SHIFT UNDERFLOW\n");
							MyCCR.CCR[1] = 1;
							MyPC.selfIncrease();

							return true;//underflow when left shift
						}
						for(int m = 15; m > 0; m--)
						{
							buffer[m] = buffer[m - 1];
							
						}
						buffer[0] = 0;
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
				else if(lrtemp.equals("1"))//logically left
				{
					for(int k = counttemp; k > 0; k--)
					{
						if(buffer[0] == 1)
						{
							MyGUI.console.append("LOGICALLY LEFT SHIFT OVERFLOW\n");
							MyCCR.CCR[0] = 1;
							MyPC.selfIncrease();

							return true;
						}
						for(int m = 0; m < 15;m++)
						{
							buffer[m] = buffer[m + 1];
						}
						buffer[15] = 0;					
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
			}
		}
		else if(MyInstruction.getOpcode().equals("100000"))//RRC
		{
			String lrtemp = lr;
			String altemp = al;
			System.out.println(count);

			int counttemp = Integer.parseInt(count,2);
			String valtemp = getValueFromRegById(r);
			int []buffer = new int[16];
			String result = "";
			for(int j = 0; j < 16; j++)
			{
				buffer[j] = Integer.parseInt(Character.toString(valtemp.charAt(j)));
				//System.out.println(buffer[j]);
			}
			if(altemp.equalsIgnoreCase("0"))//arithmetically
			{
				if(lrtemp.equals("0"))//right rotate
				{
					for(int k = counttemp; k > 0; k--)
					{
						int temp15 = buffer[15];
						for(int m = 15; m > 1; m--)
						{
							buffer[m] = buffer[m-1];
						}
						buffer[1] = temp15;
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
				else if(lrtemp.equals("1"))//left rotate
				{				
					for(int k = counttemp; k > 0; k--)
					{
						int temp1 = buffer[1];
						for(int m = 1; m < 15;m++)
						{
							buffer[m] = buffer[m + 1];
						}
						buffer[15] = temp1;
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
			}
			else if(altemp.equals("1"))//logically
			{
				if(lrtemp.equals("0"))//logically right rotate
				{
					for(int k = counttemp; k > 0; k--)
					{
						int temp15 = buffer[15];
						for(int m = 15; m > 0; m--)
						{
							buffer[m] = buffer[m - 1];
							
						}
						buffer[0] = temp15;
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
				else if(lrtemp.equals("1"))//logically left rotate
				{
					for(int k = counttemp; k > 0; k--)
					{
						int temp0 = buffer[0];
						for(int m = 0; m < 15; m++)
						{
							buffer[m] = buffer[m + 1];
						}
						buffer[15] = temp0;					
					}
					for(int l = 0; l < buffer.length; l++)
					{
						result = result + Integer.toString(buffer[l]);
					}
					setValueToRegById(r, result);
					MyPC.selfIncrease();
					return true;
				}
			}
		}
		
		
		else if(MyInstruction.getOpcode().equals("111101"))//IN读入寄存器？不科学如果数为11111五位用asc2码需要8*5 40位
		{

			int dev = Integer.parseInt(devid,2);
			if(dev == 0)
			{
				MyGUI.console.append("PLZ INPUT VALUE\n");
				while (true) 
				{
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					int val = 0;
					int flag = 1;
					if (!MyINPUT.getInputval().equals("")
							&& !MyINPUT.getInputval().equals("\n")) {
						String content = MyINPUT.getInputval();
						//System.out.println(content);
						for(int j = 0; j < content.length(); j++)
						{
							if (content.charAt(j) !='0' && content.charAt(j) != '1'&& content.charAt(j) != '2'
									&& content.charAt(j) != '3'&& content.charAt(j) != '4'&& content.charAt(j) != '5'
									&& content.charAt(j) != '6'&& content.charAt(j) != '7'&& content.charAt(j) != '8'
									&& content.charAt(j) != '9')
							{
								flag = 0;
								break;
							}
							
						}
						if(flag == 1)
						{
							
							val = Integer.parseInt(content);

						}
						else if(flag == 0){
							MyGUI.console.append("KEYBOARD NEED A INTEGER\n");
						}
						
						String temp = "";
						temp = buling(Integer.toBinaryString(val));
						MyINPUT.setInputval("");
						setValueToRegById(r, temp);
						//MyMemory.insertByAddr(Integer.toBinaryString(bootstart++),temp);
						break;
						
					}
					
					
				
				}
			}
//		    if (dev == 0)//自定义整数读写含负数处理 但因为要表示1到65536的数故不能用该方法
//			{
//
//				MyGUI.console.append("PLZ INPUT VALUE\n");
//				while (true) {
//					try {
//						Thread.sleep(300);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//					//System.out.println(MyINPUT.getInputval().equals(""));
//					int val = 0;
//					int flag = 1;
//					int sign = 1;
//					if (!MyINPUT.getInputval().equals("")
//							&& !MyINPUT.getInputval().equals("\n")) {
//						String content = MyINPUT.getInputval();
//						//System.out.println(content);
//						for(int j = 0; j < content.length(); j++)
//						{
//							if (content.charAt(j) !='0' && content.charAt(j) != '1'&& content.charAt(j) != '2'
//									&& content.charAt(j) != '3'&& content.charAt(j) != '4'&& content.charAt(j) != '5'
//									&& content.charAt(j) != '6'&& content.charAt(j) != '7'&& content.charAt(j) != '8'
//									&& content.charAt(j) != '9'&& content.charAt(j) != '-')
//							{
//								flag = 0;
//								break;
//							}
//							
//						}
//						if(flag == 1)
//						{
//							if(content.charAt(0) == '-')
//							{
//								sign = -1;
//								val = sign * Integer.parseInt(content.substring(1));
//							}
//							else	
//								val = sign * Integer.parseInt(content);
//
//						}
//						else if(flag == 0){
//							MyGUI.console.append("KEYBOARD NEED A INTEGER\n");
//						}
//						
//						String temp1 = "";
//						String temp = "";
//						if(val >= 0)
//							temp = buling(Integer.toBinaryString(val));
//						else{
//							temp = "1";
//							temp1 = Integer.toBinaryString(val - 1).substring(16);
//							for(int j = 1;j<16;j++)
//							{
//								if(temp1.charAt(j) == '0')
//									temp = temp + '1';
//								else if(temp1.charAt(j) == '1')
//									temp = temp + '0';
//							}
//						}
//						MyINPUT.setInputval("");
//						setValueToRegById(r, temp);
//						//MyMemory.insertByAddr(Integer.toBinaryString(bootstart++),temp);
//						break;
//					}
//					
//
//				}
//			
//			}
			else if(dev == 3)//console keyboard
			{
				characterstore = 400;

				MyGUI.console.append("PLZ INPUT VALUE\n");
				while (true) {
					
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println(MyINPUT.getInputval().equals(""));

					if (!MyINPUT.getInputval().equals("")
							&& !MyINPUT.getInputval().equals("\n")) {
						String content = MyINPUT.getInputval();
						//System.out.println(content);

						for (int j = 0; j < content.length(); j++) {
							String str1 = Integer.toBinaryString((int) (content
									.charAt(j)));
							setValueToRegById(r, buling(str1));
							MyMemory.insertByAddr(Integer.toBinaryString(characterstore++),buling(str1));
							
							//System.out.println(str1);
						}
						MyINPUT.setInputval("");
						break;
					}
					

				}
			}
			else if(dev == 2)//文件输入以后写
			{
				bootstart = 500;
				
//				File file = new File("paragraph.txt");
				File file = new File("paragraph.txt");
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					String sentence;
					while((sentence = br.readLine())!=null)
					{
						//System.out.println(sentence);
						sentence = sentence + "\n";
						for(int j = 0; j < sentence.length(); j++)
						{
							String binarystr = Integer.toBinaryString((int)(sentence.charAt(j)));
							MyMemory.insertByAddr(Integer.toBinaryString(bootstart++), buling(binarystr));

						}
					}
		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

			MyPC.selfIncrease();
			return true;

		}
		else if(MyInstruction.getOpcode().equals("111110"))//OUT
		{
			int dev = Integer.parseInt(devid,2);
//			if(dev == 1)//direct input register's contents直接输出不按asc码 可运算负数但表示范围少
//			{
//				int val = 0;
//				String temp = getValueFromRegById(r);
//				if(temp.charAt(0) == '0')
//					val = Integer.parseInt(temp, 2);
//				else if(temp.charAt(0) == '1')
//				{
//					val = -1 * Integer.parseInt(temp.substring(1),2);
//				}
//				MyGUI.console.append(temp + " (" + val + ')'+'\n');
//			}
//			else if(dev == 2)//(USE ASC22 TRANSFER)print character to console as output(only character)
//			{
//				int asc2word = Integer.parseInt(getValueFromRegById(r), 2);
//				MyGUI.console.append(Character.toString((char)asc2word));
//			}
			if(dev == 1)//direct input register's contents直接输出不按asc码 可运算负数
			{
				int val = 0;
				String temp = getValueFromRegById(r);
				val = Integer.parseInt(temp, 2);
				MyGUI.console.append(temp + " (" + val + ')'+'\n');
			}
			else if(dev == 2)//(USE ASC22 TRANSFER)print character to console as output(only character)
			{
				int asc2word = Integer.parseInt(getValueFromRegById(r), 2);
				MyGUI.console.append(Character.toString((char)asc2word));
			}
			
			
			
			
			MyPC.selfIncrease();
			return true;
		}

		else if(MyInstruction.getOpcode().equals("101001"))//LDX
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("LDX ERROR\n");
				return false;
			}
			String data = "";
			if(MyCache.check(ea))//with cache
			{
				data = MyCache.getValueFromCache(ea);////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data = MyMemory.getValue(ea);
			}
			
			setValueToIxById(r, data);
			MyPC.selfIncrease();
			
			return true;
			
		}
		else if(MyInstruction.getOpcode().equals("101010"))//STX
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("STX ERROR\n");
				return false;
			}
			String data = "";
			data = getValueFromIxById(r);

			if(MyCache.check(ea))
			{
				MyCache.writeBack(ea, data);///////////////////
			}
			else{
				MyCache.insertToCacheFromMemory(ea);//if not in cache, fetch it from memory and write back
				MyCache.writeBack(ea, data);
				//MyMemory.insertByAddr(ea, data);
			}
			MyPC.selfIncrease();

			return true;
			
		}
		else if (MyInstruction.getOpcode().equals("100001"))//FADD
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){
				MyGUI.console.append("FADD ERROR\n");
				return false;
			}
			String data1;
			if(MyCache.check(ea))//with cache
			{
				data1 = MyCache.getValueFromCache(ea);
				//data1 = new StringBuffer(MyCache.getValueFromCache(ea));////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data1 = MyMemory.getValue(ea);
				//data1 = new StringBuffer(MyMemory.getValue(ea));
			}
			String data2 = getValueFromFRById(r);
			
			//System.out.println(data2);

			//StringBuffer data2 = new StringBuffer(getValueFromFRById(r));
			int flag1 = 1, flag2 = 1, sign1 = 1,sign2 = 1;
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
			}
			if(data2.charAt(0) == '1')
			{
				flag2 = -1;
			}
			if(data1.charAt(1) == '1')
				sign1 = -1;
			if(data2.charAt(1) == '1')
				sign2 = -1;
			String exp1, exp2, mant1, mant2;
			int expnum1,expnum2, mantnum1tmp, mantnum2tmp;
			double mantnum1, mantnum2;
			
			exp1 = data1.substring(2, 8);
			exp2 = data2.substring(2, 8);
			mant1 = data1.substring(8);
			mant2 = data2.substring(8);
			expnum1 = sign1 * Integer.parseInt(exp1,2);
			expnum2 = sign2 * Integer.parseInt(exp2,2);
			mantnum1tmp = Integer.parseInt(mant1);
			mantnum2tmp = Integer.parseInt(mant2);
//			mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//			mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
			
			mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 
			mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2) / Math.pow(10, 7); 


			String addbin1, addbin2;
			
			DecimalFormat decimalFormat = new DecimalFormat("#.###");//格式化设置  
			
	       // addbin1 = Double.toString(mantnum1);
			//addbin2 = Double.toString(mantnum2);
	        addbin1 = decimalFormat.format(mantnum1);
			addbin2 = decimalFormat.format(mantnum2);

//			
			double adddec1,adddec2;
			String resultdec, resultbin;
			//System.out.println(addbin2);
			adddec1 = Double.parseDouble(binaryToDecimal(addbin1));
			adddec2 = Double.parseDouble(binaryToDecimal(addbin2));
			resultdec = Double.toString(adddec1 + adddec2);
			resultbin = decimalTobinary(resultdec);
			
			System.out.println(addbin2 + "+" + addbin1 + "=" + resultbin);
			System.out.println(adddec1 + "+" + adddec2 + "=" + resultdec);

			int expr1 = 0,expr2 = 0;
			String exp;
			String dishu = "";
//			System.out.println(adddec1 + "+" + adddec2 + "=" + resultdec);
//			System.out.println(addbin1 + "+" + addbin2 + "=" + resultbin);

			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) != '-' && resultbin.charAt(j) != '.')
				{
					dishu = dishu + resultbin.charAt(j);
				}
			}
			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) == '.')
				{
					expr1 = j;
					break;
					
				}
			}
			if(resultbin.charAt(0) == '0' || resultbin.charAt(0) == '-'&& resultbin.charAt(1) == '0')
			{
				for(int j = 0; j < resultbin.length(); j++)
				{
					if(resultbin.charAt(j) == '1')
					{
						expr2 = j;
						break;
						
					}
				}
			}
			else
			{
				for(int j = 0; j < resultbin.length(); j++)
				{
					if(resultbin.charAt(j) == '1')
					{
						expr2 = j + 1;
						break;
						
					}
				}
			}
			if(expr1 - expr2 >= 0)
			{
				exp = Integer.toBinaryString((expr1 - expr2));
				exp = '0' + exp;
			
			}
			else
			{
				String temp = Integer.toBinaryString(expr1 - expr2 - 1).substring(16);//transfer to binary string
				String result = "1";
				for (int j = 1; j < 7; j++) //make it a 16 bit long string
				{
					if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
						result = result + '1';
					else if(temp.charAt(j) == '1')
						result = result + '0';

				}
				exp = result;
			}
			if(exp.length() > 7)
				MyGUI.console.append("FADD OVERFLOW\n");
			if(exp.charAt(0) == '0'){
				for(int j = exp.length(); j < 7; j++)
				{
					exp = '0' + exp;
				}
			}
			else if(exp.charAt(0)=='1')
			{
				String temp = exp.substring(1, exp.length());
				for(int j = exp.length();j<6; j++)
				{
					temp = '0' + temp;
				}
				exp = '1' + temp;
			}

			for(int j = dishu.length(); j < 8; j++)
			{
				dishu = dishu + '0';
			}
			
			String result;
			String signbit = "0";
			if(resultbin.charAt(0) == '-')
				signbit = "1";
			result = signbit + exp + dishu;
			if(result.length() > 16)
				result = result.substring(0, 16);
			setValueToFRById(r, result);
			MyPC.selfIncrease();
			//System.out.println(result);
			return true;
		
	
			
			
		}
		
		else if (MyInstruction.getOpcode().equals("100010"))//FSUB
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){
				MyGUI.console.append("FADD ERROR\n");
				return false;
			}
			String data1;
			if(MyCache.check(ea))//with cache
			{
				data1 = MyCache.getValueFromCache(ea);
				//data1 = new StringBuffer(MyCache.getValueFromCache(ea));////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data1 = MyMemory.getValue(ea);
				//data1 = new StringBuffer(MyMemory.getValue(ea));
			}
			String data2 = getValueFromFRById(r);
			
			//System.out.println(data2);

			//StringBuffer data2 = new StringBuffer(getValueFromFRById(r));
			int flag1 = 1, flag2 = 1, sign1 = 1,sign2 = 1;
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
			}
			if(data2.charAt(0) == '1')
			{
				flag2 = -1;
			}
			if(data1.charAt(1) == '1')
				sign1 = -1;
			if(data2.charAt(1) == '1')
				sign2 = -1;
			String exp1, exp2, mant1, mant2;
			int expnum1,expnum2, mantnum1tmp, mantnum2tmp;
			double mantnum1, mantnum2;
			
			exp1 = data1.substring(2, 8);
			exp2 = data2.substring(2, 8);
			mant1 = data1.substring(8);
			mant2 = data2.substring(8);
			expnum1 = sign1 * Integer.parseInt(exp1,2);
			expnum2 = sign2 * Integer.parseInt(exp2,2);
			mantnum1tmp = Integer.parseInt(mant1);
			mantnum2tmp = Integer.parseInt(mant2);
//			mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//			mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
			
			mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 
			mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2) / Math.pow(10, 7); 


			String addbin1, addbin2;
			
			DecimalFormat decimalFormat = new DecimalFormat("#.#########");//格式化设置  
			
	       // addbin1 = Double.toString(mantnum1);
			//addbin2 = Double.toString(mantnum2);
	        addbin1 = decimalFormat.format(mantnum1);
			addbin2 = decimalFormat.format(mantnum2);

//			if(addbin1.length() > 9)
//			{
//				addbin1 = addbin1.substring(0, 10);
//			}
//			if (addbin2.length() > 9)
//			{
//				addbin2 = addbin2.substring(0, 10);
//			}
			
			double adddec1,adddec2;
			String resultdec, resultbin;
			//System.out.println(addbin2);
			adddec1 = Double.parseDouble(binaryToDecimal(addbin1));
			adddec2 = Double.parseDouble(binaryToDecimal(addbin2));
			resultdec = Double.toString(adddec2 - adddec1);
			resultbin = decimalTobinary(resultdec);
			
			System.out.println(addbin2 + "-" + addbin1 + "=" + resultbin);
			System.out.println(adddec2 + "-" + adddec1 + "=" + resultdec);

			int expr1 = 0,expr2 = 0;
			String exp;
			String dishu = "";
//			System.out.println(adddec1 + "+" + adddec2 + "=" + resultdec);
//			System.out.println(addbin1 + "+" + addbin2 + "=" + resultbin);

			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) != '-' && resultbin.charAt(j) != '.')
				{
					dishu = dishu + resultbin.charAt(j);
				}
			}
			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) == '.')
				{
					expr1 = j;
					break;
					
				}
			}
			if(resultbin.charAt(0) == '0' || resultbin.charAt(0) == '-'&& resultbin.charAt(1) == '0')
			{
				for(int j = 0; j < resultbin.length(); j++)
				{
					if(resultbin.charAt(j) == '1')
					{
						expr2 = j;
						break;
						
					}
				}
			}
			else
			{
				for(int j = 0; j < resultbin.length(); j++)
				{
					if(resultbin.charAt(j) == '1')
					{
						expr2 = j + 1;
						break;
						
					}
				}
			}
			if(expr1 - expr2 >= 0)
			{
				exp = Integer.toBinaryString((expr1 - expr2));
				exp = '0' + exp;
			
			}
			else
			{
				String temp = Integer.toBinaryString(expr1 - expr2 - 1).substring(16);//transfer to binary string
				String result = "1";
				for (int j = 1; j < 7; j++) //make it a 16 bit long string
				{
					if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
						result = result + '1';
					else if(temp.charAt(j) == '1')
						result = result + '0';

				}
				exp = result;
			}
			if(exp.length() > 7)
				MyGUI.console.append("FADD OVERFLOW\n");
			if(exp.charAt(0) == '0'){
				for(int j = exp.length(); j < 7; j++)
				{
					exp = '0' + exp;
				}
			}
			else if(exp.charAt(0)=='1')
			{
				String temp = exp.substring(1, exp.length());
				for(int j = exp.length();j<6; j++)
				{
					temp = '0' + temp;
				}
				exp = '1' + temp;
			}

			for(int j = dishu.length(); j < 8; j++)
			{
				dishu = dishu + '0';
			}
			
			String result;
			String signbit = "0";
			if(resultbin.charAt(0) == '-')
				signbit = "1";
			result = signbit + exp + dishu;
			if(result.length() > 16)
				result = result.substring(0, 16);
			setValueToFRById(r, result);
			MyPC.selfIncrease();
			//System.out.println(result);
			return true;
		
	
			
			
		}
		else if(MyInstruction.getOpcode().equals("000100"))//AMR   
		{
			//positive 
			/*String ea = getEffectiveAddr(ix, i, addr);
			String data1 = MyMemory.getValue(ea);
			String data2 = getValueFromRegById(r);
			int sum;
			sum = Integer.parseInt(data1,2) + Integer.parseInt(data2,2);
			setValueToRegById(r,buling(Integer.toBinaryString(sum)));
			return true;*/
			
			//negative
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){
				MyGUI.console.append("AMR ERROR\n");
				return false;
			}
			StringBuffer data1;
			if(MyCache.check(ea))//with cache
			{
				data1 = new StringBuffer(MyCache.getValueFromCache(ea));////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data1 = new StringBuffer(MyMemory.getValue(ea));
			}
			//data1 = new StringBuffer(MyMemory.getValue(ea));
			StringBuffer data2 = new StringBuffer(getValueFromRegById(r));
			int flag1 = 1, flag2 = 1;
			if(data1.charAt(0) == '1')//judge whether the number is a positive or a negative
			{
				flag1 = -1;//if flag == -1 then it is a negative
				data1.setCharAt(0, '0');
			}
			if(data2.charAt(0) == '1')
			{
				flag2 = -1;//the same as above
				data2.setCharAt(0, '0');
			}
			int sum;
			sum = Integer.parseInt(data1.toString(),2)*flag1 + Integer.parseInt(data2.toString(),2)*flag2;//calculate the sum according to flag
			//System.out.println(sum);//use java + operator to calculate the sum
			if (sum < 0) {
				if(sum <= -Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO SMALL OVERFLOW\n");
				String temp = Integer.toBinaryString(sum - 1).substring(16);//transfer to binary string
				String result = "1";
				for (int j = 1; j < 16; j++) //make it a 16 bit long string
				{
					if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
						result = result + '1';
					else if(temp.charAt(j) == '1')
						result = result + '0';

				}
				setValueToRegById(r, result);
				MyPC.selfIncrease();

				return true;
			}
			else if(sum >= 0)//if sum > 0 then easy
			{
				if(sum > Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO LARGE OVERFLOW\n");
				setValueToRegById(r, buling(Integer.toBinaryString(sum)));
				MyPC.selfIncrease();

				return true;
			}
			
			
			
			
		}
		else if(MyInstruction.getOpcode().equals("000101"))//SMR  almost like AMR
		{
			String ea = getEffectiveAddr(ix, i, addr);
			if(ea.equals("") || Integer.parseInt(ea, 2) > 2048 ){//limit the range of address
				MyGUI.console.append("SMR ERROR\n");
				return false;
			}
			StringBuffer data1;
			if(MyCache.check(ea))//with cache
			{
				data1 = new StringBuffer(MyCache.getValueFromCache(ea));////////////////////
			}
			else {
				MyCache.insertToCacheFromMemory(ea);
				data1 = new StringBuffer(MyMemory.getValue(ea));
			}
			//StringBuffer data1 = new StringBuffer(MyMemory.getValue(ea));
			StringBuffer data2 = new StringBuffer(getValueFromRegById(r));
			int flag1 = 1, flag2 = 1;
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
				data1.setCharAt(0, '0');
			}
			if(data2.charAt(0) == '1')
			{
				flag2 = -1;
				data2.setCharAt(0, '0');
			}
			int sum;
			sum =   Integer.parseInt(data2.toString(),2)*flag2 - Integer.parseInt(data1.toString(),2)*flag1;
//			if(i.equals("1")&&r.equals("10"))
//				System.out.println(data1 + "   "+ data2);
			if (sum < 0) {
				if(sum <= -Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO SMALL OVERFLOW");
				String temp = Integer.toBinaryString(sum - 1).substring(16);
				String result = "1";
				for (int j = 1; j < 16; j++) 
				{
					if(temp.charAt(j) == '0')
						result = result + '1';
					else if(temp.charAt(j) == '1')
						result = result + '0';

				}
				setValueToRegById(r, result);
				MyPC.selfIncrease();

				return true;
			}
			else if(sum >= 0)
			{
				if(sum > Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO LARGE OVERFLOW");
				setValueToRegById(r, buling(Integer.toBinaryString(sum)));
				MyPC.selfIncrease();

				return true;
			}
			
			
		}
		else if(MyInstruction.getOpcode().equals("000110"))//AIR
		{
			StringBuffer data1 = new StringBuffer(getValueFromRegById(r));//get data1
			int flag1 = 1;			
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
				data1.setCharAt(0, '0');
			}
			int sum;
			sum =   Integer.parseInt(data1.toString(),2)*flag1 + Integer.parseInt(imm,2);
			//System.out.println(sum);
			if (sum < 0) {
				if(sum <= -Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO SMALL OVERFLOW");
				String temp = Integer.toBinaryString(sum - 1).substring(16);
				String result = "1";
				for (int j = 1; j < 16; j++) 
				{
					if(temp.charAt(j) == '0')
						result = result + '1';
					else if(temp.charAt(j) == '1')
						result = result + '0';

				}
				setValueToRegById(r, result);
				MyPC.selfIncrease();

				return true;
			}
			else if(sum >= 0)
			{
				if(sum > Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO LARGE OVERFLOW");
				setValueToRegById(r, buling(Integer.toBinaryString(sum)));
				MyPC.selfIncrease();

				return true;
			}
			
		}
		else if(MyInstruction.getOpcode().equals("000111"))//SIR
		{
			StringBuffer data1 = new StringBuffer(getValueFromRegById(r));
			int flag1 = 1;			
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
				data1.setCharAt(0, '0');
			}
			int sum;
			sum =   Integer.parseInt(data1.toString(),2)*flag1 - Integer.parseInt(imm,2);
			//System.out.println(sum);
			if (sum < 0) {
				if(sum <= -Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO SMALL OVERFLOW");
				String temp = Integer.toBinaryString(sum - 1).substring(16);
				String result = "1";
				for (int j = 1; j < 16; j++) 
				{
					if(temp.charAt(j) == '0')
						result = result + '1';
					else if(temp.charAt(j) == '1')
						result = result + '0';

				}
				setValueToRegById(r, result);
				MyPC.selfIncrease();

				return true;
			}
			else if(sum >= 0)
			{
				if(sum > Math.pow(2, 16))
					MyGUI.console.setText("NUMBER TOO LARGE OVERFLOW");
				setValueToRegById(r, buling(Integer.toBinaryString(sum)));
				MyPC.selfIncrease();

				return true;
			}
			
		}
		return false;
		
	}
	public static void setValueToIxById(String id, String value)//store to IX register by ID
	{
		//System.out.println(id);
		if(id.equals("01"))
			IX1.setIxval(value);
		else if(id.equals("10"))
			IX2.setIxval(value);
		else if(id.equals("11"))
			IX3.setIxval(value);
		else if(id.equals("00"))
		{
			MyGUI.console.append("IX CAN NOT BE SET 0\n");
		}
	}
	public static String getValueFromIxById(String id)
	{
		String temp = "";
		if(id.equals("01"))
			temp = IX1.getIxval();
		else if(id.equals("10"))
			temp = IX2.getIxval();
		else if(id.equals("11"))
			temp = IX3.getIxval();
		else if(id.equals("00"))
		{
			MyGUI.console.append("IX CAN NOT BE SET ZERO\n");
		}
		return temp;
	}
	public static void setValueToFRById(String id, String value)
	{
		if(id.equals("00"))
		{
			MyFR0.setValue(value);
		}
		else if(id.equals("01"))
		{
			MyFR1.setValue(value);
		}
		else
		{
			MyGUI.console.append("WRONG FR ID\n");
		}
	}
	public static String getValueFromFRById(String id)
	{
		String temp = "";
		if(id.equals("00"))
		{
			temp = MyFR0.getValue();
		}
		else if(id.equals("01"))
		{
			temp = MyFR1.getValue();
		}
		else
		{
			MyGUI.console.append("WRONG FR ID\n");
		}
		return temp;
	}
	public static void setValueToRegById(String id, String value)
	{
		if(id.equals("00"))
			MyR0.setValue(value);
		else if(id.equals("01"))
			MyR1.setValue(value);
		else if(id.equals("10"))
			MyR2.setValue(value);
		else if(id.equals("11"))
			MyR3.setValue(value);
	}
	
	public static String getValueFromRegById(String id)
	{
		String temp = "";
		if(id.equals("00"))
			temp = MyR0.getValue();
		else if(id.equals("01"))
			temp = MyR1.getValue();
		else if(id.equals("10"))
			temp = MyR2.getValue();
		else if(id.equals("11"))
			temp = MyR3.getValue();
		
		return temp;
		
		
	}
	//可能需要改负数正数问题import part!! geteffective address according to ix and i
	public static String getEffectiveAddr(String ix, String i, String addr)//find the effective address according to ix and i
	{
		String temp = "";
		if(i.equals("0"))//直接寻址direct address
		{
			if(ix.equals("00"))//直接寻址且无需ix寄存器 no indexing
			{
				temp = addr;
				//System.out.println(temp);
			}
			else if(ix.equals("01")) //with indexing 01
			{
				temp = Integer.toBinaryString(Integer.parseInt(IX1.getIxval(), 2) + Integer.parseInt(addr,2));
				
				//System.out.println(temp);
			}
			else if(ix.equals("10"))//with indexing 02
			{
				temp = Integer.toBinaryString(Integer.parseInt(IX2.getIxval(),2) + Integer.parseInt(addr,2));
				
			}
			else if(ix.equals("11"))//with indixing 03
			{
				temp = Integer.toBinaryString(Integer.parseInt(IX3.getIxval(),2) + Integer.parseInt(addr,2));
				
			}
			
		
		}
		else if(i.equals("1"))//间接寻址 需访问内存indirect address
		{	

			String tempadd = "";
			if(ix.equals("00"))//no indexing
			{
				//System.out.println("indirect addressing");
				tempadd = addr;
				if(MyCache.check(tempadd))
				{
					temp = MyCache.getValueFromCache(tempadd);
				}
				else{
					MyCache.insertToCacheFromMemory(tempadd);
					temp = MyMemory.getValue(tempadd);
					//System.out.println(tempadd+"    "+temp);
				}
				//temp = MyMemory.getValue(tempadd);
			}
			else if(ix.equals("01"))//with indexing 1
			{

				tempadd = Integer.toBinaryString(Integer.parseInt(IX1.getIxval(),2) + Integer.parseInt(addr,2));
				if(Integer.parseInt(tempadd, 2)>2048)
					MyGUI.console.append("LOADING ERROR(INVALID ADDRESS > 2048)\n");
				//System.out.println("hehe"+ tempadd);
				else
				{
					if(MyCache.check(tempadd))
					{
						temp = MyCache.getValueFromCache(tempadd);
					}
					else{
						MyCache.insertToCacheFromMemory(tempadd);
						temp = MyMemory.getValue(tempadd);
					}
					//System.out.println(tempadd);
					//System.out.println(temp);
				}
				//System.out.println(temp);
			}
			else if(ix.equals("10"))//with indexing 2
			{
				tempadd = Integer.toBinaryString(Integer.parseInt(IX2.getIxval(),2) + Integer.parseInt(addr,2));
				if(Integer.parseInt(tempadd, 2)>2048)
					MyGUI.console.append("LOADING ERROR(INVALID ADDRESS > 2048)\n");
				else
				{	if(MyCache.check(tempadd))
					{
						temp = MyCache.getValueFromCache(tempadd);
					}
					else{
						MyCache.insertToCacheFromMemory(tempadd);
						temp = MyMemory.getValue(tempadd);
					}
					//temp = MyMemory.getValue(tempadd);
				}

			}
			else if(ix.equals("11"))//with indexing3
			{
				tempadd = Integer.toBinaryString(Integer.parseInt(IX3.getIxval(),2) + Integer.parseInt(addr,2));
				if(Integer.parseInt(tempadd, 2)>2048)
					MyGUI.console.append("LOADING ERROR(INVALID ADDRESS > 2048)\n");
				else
				{	if(MyCache.check(tempadd))
					{
						temp = MyCache.getValueFromCache(tempadd);
					}
					else{
						MyCache.insertToCacheFromMemory(tempadd);
						temp = MyMemory.getValue(tempadd);
					}
					//temp = MyMemory.getValue(tempadd);
				}

			}
		}
//		else if(i.equals("1"))//间接寻址 需访问内存indirect address
//		{	
//
//			String tempadd = "";
//			if(ix.equals("00"))//no indexing
//			{
//				
//				tempadd = addr;
//				temp = MyMemory.getValue(tempadd);
//			}
//			else if(ix.equals("01"))//with indexing 1
//			{
//
//				tempadd = Integer.toBinaryString(Integer.parseInt(IX1.getIxval(),2) + Integer.parseInt(addr,2));
//				if(Integer.parseInt(tempadd, 2)>2048)
//					MyGUI.console.append("LOADING ERROR(INVALID ADDRESS > 2048)\n");
//				//System.out.println("hehe"+ tempadd);
//				else
//					{
//						temp = MyMemory.getValue(tempadd);
//						System.out.println(tempadd);
//						System.out.println(temp);
//					}
//				//System.out.println(temp);
//			}
//			else if(ix.equals("10"))//with indexing 2
//			{
//				tempadd = Integer.toBinaryString(Integer.parseInt(IX2.getIxval(),2) + Integer.parseInt(addr,2));
//				if(Integer.parseInt(tempadd, 2)>2048)
//					MyGUI.console.append("LOADING ERROR(INVALID ADDRESS > 2048)\n");
//				else
//					temp = MyMemory.getValue(tempadd);
//
//			}
//			else if(ix.equals("11"))//with indexing3
//			{
//				tempadd = Integer.toBinaryString(Integer.parseInt(IX3.getIxval(),2) + Integer.parseInt(addr,2));
//				if(Integer.parseInt(tempadd, 2)>2048)
//					MyGUI.console.append("LOADING ERROR(INVALID ADDRESS > 2048)\n");
//				else
//					temp = MyMemory.getValue(tempadd);
//
//			}
//		}
		return temp;
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
	/*public static void main(String[] args) {
		MyMemory.insertByAddr("100", "1111111111111100");
		String str = "01";
		String str1 = "1";
		String str2 = "00010";
		IX1.setIxval("0000000000000010");
		//getEffectiveAddr(str,str1,str2);
		System.out.println(getEffectiveAddr(str,str1,str2));
		
	}*/
	
	public static String binaryToDecimal(String add1)
	{
		String big1 = "",small1 = "";
		int big = 0;
		double small = 0.0;
		int flag = 1;
		if(add1.charAt(0) == '-')
		{
			flag = -1;
		}
		for(int i = 0; i < add1.length(); i++)
		{
			if(add1.charAt(i) == '.')
			{
				if(flag == 1){
					big1 = add1.substring(0, i);
					small1 = add1.substring(i+1);
				}
				else if (flag == -1)
				{
					big1 = add1.substring(1,i);
					small1 = add1.substring(i+1);
				}
				break;
			}
		}
		if (!add1.contains("."))
		{
			if(flag == 1)
				big1 = add1.substring(0);
			else 
				big1 = add1.substring(1);
			
		}
			
		big = Integer.parseInt(big1, 2);
		for(int i = 0; i < small1.length();i++)
		{
			int temp = Integer.parseInt(Character.toString(small1.charAt(i)));
			small = small + temp * Math.pow(2, -i-1);
		}
		double result = 0;
		if(flag == 1)
			result = big + small;
		else 
			result = -(big + small);
		
		return Double.toString(result);
		
	}
	
	public static String decimalTobinary(String add1)
	{
		String big1 = "", small1 = "";
		int big = 0;
		double small = 0;
		int flag = 1;
		if(add1.charAt(0) == '-')
		{
			flag = -1;
		}
		
		for(int i = 0; i < add1.length(); i++)
		{
			
			if(add1.charAt(i) == '.')
			{
				if(flag == 1){
					big1 = add1.substring(0, i);
					small1 = add1.substring(i+1);
				}
				else if (flag == -1)
				{
					big1 = add1.substring(1,i);
					small1 = add1.substring(i+1);
				}
				break;
			}
		}
		big = Integer.parseInt(big1);
		String goodbig = Integer.toBinaryString(big);
		small = Double.parseDouble("0."+small1);
		//System.out.println(small+"\n");
		String good = "";
		for(int i = 0; i < 8; i++)
		{
			if(small == 0.0)
				break;
			else
			{
				double temp = small * 2;

				if(temp >= 1)
				{
					good = good + '1';
					small = small * 2 - 1;
					
				}
				else
				{
					good = good + '0';
					small = small * 2;
				
				}
				
			}
		}
		if(flag == 1)
			return goodbig+'.'+good;
		else
			return '-' + goodbig+'.'+good;
		
	}
}
