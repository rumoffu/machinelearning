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
	
	public static void main(String[] args){
		double[][] f3 = new double[3][3];
		double[] f1 = new double[3];
		f1[1] = 0.3;
		f1[2] = 0.2;
		f3[1][1] = 0.1;
		f3[1][2] = 0.1;
		f3[2][1] = 0.2;
		f3[2][2] = 0.4;
		double[] res = Util.matrixmult(f3, f1);
		//should return 0.05 and 0.14
		for(int i = 1; i < res.length; i++){
			System.out.println(res[i]);
		}
		System.out.println();
		
		f1[1] = 0.1;
		f1[2] = 0.2;
		f3[1][1] = 0.1;
		f3[1][2] = 0.2;
		f3[2][1] = 0.1;
		f3[2][2] = 0.4;
		res = Util.matrixmult(f3, f1);
		//should return 0.05 and 0.09
		for(int i = 1; i < res.length; i++){
			System.out.println(res[i]);
		}
		System.out.println();
		
		res = Util.ramult(f1, f3[1]);
		//should return 0.01 and 0.04
		for(int i = 1; i < res.length; i++){
			System.out.println(res[i]);
		}
		System.out.println();
		
		res = Util.radiv(f1, f3[2]);
		//should return 1 and 0.5
		for(int i = 1; i < res.length; i++){
			System.out.println(res[i]);
		}
		System.out.println();
	}
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
	
	/**
	 * Returns the l2norm, Euclidean distance, between two given vectors (Double[]'s) of the same length
	 * @param a the first given vector as a Double[]
	 * @param b the second given vector as a Double[]
	 * @return the Euclidean distance (squareroot of the sum of differences)
	 */
	protected static double euclideanDistance(Double[] a, Double[] b){
		double sum = 0.0;
		double diff = 0.0;
		for(int i = 0; i < a.length; i++){
			diff = a[i] - b[i];
			sum += diff*diff;
		}
		//return Math.sqrt(sum);
		return sum;
	}
	
	public static double[] matrixmult(double[][] bin, double[] una){
		double[] res = new double[una.length];
		for(int row = 0; row < bin.length; row++){
			res[row] = 0.0; //initialize
			for(int col = 0; col < bin[0].length; col++){
				res[row] += bin[row][col]*una[col];
			}
		}
		return res;
	}
	
	public static double[] ramult(double[] ra, double[] una){
		double[] res = new double[ra.length];
		for(int i = 0; i < ra.length; i++){
			res[i] = ra[i]*una[i];
		}
		return res;
	}
	
	public static double[] radiv(double[] ra, double[] una){
		double[] res = new double[ra.length];
		for(int i = 0; i < ra.length; i++){
			res[i] = ra[i]/una[i];
		}
		return res;
	}
	
	public static double dot(double[] w, double[] x){
		double dotsum = 0;
		for(int i = 0; i < w.length; i++){
			dotsum += w[i]*x[i];
		}
	
		return dotsum;
	}
	
	public static double[] scalarMultiply(double scaler, double[] ra){
		double[] product = new double[ra.length];
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
