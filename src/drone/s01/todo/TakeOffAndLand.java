package drone.s01.todo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import com.shigeodayo.ardrone.ARDrone;



public class TakeOffAndLand {

	
	private ARDrone ardrone;
	private JFrame jFrame;
	private JPanel jPanel;
	private JButton initDrone;
	private JButton takeOffButton;
	private JButton landButton;
	
	private TakeOffAndLand(){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}	
	
	private void createAndShowGUI(){
		// TODO
		// 1- init swing objects
		// 2- bind buttons 
		// 3- add stuff to panel and panel to window
        // 4- Display the window
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
		ardrone.setMaxAltitude(5000); // max 5 meters
	}
	
	public static void main(String[] args) {
		new TakeOffAndLand();
	}

}
