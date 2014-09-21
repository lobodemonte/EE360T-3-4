/**
 * Erik Lopez JR
 * el8779
 */

package pset3;
import java.util.ArrayList;
import java.util.Arrays;

import gov.nasa.jpf.vm.Verify;
public class MAX3SATSolver {
	public void solve(CNF3 formula) {
		// precondition: formula != null
		// postcondition: outputs a MAX-3SAT solution to "formula" (using JPF)
		Verify.setCounter(0, 0);
		boolean[] assignment = new boolean[formula.numVars()];
		for (int i =0; i< assignment.length; i++){
			assignment[i] = Verify.getBoolean();
		}
		
		//System.out.println(Arrays.toString(assignment));
		int num = formula.countSatisfiedClauses(assignment);
		if (num > Verify.getCounter(0) && num != 0){
			Verify.setCounter(0,num);
			System.out.println("Assignment "+Arrays.toString(assignment)+" satisfies "+num+" of "+formula.numClauses()+" clauses.");	
		}
	}
}