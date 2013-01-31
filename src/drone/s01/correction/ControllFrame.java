package drone.s01.correction;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import drone.s01.correction.ControllPanel.ConfigId;

public class ControllFrame {

	private JFrame jFrame;
	private ControllPanel controllPanel;
	
	private ControllFrame(){
		jFrame = new JFrame();
		controllPanel = new ControllPanel(ConfigId.WSAD);
		
		jFrame.add(controllPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ControllFrame();
			}
		});

	}

}
