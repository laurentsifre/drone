package drone.s01.correction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.AttitudeListener;
import com.shigeodayo.ardrone.navdata.javadrone.NavData;
import com.shigeodayo.ardrone.navdata.javadrone.NavDataListener;



public class DisplayInstrumentsGraph {

	private ARDrone ardrone;
	private JFrame jFrame;
	private JPanel jPanel;
	private List<GraphPanel> graphPanels;
	
	public DisplayInstrumentsGraph(){
		// 1- init swing objects
		jFrame = new JFrame(DisplayInstruments.class.toString());
		jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(5, 1));
		
		graphPanels = new ArrayList<GraphPanel>();
		int nVal = 128;
		int minVal = -180;
		int maxVal = 180;
		graphPanels.add(new GraphPanel(nVal, minVal, maxVal, "pitch"));
		graphPanels.add(new GraphPanel(nVal, minVal, maxVal, "roll"));
		graphPanels.add(new GraphPanel(nVal, minVal, maxVal, "yaw"));
		graphPanels.add(new GraphPanel(nVal, 0, 5, "alt"));
		graphPanels.add(new GraphPanel(nVal, 0, 100, "bat"));
		
		// 2- add stuff to panel and panel to window
		jPanel.setBorder(new TitledBorder(new EtchedBorder(),"instruments"));
		jPanel.setPreferredSize(new Dimension(600,800));
		for (GraphPanel graphPanel : graphPanels){
			graphPanel.setPreferredSize(new Dimension(200,100));
			jPanel.add(graphPanel);
		}
		jFrame.add(jPanel);
		jFrame.repaint();
		
		// 3- display window
		jFrame.pack();
		jFrame.setVisible(true);
		
		
		// eventually blocking code, launch in a seperate thread
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// 4- init drone
				initDrone();
				//jText.setText("Drone init finished");
				
				// 5- listen to drone instruments
				ardrone.addNavDataListener(new NavDataListener() {
					@Override
					public void navDataUpdated(NavData navData) {
						graphPanels.get(0).putFloat(navData.getPitch());
						graphPanels.get(1).putFloat(navData.getRoll());
						graphPanels.get(2).putFloat(navData.getYaw());
						//graphPanels.get(3).putFloat(navData.getAltitude());
						graphPanels.get(4).putFloat(navData.getBattery());
						
						jFrame.repaint();
					}
				});
				ardrone.addAttitudeUpdateListener(new AttitudeListener() {
					
					@Override
					public void attitudeUpdated(float pitch, float roll, float yaw, int altitude) {
						graphPanels.get(0).putFloat(pitch);
						graphPanels.get(1).putFloat(roll);
						graphPanels.get(2).putFloat(yaw);
						graphPanels.get(3).putFloat(altitude);
					}
				});
			}
		});
		thread.start();
		
		

	}
	
	
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DisplayInstrumentsGraph();
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
