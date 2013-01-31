package drone.s01.correction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;



import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.AttitudeListener;
import com.shigeodayo.ardrone.navdata.javadrone.NavData;
import com.shigeodayo.ardrone.navdata.javadrone.NavDataListener;



@SuppressWarnings("serial")
public class DroneInstrumentPanel extends JPanel {

	private ARDrone ardrone;

	private List<GraphPanel> graphPanels;

	public DroneInstrumentPanel(ARDrone ardrone){
		this.ardrone = ardrone;
		// 1- init swing objects

		this.setLayout(new GridLayout(5, 1));

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
		this.setBorder(new TitledBorder(new EtchedBorder(),"instruments"));
		this.setPreferredSize(new Dimension(150,300));
		for (GraphPanel graphPanel : graphPanels){
			graphPanel.setPreferredSize(new Dimension(200,100));
			this.add(graphPanel);
		}


		// 5- listen to drone instruments
		this.ardrone.addNavDataListener(new NavDataListener() {
			@Override
			public void navDataUpdated(NavData navData) {
				graphPanels.get(0).putFloat(navData.getPitch());
				graphPanels.get(1).putFloat(navData.getRoll());
				graphPanels.get(2).putFloat(navData.getYaw());
				//graphPanels.get(3).putFloat(navData.getAltitude());
				graphPanels.get(4).putFloat(navData.getBattery());

				repaint();
			}
		});
		this.ardrone.addAttitudeUpdateListener(new AttitudeListener() {

			@Override
			public void attitudeUpdated(float pitch, float roll, float yaw, int altitude) {
				graphPanels.get(0).putFloat(pitch);
				graphPanels.get(1).putFloat(roll);
				graphPanels.get(2).putFloat(yaw);
				graphPanels.get(3).putFloat(altitude);
				repaint();
			}
		});


	}


}
