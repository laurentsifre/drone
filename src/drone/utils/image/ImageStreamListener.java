/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.utils.image;

import java.awt.image.BufferedImage;

/**
 *
 * @author lolosifre
 */
public interface ImageStreamListener {
    public void onNewImage(BufferedImage image);
}
