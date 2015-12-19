
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.DecimalFormat;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.Font;

//single会触发两次事件如果声明在前面，什么问题？
public class MyGUI {//using windowbuilder to make the GUI

	private JFrame frame;
	public static JTextField addr;
	public static JTextField cont;
	public static JTextField show1;
	public static JTextField show2;
	public static JTextField r0;
	public static JTextField r1;
	public static JTextField r2;
	public static JTextField r3;
	public static JTextField pc;
	public static JTextField ix1;
	public static JTextField ix2;
	public static JTextField ix3;
	public static JTextArea console;//show the debug or error messages
	public static JTextArea printer;//show the information about the process of the pc,mar,mdr,ir
	public static JTextField show3;
	public static JTextField show4;
	public static JTextField show5;
	public static JLabel count;
	public static JLabel c0;
	public static JLabel c1;
	public static JLabel c2;
	public static JLabel c3;
	public static JLabel ixl1;
	public static JLabel ixl2;
	public static JLabel ixl3;
	public static JLabel pc10;
	public static JTextField keyboard;//use to input numbers or characters
	public static JButton store;//use to store something into memory
	public static JButton single;//use to execute a single instruction
	public static JButton loadpro1;
	public static JScrollPane jsp;
	public static JScrollPane jsp2;
	public static JScrollPane jsp3;
	public static JTextArea cacheprinter;
	public static JScrollPane jsp4;
	public static JTextArea cacheconsole;
	public static JLabel ccr0;
	public static JLabel ccr1;
	public static JLabel ccr2;
	public static JLabel ccr3;
	public static JLabel fval0;
	public static JLabel fval1;
	public static JTextField fr0;
	public static JTextField fr1;
	

	public static int programend = 0;
	
	
//	
	/**
	 * Launch the application.
	 */
	public void run() {
		try {
			MyGUI window = new MyGUI();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		//});
//	}

	/**
	 * Create the application.
	 */
	public MyGUI() {//initailize
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {//set the contents of the gui with Windowbuilder
		frame = new JFrame();
		frame.setBounds(100, 100, 1255, 684);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAddress = new JLabel("ADDRESS");
		lblAddress.setBounds(17, 39, 61, 16);
		frame.getContentPane().add(lblAddress);
		
		JLabel lblNewLabel = new JLabel("CONTENTS");
		lblNewLabel.setBounds(17, 85, 69, 16);
		frame.getContentPane().add(lblNewLabel);
		
		addr = new JTextField();
		addr.setBounds(96, 33, 219, 28);
		addr.setText("0");
		frame.getContentPane().add(addr);
		addr.setColumns(10);
		
		cont = new JTextField();
		cont.setBounds(96, 79, 219, 28);
		cont.setText("0000000000000000");
		frame.getContentPane().add(cont);
		cont.setColumns(10);
		
		JLabel count = new JLabel("");
		count.setBounds(96, 115, 198, 16);
		frame.getContentPane().add(count);
		
		JLabel c0 = new JLabel("");
		c0.setBounds(41, 425, 61, 16);
		frame.getContentPane().add(c0);
		
		JLabel c1 = new JLabel("");
		c1.setBounds(41, 465, 61, 16);
		frame.getContentPane().add(c1);
		
		JLabel c2 = new JLabel("");
		c2.setBounds(41, 511, 61, 16);
		frame.getContentPane().add(c2);
		
		JLabel c3 = new JLabel("");
		c3.setBounds(41, 551, 61, 16);
		frame.getContentPane().add(c3);
		
	    console = new JTextArea();//此处如果写JtextArea console则会出错 因为把它变成了局部变量 外面读不到
	    console.setForeground(Color.BLUE);
		console.setEditable(false);
		console.setBounds(365, 67, 252, 168);
		//frame.getContentPane().add(console);
	    jsp = new JScrollPane(console);
		jsp.setBounds(365, 67, 252, 168);
		
		frame.getContentPane().add(jsp);
		
	    printer = new JTextArea();
	    printer.setForeground(Color.cyan);
		printer.setEditable(false);
	    jsp2 = new JScrollPane();
		jsp2.setBounds(629, 67, 252, 168);
		jsp2.setViewportView(printer);
		frame.getContentPane().add(jsp2);
		
		JLabel ixl1 = new JLabel("");
		ixl1.setBounds(749, 363, 61, 16);
		frame.getContentPane().add(ixl1);
		
		JLabel ixl2 = new JLabel("");
		ixl2.setBounds(749, 391, 61, 16);
		frame.getContentPane().add(ixl2);
		
		JLabel ixl3 = new JLabel("");
		ixl3.setBounds(749, 419, 61, 16);
		frame.getContentPane().add(ixl3);
		
		JLabel lblConsole = new JLabel("CONSOLE INFORMATION");
		lblConsole.setBounds(417, 39, 155, 16);
		frame.getContentPane().add(lblConsole);
		
		JLabel lblAddress_1 = new JLabel("ADDRESS");
		lblAddress_1.setBounds(17, 215, 61, 16);
		frame.getContentPane().add(lblAddress_1);
		
		JLabel lblContents = new JLabel("CONTENTS");
		lblContents.setBounds(17, 259, 69, 16);
		frame.getContentPane().add(lblContents);
		
		show1 = new JTextField();
		show1.setBounds(96, 209, 219, 28);
		show1.setText("0");
		frame.getContentPane().add(show1);
		show1.setColumns(10);
		
		show2 = new JTextField();
		show2.setBounds(96, 253, 219, 28);
		show2.setText("0000000000000000");
		show2.setEditable(false);
		frame.getContentPane().add(show2);
		show2.setColumns(10);
		
		JLabel lblR = new JLabel("R0");
		lblR.setBounds(17, 430, 61, 16);
		frame.getContentPane().add(lblR);
		
		r0 = new JTextField();
		r0.setBounds(101, 425, 214, 28);
		r0.setText("0000000000000000");
		frame.getContentPane().add(r0);
		r0.setColumns(10);
		
		JLabel lable1 = new JLabel("R1");
		lable1.setBounds(17, 475, 61, 16);
		frame.getContentPane().add(lable1);
		
		r1 = new JTextField();
		r1.setBounds(101, 465, 214, 28);
		r1.setText("0000000000000000");
		r1.setColumns(10);
		frame.getContentPane().add(r1);
		
		JLabel lable2 = new JLabel("R2");
		lable2.setBounds(17, 511, 61, 16);
		frame.getContentPane().add(lable2);
		
		r2 = new JTextField();
		r2.setBounds(101, 505, 214, 28);
		r2.setText("0000000000000000");
		r2.setColumns(10);
		frame.getContentPane().add(r2);
		
		r3 = new JTextField();
		r3.setBounds(101, 545, 214, 28);
		r3.setText("0000000000000000");
		r3.setColumns(10);
		frame.getContentPane().add(r3);
		
		JLabel lblR_3 = new JLabel("R3");
		lblR_3.setBounds(17, 551, 61, 16);
		frame.getContentPane().add(lblR_3);
		
	    pc10 = new JLabel("");
		pc10.setBounds(314, 173, 46, 16);
		frame.getContentPane().add(pc10);
		
		 fval0 = new JLabel("0.0");
		fval0.setBounds(681, 590, 93, 16);
		frame.getContentPane().add(fval0);
		
		 fval1 = new JLabel("0.0");
		fval1.setBounds(681, 626, 93, 16);
		frame.getContentPane().add(fval1);
		
		
		
		
		JButton store = new JButton("STORE MEMORY");
		store.setBounds(6, 139, 128, 29);
		store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag = 1;//flag used to limit the input. if flag chages to 0, it means some invalid input
				String temp = addr.getText();
				int templen = addr.getText().length();
				String temp1 = cont.getText();
				int templen1 = temp1.length();
				if (temp.length() == 0 || temp1.length() == 0) {
					flag = 0;
					console.append("PLZ INPUT\n");
				}
				if (flag == 1) {
					for (int i = 0; i < templen; i++) {
						if (temp.charAt(i) != '0' && temp.charAt(i) != '1') {
							flag = 0;
							console.append("ADDRESS MUST BE BINARY\n");
							break;
						}
					}
					if (flag == 1) {
						for (int i = 0; i < templen1; i++) {
							if (temp1.charAt(i) != '0'
									&& temp1.charAt(i) != '1') {
								flag = 0;
								console.append("DATA MUST BE BINARY\n");
								break;
							}
						}
						if (flag == 1) {
							int tempint = Integer.parseInt(temp, 2);
							//System.out.println(tempint);
							if (tempint < 0 || tempint > 2048) {
								flag = 0;
								console.append("INVALID ADDRESS NUMBER\n");
							}
							
							int tempint1 = Integer.parseInt(temp1, 2);
							if (tempint1 > Math.pow(2, 16) ) {
								flag = 0;
								console.append("INVALID VALUE NUMBER\n");
							}
							if(flag == 1)
							{
								if(templen1!=16)
								{
									flag = 0;
									count.setText("only "+templen1+ " bits");
									console.append("CONTENTS MUST BE 16 BITS LONG\n");
									
								}
								if(flag == 1)
								{
									count.setText("");
									MyMemory.insertByAddr(temp, temp1);
									console.append("STORE SUCCESS\n");
									//System.out.println(MyMemory.getValue(temp));
								}
							}
						}
					}
				}
			}
		});
		frame.getContentPane().add(store);
		
		
		
		
		
		JButton show = new JButton("SHOW MEMORY");//button action to show the contents of memory
		show.setBounds(6, 384, 128, 29);
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag = 1;
				String temp = show1.getText();
				int templen = temp.length();
				if (temp.length() == 0 ) 
				{
					flag = 0;
					console.append("PLZ INPUT(QUERY)\n");
				}
				if(flag == 1)
				{
					for(int i = 0; i < templen; i++)
					{
						if(temp.charAt(i) !='0' && temp.charAt(i)!='1')
						{
							flag = 0;
							console.append("ADDRESS MUST BE BINARY(QUERY)\n");
						}
					}
					if (flag == 1)
					{
						int tempint = Integer.parseInt(temp,2);
						if(tempint < 0 || tempint > 2048 )
						{
							flag = 0;
							console.append("INVALID ADDRESS(QUERY)\n");
						}
						if(flag == 1)
						{
							show2.setText(MyMemory.getValue(temp));
							console.append("QUERY SUCCESS\n");
						}
					}
				}
				
				
			}
		});
		frame.getContentPane().add(show);
		
		
		
		JButton streg = new JButton("STOREREG");//buttion action to store register's contents to memory
		streg.setBounds(17, 585, 117, 29);
		streg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag = 1;
				String temp0 = r0.getText();
				String temp1 = r1.getText();
				String temp2 = r2.getText();
				String temp3 = r3.getText();
				String temp4 = fr0.getText();
				String temp5 = fr1.getText();
				int len0 = temp0.length();
				int len1 = temp1.length();
				int len2 = temp2.length();
				int len3 = temp3.length();
				int len4 = temp4.length();
				int len5 = temp5.length();
				if(len0 == 0 || len1 == 0 || len2 == 0 || len3 == 0 ||len4 == 0 || len5 == 0)
				{
					console.append("PLZ INPUT(REG)\n");
					flag = 0;
				}
				if(flag == 1)
				{
					for(int i = 0; i < len0; i++)
					{
						if(temp0.charAt(i)!='0' && temp0.charAt(i)!='1' )
						{
							flag = 0;
							console.append("REG0 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					for(int i = 0; i < len1; i++)
					{
						if(temp1.charAt(i)!='0' && temp1.charAt(i)!='1' )
						{
							flag = 0;
							console.append("REG1 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					for(int i = 0; i < len2; i++)
					{
						if(temp2.charAt(i)!='0' && temp2.charAt(i)!='1' )
						{
							flag = 0;
							console.append("REG2 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					for(int i = 0; i < len3; i++)
					{
						if(temp3.charAt(i)!='0' && temp3.charAt(i)!='1' )
						{
							flag = 0;
							console.append("REG3 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					if(flag == 1)
					{
						int tempint0 = Integer.parseInt(temp0, 2);
						int tempint1 = Integer.parseInt(temp1, 2);
						int tempint2 = Integer.parseInt(temp2, 2);
						int tempint3 = Integer.parseInt(temp3, 2);

						if (tempint0 > Math.pow(2, 16) ||tempint1 > Math.pow(2, 16)||tempint2 > Math.pow(2, 16)
								||tempint3 > Math.pow(2, 16)) {
							flag = 0;
							console.append("REG INVALID VALUE\n");
						}
						if(flag == 1)
						{
							if(len0!=16)
							{
								flag = 0;
								c0.setText(len0+ " bits");
								console.append("R0 MUST BE 16 BITS\n");
							}
							if(len1!=16)
							{
								flag = 0;
								lable1.setText(len1+ " bits");
								console.append("R1 MUST BE 16 BITS\n");
							}
							if(len2!=16)
							{
								flag = 0;
								lable2.setText(len2+ " bits");
								console.append("R2 MUST BE 16 BITS\n");
							}
							if(len3!=16)
							{
								flag = 0;
								c3.setText(len3+ " bits");
								console.append("R3 MUST BE 16 BITS\n");
							}
						}
						if(flag == 1)
						{
							c0.setText("");
							c1.setText("");
							c2.setText("");;
							c3.setText("");
							MyR0.setValue(temp0);
							MyR1.setValue(temp1);
							MyR2.setValue(temp2);
							MyR3.setValue(temp3);
							MyFR0.setValue(temp4);
							MyFR1.setValue(temp5);
							
							String data1 = fr0.getText();
							String data2 = fr1.getText();
							
							showFloatReg(data1, data2);
							

//							
							
							console.append("REG STORE SUCCESS\n");
							/*System.out.println(MyR0.getValue());
							System.out.println(MyR1.getValue());
							System.out.println(MyR2.getValue());
							System.out.println(MyR3.getValue());*/

							
						}
						
					}
				}
				
				
			}
		});
		frame.getContentPane().add(streg);
		
		JButton showreg = new JButton("SHOWREG");//show the contents of registers
		showreg.setBounds(177, 585, 117, 29);
		showreg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r0.setText(MyR0.getValue());
				r1.setText(MyR1.getValue());
				r2.setText(MyR2.getValue());
				r3.setText(MyR3.getValue());
				fr0.setText(MyFR0.getValue());
				fr1.setText(MyFR1.getValue());
				
			}
		});
		frame.getContentPane().add(showreg);
		
		JLabel lblPc = new JLabel("PC");
		lblPc.setBounds(17, 180, 61, 16);
		frame.getContentPane().add(lblPc);
		
		pc = new JTextField();
		pc.setBounds(96, 169, 219, 28);
		pc.setText("000000000000");
		frame.getContentPane().add(pc);
		pc.setColumns(10);
		
		single =  new JButton("SINGLE STEP");
		single.setBounds(386, 254, 214, 29);
		single.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int flag = 1;
				String temp = pc.getText();
				int templen = temp.length();
				if (temp.length() == 0 ) 
				{
					flag = 0;
					console.append("PLZ INPUT PC\n");
				}
				if(flag == 1)
				{
					for(int i = 0; i < templen; i++)
					{
						if(temp.charAt(i) !='0' && temp.charAt(i)!='1')
						{
							flag = 0;
							console.append("PC MUST BE BINARY(QUERY)\n");
						}
					}
					if (flag == 1)
					{
						int tempint = Integer.parseInt(temp,2);
						if(tempint > 2048 )
						{
							flag = 0;
							console.append("INVALID PC VALUE\n");
						}
						if(flag == 1)
						{
							if(templen != 12)
							{
								console.append("PC MUST BE 12 BITS\n");
								flag = 0;
							}
							if (flag == 1) {
								show3.setText(MyALU.buling(temp));
								MyMAR.setValue(MyALU.buling(temp));
								printer.append("MAR<--PC\n");
								printer.append("MAR:" + MyMAR.getValue() + "\n");
								String tempins = "0000000000000000";
								if (MyCache.check(temp)) {
									if (programend != 1) {
										tempins = MyCache
												.getValueFromCache(temp);
									}
								} else {
									if(programend != 1){
										MyCache.insertToCacheFromMemory(temp);
										tempins = MyMemory.getValue(temp);
									}
								}
								MyMDR.setValue(tempins);
								MyIR.setValue(tempins);
								show4.setText(tempins);
								show5.setText(tempins);
								printer.append("MDR<--C(MAR)\n");
								printer.append("MDR:" + MyMDR.getValue() + "\n");
								printer.append("IR<--MDR\n");
								printer.append("IR:" + MyIR.getValue() + "\n");
								String opcode = tempins.substring(0, 6);
								MyInstruction.setOpcode(opcode);
								// System.out.println(tempins.substring(0,
								// 6));
								printer.append("PC READ SUCCESS "
										+ "PC NUMBER:"
										+ Integer.parseInt(MyPC.getPcval(), 2)
										+ "\n");
								// sotre and load command
								if (opcode.equals("000001"))// LDR
								{
									// System.out.println("nice");
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
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
								} else if (opcode.equals("101001"))// LDX
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} else if (opcode.equals("101010"))// STX
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
								} else if (opcode.equals("000101"))// SMR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} else if (opcode.equals("000110"))// AIR
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.immed = tempins.substring(11);
								} else if (opcode.equals("000111"))// SIR
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
								} else if (opcode.equals("001011"))// JNE
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} else if (opcode.equals("001100"))// JCC
								{
									MyInstruction.cc = tempins.substring(6, 8);// codition
																				// code
																				// replace
																				// R
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);

								} else if (opcode.equals("001101"))// JMA
								{
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} else if (opcode.equals("001110"))// JSR
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
								} else if (opcode.equals("010001"))// JGE
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.IX = tempins.substring(8, 10);
									MyInstruction.I = tempins.substring(10, 11);
									MyInstruction.ADDR = tempins.substring(11);
								} else if (opcode.equals("010100"))// MLT
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
								} else if (opcode.equals("010111"))// AND
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} else if (opcode.equals("011000"))// ORR
								{
									MyInstruction.rx = tempins.substring(6, 8);
									MyInstruction.ry = tempins.substring(8, 10);
								} else if (opcode.equals("011001"))// NOT
								{
									MyInstruction.rx = tempins.substring(6, 8);
								} else if (opcode.equals("011111"))// SRC
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.AL = tempins.substring(8, 9);
									MyInstruction.LR = tempins.substring(9, 10);
									MyInstruction.count = tempins.substring(12);
								} else if (opcode.equals("100000"))// RRC
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.AL = tempins.substring(8, 9);
									MyInstruction.LR = tempins.substring(9, 10);
									MyInstruction.count = tempins.substring(12);
								} else if (opcode.equals("111101"))// IN
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.DevID = tempins.substring(11);
								} else if (opcode.equals("111110"))// OUT
								{
									MyInstruction.R = tempins.substring(6, 8);
									MyInstruction.DevID = tempins.substring(11);
								} else if(opcode.equals("000000")) {//HALT
									programend = 1;
									MyCache.finalwriteback();
									console.append("\nPROGRAM END\n");
									
								}
								JScrollBar bar = jsp.getVerticalScrollBar();
								bar.setValue(bar.getMaximum());
								JScrollBar bar1 = jsp2.getVerticalScrollBar();
								bar.setValue(bar1.getMaximum());

								new MyThread().start();
//								String data1 = fr0.getText();
//								String data2 = fr0.getText();
//								
//								
//								showFloatReg(data1, data2);

							}
							}
						
					}
				}
				/*if(MyALU.process())
				{
					
					refresh(); 
					
					
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
				}*/
				
				
				
			}
			
		});
		frame.getContentPane().add(single);
		
		JLabel lblIx = new JLabel("IX1");
		lblIx.setBounds(432, 363, 61, 16);
		frame.getContentPane().add(lblIx);
		
		JLabel lblIx_1 = new JLabel("IX2");
		lblIx_1.setBounds(432, 390, 61, 16);
		frame.getContentPane().add(lblIx_1);
		
		JLabel lblIx_2 = new JLabel("IX3");
		lblIx_2.setBounds(432, 417, 61, 16);
		frame.getContentPane().add(lblIx_2);
		
		ix1 = new JTextField();
		ix1.setBounds(518, 358, 219, 28);
		ix1.setText("0000000000000000");
		frame.getContentPane().add(ix1);
		ix1.setColumns(10);
		
		ix2 = new JTextField();
		ix2.setBounds(518, 386, 219, 28);
		ix2.setText("0000000000000000");
		ix2.setColumns(10);
		frame.getContentPane().add(ix2);
		
		ix3 = new JTextField();
		ix3.setBounds(518, 414, 219, 28);
		ix3.setText("0000000000000000");
		ix3.setColumns(10);
		frame.getContentPane().add(ix3);
		
		JButton btnStoreix = new JButton("STOREIX");
		btnStoreix.setBounds(455, 445, 117, 29);
		btnStoreix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag = 1;
				String temp1 = ix1.getText();
				String temp2 = ix2.getText();
				String temp3 = ix3.getText();
				
				int len1 = temp1.length();
				int len2 = temp2.length();
				int len3 = temp3.length();
				if( len1 == 0 || len2 == 0 || len3 == 0)
				{
					console.append("PLZ INPUT(IX)\n");
					flag = 0;
				}
				if(flag == 1)
				{
					
					for(int i = 0; i < len1; i++)
					{
						if(temp1.charAt(i)!='0' && temp1.charAt(i)!='1' )
						{
							flag = 0;
							console.append("IX1 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					for(int i = 0; i < len2; i++)
					{
						if(temp2.charAt(i)!='0' && temp2.charAt(i)!='1' )
						{
							flag = 0;
							console.append("IX2 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					for(int i = 0; i < len3; i++)
					{
						if(temp3.charAt(i)!='0' && temp3.charAt(i)!='1' )
						{
							flag = 0;
							console.append("IX3 CONTENTS MUST BE BINARY\n");
							break;
						}
					}
					if(flag == 1)
					{
						
						int tempint1 = Integer.parseInt(temp1, 2);
						int tempint2 = Integer.parseInt(temp2, 2);
						int tempint3 = Integer.parseInt(temp3, 2);

						if (tempint1 > Math.pow(2, 16)||tempint2 > Math.pow(2, 16)
								||tempint3 > Math.pow(2, 16)) {
							flag = 0;
							console.append("IX INVALID VALUE\n");
						}
						if(flag == 1)
						{
							
							if(len1!=16)
							{
								flag = 0;
								ixl1.setText(len1+ " bits");
								console.append("IX1 MUST BE 16 BITS\n");
							}
							if(len2!=16)
							{
								flag = 0;
								ixl2.setText(len2+ " bits");
								console.append("IX2 MUST BE 16 BITS\n");
							}
							if(len3!=16)
							{
								flag = 0;
								ixl3.setText(len3+ " bits");
								console.append("IX3 MUST BE 16 BITS\n");
							}
						}
						if(flag == 1)
						{
							ixl1.setText("");
							ixl2.setText("");
							ixl3.setText("");
							
							IX1.setIxval(temp1);
							IX2.setIxval(temp2);
							IX3.setIxval(temp3);
							console.append("REG STORE SUCCESS\n");
							
							
						}
						
					}
				}
				
				
			}
				
				
		});
		frame.getContentPane().add(btnStoreix);
		
		JButton btnShowix = new JButton("SHOWIX");//show the contents of indexing register
		btnShowix.setBounds(657, 447, 117, 29);
		btnShowix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ix1.setText(IX1.getIxval());
				ix2.setText(IX2.getIxval());
				ix3.setText(IX3.getIxval());
			}
		});
		frame.getContentPane().add(btnShowix);
		
		JButton reset = new JButton("RESET");//reset all the registers and memory
		reset.setBounds(639, 254, 214, 29);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				programend = 0;
				single.setEnabled(true);
				MyMemory.initMemory();
				MyR0.initR0();
				MyR1.initR1();
				MyR2.initR2();
				MyR3.initR3();
				IX1.initIX1();
				IX2.initIX2();
				IX3.initIX3();
				MyPC.initPC();
				MyMAR.initMyMAR();
				MyMDR.initMyMDR();
				MyIR.initMyIR();
				MyCache.initMyCache();
				MyCCR.initial();
				MyFR0.initMyFR0();
				MyFR1.initMyFR1();
				MyInstruction.setOpcode("000000");
				count.setText("");
				c0.setText("");
				c1.setText("");
				c2.setText("");
				c3.setText("");
				ixl1.setText("");
				ixl2.setText("");
				ixl3.setText("");
				ccr0.setText("0");
				ccr1.setText("0");
				ccr2.setText("0");
				ccr3.setText("0");
				pc.setText("000000000000");
				pc10.setText("");
				r0.setText("0000000000000000");
				r1.setText("0000000000000000");
				r2.setText("0000000000000000");
				r3.setText("0000000000000000");
				ix1.setText("0000000000000000");
				ix2.setText("0000000000000000");
				ix3.setText("0000000000000000");
				show3.setText("0000000000000000");
				show4.setText("0000000000000000");
				show5.setText("0000000000000000");
				console.setText("");
				addr.setText("0");
				keyboard.setText("");
				cont.setText("0000000000000000");
				show1.setText("0000000000000000");
				show2.setText("0000000000000000");
				printer.setText("");
				cacheprinter.setText("");
				cacheconsole.setText("");
				fr0.setText("0000000000000000");
				fr1.setText("0000000000000000");
				fval0.setText("0.0");
				fval1.setText("0.0");

				
				//refresh();
			}
		});
		frame.getContentPane().add(reset);
		
		JLabel lblMar = new JLabel("MAR");
		lblMar.setBounds(17, 292, 69, 16);
		frame.getContentPane().add(lblMar);
		
		JLabel lblMdr = new JLabel("MDR");
		lblMdr.setBounds(17, 329, 69, 16);
		frame.getContentPane().add(lblMdr);
		
		JLabel lblIr = new JLabel("IR");
		lblIr.setBounds(17, 365, 69, 16);
		frame.getContentPane().add(lblIr);
		
		show3 = new JTextField();
		show3.setBounds(96, 287, 219, 28);
		show3.setText("0000000000000000");
		show3.setEditable(false);
		show3.setColumns(10);
		frame.getContentPane().add(show3);
		
		show4 = new JTextField();
		show4.setBounds(96, 323, 219, 28);
		show4.setText("0000000000000000");
		show4.setEditable(false);
		show4.setColumns(10);
		frame.getContentPane().add(show4);
		
		show5 = new JTextField();
		show5.setBounds(96, 357, 219, 28);
		show5.setEditable(false);
		show5.setText("0000000000000000");
		show5.setColumns(10);
		frame.getContentPane().add(show5);
		
		JPanel panel = new JPanel();
		panel.setBounds(401, 505, 427, 62);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInputBox = new JLabel("INPUT BOX");
		lblInputBox.setBounds(6, 6, 76, 16);
		panel.add(lblInputBox);
		
		keyboard = new JTextField();
		keyboard.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int k = e.getKeyCode();
				if(k == KeyEvent.VK_ENTER)
				{
					if(!keyboard.getText().equals("")&&!keyboard.getText().equals("\n"))
					{
						MyINPUT.setInputval(keyboard.getText());
						//System.out.println(keyboard.getText().length());
					}
					//System.out.println(MyINPUT.getInputval());
					keyboard.setText("");
				}
				
			}
		});
		
		keyboard.setBounds(16, 24, 387, 28);
		panel.add(keyboard);
		keyboard.setColumns(10);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("CONSOLE PRINTER");
		lblNewLabel_1.setBounds(693, 39, 117, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton loadpro1 = new JButton("LOAD PROGRAM1");
		loadpro1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyProLoader.Load("01");
				refresh();
			}
		});
		loadpro1.setBounds(387, 287, 213, 29);
		frame.getContentPane().add(loadpro1);
		
		JButton floatpoint = new JButton("FLOAT");
		floatpoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyProLoader.Load("03");
				refresh();
			}
		});
		floatpoint.setBounds(711, 585, 78, 29);
		frame.getContentPane().add(floatpoint);
		
		
		JButton btnNewButton = new JButton("RUN PROGRAM");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new MyRunThread().start();
				
			}
				
				
				
			
				
			
		});
		btnNewButton.setBounds(518, 324, 214, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblCacheInformation = new JLabel("CACHE INFORMATION");
		lblCacheInformation.setBounds(1013, 39, 149, 16);
		frame.getContentPane().add(lblCacheInformation);
		
		jsp3 = new JScrollPane();
		jsp3.setBounds(893, 67, 337, 168);
		frame.getContentPane().add(jsp3);
		
		cacheprinter = new JTextArea();
		cacheprinter.setForeground(Color.MAGENTA);
		cacheprinter.setFont(new Font("Lucida Grande", Font.PLAIN, 7));
		cacheprinter.setEditable(false);
		jsp3.setViewportView(cacheprinter);
		
		JLabel lblCa = new JLabel("CACHE CONTROL INFO");
		lblCa.setBounds(1013, 275, 149, 16);
		frame.getContentPane().add(lblCa);
		
		jsp4 = new JScrollPane();
		jsp4.setBounds(893, 303, 337, 325);
		frame.getContentPane().add(jsp4);
		
		cacheconsole = new JTextArea();
		cacheconsole.setForeground(Color.RED);
		cacheconsole.setEditable(false);
		jsp4.setViewportView(cacheconsole);
		
		JLabel lblCcr = new JLabel("CCR");
		lblCcr.setBounds(17, 626, 61, 16);
		frame.getContentPane().add(lblCcr);
		
		ccr0 = new JLabel("0");
		ccr0.setBounds(73, 626, 27, 16);
		frame.getContentPane().add(ccr0);
		
		ccr1 = new JLabel("0");
		ccr1.setBounds(112, 626, 27, 16);
		frame.getContentPane().add(ccr1);
		
		ccr2 = new JLabel("0");
		ccr2.setBounds(151, 626, 27, 16);
		frame.getContentPane().add(ccr2);
		
		ccr3 = new JLabel("0");
		ccr3.setBounds(187, 626, 27, 16);
		frame.getContentPane().add(ccr3);
		
		JButton loadpro2 = new JButton("LOAD PROGRAM2");
		loadpro2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyProLoader.Load("02");
				refresh();
			}
		});
		JButton convert = new JButton("CONVERT");
		convert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyProLoader.Load("04");
				refresh();
			}
		});
		JButton vectoradd = new JButton("VECADD");
		vectoradd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyProLoader.Load("05");
				refresh();
				
			}
		});
		vectoradd.setBounds(711, 618, 93, 29);
		frame.getContentPane().add(vectoradd);
		convert.setBounds(786, 585, 95, 29);
		frame.getContentPane().add(convert);
		loadpro2.setBounds(639, 287, 214, 29);
		frame.getContentPane().add(loadpro2);
		
		JLabel lblFr = new JLabel("FR0");
		lblFr.setBounds(395, 590, 61, 16);
		frame.getContentPane().add(lblFr);
		
		JLabel lblFr_1 = new JLabel("FR1");
		lblFr_1.setBounds(395, 626, 61, 16);
		frame.getContentPane().add(lblFr_1);
		
		fr0 = new JTextField();
		fr0.setText("0000000000000000");
		fr0.setColumns(10);
		fr0.setBounds(455, 584, 214, 28);
		frame.getContentPane().add(fr0);
		
		fr1 = new JTextField();
		fr1.setText("0000000000000000");
		fr1.setColumns(10);
		fr1.setBounds(455, 620, 214, 28);
		frame.getContentPane().add(fr1);
		
		JButton vectorsub = new JButton("VECSUB");
		vectorsub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyProLoader.Load("06");
				refresh();
			}
		});
		vectorsub.setBounds(796, 618, 85, 29);
		frame.getContentPane().add(vectorsub);
		
		
		
		
		
		
		
		
		
	}
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
	


	public static void refresh()//refresh the registers to show them on gui
	{
		pc.setText(MyPC.getPcval());
		pc10.setText(Integer.toString(Integer.parseInt(MyPC.getPcval(),2)));
		//System.out.println(pc10.getText());
		r0.setText(MyR0.getValue());
		r1.setText(MyR1.getValue());
		r2.setText(MyR2.getValue());
		r3.setText(MyR3.getValue());
		ix1.setText(IX1.getIxval());
		ix2.setText(IX2.getIxval());
		ix3.setText(IX3.getIxval());
		show3.setText(MyMAR.getValue());
		show4.setText(MyMDR.getValue());
		show5.setText(MyIR.getValue());
		//System.out.println(MyCCR.CCR[1]);
		ccr0.setText(Integer.toString(MyCCR.CCR[0]));
		ccr1.setText(Integer.toString(MyCCR.CCR[1]));
		ccr2.setText(Integer.toString(MyCCR.CCR[2]));
		ccr3.setText(Integer.toString(MyCCR.CCR[3]));
		
		fr0.setText(MyFR0.getValue());
		fr1.setText(MyFR1.getValue());
		showFloatReg(MyFR0.getValue(), MyFR1.getValue());
		

		
		
		MyCache.print();
	}
	public static void showFloatReg(String data1, String data2)
	{
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
//		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
		
		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 
		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2) / Math.pow(10, 7); 


		String addbin1, addbin2;
		
		DecimalFormat decimalFormat = new DecimalFormat("#.#########");//格式化设置  
		
       // addbin1 = Double.toString(mantnum1);
		//addbin2 = Double.toString(mantnum2);
        addbin1 = decimalFormat.format(mantnum1);
		addbin2 = decimalFormat.format(mantnum2);
		String adddec1, adddec2;
		adddec1 = binaryToDecimal(addbin1);
		adddec2 = binaryToDecimal(addbin2);
		
		fval0.setText(adddec1);
		fval1.setText(adddec2);
		
	}
}

