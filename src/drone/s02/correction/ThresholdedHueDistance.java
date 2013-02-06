package drone.s02.correction;


import java.awt.image.BufferedImage;

import drone.utils.image.FloatImg;
import drone.utils.image.ImageStream;
import drone.utils.image.ImageStreamListener;

public class ThresholdedHueDistance implements ImageStreamListener{

	private float targetHue;
	private float distanceThreshold = 0.3f;
	private float satureThreshold = 0.6f;
	private FloatImg imIn;
	
	private ImageStream imageStream;
	
	private View view;

	public ThresholdedHueDistance(View view, float targetHue2){
		this.view = view;
		this.targetHue = targetHue2;
		imageStream = new ImageStream();
		view.addListener(new HueChangeListener() {
			@Override
			public void onNewHue(float h) {
				targetHue = h;
				thresholdedUeDistance(imIn);
			}
		});
		
		view.addListener(new SatureChangeListener() {
			
			@Override
			public void oneNewSature(float s) {
				satureThreshold = s;
				thresholdedUeDistance(imIn);
			}
		});
		
		view.addDistanceThresholdListener(new DistanceThresholdListener() {
			
			@Override
			public void onNewDT(float s) {
				distanceThreshold = s;
				thresholdedUeDistance(imIn);
			}
		});
	}

	public interface View{
		public void distance(BufferedImage image);
		public void distanceThres(BufferedImage image);
		public void addListener(HueChangeListener listener);
		public void addListener(SatureChangeListener listener);
		public void addDistanceThresholdListener(DistanceThresholdListener listener);
	}
	
	public interface HueChangeListener{
		public void onNewHue(float h);
	}
	
	public interface SatureChangeListener{
		public void oneNewSature(float s);
	}
	
	public interface DistanceThresholdListener{
		public void onNewDT(float s);
	}

	public FloatImg thresholdedUeDistance(FloatImg in){
		this.imIn = in;
		FloatImg target = in.rgb2hsv();

		int w = target.getW();
		int h = target.getH();

		FloatImg distanceImage = new FloatImg(w, h, 3);

		float H,S;
		for (int y=0; y<h; y++)
			for (int x=0; x<w; x++){
				H = target.getValueAt(x, y, 0);
				S = target.getValueAt(x, y, 1);
				//float B = target.getValueAt(x, y, 2);
				// threshold saturation
				
				S = (S>satureThreshold) ? 1 : 0;
				distanceImage.setValueAt(x, y, 0, S);


				float distance = absMinMod(H-targetHue, 1) ;
				distance = (S==1) ? distance : 1;
				distanceImage.setValueAt(x, y, 1, distance) ;

				// threshold distance
				float distanceThres = (distance>distanceThreshold) ?0:1;
				distanceImage.setValueAt(x, y, 2, distanceThres) ;

			}

		BufferedImage imOut = distanceImage.toBufferedImageBW(2);
		if (view!=null){
			view.distance(distanceImage.toBufferedImageBW(0));
			view.distanceThres(imOut);
		}
		imageStream.notifyListeners(imOut);
		
		return distanceImage;
	}

	private float mod(float a, float b){
		return (a>=0) ? a%b : (b-((-a)%b))%b ; // true modulus operator
	}
	private float absMinMod(float a,float b){
		float am = mod(a,b);
		return Math.abs(Math.min(am,b-am));
	}

	public float getTargetHue() {
		return targetHue;
	}

	public void setTargetHue(float targetHue) {
		this.targetHue = targetHue;
	}

	public float getDistanceThreshold() {
		return distanceThreshold;
	}

	public void setDistanceThreshold(float distanceThreshold) {
		this.distanceThreshold = distanceThreshold;
	}

	public float getSatureThreshold() {
		return satureThreshold;
	}

	public void setSatureThreshold(float satureThreshold) {
		this.satureThreshold = satureThreshold;
	}
	
	public ImageStream getImageStream(){
		return imageStream;
	}

	@Override
	public void onNewImage(BufferedImage image) {
		thresholdedUeDistance(new FloatImg(image));
	}


}


