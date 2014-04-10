/*
 * Kyle Wong
 * 14.3.6
 * Machine Learning
 * kwong23
 * Assignment 3
 */
package cs475;

import java.util.List;

public class Kernel {

	Double[][] gramMatrix;
	double polynomial_kernel_exponent;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public Kernel(List<Instance> instances, int number_of_features, double polynomial_kernel_exponent){
		this.gramMatrix = new Double[instances.size()][instances.size()];
		this.polynomial_kernel_exponent = polynomial_kernel_exponent;
	}
	
	public Double[][] getLinearKernelMatrix(List<Instance> instances, int number_of_features){
		Double[] xi, xj;
		for(int i = 0; i < instances.size(); i++){
			xi = instances.get(i).getFeatureVector().getAll(number_of_features);
			for(int j = 0; j < instances.size(); j++){
				xj = instances.get(j).getFeatureVector().getAll(number_of_features);
				this.gramMatrix[i][j] = Util.dot(xi, xj);
			}
		}
		return this.gramMatrix;
	}
	
	public Double[][] getPolynomialKernelMatrix(List<Instance> instances, int number_of_features){
		Double[] xi, xj;
		for(int i = 0; i < instances.size(); i++){
			xi = instances.get(i).getFeatureVector().getAll(number_of_features);
			for(int j = 0; j < instances.size(); j++){
				xj = instances.get(j).getFeatureVector().getAll(number_of_features);
				this.gramMatrix[i][j] = Math.pow(1+Util.dot(xi, xj), this.polynomial_kernel_exponent);
			}
		}
		return this.gramMatrix;
	}

}
