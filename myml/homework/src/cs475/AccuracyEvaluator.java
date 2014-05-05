package cs475;

import java.io.IOException;
import java.util.List;

public class AccuracyEvaluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public double evaluateAccuracy(List<Instance> instances, Predictor predictor){
		int numTested = 0;
		int numCorrect = 0;
		Label prediction;
		for(Instance element : instances){
			prediction = predictor.predict(element);
			numTested++;
			if( element.getLabel() != null && prediction != null){
				if (prediction.toString().equals(element.getLabel().toString())){
					numCorrect++;
				}
			}
		}
		System.out.printf("%s (%s/%s) \n", 1.0* numCorrect / numTested, numCorrect, numTested);
		return (double) numCorrect / numTested;
	}
	
	public double evaluateAndPrintAccuracy(List<Instance> instances, Predictor predictor, PredictionsWriter writer) throws IOException{
		int numTested = 0;
		int numCorrect = 0;
		Label prediction;
		for(Instance element : instances){
			prediction = predictor.predict(element);
//			writer.writePrediction(prediction);
			numTested++;
			if( element.getLabel() != null && prediction != null){
				if (prediction.toString().equals(element.getLabel().toString())){
					numCorrect++;
				}
			}
		}
		System.out.printf("%s (%s/%s) \n", 1.0* numCorrect / numTested, numCorrect, numTested);
		return (double) numCorrect / numTested;
	}

}
