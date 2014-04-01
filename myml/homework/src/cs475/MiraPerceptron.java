/*
 * Kyle Wong
 * 14.3.6
 * Machine Learning
 * kwong23
 * Assignment 3
 */
package cs475;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MiraPerceptron extends Predictor{

	private Double[] weights;
	private int online_training_iterations;
	private int number_of_features;
	
	public MiraPerceptron(int online_training_iterations) {
		this.online_training_iterations = online_training_iterations;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Here we go - unit test");
		ArrayList<Instance> instances = new ArrayList<Instance>();
		FeatureVector feature_vector = new FeatureVector();
		Label label = null;
		
		label = new ClassificationLabel(1);
		feature_vector.add(1, -0.112217);
		feature_vector.add(2, 0.077192);
		Instance instance = new Instance(feature_vector, label);
		instances.add(instance);
		
		feature_vector = new FeatureVector();
		label = new ClassificationLabel(0);
		feature_vector.add(1, 0.496442);
		feature_vector.add(2, -0.290597);
		instance = new Instance(feature_vector, label);
		instances.add(instance);
		
		feature_vector = new FeatureVector();
		label = new ClassificationLabel(1);
		feature_vector.add(1, 0.418255);
		feature_vector.add(2, -1.791584);
		instance = new Instance(feature_vector, label);
		instances.add(instance);
		
		feature_vector = new FeatureVector();
		label = new ClassificationLabel(0);
		feature_vector.add(1, 0.237331);
		feature_vector.add(2, 0.9901527);
		instance = new Instance(feature_vector, label);
		instances.add(instance);
		
		feature_vector = new FeatureVector();
		label = new ClassificationLabel(0);
		feature_vector.add(1, 0.537331);
		feature_vector.add(2, 0.9901527);
		instance = new Instance(feature_vector, label);
		instances.add(instance);
		
		for(Instance e : instances){

			System.out.println(e);
			System.out.println(e.getLabel());
			Iterator elements = e.getFeatureVector().getIterator();
			while(elements.hasNext()){
				Map.Entry items = (Map.Entry)elements.next();
				System.out.printf("key %s value %s\n", items.getKey(), items.getValue() );
			}
			System.out.println();
		}
		int online_training_iterations = 5;
		MiraPerceptron ml = new MiraPerceptron(online_training_iterations);
		ml.train(instances);
		System.out.println("Predicted: " + ml.predict(instance));
		System.out.println("Done unit test for logistic classifier.");
		
	}

	@Override
	public void train(List<Instance> instances) {
		//set weights to be 0
		this.number_of_features = Util.getMaxFeatureKey(instances);
		this.weights = new Double[this.number_of_features];
		for(int i = 0; i < this.weights.length; i++){
			this.weights[i] = 0.0;
		}
		
		//train the weights
		Double[] xi;
		double yi = 0;
		double tau = 0.0;
		double numer = 0.0;
		double denom = 0.0;
		for (int i=0; i < this.online_training_iterations; i++) {
			for(Instance e : instances){
				xi = e.getFeatureVector().getAll(this.number_of_features);
				if(e.getLabel().toString().equals("0")){ 
					yi = -1.0; //perceptron uses -1 and 1
				}
				else if(e.getLabel().toString().equals("1")){ 
					yi = 1.0;
				}
				numer = 1 - yi*Util.dot(this.weights, xi);
				denom = Util.dot(xi,xi);
				tau = numer / denom;
				if(yi*Util.dot(this.weights, xi) < 1){ //margin less than 1
					this.weights = Util.vectorAdd(this.weights, Util.scalarMultiply(tau*yi, xi));
				}
			}
		}
	}

	@Override
	public Label predict(Instance instance) {
		Double[] xi = instance.getFeatureVector().getAll(this.number_of_features);
		double yhat = Util.dot(this.weights, xi);
		if(yhat >= 0){
			return new ClassificationLabel(1);
		}
		else{ // negative
			return new ClassificationLabel(0);
		}
	}
	

	public String toString() {
		// TODO Auto-generated method stub
		return "Perceptron with Margin";
	}

}
