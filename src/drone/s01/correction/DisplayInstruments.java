package drone.s01.correction;

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
		jFrame = new JFrame(DisplayInstruments.class.toString());
		jPanel = new JPanel();
		jText = new JTextArea("Wait for init drone");
		jText.setEditable(false);
		
		// 2- add stuff to panel and panel to window
		jPanel.add(jText);
		jFrame.add(jPanel);
		jPanel.setBorder(new TitledBorder(new EtchedBorder(),"instruments"));
		jPanel.setPreferredSize(new Dimension(600,800));
		
		// 3- display window
		jFrame.pack();
		jFrame.setVisible(true);
		
		// eventually blocking code, launch in a seperate thread
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// 4- init drone
				initDrone();
				jText.setText("Drone init finished");
				
				// 5- listen to drone instruments
				ardrone.addNavDataListener(new NavDataListener() {
					@Override
					public void navDataUpdated(NavData navData) {
						jText.setText(navData.toDetailString());
					}
				});
			}
		});
		thread.start();
		
		

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
