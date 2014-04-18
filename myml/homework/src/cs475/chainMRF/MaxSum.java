/*
 * Kyle Wong
 * 14.4.17
 * Machine Learning
 * kwong23
 * Assignment 5
 */
package cs475.chainMRF;
import cs475.chainMRF.*;

public class MaxSum {

	private ChainMRFPotentials potentials;
	private int[] assignments;
	// add whatever data structures needed

	public MaxSum(ChainMRFPotentials p) {
		this.potentials = p;
		assignments = new int[p.chainLength()+1];
	}
	
	public int[] getAssignments() {
		return assignments;
	}

	public double maxProbability(int x_i) {
		return x_i;
		// TODO
	}
}
