/*
 * Kyle Wong
 * 14.3.6
 * Machine Learning
 * kwong23
 * Assignment 3
 */
package cs475;

import java.util.List;


/**
 * Class for doing utility operations including: finding the max feature key, dot product, array adding, 
 * and scalar multiplication of an array.
 * @author KT Wong
 *
 */
public class Util{
	
	/**
	 * Returns the integer id of the maximum feature id over a set of instances
	 * @param instances the list of data Instances
	 * @return maxkey the maximum feature id over the given list of instances
	 */
	protected static int getMaxFeatureKey(List<Instance> instances){
		int maxkey = 0;
		int tempkey = 0;
		for (Instance e: instances){
			tempkey = e.getFeatureVector().getMaxKey();
			if ( tempkey > maxkey){
				maxkey = tempkey;
			}
		}
		return maxkey;
	}

	/**
	 * Returns dot product of w and x
	 * @param w weight vector as a Double[]
	 * @param x input vector as a Double[]
	 * @return dotsum the result of the dot product
	 */
	protected static double dot(Double[] w, Double[] x){
		double dotsum = 0;
		for(int i = 0; i < w.length; i++){
			dotsum += w[i]*x[i];
		}
	
		return dotsum;
	}
	
	protected static Double[] vectorAdd(Double[] toadd, Double[] added){
		Double[] sum = new Double[toadd.length];
		for(int i = 0; i < toadd.length; i++){
			sum[i] = toadd[i] + added[i];
		}
		return sum;
	}
	
	protected static Double[] scalarMultiply(double scaler, Double[] ra){
		Double[] product = new Double[ra.length];
		for(int i = 0; i < product.length; i++){
			product[i] = scaler*ra[i];
		}
		return product;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return "Vector";
	}

}
