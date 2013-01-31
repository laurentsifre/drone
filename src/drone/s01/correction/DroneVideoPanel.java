package drone.s01.correction;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

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

	public DroneVideoPanel(ARDrone ardrone){
		this.ardrone = ardrone;
		imagePanel = new ImagePanel("data/drone.jpg");
		this.add(imagePanel);
		this.setBorder(new TitledBorder(new EtchedBorder(),"videopanel"));
		this.setPreferredSize(new Dimension(700,400));
		bindImagePanel();
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
