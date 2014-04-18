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
	private Double[][] raDesxtof;
	private Double[][] raDesftox;
	private Double[][] raAscxtof;
	private Double[][] raAscftox;
	private int[][] backtrack;
	
	public MaxSum(ChainMRFPotentials p) {
		this.potentials = p;
		assignments = new int[p.chainLength()+1];
		this.n = p.chainLength();
		this.k = p.numXValues();
		backtrack = new int[n+1][k+1];
		
		//for storing messages from variable x to factor f and vice versa
		raDesxtof = new Double[n+1][k+1];
		raDesftox = new Double[n+1][k+1];
		raAscxtof = new Double[n+1][k+1];
		raAscftox = new Double[n+1][k+1];
		Double value;
		for(int i = 0; i < n+1; i++){
			for(int j = 0; j < k+1; j++){
				value = null;
				if(i == 0 || j == 0){
					value = 0.0;
				}
				raDesxtof[i][j] = value;
				raDesftox[i][j] = value;
				raAscxtof[i][j] = value;
				raAscftox[i][j] = value;
			}
		}
		Double[] zeros = new Double[k+1];
		for(int i = 0; i < k+1; i++){
			zeros[i] = 0.0;
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
//		System.out.println();
		
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
	private Double[] msgxtof(int xind, int find){
		//Double[] msg = new Double[this.k+1];
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
	private Double[] getunary(int ind){
		Double[] unary = new Double[this.k+1];
		for(int i = 1; i <= this.k; i++){
			unary[i] = Math.log(this.potentials.potential(ind, i));
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
					binary[i][j] = Math.log(this.potentials.potential(ind, i, j));
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
					binary[i][j] = Math.log(this.potentials.potential(ind, j, i));
				}
			}
		}
		return binary;
	}

	public int[] getAssignments() {
		return assignments;
	}

	
	public Double maxProbability(int x_i) {
//		x_i = n -1;
		Double[] topmsg, rightinmsg, leftinmsg, resu, probability;
		Double max = Double.NEGATIVE_INFINITY;
		Double z = 0.0;
		for(int p = 1; p <= n; p++){
		max = Double.NEGATIVE_INFINITY;
		z = 0.0;
		x_i = p;
		topmsg = getunary(x_i);
		rightinmsg = raDesftox[x_i];
		leftinmsg = raAscftox[x_i];
		
		resu = Util.raadd(Util.raadd(topmsg, rightinmsg), leftinmsg);
		//get max of the sum over all k configurations
		
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
		
		//raAscftox = sp.raAscftox;
		
		if(x_i == 1){
			probability = Util.ramult(topmsg, rightinmsg);
		}
		else if(x_i == n){
			probability = Util.ramult(topmsg, leftinmsg);
		}
		else{
			probability = Util.ramult(Util.ramult(topmsg, rightinmsg), leftinmsg);
		}
		for(int i = 1; i <= this.k; i++){
			z += probability[i];
		}
//		System.out.println();
		}
		return max - Math.log(z);
		
//		return max;
		
	}
	
	private Double[] matrixmax(Double[][] bin, Double[] una, int xind){
		int temp = 1;
		Double[] res = new Double[una.length];
		for(int row = 0; row < bin.length; row++){
			res[row] = Double.NEGATIVE_INFINITY; //initialize
			for(int col = 1; col <= this.k; col++){
				if(bin[row][col] + una[col] > res[row]){
					res[row] = bin[row][col] + una[col];
					backtrack[xind][row] = col;
					temp = col;
				}
//				System.out.println(col);
			}
		}
//		System.out.println(temp);
		
		return res;
	}
}
