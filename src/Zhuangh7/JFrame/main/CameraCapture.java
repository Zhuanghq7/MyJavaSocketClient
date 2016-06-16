package Zhuangh7.JFrame.main;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
	public static Vector<IplImage> vector= new Vector<IplImage>();
	public CameraCapture(Socket s){
		ss = s;
	}
    public static void start() throws Exception   
    {   
  
        final String savedImageFile = "d:\\my.jpg";   //�������洢���ļ�  
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);    
        grabber.start();   //��ʼ��ȡ����ͷ����  
        IplImage image =grabber.grab(); //������ȡ����ͷ���ݷ���IplImage  
        
        int width = image.width();   
        //image.getBufferedImage();
        int height = image.height();  
        final BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);    
        Graphics2D bGraphics = bImage.createGraphics();                         
        
        CanvasFrame canvas = new CanvasFrame("Camera",1);  
        canvas.setCanvasSize(width, height);    
          
        canvas.getCanvas().addMouseListener(new MouseAdapter(){    
                public void mouseClicked(MouseEvent e){         
                  try{  
                    // ImageIO.write(bImage, "jpg", new File(savedImageFile));//����д��my.jpg
                	  
                	 // BufferedImage image = ImageIO.read(new File("1.gif"));  //��ȡ1.gif������  
                      ByteArrayOutputStream out = new ByteArrayOutputStream();  
                      boolean flag = ImageIO.write(bImage, "jpg", out);  
                      byte[] b = out.toByteArray();
                      ss.getOutputStream().write(b.length);
                      ss.getOutputStream().write(b);
                     // dout.write(b);  
                     }   
                  catch (IOException e1){  
                    // TODO Auto-generated catch block  
                    e1.printStackTrace();  
                    }    
               }  
            });  
        if(canvas.isVisible()){
        	MainClass.print("����ͷ��������");
        }
       while(canvas.isVisible() && (image=grabber.grab()) != null){    
        canvas.showImage(image);   
        bGraphics.drawImage(image.getBufferedImage(),null,0,0);   
        }  
          
            //cvReleaseImage(image);   
       image.release();
       grabber.stop();    
       canvas.dispose();       
    }  
      
}  