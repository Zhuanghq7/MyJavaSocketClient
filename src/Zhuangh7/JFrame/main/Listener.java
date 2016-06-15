package Zhuangh7.JFrame.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class Listener extends Thread{
	public boolean listennn = true;
	public BufferedReader br;
	private Socket ss;
	public Listener(Socket ss){
		this.ss = ss;
	}
	@Override  
    public void run() { 
		try {
			
			
			br = new BufferedReader( new InputStreamReader(ss.getInputStream(),"UTF-8"));
		
			String read;
			read = br.readLine();
			while((!read.equals("close"))&&(listennn == true)){
				if(read!=null){
					MainClass.print("接收到："+read);
				}
				read = br.readLine();
			}
			if(read.equals("close")){
				MainClass.print("对方关闭了客户端");
			}
			br.close();
			
		
		
			
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
	}
}
