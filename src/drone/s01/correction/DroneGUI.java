package drone.s01.correction;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.shigeodayo.ardrone.ARDrone;

public class DroneGUI {

	JFrame jFrame;
	JPanel mainPanel;
	DroneControllPanel droneControllPanel;
	DroneVideoPanel droneVideoPanel; 
	DroneInstrumentPanel droneInstrumentPanel;
	private ARDrone ardrone;
	
	private DroneGUI(){
		ardrone=new ARDrone("192.168.1.1");
		droneControllPanel = new DroneControllPanel(ardrone);
		droneVideoPanel = new DroneVideoPanel(ardrone);
		droneInstrumentPanel = new DroneInstrumentPanel(ardrone);
		jFrame = new JFrame("Drone GUI");
		mainPanel = new JPanel();
		mainPanel.add(droneControllPanel);
		mainPanel.add(droneVideoPanel);
		mainPanel.add(droneInstrumentPanel);
		jFrame.add(mainPanel);
		
		
		jFrame.pack();
        jFrame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DroneGUI();
			}
		});

	}

}
