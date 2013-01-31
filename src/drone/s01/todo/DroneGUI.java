package drone.s01.todo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.shigeodayo.ardrone.ARDrone;

public class DroneGUI {

	JFrame jFrame;
	JPanel mainPanel;
	// TODO : ecrire une classe DroneControllPanel
	//DroneControllPanel droneControllPanel;
	// TODO : ecrire une classe DroneVideoPanel
	//DroneVideoPanel droneVideoPanel;
	// TODO : ecrire une classe droneInstrumentPanel
	//DroneInstrumentPanel droneInstrumentPanel;
	private ARDrone ardrone;
	
	private DroneGUI(){
		// todo 
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
