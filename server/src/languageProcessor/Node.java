package languageProcessor;


public class Node {
	String node;
	Node left;
	Node right;
	Node parent;
	
	Node(){
		node = null;
		left = null;
		right = null;
		parent = null;
	}
	
	Node(String str){
		node = str;
		left = null;
		right = null;
		parent = null;
	}
	
	Node(String str, Node parentNode){
		node = str;
		left = null;
		right = null;
		parent = parentNode;
	}
	
	Node(String str, Node leftNode, Node rightNode, Node parentNode){
		node = str;
		left = leftNode;
		right = rightNode;
		parent = parentNode;
	}
}
