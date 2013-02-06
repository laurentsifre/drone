/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.utils.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author lolosifre
 */
public class ImageStream {
    private ArrayList<ImageStreamListener> listeners;
    
    
    public ImageStream(){
        this.listeners = new ArrayList<ImageStreamListener>();
    }
    
    public void register(ImageStreamListener listener){
        listeners.add(listener);
    }
    
    public void notifyListeners(BufferedImage bi){
        for (ImageStreamListener listener : listeners){
            listener.onNewImage(bi);
        }
    }
}
