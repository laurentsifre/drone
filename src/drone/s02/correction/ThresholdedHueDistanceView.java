package drone.s02.correction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import drone.s02.correction.ThresholdedHueDistance.DistanceThresholdListener;
import drone.s02.correction.ThresholdedHueDistance.HueChangeListener;
import drone.s02.correction.ThresholdedHueDistance.SatureChangeListener;
import drone.s02.correction.ThresholdedHueDistance.View;
import drone.utils.image.ImagePanel;

@SuppressWarnings("serial")
public class ThresholdedHueDistanceView extends JPanel implements View {

	private ImagePanel imPanel;
	private ImagePanel imPanel2;
	private JSlider sliderHue;
	private JSlider sliderSature;
	private JSlider sliderDistance;
	private JLabel labelHue;
	private JLabel labelSature;
	private JLabel labelDistance;
	private JPanel sliderPanel;
	private HueChangeListener hueChangeListener;
	private SatureChangeListener satureChangeListener;
	private DistanceThresholdListener distanceThresholdListener;
	
	
	public ThresholdedHueDistanceView(){
		imPanel = new ImagePanel(1, 1);
		imPanel2 = new ImagePanel(1, 1);
		sliderHue = new JSlider();
		sliderSature = new JSlider();
		sliderDistance = new JSlider();
		
		
		labelHue = new JLabel("hue");
		labelSature = new JLabel("sature");
		labelDistance = new JLabel("distance");
		
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(3, 2));
		
		
		
		sliderPanel.add(sliderHue);
		sliderPanel.add(labelHue);
		sliderPanel.add(sliderSature);
		sliderPanel.add(labelSature);
		sliderPanel.add(sliderDistance);
		sliderPanel.add(labelDistance);
		
		this.add(sliderPanel);
		
		this.add(imPanel);
		this.setBorder(new TitledBorder(new EtchedBorder(),"Thresholded hue distance"));
		
		
		this.add(imPanel2);
		bindSlider();
	}
	
	public void bindSlider(){
		sliderHue.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				float val = sliderHue.getValue()/100.0f;
				hueChangeListener.onNewHue(val);
			}
		});
		
		sliderSature.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				float val = sliderSature.getValue()/100.0f;
				satureChangeListener.oneNewSature(val);
			}
		});
		
		sliderDistance.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				float val = sliderDistance.getValue()/100.0f;
				distanceThresholdListener.onNewDT(val);
			}
		});
	}

	@Override
	public void distance(BufferedImage image) {
		imPanel.setImage(image);
		imPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		imPanel.setPreferredSize(new Dimension(200,100));
		repaint();
	}

	@Override
	public void distanceThres(BufferedImage image) {
		imPanel2.setImage(image);
		imPanel2.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		imPanel2.setPreferredSize(new Dimension(200,100));
		repaint();
	}

	@Override
	public void addListener(HueChangeListener listener) {
		this.hueChangeListener = listener;
		
	}

	@Override
	public void addListener(SatureChangeListener listener) {
		this.satureChangeListener = listener;
	}

	@Override
	public void addDistanceThresholdListener(DistanceThresholdListener listener) {
		this.distanceThresholdListener = listener;
	}

}
