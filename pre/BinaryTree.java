package edu.jhu.cs.kwong23.ml.binarytree;

public class BinaryTree {

	BinaryTreeNode root;
	
	public BinaryTree(){
		root = new BinaryTreeNode();
		root.data = 0;
		root.left = null;
		root.right = null;
		root.parent = null;
	}
	
	void insert(int number){
		BinaryTreeNode current;
		BinaryTreeNode parent;
		if(root.data == 0){
			root.data = number;
		}
		else{
			current = root;
			parent = current;
			while(current != null){
				parent = current;
				if(current.data < number){
					current = current.right;
				}
				else if(current.data > number){
					current = current.left;
				}
			}
			current = new BinaryTreeNode();
			current.parent = parent;
			current.left = null;
			current.right = null;
			current.data = number;
			if(parent.data < number){
				parent.right = current;
			}
			if(parent.data > number){
				parent.left = current;
			}
		}
	} // end insert
	void simpleprint()
	{
		BinaryTreeNode current = new BinaryTreeNode();
		if(current != null){
			System.out.println("current is not null");
		}
		System.out.println("newline");
	}
	void print()
	{
		print(root, 0);
	}
	void print(BinaryTreeNode node, int skip)
	{
		
		if(node != null)
		{
			if(node.left != null) print(node.left, skip+4);
			if(node.right != null) print(node.right, skip+4);
			if (skip != 0)
			{
				for(int i = 0; i < skip; i++)
				{
					System.out.print(' ');
				}
			}
			System.out.println(node.data);
		}
	}
	void test()
	{
		System.out.println(root.data);
		System.out.println(root.left.data);
		System.out.println(root.right.data);
		System.out.println(root.left.parent.data);
	}
	
	static class BinaryTreeNode
	{
		int data;
		BinaryTreeNode left;
		BinaryTreeNode right;
		BinaryTreeNode parent;
		
		public BinaryTreeNode( int thedata)
		{
			this.data = thedata;
		}
		
		public BinaryTreeNode( )
		{
			
		}
		
	}
	

	
	

	
	

}
