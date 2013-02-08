package drone.s02.todo;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import drone.utils.image.FloatImg;
import drone.utils.image.ImagePanel;

public class ThresholdedHueDistanceTest {

	private JFrame jFrame;
	private JPanel mainPanel;
	private ImagePanel imPanel;
	private BufferedImage im;
	private ThresholdedHueDistanceView view;
	private ThresholdedHueDistance thresDisController;
	private float targetHue;
	
	public ThresholdedHueDistanceTest(){
		jFrame = new JFrame("demo threshold distance");
		mainPanel = new JPanel();
		imPanel = new ImagePanel("data/drone.jpg");
		im = imPanel.getImage();
		view = new ThresholdedHueDistanceView();
		targetHue = 0;
		thresDisController = new ThresholdedHueDistance(view, targetHue);
		
		mainPanel.add(imPanel);
		mainPanel.add(view);
		thresDisController.thresholdedUeDistance(new FloatImg(im));
		jFrame.add(mainPanel);
		
		
		jFrame.pack();
		jFrame.setVisible(true);
		
	}
	
	public static void main(String[] args) {	
		new ThresholdedHueDistanceTest();
	}

}
