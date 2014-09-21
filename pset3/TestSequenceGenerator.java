/**
 * Erik Lopez JR
 * el8779
 */

package pset3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import gov.nasa.jpf.vm.Verify;
	
public class TestSequenceGenerator {
		
	public void generateAllBoundedSequences(String classname, List<MethodDecl> methods,ValueDomains doms) {
// precondition: assume all methods in "methods" are declared by class "classname" and
// "doms" define value domains for all method types that appear in given methods;
// assume also that "classname" defines an instance method "invocationCount()", which
// returns the number of methods invoked on "this" since its creation
// postcondition: prints a JUnit test suite to standard console; the suite contains
// method invocation sequences of length <= methods.size(), such that (1) each method
// sequence for each parameter value combination is executed and (2) each test case
// includes a test assertion that validates the result of method "invocationCount()".
//...
		Verify.setCounter(0, 0);
		int j =-1;
		String metTest="";
		String mLine="";
		String param = "";
		MethodDecl method;
		int invocations = 0;
		while (j < methods.size()){
			if (j >=0){
				method = methods.get(Verify.getInt(0, methods.size()-1));
				mLine = method.className();
				param = "";
				invocations++;
				if (method.argumentTypes() != null){//
					String[] inputTypes = method.argumentTypes(); //(String x, int y, bool z ...)
					int i = 0;
				
					while (i < inputTypes.length){
						if (doms != null){//
							List<Object> inputs = doms.getDomain(inputTypes[i]);
							Object sym ;
							if (inputs != null && inputs.size()!=0){
								sym = inputs.get(Verify.getInt(0, inputs.size()-1));
						
								if (sym != null){
									if (param.length()==0)
										param = param+sym.toString();
									else 
										param = param+", "+sym.toString();
								}
								else {
									if (param.length()==0)
										param = param+"null";
									else 
										param = param+", null ";
								}
							}
						}//
						i++;
					}
				}//
				
				if (metTest.length()==0){
					metTest=metTest+method.methodName()+"("+param+");"; //C.m(param);
				}
				else{
					metTest=metTest+"\n"+"\t"+method.className()+"."+method.methodName()+"("+param+");";
				}
				
			}
			System.out.println("@Test public void test"+Verify.getCounter(0)+"(){");
			Verify.incrementCounter(0);
				System.out.println("\t"+classname+" var = new "+classname+"();");
				if (mLine.length()!=0)
					System.out.println("\t"+mLine+"."+metTest);
				System.out.println("\tassertTrue(var.invocationCount() == "+invocations+");\n}");
				
			j++;
		}
		
		
		
	}
	public static void main(String args[]){
		/*
		MethodDecl[] methods = {
			new MethodDecl("N", "m", new String[]{""}),
			new MethodDecl("N", "n", new String[]{"java.lang.String", "boolean"}),
			new MethodDecl("N", "a", new String[]{"int", "int"})
		};
		MethodDecl m = new MethodDecl("C", "m", new String[]{"int"});
		MethodDecl n = new MethodDecl("C", "n", new String[]{"java.lang.String", "boolean"});
		List<MethodDecl> methods2 = new LinkedList<MethodDecl>();
		methods2.add(methods[0]);
		methods2.add(methods[1]);
		methods2.add(methods[2]);
		new TestSequenceGenerator().generateAllBoundedSequences("C", methods2, new ValueDomains());
		*/
		CNF3 formula = new CNF3("(v98 or v1 or v2) and (v0 or !v1 or v3) and (!v0 or v1 or !v2) and "
				  + "(!v0 or !v1 or v2) and (!v0 or !v1 or !v2) and (!v0 or !v1 or !v3)"
				  + " and (v0 or v1 or !v2) and (v0 or v1 or v3) and (v0 or !v1 or v2)"
				  + " and (v0 or !v1 or !v2) and (!v0 or v1 or v2)", 4, 11); 
		MAX3SATSolver s = new MAX3SATSolver();
		s.solve(formula);
	}
		
}