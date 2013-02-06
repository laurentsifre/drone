/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.s02.correction;



import drone.utils.image.FloatImg;

/**
 *
 * @author lolosifre
 */
public class ConnexComponentExtractor {
	
	
	public interface View {
		// TODO
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

	public static FloatImg dilate4(FloatImg targetBinaryImage, int channelId){
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

	public static FloatImg extract(FloatImg targetBinaryImage, int channelId){

		int w = targetBinaryImage.getW();
		int h = targetBinaryImage.getH();
		int d = targetBinaryImage.getD();

		float[] targetValues = targetBinaryImage.getValues();


		int maxLabel = 10000;
		int[] equivalentLabel = new int[maxLabel];
		int biggerLabel = 1;
		int dcc = 1;
		FloatImg cc = new FloatImg(w, h, dcc);
		float[] ccval = cc.getValues();

		for (int y=0; y<h; y++){
			for (int x=0; x<w; x++){
				//float val = targetBinaryImage.getValueAt(x, y, channelId);
				float val = targetValues[channelId + d*(x+w*y)];
				if (val == 1){
					// check previous neighbor labeling
					int nx = mod(x-1, w);
					int ny = mod(y-1, h);
					float nlab1 = ccval[ dcc*(nx+w*y) ];
					float nlab2 = ccval[ dcc*(x+w*ny) ];
					int numberOfLabeledNeighbor=0;
					if (nlab1>0) numberOfLabeledNeighbor++;
					if (nlab2>0) numberOfLabeledNeighbor++;

					// first pass
					switch (numberOfLabeledNeighbor){
					case 0:
						// neighbors are not labeled :
						// assign to a new label.
						ccval[dcc*(x+w*y)] = biggerLabel;
						equivalentLabel[biggerLabel] = biggerLabel;
						biggerLabel++;
						break;
					case 1:
						// one neighbor is labeled :
						// assign to its label.
						float nlab = (nlab1>0) ? nlab1 : nlab2;
						ccval[dcc*(x+w*y)] = nlab;
						break;
					case 2:
						// two neigbors are labeled :
						// assign to the smallest
						// put equivalent relationship if needed
						float nlabSmaller = (nlab1<=nlab2) ? nlab1 : nlab2;
						float nlabBigger  = (nlab1<=nlab2) ? nlab2 : nlab1;
						ccval[dcc*(x+w*y)] = nlabSmaller;
						if (nlabBigger>nlabSmaller){
							equivalentLabel[(int) nlabBigger] = (int)(nlabSmaller);
						}
						// TODO CHECK
					}// end switch
				}// end if
			}//end for x
		}// end for y
		// find the unique class representant (the smallest)
		for (int c =1; c<biggerLabel +1; c++){
			int cmin = c;
			while (equivalentLabel[cmin]< cmin){
				cmin = equivalentLabel[cmin];
			}
			equivalentLabel[c]= cmin;

		}
		// second pass : assing label to smallest representant
		// and class count
		int[] classCount = new int[equivalentLabel.length];
		for (int y=0; y<h; y++)
			for (int x=0; x<w; x++){
				int lab = (int) ccval[dcc*(x+w*y)];
				int smalletRepresentant = equivalentLabel[lab];
				ccval[dcc*(x+w*y)] = smalletRepresentant;
				classCount[smalletRepresentant]++;
			}
		// find max
		int maxClassCount = 0;
		int iMaxClassCount = -1;
		for (int c =1; c<classCount.length;c++){
			if (classCount[c]>maxClassCount){
				maxClassCount = classCount[c];
				iMaxClassCount = c;
			}
		}
		// third pass :
			// keep only largest connex component.
			for (int y=0; y<h; y++)
				for (int x=0; x<w; x++){
					int c = (int) cc.getValueAt(x, y, 0);
					if (c!= iMaxClassCount){
						ccval[dcc*(x+w*y)]= 0;
					}
				}
			return cc;
	}
}
