/*
 * Kyle Wong
 * 14.2.11
 * Machine Learning
 * kwong23
 * Assignment 1
 */
package cs475;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EvenOddClassifier extends Predictor{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		// No need to train for an even-odd classifier
		
	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		Iterator elements = instance.getFeatureVector().getIterator();
		double evensum = 0;
		double oddsum = 0;
		while(elements.hasNext()){
			Map.Entry items = (Map.Entry)elements.next();
			if(Integer.parseInt(items.getKey().toString()) % 2 == 0){
				evensum += Double.parseDouble(items.getValue().toString());
			}
			else {//odd
				oddsum += Double.parseDouble(items.getValue().toString());
			}
				
		}
		if(evensum >= oddsum){
			return new ClassificationLabel(1);
		}
		else{
			return new ClassificationLabel(0);
		}
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "EvenOddClassifier";
	}
}
