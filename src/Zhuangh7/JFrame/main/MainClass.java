package Zhuangh7.JFrame.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainClass {
	private static final String TITLE = "Socket_DEMO";
	public static final int WIN_WIDTH = 400;
	public static final int WIN_HEIGHT = 700;
	public static int Tag = 0;
	public static Socket ss;
	//public static Main_WIN mainWin;
	static Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
	static JPanel p1 = new JPanel();
	static JPanel p2 = new JPanel();
	static JPanel p3 = new JPanel();
	private static Listener listener;
	static ArrayList<JLabel> label = new ArrayList<JLabel>();
	private static int Str_NUM = 15;
	/*static JLabel label1 = new JLabel("1");
	static JLabel label2 = new JLabel("2");*/
	static JTextField tf = new JTextField();
	//static JButton b = new JButton("connect");
	static JLabel t;
	private static JFrame Win;
	
	public static void main(String[] args){
		Win = new JFrame(TITLE);
		InputHandler I = new InputHandler();
		Win.addMouseListener(I);
		Win.addKeyListener(I);
		Win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Win.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
		    { 
				if(ss!=null){
					try {
						if(listener!=null){
							listener.listennn = false;
						//	listener.br.close();
						}
						if(ss!=null){
							out("close");
							ss.close();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.exit(0); 
			}     
		});
		
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		Win.setLocation((width-WIN_WIDTH)/2, (height-WIN_HEIGHT)/2);
		Win.setResizable(true);
		//mainWin = new Main_WIN(WIN_WIDTH,WIN_HEIGHT);
		//Win.add(mainWin);
		Win.setLayout(new BorderLayout());
		initLabel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		for(int i = 0;i<Str_NUM;i++){
			p1.add(label.get(i));
		}
	//	p1.add(tf);
		Win.add(p1,BorderLayout.NORTH);
		p2.add(tf);
		tf.setPreferredSize(new Dimension(200,30));
		tf.setEditable(true);
		tf.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{			
					t.setForeground(Color.RED);
					String readline;
					readline=tf.getText().toString();
					out(readline);
					//System.out.println("Client:"+readline);
					print('`'+tf.getText());
					tf.setText("");
				}
				if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
					try {
						if(listener!=null){
							listener.listennn = false;
						//	listener.br.close();
						}
						if(ss!=null){
							out("close");
							ss.close();
						}
						System.exit(0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					print("断开连接");
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				t.setForeground(Color.GRAY);
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		t = new JLabel("SEND");
		p3.add(t,BorderLayout.CENTER);
		t.setForeground(Color.GRAY);
		Win.add(p2);
		Win.add(p3, BorderLayout.SOUTH);
		Win.pack();
		//Win.setSize(200,400);
		Win.setVisible(true);
		initSocket();
	}
	
	public static void initSocket(){
		try {
			ss = new Socket("123.206.208.46",1995);
			print("连接成功！");
			listener = new Listener(ss);
			listener.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			print("连接服务器失败！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			print("连接服务器失败！");
		}
	}
	
	public static void print(String str){
		label.get(Tag).setText(str);
		Tag++;
		if(Tag>=Str_NUM){
			Tag = 0;
		}
	}
	
	public static void initLabel(){
		for(int i = 0;i<Str_NUM;i++){
			JLabel temp = new JLabel(""+'`');
			label.add(temp);
		}
	}
	public static void mouseClicked(java.awt.event.MouseEvent m) {
		// TODO Auto-generated method stub
		
	}
	public static void keyPressed(KeyEvent k) {
	
		// TODO Auto-generated method stub
		
	}
	   public static void out(String out) {  
	        try {  
	    //    	PrintWriter os=new PrintWriter(ss.getOutputStream());
	            ss.getOutputStream().write((out+"\n").getBytes("UTF-8"));  

	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            MainClass.print("断开了一个客户端链接");  
	           // ChatManager.getChatManager().remove(this);  
	            e.printStackTrace();  
	        }  
	    }  	
}
