/*
 * Kyle Wong
 * 14.2.11
 * Machine Learning
 * kwong23
 * Assignment 1
 */
package cs475;

import java.io.Serializable;

public class ClassificationLabel extends Label implements Serializable {
	private int label;
	private RegressionLabel value;
	public ClassificationLabel(int label) {
		// TODO Auto-generated constructor stub
		this.label = label;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(label);
	}
	

}
