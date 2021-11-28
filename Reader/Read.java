package Reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;

import Graph.*;

public class Read {

	public static Network ReadCsv(String filepath) {
		Network network = new Network();
		CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(filepath),';');
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
            	if(!(line[0].equals(line[1]))) {
                network.addEdge(new Edge(new Node(line[0]),new Node(line[1]),Double.parseDouble(line[2])));
            	}
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return network;
	}

	public static ArrayList<Node> readBestNodes (Network net, String filepath, int best) throws FileNotFoundException {
	    ArrayList<Node> nodeArrayList = new ArrayList<>();
        CSVReader reader;
        String[] line;
        Node nextNode;
        try {
            reader = new CSVReader(new FileReader(filepath),':');
            for (int i = 0; i < best; i++) {
                line = reader.readNext();
                nextNode = net.getNode(line[0]);
                nodeArrayList.add(nextNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	    return nodeArrayList;
    }
}
