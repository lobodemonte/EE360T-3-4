package pset4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import gov.nasa.jpf.vm.Verify;

public class BinaryTree {
	int size; boolean size_accessed = false;
	Node root; boolean root_accessed = false;
	static class Node {
		Object element; boolean element_accessed = false;
		boolean visited= false;
		Node left, right, parent; 
		boolean left_accessed = false, right_accessed = false, parent_accessed= false;
		
		public Object element(){
			if (!element_accessed){
				element_accessed = true;
				return element;//REPLACE WITH VERIFY
			}
			return element;
		}
		public Node left(){
			if (!left_accessed){
				left_accessed = true;
				return left; //REPLACE WITH VERIFY
			}
			return left;
		}
		public Node right(){
			if (!right_accessed){
				right_accessed = true;
				return right;//REPLACE WITH VERIFY
			}
			return right;
		}
		public Node parent(){
			if (!parent_accessed){
				parent_accessed = true;
				return parent;//REPLACE WITH VERIFY
			}
			return parent;
		}
		@Override
		public String toString(){
			return "self="+this+" value= "+element+ " parent="+this.parent+", left="+this.left+" right="+this.right;
		}
	}
	public boolean repOk() {
		// precondition: true
		// postcondition: result = true <=>
		/*the input is a valid binary tree with parent pointers
		...*/
		Set<Node> checked = new HashSet<Node>();
		Queue<Node> nodeSet = new LinkedList<Node>();
		Node hold;
		if (root == null){ return size == 0;}
		if (root.parent() != null) {return false;}
		
		checked.add(root);
		nodeSet.add(root);
		
		while (!nodeSet.isEmpty()){
			hold = nodeSet.remove();
			if (hold.left != null){
				if (!checked.add(hold.left)) {
					//System.out.println("Left Node is a loop");
					return false;
				}
				if (hold.left.parent != hold) {
					//System.out.println("Left Parent is Wrong");
					return false;
				}
				nodeSet.add(hold.left);
			}
			if (hold.right != null){
				if (!checked.add(hold.right)) {
					//System.out.println("Right Node is a loop");
					return false;
				}
				if (hold.right.parent != hold) {
					//System.out.println("Right Parent is Wrong");
					return false;
				}
				nodeSet.add(hold.right);
			}
		}
		//System.out.println(checked.size()+"&"+size);
		return checked.size() == size;
		
	}
	void filterBasedGenerator(int n) {
	
		if (n < 0){ return;}
		
		// allocate objects
		int i;
		BinaryTree tree = new BinaryTree();
		Object[] inputs = new Object[]{null, new Integer(0)};
		size = tree.size = Verify.getInt(0, n);
		Node[] nodes = new Node[n+1];
		
		nodes[0] = null;
		for (i = 1; i < n+1; i++){
			nodes[i] = new Node();
		}
		
		tree.root = nodes[size];
		
		for (i = 1; i < nodes.length; i++){
			nodes[i].element = inputs[Verify.getInt(0, 1)];
		}
		
		if (size > 1){
			for (i = 1; i < nodes.length; i++){
				nodes[i].left = nodes[ Verify.getInt(0, n)];
				nodes[i].right = nodes[ Verify.getInt(0, n)];
				nodes[i].parent = nodes[ Verify.getInt(0, n)];
			}
		}
			
			
		if (tree.repOk()){
			Verify.incrementCounter(0);
			System.out.println("Test #"+Verify.getCounter(0)+" Size:"+size);
		}
		
		
			
	}
	void pruningBasedGenerator(int n) {
		// allocate objects
		//...
		// set field domains
		//...
		// run instrumented repOk to enable non-deterministic field
		// assignments on field access, and output the tree if valid
		//...
		
	}
	public int size(){
		if (!size_accessed){
			size_accessed = true;
			return size;//REPLACE WITH VERIFY
		}
		return size;
	}
	public Node root(){
		if (!root_accessed){
			root_accessed = true;
			return root; //REPLACE WITH VERIFY
		}
		return root;
	}
	
	
	
	public static void main(String[] args) {
		BinaryTree t = new BinaryTree();
		Verify.setCounter(0, 0); //for legal trees
		t.filterBasedGenerator(1);
		
		
	}
	

}
