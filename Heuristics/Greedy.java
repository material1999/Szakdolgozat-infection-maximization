package Heuristics;

import Graph.Network;
import Graph.Node;
import Simulations.CompleteSim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Greedy {
	public static double finalbestfv=0.0;
	public static LinkedList<Node> LetsRunTheGreedy(Network net, int k, int samplesize, ArrayList<Node> narrowedset, boolean directed, String Simulation, FileWriter fw) {

		LinkedList<Node> bestnodes=new LinkedList<Node>();
		LinkedList<Node> actualnodes= new LinkedList<Node>();
		ArrayList<Node> possiblenodes = narrowedset;
		double bestfv = 0;
		while(bestnodes.size() < k) {
			bestfv = 0;
			double act = 0;
			Node chosennode = null;
			for(Node actualnode: possiblenodes) {
				actualnodes.add(actualnode);

				switch(Simulation) {
				case "Complete": 
					act = CompleteSim.Simulation(net,actualnodes,samplesize, directed);
					net.deleteFv();
				}
				if(act > bestfv) {
					bestfv = act;
					chosennode = actualnode;
				}
				actualnodes.remove(actualnode);
			}
			
			bestnodes.add(chosennode);
			actualnodes.add(chosennode);
			possiblenodes.remove(chosennode);
			try {
				//System.out.print("[");
				fw.write("[");
				for(int i = 0; i < bestnodes.size(); i++) {
					if(i == bestnodes.size() - 1) {
						//System.out.print(bestnodes.get(i).getId());
						fw.write(bestnodes.get(i).getId());
					}else {
						//System.out.print(bestnodes.get(i).getId()+",");
						fw.write(bestnodes.get(i).getId() + ",");
					}
				}
				//System.out.println("]\nA hozzajuk tartozo fertozottseg: " + bestfv + " k:" + bestnodes.size() +
				// " maradt:" + possiblenodes.size());
				fw.write("];" + bestfv + "\n");
				System.out.println("k: " + bestnodes.size() + "/" + k);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		finalbestfv=bestfv;
		
		return bestnodes;
	}
}
