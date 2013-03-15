/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drone.s01.correction;


import com.shigeodayo.ardrone.ARDrone;

import drone.utils.image.MyPoint2d;

public class DroneController{
	private ARDrone drone;
	private MyPoint2d horizontalTargetSpeed;
	private MyPoint2d verticalAndRotationalTargetSpeed;

	public DroneController(ControllPanel horizontalController,
			ControllPanel verticalAndRotationalController,
			ARDrone drone){
		horizontalController.register(new HorizontalTargetSpeedListener());
		verticalAndRotationalController.register(new VerticalAndRotationalTargetSpeedListener());
		this.drone = drone;
		horizontalTargetSpeed = new MyPoint2d(0,0);
		verticalAndRotationalTargetSpeed = new MyPoint2d(0,0);
	}

	class HorizontalTargetSpeedListener implements ControllPanelListener{
		@Override
		public void notify(MyPoint2d newValue) {
			horizontalTargetSpeed = newValue;
			updateDroneCommand();
		}
	}

	class VerticalAndRotationalTargetSpeedListener implements ControllPanelListener{
		@Override
		public void notify(MyPoint2d newValue) {
			verticalAndRotationalTargetSpeed = newValue;
			updateDroneCommand();
		}
	}

	public void updateDroneCommand(){
		if (drone != null){
			float left_right_tilt = (float) horizontalTargetSpeed.x;
			float front_back_tilt = (float) - horizontalTargetSpeed.y;
			float vertical_speed =  (float) verticalAndRotationalTargetSpeed.y;
			float angular_speed = (float) verticalAndRotationalTargetSpeed.x;

			drone.move3D((int)(100 *-front_back_tilt), (int)(100 *-left_right_tilt), (int)(100 *-vertical_speed), (int)(100 *-angular_speed));

		}

	}


}
