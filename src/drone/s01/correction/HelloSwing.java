package drone.s01.correction;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class HelloSwing {

	 private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("HelloDrone");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        JPanel panel = new HelloPanel();
	        frame.getContentPane().add(panel);
	        
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
	 
	 public static void main(String[] args) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
		
}
