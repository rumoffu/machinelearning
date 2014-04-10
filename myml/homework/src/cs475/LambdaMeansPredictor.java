/*
 * Kyle Wong
 * 14.4.1
 * Machine Learning
 * kwong23
 * Assignment 4
 */
package cs475;

import java.util.List;
import java.util.ArrayList;

public class LambdaMeansPredictor extends Predictor{

	private int number_of_features;
	private double cluster_lambda;
	private int num_clusters = 1;
	private int clustering_training_iterations;
	private ArrayList<ArrayList<Integer>> rnk = new ArrayList<ArrayList<Integer>>(); //track which instance's are in a cluster by id
	private ArrayList<Double[]> mewk = new ArrayList<Double[]>(); //track the mew - centers of each cluster (x-dimensional)
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Double[] a = new Double[2];
		Double[] b = new Double[2];
		a[0] = 3.0;
		a[1] = 5.0;
		b[0] = 6.0;
		b[1] = 1.0;
		double c = Util.euclideanDistance(a, b);
		System.out.println("euclidean distance expected 5: " + c);


	}

	public LambdaMeansPredictor(double cluster_lambda, int clustering_training_iterations){

		this.cluster_lambda = cluster_lambda;
		this.clustering_training_iterations = clustering_training_iterations;
		
	}
	
	@Override
	public void train(List<Instance> instances) {
		// Initialize prototype vector to the mean of all instances
		this.number_of_features = Util.getMaxFeatureKey(instances);
		Double[] sum = new Double[this.number_of_features];
		for(int i = 0; i < this.number_of_features; i++){
			sum[i] = 0.0;
		}
		Double[] xi;
		
		for(Instance e : instances){
			xi = e.getFeatureVector().getAll(this.number_of_features);
			sum = Util.vectorAdd(sum, xi);
		}
		mewk.add(Util.scalarMultiply(1.0/instances.size(), sum));
		
		// Initialize rnk
		ArrayList<Integer> newCluster = new ArrayList<Integer>();
		rnk.add(newCluster);
		
		// Initialize Lambda Value
		if(this.cluster_lambda == 0.0){
			double lambdasum = 0.0;
			for(Instance e : instances){
				xi = e.getFeatureVector().getAll(this.number_of_features);
				lambdasum += Util.euclideanDistance(xi, mewk.get(0));
			}
			this.cluster_lambda = 1.0/instances.size() * lambdasum;
		}
		System.out.println("Cluster lambda: " + this.cluster_lambda);
		
		// Perform training iterations
		for(int i = 0; i < this.clustering_training_iterations; i++){
			Estep(instances);
			Mstep(instances);
		}
	}

	private void Estep(List<Instance> instances){
		double minDist = Double.POSITIVE_INFINITY;
		double dist;
		Double[] xi;
		int minCluster = -1;
		// Reset old cluster assignments
		for(ArrayList<Integer> ra : rnk){
			ra.clear();
		}
		// For each instance, assign to cluster
		for(int j = 0; j < instances.size(); j++){
			xi = instances.get(j).getFeatureVector().getAll(this.number_of_features);
			minDist = Double.POSITIVE_INFINITY;
			// get the minimum distance cluster k that the instance belongs to 
			for(int k = 0; k < this.mewk.size(); k++){
				dist = Util.euclideanDistance(xi, mewk.get(k));
				if(dist < minDist){//defaults to break ties by assigning to lowest cluster number
					minDist = dist;
					minCluster = k;
				}
			}
			if(minDist <= this.cluster_lambda){
				// fits in a current cluster, so get the cluster's arraylist and add the instance id to it
				
				rnk.get(minCluster).add(j);
			}
			else{ //bigger than lambda so we make a new cluster and make a new mew_k
				ArrayList<Integer> newCluster = new ArrayList<Integer>();
				newCluster.add(j);
				rnk.add(newCluster);
				mewk.add(xi);
			}
		}
		
	}
	private void Mstep(List<Instance> instances){
		Double[] xi;
		Double[] sum = new Double[this.number_of_features];
		
		for(int k = 0; k < mewk.size(); k++){ // for each mew
			for(int i = 0; i < this.number_of_features; i++){
				sum[i] = 0.0;
			}
			ArrayList<Integer> cluster = rnk.get(k);
			for(int n : cluster){ //for each instance in mew
				xi = instances.get(n).getFeatureVector().getAll(this.number_of_features);
				sum = Util.vectorAdd(sum, xi);
			}
			if(cluster.size() == 0){ //if it is empty, set it to 0's
				sum = new Double[this.number_of_features];
				for(int i = 0; i < this.number_of_features; i++){
					sum[i] = 0.0;
				}
				mewk.set(k, sum);
			}
			else{//update to 1 over n times the sum of each instance in it
				mewk.set(k, Util.scalarMultiply(1.0/cluster.size(), sum));
			}
		}
		
		
	}
	@Override
	/**
	 * Predicts the cluster number that the instance belongs to.
	 * @return a label with number 0 to k-1 which is the cluster number
	 */
	public Label predict(Instance instance) {
		double minDist = Double.POSITIVE_INFINITY;
		double dist;
		Double[] xi = instance.getFeatureVector().getAll(this.number_of_features);
		int minCluster = -1;
		for(int k = 0; k < this.mewk.size(); k++){ //compare to each mewk
			dist = Util.euclideanDistance(xi, mewk.get(k));
			if(dist < minDist){ //defaults to break ties by assigning to lowest cluster number
				minDist = dist;
				minCluster = k;
			}
		}
		return new ClassificationLabel(minCluster);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
