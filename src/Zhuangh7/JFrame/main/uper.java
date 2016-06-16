package Zhuangh7.JFrame.main;

import java.net.Socket;

public class uper extends Thread{
	
	private Socket ss;
	public uper(Socket s){
		ss = s;
	}
	@Override
	public void run(){
		
		/*	int size = s.Size.get(s.Size.size());
			s.Size.remove(s.Size.size());
			ChatManager.getChatManager().publish_size(s, size);
			if(!s.map.isEmpty()){
				byte[] t = s.map.get(s.map.size());
				s.map.remove(s.map.size());
				ChatManager.getChatManager().publish_byte(s, t);
			}*/
	}
	
}
