/*
 * Kyle Wong
 * 14.4.17
 * Machine Learning
 * kwong23
 * Assignment 5
 */
package cs475.chainMRF;
import cs475.Util;
import cs475.chainMRF.*;

public class MaxSum {

	private ChainMRFPotentials potentials;
	private int[] assignments;
	private int n;
	private int k;
	// add whatever data structures needed
	//for storing messages from variable x to factor f and vice versa
	private double[][] raDesxtof;
	private double[][] raDesftox;
	private double[][] raAscxtof;
	private double[][] raAscftox;
	private int[][] backtrack;
	
	public MaxSum(ChainMRFPotentials p) {
		this.potentials = p;
		assignments = new int[p.chainLength()+1];
		this.n = p.chainLength();
		this.k = p.numXValues();
		backtrack = new int[n+1][k+1];
		
		//for storing messages from variable x to factor f and vice versa
		raDesxtof = new double[n+1][k+1];
		raDesftox = new double[n+1][k+1];
		raAscxtof = new double[n+1][k+1];
		raAscftox = new double[n+1][k+1];
		
		//Get all descending messages that go from right to left
		for(int ind = n; ind >= 1; ind--){
			if(ind == this.n){ //right hand edge
				raDesxtof[ind] = getunary(ind);
			}
			else if(ind != 1){
				raDesftox[ind] = msgftox(this.n+ind, ind);
				raDesxtof[ind] = msgxtof(ind, this.n+ind-1);
			}
			else {//ind == 1
				raDesftox[ind] = msgftox(this.n+1, ind);
			}
		}
		
		//Get all ascending messages that go from left to right
		for(int ind = 1; ind <= n; ind++){
			if(ind == 1){
				raAscxtof[ind] = msgxtof(ind, this.n+ind);
			}
			else if(ind != this.n){
				raAscftox[ind] = msgftox(this.n+ind-1, ind);
				raAscxtof[ind] = msgxtof(ind, this.n+ind);
			}
			else {// ind == n
				raAscftox[ind] = msgftox(this.n+ind-1, ind);
			}
		}
	}
	
	/**
	 * Returns the message from f to x specified by indices
	 * @param find the f index
	 * @param xind the x index
	 * @return msg the message requested from f to x
	 */
	private double[] msgftox(int find, int xind){
		double[] msg = new double[this.k+1];
		if(find == this.n + xind){ //f leftarrow x message
			msg = matrixmax(getbinary(find), raDesxtof[xind+1], xind);
		}
		else if(find == this.n + xind - 1){ //f rightarrow x message
			msg = matrixmax(getbinary2(find), raAscxtof[xind-1], xind);
			//System.out.println();
		}
		return msg; //max gives the joint probability maximum and sets backtracking
	}
	/**
	 * Returns the message from x to f specified by indices
	 * @param xind the x index
	 * @param find the f index
	 * @return msg the message requested from f to x
	 */
	private double[] msgxtof(int xind, int find){
		//double[] msg = new double[this.k+1];
		if(find == this.n + xind){ //x rightarrow f message
			if(xind == 1){//special edge case
				return getunary(xind);
			}
			else {//all other ascending
				return Util.raadd(getunary(xind), raAscftox[xind]);
			}
		}
		else if(find == this.n + xind - 1){ // x leftarrow f message
			if(xind == this.n){ //special edge case
				return getunary(xind);
			}
			else {//all other descending
				return Util.raadd(getunary(xind), raDesftox[xind]);
			}
			
		}
		//should not ever reach here
		System.out.printf("Error in message asked for for x index %s and f index %s.\n", xind, find);
		return null;		
	}

	/**
	 * 
	 * @param ind
	 * @return
	 */
	private double[] getunary(int ind){
		double[] unary = new double[this.k+1];
		for(int i = 1; i <= this.k; i++){
			unary[i] = Math.log(this.potentials.potential(ind, i));
		}
		return unary;
	}
	
	/**
	 * 
	 * @param ind
	 * @return
	 */
	private double[][] getbinary(int ind){
		double[][] binary = new double[this.k+1][this.k+1];
		for(int i = 1; i <= this.k; i++){
			for(int j = 1; j <= this.k; j++){
				binary[i][j] = Math.log(this.potentials.potential(ind, i, j));
			}
		}
		return binary;
	}
	
	private double[][] getbinary2(int ind){
		double[][] binary = new double[this.k+1][this.k+1];
		for(int i = 1; i <= this.k; i++){
			for(int j = 1; j <= this.k; j++){
				binary[i][j] = Math.log(this.potentials.potential(ind, j, i));
			}
		}
		return binary;
	}

	public int[] getAssignments() {
		return assignments;
	}

	public double maxProbability(int x_i) {
		double[] topmsg = getunary(x_i);
		double[] rightinmsg = raDesftox[x_i];
		double[] leftinmsg = raAscftox[x_i];
		double[] numer;
		double[] resu;
		double[] probability;
		
		resu = Util.raadd(Util.raadd(topmsg, rightinmsg), leftinmsg);
		//get max of the sum over all k configurations
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 1; i <= this.k; i++){
			if(resu[i] > max){
				max = resu[i];
				assignments[x_i] = i;
			}
		}
		//Normalize
		SumProduct sp = new SumProduct(this.potentials);
		topmsg = sp.getunary(x_i);
		rightinmsg = sp.raDesftox[x_i];
		leftinmsg = sp.raAscftox[x_i];
		double z = 0.0;
		probability = Util.ramult(Util.ramult(topmsg, rightinmsg), leftinmsg);
		for(int i = 0; i <= this.k; i++){
			z += probability[i];
		}
		return max - Math.log(z);
		
				
//		if(x_i == 1){ // special case f leftarrow x1
//			//double[] rightoutmsg = msgxtof(x_i, this.n + x_i); // == topmsg
//			numer = Util.raadd(topmsg, rightinmsg);
//			double denom = Util.dot(topmsg, rightinmsg);
//			resu = Util.scalarMultiply(1/denom, numer);
//		}
//		else if (x_i == this.n){ //special case f rightarrow xn
//			numer = Util.raadd(topmsg, leftinmsg);
//			double denom = Util.dot(topmsg, leftinmsg);
//			resu = Util.scalarMultiply(1/denom, numer);
//			}
//		else{ //rest use 3way
//			numer = Util.raadd(Util.raadd(topmsg, leftinmsg), rightinmsg);
//			double denom = Util.dot(topmsg, Util.raadd(leftinmsg, rightinmsg));
//			resu = Util.scalarMultiply(1/denom, numer);
//		}
//		double max = Double.NEGATIVE_INFINITY;
//		for(int i = 1; i < resu.length; i++){
//			if(resu[i] > max){
//				max = resu[i];
//			}
//		}
//		return max;
//		// TODO
	}
	
	private double[] matrixmax(double[][] bin, double[] una, int xind){
		double[] res = new double[una.length];
		for(int row = 0; row < bin.length; row++){
			res[row] = Double.NEGATIVE_INFINITY; //initialize
			for(int col = 0; col < bin[0].length; col++){
				if(bin[row][col]+ una[col] > res[row]){
					res[row] = bin[row][col] + una[col];
					backtrack[xind][row] = col;
				}
			}
		}
		return res;
	}
}
