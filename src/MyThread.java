import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MyThread extends Thread{//thread to wait for the keyboard output and run single step
		public MyThread()
		{
		}
		
		public void run()
		{
			MyGUI.single.setEnabled(false);
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
			
			MyGUI.single.setEnabled(true);
		}
	}