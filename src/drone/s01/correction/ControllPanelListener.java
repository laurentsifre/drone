/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.s01.correction;

import drone.utils.image.MyPoint2d;



/**
 *
 * @author lolosifre
 */
public interface ControllPanelListener {
    public void notify(MyPoint2d newValue);
}
