package DiGraph_A5;

public class DiGraphPlayground {
	 public static void main (String[] args) {
		  
	      // thorough testing is your responsibility
	      //
	      // you may wish to create methods like 
	      //    -- print
	      //    -- sort
	      //    -- random fill
	      //    -- etc.
	      // in order to convince yourself your code is producing
	      // the correct behavior
	    DiGraph d = new DiGraph();
	    //d.d.d.d.addNode(0, "z");
	    d.addNode(0, "a");
	    d.addNode(1, "b");
	    d.addNode(2, "c");
	    d.addNode(3, "d");
	    d.addEdge(0, "d", "a", 0, null);
	    d.addEdge(1, "a", "b", 0, null);
	    d.addEdge(2, "b", "c", 0, null);
	    d.delEdge("d", "a");
	    d.addEdge(-1, "c", "d", 0, null);
	    //d.addNode(3, "s");
	    
	    System.out.println(d.numEdges);
	    
	    printTOPO(d.topoSort());
		 //exTest2();
	    }
	  
	    public static void printTOPO(String[] toPrint){
	      System.out.print("TOPO Sort: ");
	      for (String string : toPrint) {
	      System.out.print(string+" ");
	    }
	      System.out.println();
	    }
	    public static void exTest2(){
	    	DiGraph d = new DiGraph();
	    	long startTime = System.currentTimeMillis();
	    	
	    	for(int i = 0; i < 10000; i++){
	    		d.addNode((long) i, Integer.toString(i));
	    	}
	    	
	    	for(int i = 0; i < 10000; i++){
	    		for(int j = 0; j < 10000-1; j++){
	    			d.addEdge((long) i, Integer.toString(i), Integer.toString(j), 1, null);
	    		}
	    	}
	    	
	    	long endTime = System.currentTimeMillis();
	    	long totalTime = endTime - startTime;
	    	System.out.println("numEdges: "+d.numEdges());
		    System.out.println("numNodes: "+d.numNodes());
		    System.out.println("Build took: "+ totalTime + " ms or " + (totalTime/1000) + " seconds."); 
		    
		    long startTime2 = System.currentTimeMillis();
		    d.topoSort();
		    long endTime2 = System.currentTimeMillis();
		    long totalTime2 = endTime2 - startTime2;
		    System.out.println("Topo sort took: "+ totalTime2 + " ms or " + (totalTime2/1000) + " seconds."); 

	    }
	

}
