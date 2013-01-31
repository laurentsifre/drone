/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.s01.correction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import drone.utils.image.ImagePanel;
import drone.utils.image.MyPoint2d;



@SuppressWarnings("serial")
public class ControllPanel extends ImagePanel {
    
   
    private MyPoint2d targetSpeedRel;
    private ArrayList< ControllPanelListener> listeners;
    
    
    public ControllPanel(ConfigId configId) {
    	super(100, 100);
        targetSpeedRel = new MyPoint2d(0,0);
        listeners = new ArrayList<ControllPanelListener>();
        updateImage();
        setKeyBinding(configId);
    }
    
    public void register(ControllPanelListener listener){
        listeners.add(listener);
    }
    
    public void setTargetSpeedRel(MyPoint2d targetSpeedRel){
        this.targetSpeedRel = targetSpeedRel;
        updateImage();
        notifyListeners();
    }
    
    private void notifyListeners(){
        for (ControllPanelListener listener : listeners){
            listener.notify(targetSpeedRel);
        }
    }
    
    private BufferedImage getControllImage(int w, int h){
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        
        g2d.setColor(Color.red);
        int wcenter = 10;
        g2d.fillRect((w-wcenter)/2,(h-wcenter)/2,wcenter,wcenter);
        
        int wboundary = 10;
        g2d.drawRect(wboundary, wboundary, w-2*wboundary-1, h-2*wboundary-1);
        
        g2d.setColor(Color.white);
        int wtarget = 5;
        int posx = (int)((w-wtarget)/2.0  + (w/2.0-wboundary-wtarget)*targetSpeedRel.x) ;
        int posy = (int)((h-wtarget)/2.0  - (h/2.0-wboundary-wtarget)*targetSpeedRel.y) ;
        g2d.fillRect(posx,posy,wtarget,wtarget);
        return img;
    }
    
    private void updateImage(){
        BufferedImage img = getControllImage(100,100);
        this.setImage(img);
        repaint();
    }
    
    public enum ConfigId{
        AUTOPILOT,
        WSAD,
        UPDOWNLEFTRIGHT;
    }
    private void setKeyBinding(ConfigId configId){
        
        switch (configId){
            case WSAD:
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "GO_FORWARD");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "GO_FORWARD_STOP");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "GO_BACKWARD");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "GO_BACKWARD_STOP");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "GO_LEFT");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "GO_LEFT_STOP");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "GO_RIGHT");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "GO_RIGHT_STOP");
                break;
            case UPDOWNLEFTRIGHT:
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "GO_FORWARD");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "GO_FORWARD_STOP");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "GO_BACKWARD");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "GO_BACKWARD_STOP");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "GO_LEFT");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "GO_LEFT_STOP");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "GO_RIGHT");
                this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "GO_RIGHT_STOP");
                break;
		default:
			break;
        }
        
        switch (configId){
            case WSAD: case UPDOWNLEFTRIGHT :
                this.getActionMap().put("GO_FORWARD", new ActionChangeTargetSpeedFromKeyboard(1, 1));
                this.getActionMap().put("GO_FORWARD_STOP", new ActionChangeTargetSpeedFromKeyboard(1, 0));
                this.getActionMap().put("GO_BACKWARD", new ActionChangeTargetSpeedFromKeyboard(1, -1));
                this.getActionMap().put("GO_BACKWARD_STOP", new ActionChangeTargetSpeedFromKeyboard(1, 0));
                this.getActionMap().put("GO_LEFT", new ActionChangeTargetSpeedFromKeyboard(0, -1));
                this.getActionMap().put("GO_LEFT_STOP", new ActionChangeTargetSpeedFromKeyboard(0, 0));
                this.getActionMap().put("GO_RIGHT", new ActionChangeTargetSpeedFromKeyboard(0, 1));
                this.getActionMap().put("GO_RIGHT_STOP", new ActionChangeTargetSpeedFromKeyboard(0, 0));
                break;
		default:
			break;
        }
    }
    
    class ActionChangeTargetSpeedFromKeyboard extends AbstractAction{
        private int coordinateId; // 0 for x, 1 for y
        private double newCoordinateValue;
        
        public ActionChangeTargetSpeedFromKeyboard(int coordinateId,double newCoordinateValue){
            this.coordinateId = coordinateId;
            this.newCoordinateValue = newCoordinateValue;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (coordinateId == 0){
                targetSpeedRel.x = newCoordinateValue;
            }
            else {
                targetSpeedRel.y = newCoordinateValue;
            }
            updateImage();
            notifyListeners();
        }
        
    }
    
}
