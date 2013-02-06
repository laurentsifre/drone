/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.utils.image;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class FloatImg {
    private int w,h,d;
    private int N;
    private float[] values;
    
    public FloatImg(int w, int h, int d){
        this.h = h;
        this.w = w;
        this.d = d;
        this.N = w*h*d;
        this.values = new float[w*h*d];
    }
    
    public FloatImg(int w,int h,int d,float[] values){
        this.h = h;
        this.w = w;
        this.d = d;
        this.N = w*h*d;
        this.values = values;
    }
    
    
    public FloatImg(BufferedImage img){
        //assumes that the buffered image is in RGB
        this.h = img.getHeight();
        this.w = img.getWidth();
        this.d = 3;
        this.N = h*w*d;
        this.values = new float[w*h*d];
        for (int y=0; y<h; y++)
            for (int x=0; x<w; x++){
                int rgb = img.getRGB(x, y);
                values[0+d*(x+w*y)] = 1/255.0f*((rgb>>16) & 0xFF);
                values[1+d*(x+w*y)] = 1/255.0f*((rgb>>8)  & 0xFF);
                values[2+d*(x+w*y)] = 1/255.0f*((rgb)     & 0xFF);
            }
    }
    
    public float getValueAt(int x, int y, int z){
        return values[z+d*(x+w*y)];
    }
    
    public int getH(){
        return h;
    }
    
    public int getW(){
        return w;
    }
    
    public int getD(){
        return d;
    }
    
    
    public float[] getValues(){
        return values;
    }
    
    public void setValueAt(int x,int y,int z,float val){
        values[z+d*(x+w*y)]=val;
    }
    
    public FloatImg downResX2(){
        int w = this.getW();
        int h = this.getH();
        int d = this.getD();
        float[] imgval = this.getValues();
        FloatImg imgdownres = new FloatImg(w/2, h/2, d);
        float[] imgvaldownres = imgdownres.getValues();
        for (int y=0; y<(h/2); y++)
            for (int x=0; x<(w/2); x++){
                for (int k=0;k<d ;k++){
                    imgvaldownres[k + d*(x+(w/2)*y)] =
                            (imgval[k +d*(2*x + w*2*y)] +     
                            imgval[k +d*(2*x+1 + w*2*y)] +
                            imgval[k +d*(2*x + w*(2*y+1))] +
                            imgval[k +d*(2*x+1 + w*(2*y+1))])/4 ;
                }
            }
        return imgdownres;
    }
    
    public float[] getMinMax(int chan){
        float m = 1E20f;
        float M = -1E20f;
        int wh=w*h;
        for (int k = 0; k<wh; k++){
            m = Math.min(m, values[chan +d*k]);
            M = Math.max(M, values[chan +d*k]);
        }
        return new float[]{m,M};
    }
    
    public BufferedImage toBufferedImageBW(int k){
        float[] minMax = this.getMinMax(k);
        float m = minMax[0];
        float Delta = minMax[1] - m;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int y=0; y<h; y++)
            for (int x=0; x<w; x++){
                int val = (int)(255*( values[k+d*(x+w*y)]-m)/Delta );
                image.setRGB(x, y, rgb2int(val,val,val));
            }
        return image;
    }
    
    public BufferedImage toBufferedImageRGB(){
        //assumes that d>=3
        //and that components are between 0 and 1
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int y=0; y<h; y++)
            for (int x=0; x<w; x++){
                int k = d*(x+w*y);
                image.setRGB(x, y, rgb2int( (int)(255*values[0+k]),(int)(255*values[1+k]),(int)(255*values[2+k])));
            }
        return image;
    }
    
    public void putRand(){
        for (int k = 0; k<N; k++){
            values[k] = (float)(Math.random());
        }
    }
    
    public static int rgb2int(final int red, final int green, final int blue) {
        return (red << 16) + (green << 8) + blue;
    }
    
    public FloatImg rgb2hsv(){
        // assumes current image is rgb (d>=3)
        FloatImg hsvImg = new FloatImg(w, h, 3);
        float[] hsvImgVal = hsvImg.values;
        float[] hsv = new float[3];
        for (int y=0; y<h; y++)
            for (int x=0; x<w; x++){
                int k = d*(x+w*y);
                //RGBtoHSV((int)(255*values[0+k]),(int)(255*values[1+k]),(int)(255*values[2+k]),hsv);
            	Color.RGBtoHSB((int)(255*values[0+k]),(int)(255* values[1+k]),(int)(255* values[2+k]), hsv);
                hsvImgVal[0 +3*(x+w*y)] = hsv[0];
                hsvImgVal[1 +3*(x+w*y)] = hsv[1];
                hsvImgVal[2 +3*(x+w*y)] = hsv[2];
            }
        
        return hsvImg;
    }
    
    
    
    
    
    public static void RGBtoHSV(float r, float g, float b, float[] hsv){
        // r g b between 0 and 1
        // h between -60, 300
        // s
        // v
        float M = Math.max(r, Math.max(g, b));
        float m = Math.min(r, Math.min(g, b));
        float C  = M - m;
        
        float hp = 0;
        if (C>1E-5){
            if (M == r){
                hp = (g-b)/C + 1 ;
            }
            if (M == g){
                hp = (b-r)/C + 3;
            }
            if (M == b){
                hp = (r-g)/C + 5;
            }
        }
        float H = 60*hp;
        float s = 0;
        if (M>1E-10){
            s = C/M;
        }
        float v = M;
        if (Math.random()<1E-5){
            //System.out.format("RGBHSV r %f g %f b %f h %f s %f v %f %n",r,g,b,H,s,v);
        }
        hsv[0]= H;
        hsv[1]= s;
        hsv[2]= v;
    }
    

}