import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MyRunThread extends Thread//thread to run the whole program
	{
		public MyRunThread()
		{
		}
		public void run()
		{
			
			int flag = 1;
			while (flag == 1) {

				String temp = MyGUI.pc.getText();
				int templen = temp.length();
				if (temp.length() == 0) {
					flag = 0;
					MyGUI.console.append("PLZ INPUT PC\n");
				}
				if (flag == 1) {
					for (int i = 0; i < templen; i++) {
						if (temp.charAt(i) != '0' && temp.charAt(i) != '1') {
							flag = 0;
							MyGUI.console.append("PC MUST BE BINARY(QUERY)\n");
						}
					}
					if (flag == 1) {
						int tempint = Integer.parseInt(temp, 2);
						if (tempint > 2048) {
							flag = 0;
							MyGUI.console.append("INVALID PC VALUE\n");
						}
						if (flag == 1) {
							if (templen != 12) {
								MyGUI.console.append("PC MUST BE 12 BITS\n");
								flag = 0;
							}
							if (flag == 1) {
								MyGUI.show3.setText(MyALU.buling(temp));
								MyMAR.setValue(MyALU.buling(temp));
								MyGUI.printer.append("MAR<--PC\n");
								MyGUI.printer.append("MAR:" + MyMAR.getValue() + "\n");
								String tempins = "0000000000000000";
								if(MyCache.check(temp) )
								{
									if(MyGUI.programend != 1){
										tempins = MyCache.getValueFromCache(temp);
									}
								}
								else{
									if(MyGUI.programend != 1){

										MyCache.insertToCacheFromMemory(temp);
										tempins = MyMemory.getValue(temp);
									}
								}
								MyMDR.setValue(tempins);
								MyIR.setValue(tempins);
								MyGUI.show4.setText(tempins);
								MyGUI.show5.setText(tempins);
								MyGUI.printer.append("MDR<--C(MAR)\n");
								MyGUI.printer.append("MDR:"+ MyMDR.getValue()+"\n");
								MyGUI.printer.append("IR<--MDR\n");
								MyGUI.printer.append("IR:" + MyIR.getValue() + "\n");
								String opcode = tempins.substring(0, 6);
								MyInstruction.setOpcode(opcode);
								// System.out.println(tempins.substring(0, 6));
								MyGUI.printer.append("PC READ SUCCESS " + "PC NUMBER:"+Integer.parseInt(MyPC.getPcval(),2)+"\n");
								// sotre and load command
								if (opcode.equals("000001"))// LDR
								{
									// System.out.println("nice");
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
//									if(MyInstruction.I.equals("1"))
//										System.out.println(tempins);
								} 
								else if (opcode.equals("110010"))// LDFR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								}
								else if (opcode.equals("110011"))// STFR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								}
								else if (opcode.equals("100001"))// FADD
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								}
								else if (opcode.equals("100010"))// FSUB
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								}
								else if (opcode.equals("100101"))// CNVRT
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								}
								else if (opcode.equals("100011"))// VADD
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								}else if (opcode.equals("100100"))// VSUB
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								}
								
								else if (opcode.equals("000010"))// STR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								} 
								else if (opcode.equals("000011"))// LDA
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} 
								else if (opcode.equals("101001"))// LDX
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} 
								else if (opcode.equals("101010"))// STX
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								}
								// arithmetic command
								else if (opcode.equals("000100"))// AMR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} 
								else if (opcode.equals("000101"))// SMR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} 
								else if (opcode.equals("000110"))// AIR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.immed = tempins.substring(11);
								}
								else if (opcode.equals("000111"))// SIR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.immed = tempins.substring(11);
									// System.out.println("SIR");
								}
								// transfer command
								else if (opcode.equals("001010"))// JZ
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								}
								else if (opcode.equals("001011"))// JNE
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} 
								else if (opcode.equals("001100"))// JCC
								{
									MyInstruction.cc = tempins.substring(6, 8);// codition
																				// code
																				// replace
																				// R
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								} 
								else if (opcode.equals("001101"))// JMA
								{
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} 
								else if (opcode.equals("001110"))// JSR
								{
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} else if (opcode.equals("001111"))// RFS
								{
									MyInstruction.immed = tempins.substring(11);
								} else if (opcode.equals("010000"))// SOB
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								}
								else if (opcode.equals("010001"))// JGE
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								}
								else if (opcode.equals("010100"))// MLT
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} else if (opcode.equals("010101"))// DVD
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} else if (opcode.equals("010110"))// TRR
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} 
								else if (opcode.equals("010111"))// AND
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} 
								else if (opcode.equals("011000"))// ORR
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} 
								else if (opcode.equals("011001"))// NOT
								{
									MyInstruction.rx = tempins.substring(6, 8);
								} else if (opcode.equals("011111"))// SRC
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.AL = tempins.substring(8, 9);
									MyInstruction.LR = tempins.substring(9, 10);
									MyInstruction.count = tempins.substring(12);
								}
								else if(opcode.equals("100000"))//RRC
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.AL = tempins.substring(8, 9);
									MyInstruction.LR = tempins.substring(9, 10);
									MyInstruction.count = tempins.substring(12);
								}
								else if (opcode.equals("111101"))// IN
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.DevID = tempins.substring(11);
								} 
								else if (opcode.equals("111110"))// OUT
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.DevID = tempins.substring(11);
								} 
								else {
									MyGUI.programend = 1;
									MyCache.finalwriteback();
									MyGUI.console.append("\nPROGRAM END\n");
									flag = 0;
								}
								if(MyALU.process())
								{
									MyGUI.refresh(); 
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

							}

						}
					}
				}
			}
		}
		
	}
	