package drone.utils.image;

import java.awt.image.BufferedImage;

public class TestImageFactory {
	
	public static BufferedImage diagonale(int n){
		return diagonaleFloatImg(n).toBufferedImageBW(0);
	}

	private static FloatImg diagonaleFloatImg(int n){
		FloatImg img = new FloatImg(n, n, 1);
		for (int y = 0; y<n ; y++){
			for (int x=0 ; x<n; x++){
				if (x>=n-y){
					img.setValueAt(x, y, 0, 1);
				}
			}
		}
		return img;
	}
	
	public static BufferedImage diagonaleWithHole(int n){
		return diagonaleWithHoleFloatImg(n).toBufferedImageBW(0);
	}
	private static FloatImg diagonaleWithHoleFloatImg(int n){
		FloatImg img = diagonaleFloatImg(n);
		img.setValueAt((3*n)/4, (3*n)/4, 0, 0);
		return img;
	}
	
}
