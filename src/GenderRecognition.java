import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.neuroph.nnet.learning.BackPropagation;

/**
 * 
 * Class responsible for loading artificial neural network 
 * and performing gender recognitinion
 * @author Pavlo Paskalov 2015  
 *
 */

public class GenderRecognition {
	
	private static final NeuralNetwork<BackPropagation> NN;
	private static final String PATH_TO_NN = "net/GenderRecognitionNet.nnet";
	
	private static  ImageRecognitionPlugin imageRecognition;
	private static HashMap<String, Double> output;
	
	static {
		 NN = NeuralNetwork.createFromFile(
					new File(PATH_TO_NN));
		 imageRecognition = (ImageRecognitionPlugin) NN.getPlugin(ImageRecognitionPlugin.class);
	}
	/**
	 * Recognizes gender from face image
	 * @param face - buffered face image
	 * @return Man or Woman label
	 */
	public static String recognize(BufferedImage face) {
		String gender = "not recognized";
	
		output = imageRecognition.recognizeImage(face);
		double wRes = output.get("woman");
		double mRes = output.get("man");
			
		if (wRes > mRes) gender = "Woman";
		else gender = "Man";
		
		System.out.println(gender + ": w: " + wRes+ ", m: " + mRes);
		
		return gender;
	}
}
