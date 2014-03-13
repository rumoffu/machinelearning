/*
 * Kyle Wong
 * 14.2.11
 * Machine Learning
 * kwong23
 * Assignment 1
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
	
	public Double[] getDoubles(int[] bestgains){
		Double[] alldata = new Double[bestgains.length];
		for(int i = 0; i < bestgains.length; i++){
			alldata[i] = this.get(bestgains[i]);
		}

		return alldata;
	}

}
