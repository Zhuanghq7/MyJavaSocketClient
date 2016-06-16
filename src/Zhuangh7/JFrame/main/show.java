package Zhuangh7.JFrame.main;

import org.bytedeco.javacv.CanvasFrame;

public class show extends Thread{
	private CanvasFrame canvas;
	public show(CanvasFrame canvas){
		this.canvas = canvas;
	}
	
	@Override
	public void run(){
		while(true){
		if(!Listener_image.images.isEmpty()){
			if(canvas == null){
	        	canvas = new CanvasFrame("Image",1);
	        }
	        canvas.showImage(Listener_image.images.get(Listener_image.images.size()-1));
	        Listener_image.images.remove(Listener_image.images.size()-1);
		}
		}
	}
}
