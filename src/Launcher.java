import java.awt.EventQueue;


public class Launcher {
	
	public static void main(String args[]) {
		VideoCam camera = new VideoCam();
		 try {
			 Thread.sleep(1000);
		 } catch (InterruptedException ex) {ex.printStackTrace();}
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VideoFrame frame = new VideoFrame(camera);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
