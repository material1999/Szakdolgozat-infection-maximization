package Runnable;

import Graph.Network;
import Graph.Node;
import Heuristics.Greedy;
import Reader.Read;
import Simulations.CompleteSim;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import static Runnable.Parameters.best;
import static Runnable.Parameters.ksize;

public class InfectionRun {

	static long startTime, elapsedTime, elapsedSeconds, seconds, minutes;

	public static void main(String[] args) throws IOException {

		String networkFilePath;
		String communityFilePath;


		// Greedy method whole graph console info
		/*
		System.out.println("\nRunning greedy on the whole graph ...");
		System.out.println("ksize: " + Parameters.ksize + ", greedy iterations: " + Parameters.CompleteSimSamplesize +
						", simulation iterations: " + Parameters.CompleteSimFinalSamplesize + "\n");
		*/


		// Greedy method community finder console info

		System.out.println("\nRunning greedy on " + Parameters.communityFinder + " communities ...");
		System.out.println("ksize: " + ksize + ", best: " + best + ", greedy iterations: " + Parameters.CompleteSimSamplesize +
			", simulation iterations: " + Parameters.CompleteSimFinalSamplesize + "\n");



		/*
		TreeSet<Integer> on = new TreeSet<>();
		on.add(1);
		on.add(259);
		on.add(462);
		on.add(691);
		on.add(777);
		for (int i : on) {
		*/
		//for (int i = 1; i <= 1; i++) {
		for (int i = 1; i <= Parameters.fileCount; i++) {

			networkFilePath = Parameters.networksFolder + i + "/edgeweighted_edit.csv";

			Network net = Read.ReadCsv(networkFilePath);

			////////////////
			String temp = "_max10_50szazalek_4x";
			communityFilePath = Parameters.communitiesFolder + "sim_com_" + i + temp + "_sorted.csv";
			File output = new File("results/greedy_" + i + temp + ".csv");
			//File output = new File("results/greedy_" + i + "_full.csv");
			////////////////

			/*
			Path path = Paths.get(communityFilePath);
			long lines = Files.lines(path).count();

			int best = Parameters.best;
			if (lines < best) best = (int)lines;
			int ksize = Parameters.ksize;
			if (lines < ksize) ksize = (int)lines;
			*/


			try {
				FileWriter fw = new FileWriter(output);

				System.out.println("\nStarted with file " + i);


				// run the Greedy method on the whole graph
				/*
				startTime = System.currentTimeMillis();
				fw.write("ksize: " + Parameters.ksize + ", greedy iterations: " + Parameters.CompleteSimSamplesize +
						", simulation iterations: " + Parameters.CompleteSimFinalSamplesize + "\n\n");
				ArrayList<Node> infectorset_whole = new ArrayList<Node>(net.getNodes());
				LinkedList<Node> bestnodes_whole = Greedy.LetsRunTheGreedy(net, Parameters.ksize, Parameters.CompleteSimSamplesize, infectorset_whole,
					Parameters.directed, Parameters.GreedySimulation, fw);
				double finalinfection_greedy = CompleteSim.Simulation(net, bestnodes_whole, Parameters.CompleteSimFinalSamplesize, Parameters.directed);
				fw.write("\nFinal greedy infection: " + finalinfection_greedy);
				elapsedTime = System.currentTimeMillis() - startTime;
				elapsedSeconds = elapsedTime / 1000;
				seconds = elapsedSeconds % 60;
				minutes = elapsedSeconds / 60;
				System.out.println("elapsed time: " + minutes + " min " + seconds + " sec\n");
				*/


				// run the Greedy method on the most infectious nodes based on communitiy detection algorithms

				startTime = System.currentTimeMillis();
				fw.write("ksize: " + ksize + ", best: " + best + ", greedy iterations: " + Parameters.CompleteSimSamplesize +
						", simulation iterations: " + Parameters.CompleteSimFinalSamplesize + "\n\n");
				ArrayList<Node> infectorset_community = Read.readBestNodes(net, communityFilePath, best);
				LinkedList<Node> bestnodes_community = Greedy.LetsRunTheGreedy(net, ksize, Parameters.CompleteSimSamplesize, infectorset_community,
						Parameters.directed, Parameters.GreedySimulation, fw);
				double finalinfection_community = CompleteSim.Simulation(net, bestnodes_community, Parameters.CompleteSimFinalSamplesize, Parameters.directed);
				fw.write("\nFinal greedy infection: " + finalinfection_community);
				elapsedTime = System.currentTimeMillis() - startTime;
				elapsedSeconds = elapsedTime / 1000;
				seconds = elapsedSeconds % 60;
				minutes = elapsedSeconds / 60;
				System.out.println("elapsed time: " + minutes + " min " + seconds + " sec\n");



				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Done with file " + i);
		}
		System.out.println("\nProgram finished successfully, press ENTER to exit ...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
