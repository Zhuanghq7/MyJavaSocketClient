package Zhuangh7.JFrame.main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.CanvasFrame;

public class Listener_image extends Thread{
	public boolean listennn = true;
	public BufferedReader br;
	private Socket ss;
	private CanvasFrame canvas = null;
	public static Vector<BufferedImage> images = new Vector<BufferedImage>();
	public Listener_image(Socket ss){
		this.ss = ss;
	}
	@Override  
    public void run() { 
		try {
			new show(null).start();
			InputStream is = ss.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			int size = dis.readInt();
			while(listennn){
    			if(size!=0){
    				if(size == -1){
    					listennn = false;
    				} 
	    			//Size.add(size);
	                byte[] data = new byte[size];    
	                int len = 0;    
	                try{
	                while (len < size) {    
	                    len += dis.read(data, len, size - len);    
	                }
	                }catch(IOException e){
	                	MainClass.print("e");
	                }
	               // map.add(data);
	                ByteArrayInputStream in = new ByteArrayInputStream(data);    //将b作为输入流；
	                BufferedImage image = ImageIO.read(in);
	               // MainClass.print("接收图片"+size);
	                //MainClass.print("缓存大小"+images.size());
	                images.addElement(image);
    			}
    			size = dis.readInt();
			}
			/*br = new BufferedReader( new InputStreamReader(ss.getInputStream(),"UTF-8"));
		
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
			br.close();*/
			
		
		
			
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
	}
}
