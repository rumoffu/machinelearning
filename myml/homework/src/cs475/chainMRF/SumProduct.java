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
	protected Double[][] raDesxtof;
	protected Double[][] raDesftox;
	protected Double[][] raAscxtof;
	protected Double[][] raAscftox;
	
	public static void main(String[] args){
		ChainMRFPotentials p = new ChainMRFPotentials("sample_mrf_potentials_small.txt");
		SumProduct sp = new SumProduct(p);
		Double[] una = sp.getunary(1);
		Double[][] bin = sp.getbinary(3);
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
		raDesxtof = new Double[n+1][k+1];
		raDesftox = new Double[n+1][k+1];
		raAscxtof = new Double[n+1][k+1];
		raAscftox = new Double[n+1][k+1];
		for(int i = 0; i < n+1; i++){
			for(int j = 0; j < k+1; j++){
				raDesxtof[i][j] = 0.0;
				raDesftox[i][j] = 0.0;
				raAscxtof[i][j] = 0.0;
				raAscftox[i][j] = 0.0;
			}
		}
		Double[] zeros = new Double[k+1];
		for(int i = 0; i < k+1; i++){
			zeros[i] = 1.0;
		}
		//Get all descending messages that go from right to left
		for(int ind = n; ind >= 1; ind--){
			if(ind == this.n){ //right hand edge
				raDesxtof[ind] = getunary(ind);
				raDesftox[ind] = zeros; //
			}
			else if(ind != 1){
				raDesftox[ind] = msgftox(this.n+ind, ind);
				raDesxtof[ind] = msgxtof(ind, this.n+ind-1);
			}
			else {//ind == 1
				raDesftox[ind] = msgftox(this.n+1, ind);
				raDesxtof[ind] = Util.ramult(msgftox(this.n+1, ind), getunary(ind));
			}
		}
		
		//Get all ascending messages that go from left to right
		for(int ind = 1; ind <= n; ind++){
			if(ind == 1){
				raAscxtof[ind] = msgxtof(ind, this.n+ind);
				raAscftox[ind] = getunary(ind);
			}
			else if(ind != this.n){
				raAscftox[ind] = msgftox(this.n+ind-1, ind);
				raAscxtof[ind] = msgxtof(ind, this.n+ind);
			}
			else {// ind == n
				raAscftox[ind] = msgftox(this.n+ind-1, ind);
				raAscxtof[ind] = Util.ramult(msgftox(this.n+ind-1, ind), getunary(ind));
			}
		}
	}
	
	/**
	 * Returns the message from f to x specified by indices
	 * @param find the f index
	 * @param xind the x index
	 * @return msg the message requested from f to x
	 */
	private Double[] msgftox(int find, int xind){
		Double[] msg = new Double[this.k+1];
		if(find == this.n + xind){ //f leftarrow x message
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
	private Double[] msgxtof(int xind, int find){
		//Double[] msg = new Double[this.k+1];
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
	protected Double[] getunary(int ind){
		Double[] unary = new Double[this.k+1];
		for(int i = 1; i <= this.k; i++){
			unary[i] = this.potentials.potential(ind, i);
		}
		unary[0] = 0.0;
		return unary;
	}
	
	/**
	 * 
	 * @param ind
	 * @return
	 */
	private Double[][] getbinary(int ind){
		Double[][] binary = new Double[this.k+1][this.k+1];
		for(int i = 0; i <= this.k; i++){
			for(int j = 0; j <= this.k; j++){
				if(i == 0 || j == 0){
					binary[i][j] = 0.0;
				}
				else{
					binary[i][j] = this.potentials.potential(ind, i, j);
				}
			}
		}
		return binary;
	}
	
	private Double[][] getbinary2(int ind){
		Double[][] binary = new Double[this.k+1][this.k+1];
		for(int i = 0; i <= this.k; i++){
			for(int j = 0; j <= this.k; j++){
				if(i == 0 || j == 0){
					binary[i][j] = 0.0;
				}
				else{
					binary[i][j] = this.potentials.potential(ind, j, i);
				}
			}
		}
		return binary;
	}
	
	public double[] marginalProbability(int x_i) {
		Double[] topmsg = getunary(x_i);
		Double[] rightinmsg = raDesftox[x_i];
		Double[] leftinmsg = raAscftox[x_i];
		Double[] resu;
		if(x_i == 1){ // special case f leftarrow x1
			//Double[] rightoutmsg = msgxtof(x_i, this.n + x_i); // == topmsg
			Double[] numer = Util.ramult(topmsg, rightinmsg);
			Double denom = Util.dot(topmsg, rightinmsg);
			resu = Util.scalarMultiply(1/denom, numer);
//			return resu;
		}
		else if (x_i == this.n){ //special case f rightarrow xn
			Double[] numer = Util.ramult(topmsg, leftinmsg);
			Double denom = Util.dot(topmsg, leftinmsg);
			resu = Util.scalarMultiply(1/denom, numer);
//			return resu;
		}
		else{ //rest use 3way
			
			Double[] numer = Util.ramult(Util.ramult(topmsg, leftinmsg), rightinmsg);
			Double denom = Util.dot(topmsg, Util.ramult(leftinmsg, rightinmsg));
			resu = Util.scalarMultiply(1/denom, numer);
//			return resu;
		}
		double[] res = new double[resu.length];
		for(int i = 0; i < res.length; i++){
			res[i] = resu[i];
		}
		return res;
	}

}

