package drone.s01.todo;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.video.ImageListener;

import drone.utils.image.ImagePanel;

public class DisplayVideo {


	private ARDrone ardrone;
	private JFrame jFrame;
	private JPanel jPanel;
	private ImagePanel imagePanel;


	private DisplayVideo(){
		// TODO
		//1- init swing objects
		
		//2- add stuff to panel and panel to frame
		
		//3- init drone
		
		//4- bind image panel to the drone
		
		//5- Display the window.
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DisplayVideo();
			}
		});
	}

	private void initDrone(){
		ardrone=new ARDrone("192.168.1.1");
		System.out.println("connect drone controller");
		ardrone.connect();
		System.out.println("connect drone navdata");
		ardrone.connectNav();
		System.out.println("connect drone video");
		ardrone.connectVideo();
		System.out.println("start drone");
		ardrone.start();
		ardrone.setMaxAltitude(5000); 
	}


}
