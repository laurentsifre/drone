/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.s02.correction;



import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


import drone.utils.image.FloatImg;

/**
 *
 * @author lolosifre
 */
public class ConnexComponentExtractor {

	private View view;

	public interface View {
		public void setAllCCImage(BufferedImage image);
		public void setLargestCCImage(BufferedImage image);
	}

	public ConnexComponentExtractor(View view){
		this.view = view;
	}


	public static int mod(int a, int b){
		return (a>=0) ? a%b : (b-((-a)%b))%b ; // true modulus operator
	}

	public static void dilaten2(FloatImg targetBinaryImage, int channelId){
		// in place dilation 
		int w = targetBinaryImage.getW();
		int h = targetBinaryImage.getH();
		int d = targetBinaryImage.getD();
		float[] targetBinaryVal = targetBinaryImage.getValues();
		for (int y=0; y<h; y++){
			for (int x=0; x<w; x++){
				int nx2 = mod(x+1, w);
				int ny2 = mod(y+1, h);
				float nval = 0;
				nval+= targetBinaryVal[ channelId+ d*(nx2+w*y) ];
				nval+= targetBinaryVal[ channelId+ d*(x+w*ny2) ];
				if (nval>0){
					targetBinaryVal[ channelId + d*(x+w*y) ] = 1;
				}
			}
		}
	}

	public FloatImg dilate4(FloatImg targetBinaryImage, int channelId){
		// not in place but n4
		int w = targetBinaryImage.getW();
		int h = targetBinaryImage.getH();
		int d = targetBinaryImage.getD();
		float[] targetBinaryVal = targetBinaryImage.getValues();
		FloatImg dilatedImg = new FloatImg(w, h, 1);
		float[] dilatedImgVal = dilatedImg.getValues();
		for (int y=0; y<h; y++){
			for (int x=0; x<w; x++){
				int nx = mod(x-1, w);
				int ny = mod(y-1, h);
				int nx2 = mod(x+1, w);
				int ny2 = mod(y+1, h);
				float nval = targetBinaryVal[ channelId+ d*(x+w*y) ];
				nval+= targetBinaryVal[ channelId+ d*(nx+w*y) ];
				nval+= targetBinaryVal[ channelId+ d*(x+w*ny) ];
				nval+= targetBinaryVal[ channelId+ d*(nx2+w*y) ];
				nval+= targetBinaryVal[ channelId+ d*(x+w*ny2) ];
				if (nval>0){
					dilatedImgVal[ x+w*y ] = 1;
				}
			}
		}
		return dilatedImg;
	}

	public FloatImg extractNew(FloatImg targetBinaryImage, int channelId){

		int w = targetBinaryImage.getW();
		int h = targetBinaryImage.getH();
		

		FloatImg ccIm = new FloatImg(w, h, 2);
		List<Integer> labelGraph = new ArrayList<Integer>();
		labelGraph.add(new Integer(-1));
		// first pass
		for (int y=1; y<h; y++){
			for (int x=1; x<w; x++){
				if (targetBinaryImage.getValueAt(x, y, channelId)==1){
					int upLabel = (int) ccIm.getValueAt(x, y-1, 0);
					int leftLabel = (int) ccIm.getValueAt(x-1, y, 0);
					int smallerLabel = (upLabel<leftLabel)?upLabel:leftLabel;
					int biggerLabel = (upLabel>=leftLabel)?upLabel:leftLabel;
					int numberOfLabeledNeighbor = 0;
					if (smallerLabel>0) numberOfLabeledNeighbor++;
					if (biggerLabel>0) numberOfLabeledNeighbor++;
					switch (numberOfLabeledNeighbor) {
					case 0: 
						int newLabel = labelGraph.size();
						ccIm.setValueAt(x, y, 0, newLabel);
						labelGraph.add(new Integer(newLabel));
						break;
					case 1:
						ccIm.setValueAt(x, y, 0, new Integer(biggerLabel));
						break;

					case 2:
						// set all sons of larger label to the greater son of smaller label
						int smallerSonOfSmallerLabel = getSmallerSon(labelGraph, smallerLabel);
						setAllSon(labelGraph, biggerLabel, smallerSonOfSmallerLabel);
						setAllSon(labelGraph, smallerLabel, smallerSonOfSmallerLabel);
						ccIm.setValueAt(x, y, 0, new Integer(smallerSonOfSmallerLabel));
						break;

					default:
						break;
					}
				}
			}
		}
		view.setAllCCImage(ccIm.toBufferedImageBW(0));
		// second pass
		for (int y=1; y<h; y++){
			for (int x=1; x<w; x++){
				if (targetBinaryImage.getValueAt(x, y, channelId)==1){
					int label = labelGraph.get((int) ccIm.getValueAt(x, y, 0));
					ccIm.setValueAt(x, y, 0, label);
				}
			}
		}
		view.setLargestCCImage(ccIm.toBufferedImageBW(0));

		return ccIm;
	}
	public int getSmallerSon(List<Integer> graphLabel, int pos){
		if (graphLabel.get(pos).intValue() != pos){
			return getSmallerSon(graphLabel, graphLabel.get(pos).intValue() );
		}
		return graphLabel.get(pos).intValue();
	}

	public void setAllSon(List<Integer> graphLabel, int pos, int label){
		if (graphLabel.get(pos).intValue() != pos){
			setAllSon(graphLabel, graphLabel.get(pos).intValue(), label);
		}
		graphLabel.set(pos, new Integer(label));
	}


}
