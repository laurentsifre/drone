/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.s01.correction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class GraphPanel extends javax.swing.JPanel {
    
    private float[] data;
    private int currPos;
    private float minVal;
    private float maxVal;
    
    public GraphPanel(int nVal,float minVal,float maxVal,String title) {
        initComponents();
        this.setVisible(true);
        this.minVal=minVal;
        this.maxVal=maxVal;
        data=new float[nVal];
        this.currPos=0;
        titleLabel.setText(title);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Rectangle rectangle=g.getClipBounds();
        int xmin=rectangle.x+3;
        int ymin=rectangle.y+3;
        int w=rectangle.width-7;
        int h=rectangle.height-7;
        int x1,y1,x2,y2;
        float rescaledX,rescaledY;
        g.setColor(Color.blue);
        for (int elapsedTime=0;elapsedTime<data.length-1;elapsedTime++){
            int pos=currPos-elapsedTime-1;
            int posP=currPos-elapsedTime-2;
            if (pos<0){
                pos += data.length;
            }
            if (posP<0){
                posP += data.length;
            }
            rescaledY=(data[pos] - minVal)/(maxVal-minVal);
            y1=ymin+(int)(h*(1-rescaledY));
            rescaledY=(data[posP] - minVal)/(maxVal-minVal);
            y2=ymin+(int)(h*(1-rescaledY));
            rescaledX=(data.length-elapsedTime)/(1.0f*data.length);
            x1=(int)(xmin+w*(rescaledX));
            rescaledX=(data.length-elapsedTime-1)/(1.0f*data.length);
            x2=(int)(xmin+w*(rescaledX));
            g.drawLine(x1, y1, x2, y2);
        }
        g.setColor(Color.black);
        g.drawRect(xmin-1, ymin-1, w+2, h+2);
    }
    
    public void putFloat(float f){
        data[currPos]=f;
        currPos++;
        if (currPos==data.length) {currPos=0;}
    }
    
    private void initComponents() {
        titleLabel = new javax.swing.JLabel();
        titleLabel.setText("jLabel1");
        titleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        titleLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        add(titleLabel);
    }
    private javax.swing.JLabel titleLabel;

}
