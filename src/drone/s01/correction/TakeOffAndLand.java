package drone.s01.correction;

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
		jFrame = new JFrame();
		jPanel = new JPanel();
		initDrone = new JButton("Init drone !");
		takeOffButton = new JButton("Take off !");
		landButton = new JButton("Land !");
		bindButtons();
		
		// add stuff to panel and panel to window
		jPanel.add(initDrone);
		jPanel.add(takeOffButton);
		jPanel.add(landButton);
		jFrame.add(jPanel);
		
		 
        //Display the window.
        jFrame.pack();
        jFrame.setVisible(true);
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
