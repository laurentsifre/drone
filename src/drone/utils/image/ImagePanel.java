package drone.utils.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{

	private BufferedImage image;
	
	public void setImage(BufferedImage image){
		this.image = image;
		repaint();
	}

	public ImagePanel(String pathToFile){
		try {
			image = ImageIO.read(new File(pathToFile));
			resetDim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ImagePanel(BufferedImage image){
		this.image = image;
		resetDim();
	}

	
	public void resetDim(){
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		double scaleX=g.getClipBounds().width*1.0d/image.getWidth();
		double scaleY=g.getClipBounds().height*1.0f/image.getHeight();
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image, AffineTransform.getScaleInstance(scaleX,scaleY), this);
	}

}
