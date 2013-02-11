package drone.s02.correction;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import drone.utils.image.FloatImg;
import drone.utils.image.ImagePanel;
import drone.utils.image.TestImageFactory;

@SuppressWarnings("serial")
public class ConnexComponentExtractorTest extends JFrame{
	
	private JPanel mainPanel;
	private ImagePanel imageInPanel;
	private ConnexComponentExtractorView cceView;
	private ConnexComponentExtractor cce;
	private ConnexComponentExtractorTest(){
		mainPanel = new JPanel();
		imageInPanel = new ImagePanel(TestImageFactory.diagonaleWithHole(16));
		imageInPanel.setPreferredSize(new Dimension(200,200));
		cceView = new ConnexComponentExtractorView();
		cce = new ConnexComponentExtractor(cceView);
		cce.extractNew(new FloatImg(imageInPanel.getImage()),0);
		mainPanel.add(imageInPanel);
		mainPanel.add(cceView);
		
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ConnexComponentExtractorTest();
			}
		});
		
	}

}
