package Simulations;

import java.util.*;
import Graph.*;

public class CompleteSim {

	public static double Simulation(Network net, LinkedList<Node> initinfected, int samplesize, boolean directed) {
		for(int j=0;j<samplesize;j++) {

			// Generate G'
			net = GenerateG(net);
			
			for(Node n:initinfected) {
				DFS(n,net,directed);
			}
			net.notVisited();
		}
		for(Node n: net.getNodes()) {
			n.finalizefv(samplesize);
		}
		return net.getFvSum();
	}
	
	public static Network GenerateG(Network net) {
		Iterator<Edge> it=net.getEdges().iterator();
		while(it.hasNext()) {
			Edge e=(Edge)it.next();
			if(Math.random()<e.getWeight()) {
				e.setAlive(true);
			}else {
				e.setAlive(false);
			}
		}
		return net;
	}
	
	
	public static void DFS(Node v, Network net, boolean directed) {
		Stack<Node> s=new Stack<Node>();
		s.push(v);
		while(!s.isEmpty()){
			Node act=s.pop();
			if(act.getState()!= States.VISITED) {
				act.setState(States.VISITED);
				act.addFv();
				if(directed) {
					for(Edge e: act.getOutlist()) {
						if(e.isAlive()) {
							s.push(e.getIn());
						}
					}
				}else {
					for(Edge e: act.getOutlist()) {
						if(e.isAlive()) {
							s.push(e.getIn());
						}
					}
					for(Edge e: act.getInlist()) {
						if(e.isAlive()) {
							s.push(e.getOut());
						}
					}
				}
			}
		}
	}
}
