package drone.s01.todo;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.javadrone.NavData;
import com.shigeodayo.ardrone.navdata.javadrone.NavDataListener;



public class DisplayInstruments {

	private ARDrone ardrone;
	private JFrame jFrame;
	private JPanel jPanel;
	private JTextArea jText;
	
	public DisplayInstruments(){
		// 1- init swing objects
		// 2- add stuff to panel and panel to window
		// 3- display window
		
		// eventually blocking code, launch in a seperate thread
				// 4- init drone
				// 5- listen to drone instruments
	}
	
	
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DisplayInstruments();
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
