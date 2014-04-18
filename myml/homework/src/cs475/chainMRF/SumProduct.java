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

public class SumProduct {

	private ChainMRFPotentials potentials;
	private int n;
	private int k;
	// add whatever data structures needed
	//for storing messages from variable x to factor f and vice versa
	private double[][] raDesxtof;
	private double[][] raDesftox;
	private double[][] raAscxtof;
	private double[][] raAscftox;
	
	public static void main(String[] args){
		ChainMRFPotentials p = new ChainMRFPotentials("sample_mrf_potentials_small.txt");
		SumProduct sp = new SumProduct(p);
		double[] una = sp.getunary(1);
		double[][] bin = sp.getbinary(3);
		// should print out 0.3 then 0.2
		for(int i = 1; i < una.length; i++){
			System.out.println(una[i]);
		}
		System.out.println();
		
		//should print out 0.1 0.2 and then 0.1 0.4
		for(int i = 1; i < bin.length; i++){
			for(int j = 1; j < bin[0].length; j++){
				System.out.print(bin[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();		
	}
	
	/**
	 * Construct the factor graph and compute all the messages
	 * @param p an object for getting the potential functions
	 */
	public SumProduct(ChainMRFPotentials p) {
		this.potentials = p;
		
		this.n = p.chainLength();
		this.k = p.numXValues();
		
		//for storing messages from variable x to factor f and vice versa
		raDesxtof = new double[n+1][k+1];
		raDesftox = new double[n+1][k+1];
		raAscxtof = new double[n+1][k+1];
		raAscftox = new double[n+1][k+1];
		
		//Get all descending messages that go from right to left
		for(int ind = n; ind >= 1; ind--){
			if(ind == this.n){ //right hand edge
				raDesxtof[ind] = msgftox(ind, ind);
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
		if(find == xind){ //top down from factor to variable node
			msg = getunary(xind);
		}
		else if(find == this.n + xind){ //f leftarrow x message
			msg = Util.matrixmult(getbinary(find), raDesxtof[xind+1]);
		}
		else if(find == this.n + xind - 1){ //f rightarrow x message
			msg = Util.matrixmult(getbinary2(find), raAscxtof[xind-1]);
			//System.out.println();
		}
		return msg;
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
				return Util.ramult(getunary(xind), raAscftox[xind]);
			}
		}
		else if(find == this.n + xind - 1){ // x leftarrow f message
			if(xind == this.n){ //special edge case
				return getunary(xind);
			}
			else {//all other descending
				return Util.ramult(getunary(xind), raDesftox[xind]);
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
			unary[i] = this.potentials.potential(ind, i);
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
				binary[i][j] = this.potentials.potential(ind, i, j);
			}
		}
		return binary;
	}
	
	private double[][] getbinary2(int ind){
		double[][] binary = new double[this.k+1][this.k+1];
		for(int i = 1; i <= this.k; i++){
			for(int j = 1; j <= this.k; j++){
				binary[i][j] = this.potentials.potential(ind, j, i);
			}
		}
		return binary;
	}
	
	public double[] marginalProbability(int x_i) {
		if(x_i == 1){ // special case f leftarrow x1
			double[] topmsg = msgftox(x_i, x_i);
			double[] rightinmsg = msgftox(this.n + x_i, x_i);
			//double[] rightoutmsg = msgxtof(x_i, this.n + x_i); // == topmsg
			double[] numer = Util.ramult(topmsg, rightinmsg);
			double denom = Util.dot(topmsg, rightinmsg);
			double[] resu = Util.scalarMultiply(1/denom, numer);
			return resu;
		}
		else if (x_i == this.n){ //special case f rightarrow xn
			double[] topmsg = msgftox(x_i, x_i);
			double[] leftinmsg = msgftox(this.n + x_i - 1, x_i);
			double[] numer = Util.ramult(topmsg, leftinmsg);
			double denom = Util.dot(topmsg, leftinmsg);
			double[] resu = Util.scalarMultiply(1/denom, numer);
			return resu;
		}
		else{ //rest use 3way
			double[] num = Util.ramult(msgxtof(x_i, this.n + x_i), msgftox(this.n + x_i - 1, x_i));
			double denom = Util.dot(msgftox(this.n + x_i - 1, x_i), msgftox(x_i, x_i));
			double[] left = msgftox(this.n + x_i - 1, x_i);
			double[] right = msgftox(x_i, x_i);
			double[] ans = Util.scalarMultiply(1/Util.dot(msgftox(this.n + x_i - 1, x_i), msgftox(x_i, x_i)), Util.ramult(msgftox(x_i, x_i), msgftox(this.n + x_i - 1, x_i)));
			//System.out.println();
			return Util.scalarMultiply(1/Util.dot(msgftox(this.n + x_i - 1, x_i), msgftox(x_i, x_i)), Util.ramult(msgftox(x_i, x_i), msgftox(this.n + x_i - 1, x_i)));
		
		}
	}

}

