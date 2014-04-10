/*
 * Kyle Wong
 * 14.3.5
 * Machine Learning
 * kwong23
 * Assignment 3
 */
package cs475;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DualMarginPerceptron extends Predictor{

	private Double[] alphas;
	private double online_learning_rate;
	private int online_training_iterations;
	private int number_of_features;
	private double polynomial_kernel_exponent;
	private String algorithm;
	private List<Instance> instances;
	private Double[][] gramMatrix;
	
	public DualMarginPerceptron(double online_learning_rate, int online_training_iterations, String algorithm, double polynomial_kernel_exponent) {
		this.online_learning_rate = online_learning_rate;
		this.online_training_iterations = online_training_iterations;
		this.polynomial_kernel_exponent = polynomial_kernel_exponent;
		this.algorithm = algorithm;
		this.gramMatrix = null;
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
		double online_learning_rate = 1.0;
		double polynomial_kernel_exponent = 2;
		int online_training_iterations = 5;
		String algorithm = "perceptron_linear_kernel";
		DualMarginPerceptron ml = new DualMarginPerceptron(online_learning_rate, online_training_iterations, algorithm, polynomial_kernel_exponent);
		ml.train(instances);
		System.out.println("Predicted: " + ml.predict(instance));
		System.out.println("Done unit test for logistic classifier.");
		
	}

	@Override
	public void train(List<Instance> instances) {
		this.instances = instances;
		//set weights to be 0
		this.number_of_features = Util.getMaxFeatureKey(instances);
		this.alphas = new Double[this.instances.size()];
		for(int i = 0; i < this.alphas.length; i++){
			this.alphas[i] = 0.0;
		}
		
		//train the weights
		Kernel kernel = new Kernel(instances, this.number_of_features, this.polynomial_kernel_exponent);
		if(algorithm.equals("perceptron_linear_kernel")){
			this.gramMatrix = kernel.getLinearKernelMatrix(instances, number_of_features);
		}
		else if(algorithm.equals("perceptron_polynomial_kernel")){
			this.gramMatrix = kernel.getPolynomialKernelMatrix(instances, number_of_features);
		}
//		Double[] xi;
		double yk = 0;
		double margin = 0.0;
		for (int i=0; i < this.online_training_iterations; i++) {
			for(int k = 0; k < instances.size(); k++){
//				xi = instances.get(k).getFeatureVector().getAll(this.number_of_features);
				if(instances.get(k).getLabel().toString().equals("0")){ 
					yk = -1.0; //perceptron uses -1 and 1
				}
				else if(instances.get(k).getLabel().toString().equals("1")){ 
					yk = 1.0;
				}
				margin = 0.0;
				for (int j=0; j < instances.size(); j++) {
					int yj = 0;
					if(instances.get(j).getLabel().toString().equals("0")){ 
						yj = -1; //perceptron uses -1 and 1
					}
					else if(instances.get(j).getLabel().toString().equals("1")){ 
						yj = 1;
					}
					margin = margin + this.alphas[j]*yj*this.gramMatrix[j][k];
				}
				if(yk*margin < 1){  
					this.alphas[k]++;
				}
			}
		}
	}

	@Override
	public Label predict(Instance instance) {
		Double[] x = instance.getFeatureVector().getAll(this.number_of_features);
		Double margin = 0.0;
		Double[] xi;
		for (int i=0; i < this.instances.size(); i++) {
			int yi = 0;
			if(this.instances.get(i).getLabel().toString().equals("0")){ 
				yi = -1; //perceptron uses -1 and 1
			}
			else if(this.instances.get(i).getLabel().toString().equals("1")){ 
				yi = 1;
			}
			//KTW
			xi = this.instances.get(i).getFeatureVector().getAll(number_of_features);
			if (this.algorithm.equals("perceptron_linear_kernel"))
				margin += this.alphas[i] * yi * Util.dot(xi, x);
			else if (this.algorithm.equals("perceptron_polynomial_kernel"))
				margin += this.alphas[i] * yi * Math.pow((1 + Util.dot(xi, x)), this.polynomial_kernel_exponent);
			//KTW
		}

		if(margin >= 0){
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
