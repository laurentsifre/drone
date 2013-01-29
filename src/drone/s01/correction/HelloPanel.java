package drone.s01.correction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import drone.utils.image.ImagePanel;

@SuppressWarnings("serial")
public class HelloPanel extends JPanel{

	public HelloPanel(){
		JLabel label = new JLabel("Hello Drone");
		JButton button = new JButton("Hello Button");
		ImagePanel imagePan = new ImagePanel("data/drone.jpg");
		this.add(label);
		this.add(button);
		this.add(imagePan);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("button has been clicked");
				
			}
		});
	}
	
	

}
