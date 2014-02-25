/*
 * Kyle Wong
 * 14.2.11
 * Machine Learning
 * kwong23
 * Assignment 1
 */
package cs475;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class FeatureVector implements Serializable {
	HashMap<Integer, Double> savedStuff = new HashMap<Integer, Double>();
	
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

}
