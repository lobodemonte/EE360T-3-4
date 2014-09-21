/**
 * Erik Lopez JR
 * el8779
 */

package pset3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class CNF3 { // represents a 3-CNF formula
	private int numVars; // total number of boolean variables in the formula
	// the variables are v_0, v_1, ..., v_{numVars - 1}
	private List<Clause> clauses;
	
	public CNF3(String formula, int numVars, int numClauses) {
		// String "formula" represents the formula in 3-CNF;
		// "numVars" is the number of variables in the "formula";
		// "numClauses" is the number of clauses in the "formula".
		// assume: numVars >= 1, numClauses >= 1, and the
		// variables are from the set { v0, v1, ..., v9 }
	
		StringTokenizer st = new StringTokenizer(formula, " \t\n\r\f()");
		this.numVars = numVars;
		
		int[] v= {-1,-1,-1}; 
		clauses = new ArrayList<Clause>();
		boolean[] n= {true, true, true};
		int index = 0;
		while (st.hasMoreTokens()){ 
			String token = st.nextToken();
			if (token.matches(".*\\d.*")){//if we have a literal 
				if (token.contains("!")){
					n[index] = false;
				}
				else 
					n[index]=true;
				v[index] = Character.getNumericValue(token.charAt(token.length()-1));
				index++;
			}
			if (token.contains("and")){				
				addClause(v[0], n[0], v[1], n[1],v[2],n[2]);
				index = 0;	
			}
			
			if (!st.hasMoreTokens()){
				addClause(v[0], n[0], v[1], n[1],v[2],n[2]);
				index = 0;
			}
			//System.out.println(token);
		}
	}
	public int numVars() {
		return numVars;
	}
	public int numClauses() {
		return clauses.size();
	}
	private void addClause(int v1, boolean n1, int v2, boolean n2, int v3, boolean n3) {
		clauses.add(new Clause(v1, n1, v2, n2, v3, n3));
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for (Clause cls: clauses){
			str.append(cls.toString()+"\n");
		}
		return str.toString();
	}
	
	public int countSatisfiedClauses(boolean[] assignment) {
		//precondition: assignment != null && assignment.length == numVars

		int count = 0;
		
		boolean[] ass;
		for (Clause cls: clauses){
			ass = assignment.clone();
			
			//System.out.println(cls.toString());
			//System.out.println("Before: "+Arrays.toString(ass));
			
			if(cls.neg1 == false){
				//System.out.println("neg1 taken");
				ass[cls.var1] = !ass[cls.var1];
				}
			if(cls.neg2 == false){
				//System.out.println("neg2 taken");
				ass[cls.var2] = !ass[cls.var2];
				}
			if(cls.neg3 == false){
				//System.out.println("neg3 taken");
				ass[cls.var3] = !ass[cls.var3];
			}
			
			//System.out.println("After: "+Arrays.toString(ass));
		
			if (ass[cls.var1]||ass[cls.var2]||ass[cls.var3]){
				
				//System.out.println("good\n");
				count++;
			}
		}
		return count;
	}

	
	private static class Clause {
		int var1, var2, var3;
		boolean neg1, neg2, neg3; // "neg" iff the corresponding variable is negated
		Clause(int v1, boolean n1, int v2, boolean n2, int v3, boolean n3) {
			var1 = v1; neg1 = n1;
			var2 = v2; neg2 = n2;
			var3 = v3; neg3 = n3;
		}
		
		public String toString(){
			String n1="";
			if (neg1 == false)
				n1="!";
			String n2="";
			if (neg2 == false)
				n2="!";
			String n3="";
			if (neg3 == false)
				n3="!";
			return n1+"v"+var1+" "+n2+"v"+var2+" "+n3+"v"+var3;
		}
	}
}
