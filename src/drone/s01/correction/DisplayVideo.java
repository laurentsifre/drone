package drone.s01.correction;

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
		//1- init swing objects
		jFrame = new JFrame("DisplayVideo");
		jPanel = new JPanel();
		imagePanel = new ImagePanel("data/drone.jpg");

		//2- add stuff to panel and panel to frame
		jPanel.add(imagePanel);
		jFrame.add(jPanel);

		//3- init drone
		initDrone();
		
		//4- bind image panel to the drone
		bindImagePanel();

		//5- Display the window.
		jFrame.pack();
		jFrame.setVisible(true);
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

	private void bindImagePanel(){
		ardrone.addImageUpdateListener(new ImageListener() {
			@Override
			public void imageUpdated(BufferedImage image) {
				imagePanel.setImage(image);
			}
		});
	}

}
