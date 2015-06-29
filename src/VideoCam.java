import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 * Class responsible for video camera handling
 * @author Pavlo Paskalov 2015
 *
 */

public class VideoCam {
	
	private VideoCapture camera;
	private Mat frame;
	private BufferedImage image;
	private CascadeClassifier faceDetector;
	private MatOfRect faceDetections;
	private Mat detectedFace;
	
	private final Size size = new Size(125, 127); 
	
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		 
		faceDetector = new CascadeClassifier("xml/lbpcascade_frontalface.xml");
		faceDetections = new MatOfRect();
		camera = new VideoCapture(0);
		frame = new Mat();
	}
	
	/**
	 * Converts OpenCV's Mat image into Java's BufferedImage 
	 * @param matImage mat mage
	 * @return buffered image
	 * @throws IOException
	 */
	private BufferedImage toBufferedImage(Mat matImage) 
												throws IOException {
		MatOfByte bytemat = new MatOfByte();
		Imgcodecs.imencode(".jpg", matImage, bytemat);
		byte[] bytes = bytemat.toArray();
		InputStream in = new ByteArrayInputStream(bytes);
		return ImageIO.read(in);
	}
	
	/**
	 * Gets current image from camera
	 * @return buffered image
	 */
	public BufferedImage getCurrentImage() {
		image = null;
		try {
			if(camera.read(frame)) {
			     faceDetector.detectMultiScale(frame, faceDetections);
			     for (Rect rect : faceDetections.toArray()) {
			        Imgproc.rectangle(
			        		frame, new Point(rect.x, rect.y), 
			        		new Point(rect.x + rect.width, rect.y + rect.height), 
			        		new Scalar(0, 255, 0));
			        detectedFace = new Mat(frame, rect);
			        Imgproc.resize(detectedFace, detectedFace, size);
			        Imgproc.cvtColor(detectedFace, detectedFace, Imgproc.COLOR_BGR2GRAY);
			        String gend = GenderRecognition.recognize(toBufferedImage(detectedFace));
			        Imgproc.putText(
			        		frame, gend, new Point(rect.x, rect.y-10), 
			        		Core.FONT_HERSHEY_PLAIN, 1.0, 
			        		new Scalar(0, 255, 0));
			     }
			}
			image = toBufferedImage(frame);
		} catch (IOException e) { e.printStackTrace();}
		return image;
	}
	
	/**
	 * Releases camera
	 */
	public void release() {
		camera.release();
	}
	
}
