package Zhuangh7.JFrame.main;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class CameraCapture  
{    
	public static Socket ss;
	public static float TimeWindow = 100;
	public static float nowTime = 0;
	public static boolean begin = false;
	public static Vector<IplImage> vector= new Vector<IplImage>();
	public CameraCapture(Socket s){
		ss = s;
	}
    public static void start() throws Exception   
    {   
  
        final String savedImageFile = "d:\\my.jpg";   //拍摄所存储的文件  
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);    
        grabber.start();   //开始获取摄像头数据  
        IplImage image =grabber.grab(); //将所获取摄像头数据放入IplImage  
        
        int width = image.width();   
        //image.getBufferedImage();
        int height = image.height();  
        final BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);    
        Graphics2D bGraphics = bImage.createGraphics();                         
        
        CanvasFrame canvas = new CanvasFrame("Camera",1);  
        canvas.setCanvasSize(width, height);    
          
        canvas.getCanvas().addMouseListener(new MouseAdapter(){    
                public void mouseClicked(MouseEvent e){         
                 
                    // ImageIO.write(bImage, "jpg", new File(savedImageFile));//数据写入my.jpg
                	  
                	 // BufferedImage image = ImageIO.read(new File("1.gif"));  //读取1.gif并传输  
                      /*ByteArrayOutputStream out = new ByteArrayOutputStream();  
                      boolean flag = ImageIO.write(bImage, "jpg", out);  
                      byte[] b = out.toByteArray();
                      DataOutputStream dps = new DataOutputStream(ss.getOutputStream());
                      dps.writeInt(b.length);
                      dps.write(b);*/
                	  begin = true;
                     // dout.write(b);  
                     }   
                    
                 
            });  
        if(canvas.isVisible()){
        	MainClass.print("摄像头正常启动");
        }
        long updateDurationMillis = 0;
		
		
       while(canvas.isVisible() && (image=grabber.grab()) != null){
    	long beforeUpdateRender = System.nanoTime();
			
        canvas.showImage(image);   
        bGraphics.drawImage(image.getBufferedImage(),null,0,0);   
        
        
        updateDurationMillis = (System.nanoTime()-beforeUpdateRender)/1000000L;
        if(begin == true)
        {
        	nowTime += updateDurationMillis;
        }
        if(nowTime>=TimeWindow){
        	nowTime=0;
        	ByteArrayOutputStream out = new ByteArrayOutputStream();  
            boolean flag = ImageIO.write(bImage, "jpg", out);  
            byte[] b = out.toByteArray();
            DataOutputStream dps = new DataOutputStream(ss.getOutputStream());
            dps.writeInt(b.length);
            dps.write(b);
            dps.flush();
        }
        
        }  
          
            //cvReleaseImage(image);   
       image.release();
       grabber.stop();    
       canvas.dispose();       
    }  
      
}  