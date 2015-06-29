import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class responsible for updating webcam images 
 * @author Pavlo Paskalov 2015
 *
 */

public class VideoFrame extends JFrame {
	
	private VideoCam camera;
	private JPanel contentPane;
	private JPanel framePanel;

	/**
	 * Create the frame.
	 */
	public VideoFrame(VideoCam camera) {
		super("Gender recognition");
		this.camera = camera;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePanel = new JPanel(new BorderLayout());
		contentPane = new JPanel();
		framePanel.add(contentPane);
		
		setContentPane(framePanel);
		new Refresher().start();
		setSize(640,480);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		g = contentPane.getGraphics();
		g.drawImage(camera.getCurrentImage(), 0, 0, this);
	}
	/**
	 * Image refresher thread
	 * @author Pavlo Paskalov
	 *
	 */
	private class Refresher
					extends Thread {
		@Override 
		public void run() {
			while (!this.isInterrupted()) {
				repaint();
			}
		}
	}

}
