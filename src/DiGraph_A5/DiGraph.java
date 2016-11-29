package DiGraph_A5;

import java.awt.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import sun.misc.Queue;

public class DiGraph implements DiGraphInterface{
	Map<String, Node> nodeHash = new HashMap <String,Node>();
	Map<Long,Node> idHash = new HashMap <Long,Node>();
	Map<String,LinkedList<Edge>> vertices = new HashMap<String,LinkedList<Edge>>();
	Map<Long,Edge>edges = new HashMap<Long,Edge>();
	int numEdges, numNodes;
	@Override
	public boolean addNode(long idNum, String label) {
		if(nodeHash.containsKey(label) || idNum < 0 || idHash.containsKey(idNum) || label == null){//checks to see if the node is already in the graph,if the idNum is greater than zero
			return false;
		}else{
			Node newNode = new Node(idNum, label);  //create a new node
			nodeHash.put(label, newNode);  //add to label hashmap
			idHash.put(idNum, newNode);  //add to id hashmap
			vertices.put(label, new LinkedList<Edge>());  //add to adjacency list
			numNodes++;  //update number of nodes in the graph 
			return true;

		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {

		if(idNum < 0 || !nodeHash.containsKey(sLabel) || !nodeHash.containsKey(dLabel) || edges.containsKey(idNum)){  //makes sure that both nodes are contained in the graph
			return false;
		}else{    
			for(int i=0; i < vertices.get(sLabel).size(); i++){  //checks for duplicate edge
				if(vertices.get(sLabel).get(i).getDLabel().equals(dLabel)){
					return false;
				}
			}
			Edge e = new Edge(idNum, sLabel, dLabel,weight,eLabel);
			vertices.get(sLabel).add(e); //add edge to start label's adjacent nodes
			nodeHash.get(dLabel).inDegree++; //update the indegree of the destination label 
			edges.put(idNum, e);
			numEdges++;
			return true;
		}
	}

	@Override
	public boolean delNode(String label) {
		if(!nodeHash.containsKey(label)){ 
			return false;
		}
		int len = vertices.get(label).size();
		for(int i=0; i < len; i++){  //decrement the indegree of all the nodes adjacent to the node
			String temp = vertices.get(label).get(i).getDLabel();
			if(nodeHash.containsKey(temp)){
				nodeHash.get(temp).inDegree--;
			}
		}

		if(nodeHash.get(label).inDegree != 0){
			findInEdge(label);  //refer to findInEdge method
		}
		long temp = nodeHash.get(label).getId();
		numEdges = numEdges - len;
		vertices.get(label).clear();
		vertices.remove(label);
		nodeHash.remove(label);
		idHash.remove(temp);
		numNodes--;
		return true;
	}
	public boolean findInEdge(String label){  //find all the edges that the deleted node was the destination node for
		for(Entry<String, LinkedList<Edge>> s: vertices.entrySet()){
			for(int i=0; i < vertices.get(s.getKey()).size();i++){
				if(vertices.get(s.getKey()).get(i).getDLabel().equals(label)){
					delEdge(s.getKey(),label);
				}
			}
		}

		return true;
	}
	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		boolean tOrF = false;
		if(!vertices.containsKey(sLabel) || !vertices.containsKey(dLabel)){
			return false;
		}

		for(int i=0; i < vertices.get(sLabel).size(); i++){  //remove the edge from the adjacency list
			if(vertices.get(sLabel).get(i).getDLabel().equals(dLabel)){
				vertices.get(sLabel).remove(i);
				tOrF = true;
			}
		}
		if(tOrF == false){
			return false;
		}
		for(Map.Entry<Long, Edge> edge : edges.entrySet()){
			if(edge.getValue().getSLabel().equals(sLabel) && edge.getValue().getDLabel().equals(dLabel)){
				edges.remove(edge.getKey());
				break;
			}
		}
		nodeHash.get(dLabel).inDegree--;
		numEdges--;
		return true;
	}

	@Override
	public long numNodes() {
		// TODO Auto-generated method stub
		return numNodes;
	}

	@Override
	public long numEdges() {
		// TODO Auto-generated method stub
		return numEdges;
	}

	@Override
	public String[] topoSort() {
		LinkedList<Node> q = new LinkedList<Node>();
		int count = 0;
		String[] topoS = new String[numNodes];
		DiGraph g = new DiGraph(); //create temp digraph so that we dont alter the actual digraph 
		Node s;
		for(Map.Entry<String, Node> entry : nodeHash.entrySet()){
			g.nodeHash.put(entry.getKey(), entry.getValue());
		}
		for(Map.Entry<String, Node> entry : g.nodeHash.entrySet()){ //find the nodes with indegrees of 0 and add them to the priority queue
			if(entry.getValue().getInDegree() == 0){
				q.add(entry.getValue());
			}
		}
		while(q.size() > 0){
			s = q.remove();
			String temp = s.label;
			for(int i=0; i < vertices.get(temp).size(); i++){
				g.nodeHash.get(vertices.get(temp).get(i).getDLabel()).inDegree--;  //you are performing a lazy deletion and still need to decrement the indegree of all the adjacent nodes 
				if(g.nodeHash.get(vertices.get(temp).get(i).getDLabel()).getInDegree() == 0){
					q.add(g.nodeHash.get(vertices.get(temp).get(i).getDLabel()));  //add nodes with indegree of 0 to the priority queue
				}
			}
			topoS[count] = s.getLabel();
			count++;
		}

		if(topoS[topoS.length-1] == null){
			return null;
		}
		return topoS;
	}

}
