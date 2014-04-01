/*
 * Kyle Wong
 * 14.2.11
 * Machine Learning
 * kwong23
 * Assignment 1
 */
package cs475;

import java.util.List;

public class MajorityClassifier extends Predictor{

	private int theMajority = -2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 2;
		System.out.printf("Hi %d\n", x);
		System.out.println("there we go - unit test");
	}

	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		int zerocount = 0;
		int onecount = 0;
		String data;
		int theLabel;
		for(Instance element : instances){
			data = element.getLabel().toString();
			theLabel = Integer.parseInt(data);
			if(theLabel == 1){
				onecount++;
			}
			else if(theLabel == 0){
				zerocount++;
			}
		}
		if(zerocount > onecount){
			this.theMajority = 0;
		}
		else{ //prioritize guessing 1 slightly in the edge case
			this.theMajority = 1;
		}
	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		return new ClassificationLabel(theMajority);
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "MajorityClassifier";
	}

}
