package cs475;

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
		System.out.printf("Number correct: %s/%s (%.2f%%)\n", numCorrect, numTested, 100.0*numCorrect / numTested);
		return (double) numCorrect / numTested;
	}

}
