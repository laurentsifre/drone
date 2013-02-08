package drone.s02.todo;


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
		return null;
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


