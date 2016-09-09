package languageProcessor;

import java.util.ArrayList;

public class DecisionTree {
	Node root;
	
	DecisionTree(){
		Node temp = new Node("who");
		root = temp;
		
		temp = new Node("ticket");
		root.left = temp;
		
		root.left.left = new Node("person");
		root.left.left.left = new Node("assignee");
		root.left.left.left.left = new Node("assignee_of_ticket");	
		root.left.left.left.right = new Node("reporter_of_ticket");
		
		
		root.right = new Node("what");
		root.right.left = new Node("ticket");
		root.right.left.left = new Node("description_of_ticket");
		
	}
	
	public void insertRoot(Node node){
		if (root == null)
			root = node;
	}
	
	/*
	 * Insert new node to the left
	 */
	public void insertLeft(Node node){
		if (root == null)
			root = node;
		else{
			Node temp = root; 
			while(temp.left != null){
				temp = temp.left;
			}
			temp.left = node;
		}
	}
	
	/*
	 * Insert new node to the right
	 */
	public void insertRight(Node node){
		if (root == null)
			root = node;
		else{
			Node temp = root; 
			while(temp.right != null){
				temp = temp.right;
			}
			temp.right = node;
		}
	}
	
	public static void main(String args[]){
		
	}
	
	public String traverse(ArrayList<String> keywords){
		boolean flag = false;
		Node temp = root;
		while(temp!= null){
			String keyword = temp.node;
			
			
			if(keywords.contains(keyword))
				flag = true;	
			else 
				flag = false;
			
			//System.out.println(keywords.contains(keyword));
			
			if(temp.left == null && temp.right == null)
				return keyword;
			
			if(flag == true){
				temp = temp.left;
				//System.out.println("Left");
			}
			else{
				temp = temp.right;
				//System.out.println("Right");
			}			
		}
		return null;
	}
}
