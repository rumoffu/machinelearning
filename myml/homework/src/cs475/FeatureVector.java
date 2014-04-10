/*
 * Kyle Wong
 * 14.3.6
 * Machine Learning
 * kwong23
 * Assignment 3
 */
package cs475;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class FeatureVector implements Serializable {
	TreeMap<Integer, Double> savedStuff = new TreeMap<Integer, Double>();
	
	public void add(int index, double value) {
		// TODO Auto-generated method stub
		this.savedStuff.put(index, value);
	}
	
	public double get(int index) {
		// TODO Auto-generated method stub
		if(this.savedStuff.containsKey(index))
			return this.savedStuff.get(index);
		else
			return 0;

	}
	
	public Iterator getIterator(){
		return savedStuff.entrySet().iterator();
	}
	
	public int getMaxKey(){
		return savedStuff.lastKey();
	}
	
	/**
	 * Returns all the feature vector data as a Double[]
	 * @param numFeatures the number of features to get the data for
	 * @return allFeatures a Double[] holding all the feature data
	 */
	public Double[] getAll(int numFeatures) {
		Double[] allFeatures = new Double[numFeatures];
		for(int i = 1; i <= numFeatures; i++){
			if(this.savedStuff.containsKey(i))
				allFeatures[i-1] = this.savedStuff.get(i);
			else
				allFeatures[i-1] = 0.0;
		}
		return allFeatures;

	}
	
	/**
	 * Returns the data of the feature vector as a Double[]
	 * @param bestgains an integer array sorted by the best information gains
	 * @return alldata the data within this feature vector as a Double[]
	 */
	public Double[] getDoubles(int[] bestgains){
		Double[] alldata = new Double[bestgains.length];
		for(int i = 0; i < bestgains.length; i++){
			alldata[i] = this.get(bestgains[i]);
		}

		return alldata;
	}

}
