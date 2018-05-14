package Test2;
import java.util.ArrayList;



public class Node {
	
	
	public int num = 0;
	public int si;//splitIndex
	public Node parent;
	public Node left;
	public Node right;
	public int count1 = 0;
	public int count0 = 0;
	public String name;
	public ArrayList<String[]> data = new ArrayList<String[]>();
	public int depth = 0;
	public String[] attri;
	public int lable;
	public double pe;
	void setleft(Node node){
		this.left = node;
	}
	void setright(Node node){
		this.right = node;
		
	}

	
}
