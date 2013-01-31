package drone.s01.correction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.shigeodayo.ardrone.ARDrone;

import drone.s01.correction.ControllPanel.ConfigId;

public class DroneControllPanel extends JPanel {

	
	private ARDrone ardrone;
	private DroneController controller;
	
	
	private ControllPanel ctrPanel1;
	private ControllPanel ctrPanel2;
	
	private JButton initDrone;
	private JButton takeOffButton;
	private JButton landButton;
	
	
	public DroneControllPanel(ARDrone ardrone){
		this.ardrone = ardrone;
		
		ctrPanel1 = new ControllPanel(ConfigId.WSAD);
		ctrPanel2 = new ControllPanel(ConfigId.UPDOWNLEFTRIGHT);
		controller = new DroneController(ctrPanel1, ctrPanel2, ardrone);
	
		initDrone = new JButton("Init drone !");
		takeOffButton = new JButton("Take off !");
		landButton = new JButton("Land !");
		
		this.setBorder(new TitledBorder(new EtchedBorder(),"controll"));
		this.add(initDrone);
		this.add(takeOffButton);
		this.add(landButton);
		this.add(ctrPanel1);
		this.add(ctrPanel2);
		
		bindButtons();
		
	}
	
	private void bindButtons(){
		initDrone.addActionListener(new ActionListener() {
			
			// good practice : launch in a separate thread
			// to avoid block the GUI thread
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						initDrone();	
					}
				});
				thread.start();
			}
		});
		
		takeOffButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ardrone.reset();
				ardrone.takeOff();
			}
		});
		
		landButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ardrone.landing();	
			}
		});
	}
	
	private void initDrone(){
		
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
	

}
