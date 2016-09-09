import java.awt.EventQueue;
import javax.swing.JFrame;

/**
* Final Project
* CS565 - Advanced Java Programming
* Due Date: 04/26/2016
* @author ipaluyanava
* 
* Creates and displays main application form.
*/

public class Simulator {
	/**
	 * Creates main application form.
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Formulator mainForm = new Formulator();
				mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainForm.setSize(814, 574);
				mainForm.setLocationRelativeTo(null);
				mainForm.setVisible(true);
			}
		});    
	}    
}
