package drone.s02.correction;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import drone.s02.correction.ConnexComponentExtractor.View;
import drone.utils.image.ImagePanel;

@SuppressWarnings("serial")
public class ConnexComponentExtractorView extends JPanel implements View {
	
	private ImagePanel imAllCCPanel;
	private ImagePanel imBiggestCCPanel;
	
	
	
	public ConnexComponentExtractorView(){
		super();
		imAllCCPanel = new ImagePanel(10,10);
		imBiggestCCPanel = new ImagePanel(10,10);
		imAllCCPanel.setPreferredSize(new Dimension(200,200));
		imBiggestCCPanel.setPreferredSize(new Dimension(200,200));
		this.add(imAllCCPanel);
		this.add(imBiggestCCPanel);
		this.setBorder(new TitledBorder(new EtchedBorder(),"extract connex component"));
		
	}



	@Override
	public void setAllCCImage(BufferedImage image) {
		imAllCCPanel.setImage(image);
	}



	@Override
	public void setLargestCCImage(BufferedImage image) {
		imBiggestCCPanel.setImage(image);
	}
	
	
	
}
