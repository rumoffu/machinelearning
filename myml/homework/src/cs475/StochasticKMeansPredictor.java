/*
 * Kyle Wong
 * 14.4.2
 * Machine Learning
 * kwong23
 * Assignment 4
 */
package cs475;

import java.util.ArrayList;
import java.util.List;

public class StochasticKMeansPredictor extends Predictor{
	private int number_of_features;
	private int num_clusters = 1;
	private int clustering_training_iterations;
	private ArrayList<ArrayList<Integer>> rnk = new ArrayList<ArrayList<Integer>>(); //track which instance's are in a cluster by id
	private ArrayList<Double[]> mewk = new ArrayList<Double[]>(); //track the mew - centers of each cluster (x-dimensional)
	private int[] instanceToCluster;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public StochasticKMeansPredictor(int clustering_training_iterations, int num_clusters){
		this.clustering_training_iterations = clustering_training_iterations;
		this.num_clusters = num_clusters;

	}
	@Override
	public void train(List<Instance> instances) {
		this.instanceToCluster = new int[instances.size()];
		//initialize instance assignments to be assigned to cluster -1 by default
		for(int i = 0; i < this.num_clusters; i++){
			this.instanceToCluster[i] = -1;
		}
		// Initialize mewk based on the first num_clusters of example instances
		this.number_of_features = Util.getMaxFeatureKey(instances);
		Double[] xi;
		ArrayList<Integer> newCluster;
		for(int i = 0; i < num_clusters; i++){ 
			xi = instances.get(i).getFeatureVector().getAll(this.number_of_features);
			mewk.add(xi);
			instanceToCluster[i] = i; //instance i is now in cluster i
			// Initialize rnk
			newCluster = new ArrayList<Integer>();
			newCluster.add(i);
			rnk.add(newCluster);
		}
		
		// Perform training iterations
		for(int i = 0; i < this.clustering_training_iterations; i++){
			clusterData(instances);
		}

	}
	
	private void clusterData(List<Instance> instances){
		double minDist = Double.POSITIVE_INFINITY;
		double dist;
		Double[] xi;
		int minCluster = -1;
		Double[] sum = new Double[this.number_of_features];
		ArrayList<Integer> cluster;
		// For each instance, reassign to new cluster and update
		for(int j = 0; j < instances.size(); j++){
			xi = instances.get(j).getFeatureVector().getAll(this.number_of_features);
			minDist = Double.POSITIVE_INFINITY;
			// Iterate over mewk to get the minimum distance cluster k that the instance belongs to 
			for(int k = 0; k < this.mewk.size(); k++){
				dist = Util.euclideanDistance(xi, mewk.get(k));
				if(dist < minDist){//defaults to break ties by assigning to lowest cluster number
					minDist = dist;
					minCluster = k;
				}
			}
			// Remove from old cluster, add to new cluster and update both
			if(this.instanceToCluster[j] >= 0){ //was in an old cluster
				rnk.get(this.instanceToCluster[j]).remove(new Integer(j));
				updateCluster(this.instanceToCluster[j], instances);
			}
			this.instanceToCluster[j] = minCluster; //add to new min cluster
			rnk.get(minCluster).add(j);
			updateCluster(minCluster, instances);
		}
		
	}
	private void updateCluster(int cluster_num, List<Instance> instances){
		ArrayList<Integer> cluster = rnk.get(cluster_num);
		Double[] xi;
		Double[] sum = new Double[this.number_of_features];
		for(int i = 0; i < this.number_of_features; i++){
			sum[i] = 0.0;
		}
		for(int n : cluster){
			xi = instances.get(n).getFeatureVector().getAll(this.number_of_features);
			sum = Util.vectorAdd(sum, xi);
		}
		if(cluster.size() > 0){
			sum = Util.scalarMultiply(1.0/cluster.size(), sum);
		}
		mewk.set(cluster_num, sum);
	}

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
