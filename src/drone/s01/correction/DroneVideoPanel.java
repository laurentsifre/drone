package drone.s01.correction;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.video.ImageListener;

import drone.utils.image.ImagePanel;

@SuppressWarnings("serial")
public class DroneVideoPanel extends JPanel {

	private ARDrone ardrone;
	private ImagePanel imagePanel;
	private JButton toggleButton;

	public DroneVideoPanel(ARDrone ardrone){
		this.ardrone = ardrone;
		imagePanel = new ImagePanel("data/drone.jpg");
		toggleButton = new JButton("toggle camera");
		this.add(toggleButton);
		this.add(imagePanel);
		this.setBorder(new TitledBorder(new EtchedBorder(),"videopanel"));
		this.setPreferredSize(new Dimension(700,400));
		bindImagePanel();
		bindButton();
	}


	private void bindButton() {
		toggleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ardrone.toggleCamera();
				
			}
		});
		
	}


	private void bindImagePanel(){
		ardrone.addImageUpdateListener(new ImageListener() {
			@Override
			public void imageUpdated(BufferedImage image) {
				imagePanel.setImage(image);
			}
		});
	}

}
