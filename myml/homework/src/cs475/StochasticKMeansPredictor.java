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
			newCluster.add(new Integer(i));
			rnk.add(newCluster);
		}
		
		// Perform training iterations
//		System.out.println("iteration, number in each cluster");
		for(int i = 0; i < this.clustering_training_iterations; i++){
			Double[] um = new Double[this.number_of_features];
			for(int z = 0; z < this.number_of_features; z++){
				um[z] = 0.0;
			}
			System.out.println(Util.euclideanDistance(this.mewk.get(0), um));
//			System.out.println(i + " ");
//			for(int j = 0; j < this.rnk.size(); j++){
//				System.out.print(this.rnk.get(j).size() + " ");
////				for(int k = 0; k < this.mewk.get(0).length; k++){
////					System.out.print(this.mewk.get(0)[k] + " ");
////				}
////			}
//			System.out.println();
			clusterData(instances);
		}

	}
	
	private void clusterData(List<Instance> instances){
//		Double[] um = new Double[this.number_of_features];
//		for(int i = 0; i < this.number_of_features; i++){
//			um[i] = 0.0;
//		}
//		System.out.println(Util.euclideanDistance(this.mewk.get(0), um));
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
//				removeFromCluster(instances.get(j), j); //faster but might be wrong
			}
			this.instanceToCluster[j] = minCluster; //add to new min cluster
			
			rnk.get(minCluster).add(j);
			updateCluster(minCluster, instances);
//			addToCluster(instances.get(j), j); //faster but might be wrong
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
	 * speed up 1 second insteaed of ~20 for ska but
	Test (on train data from train model) VI: 2.496316
	Test (on dev data from train model) VI: 2.506161

	dev with mod VI: VI: 2.697404
	train with mod VI: VI: 2.784189
	 * @param instance
	 * @param key_to_remove
	 */
	private void removeFromCluster(Instance instance, int key_to_remove){
		ArrayList<Integer> cluster = rnk.get(this.instanceToCluster[key_to_remove]);
		Double[] xi;
		Double[] sum = mewk.get(this.instanceToCluster[key_to_remove]);
		// use number in cluster to recalculate old sum
		sum = Util.scalarMultiply(cluster.size(), sum);
		xi = instance.getFeatureVector().getAll(this.number_of_features);
		// remove this instance's contribution
		xi = Util.scalarMultiply(-1, xi);
		sum = Util.vectorAdd(sum, xi);
		// remove key and update old mewk
		cluster.remove(new Integer(key_to_remove));
		if(cluster.size() > 0){
			mewk.set(this.instanceToCluster[key_to_remove], Util.scalarMultiply(1.0/cluster.size(), sum));
		}
		else{ //empty 
			sum = new Double[this.number_of_features];
			for(int i = 0; i < this.number_of_features; i++){
				sum[i] = 0.0;
			}
			mewk.set(this.instanceToCluster[key_to_remove], sum);
		}
	}
	
	private void addToCluster(Instance instance, int key_to_add){
		ArrayList<Integer> cluster = rnk.get(this.instanceToCluster[key_to_add]);
		Double[] xi;
		Double[] sum = mewk.get(this.instanceToCluster[key_to_add]);
		// use number in cluster to recalculate old sum
		sum = Util.scalarMultiply(cluster.size(), sum);
		xi = instance.getFeatureVector().getAll(this.number_of_features);
		// add this instance's contribution
		sum = Util.vectorAdd(sum, xi);
		// add key and update new mewk
		cluster.add(new Integer(key_to_add));
		mewk.set(this.instanceToCluster[key_to_add], Util.scalarMultiply(1.0/cluster.size(), sum));

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
