import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Q2 {
	
	
	//Two hashmaps for the correct probabilities and other for the empirical frequency 
	public HashMap<String, Double> corrFreqMap;
	public HashMap<String, Double> empMap;

	public Q2(HashMap<String, Double> correctFreqMap, HashMap<String, Double> empericalMap) {
		corrFreqMap = correctFreqMap;
		empMap = empericalMap;
		
	}
	
	public String createSequence(int len) {
		
		int randomNumbers = 0;
		double empericalFreq = 0;
		String inputs = "";
		
		for(int i = 0; i < len; i++) {
			
			randomNumbers = numGenerator(1000);
			
			if(randomNumbers >= 0 && randomNumbers <= 214) {
				empericalFreq = (empMap.get("A") + 1);
				inputs += "A";
			}
			
			if(randomNumbers >= 215 && randomNumbers <= 478) {
				empericalFreq = (empMap.get("T") + 1);
				inputs += "T";
			}
			if(randomNumbers >= 479 && randomNumbers <= 799) {
				empericalFreq = (empMap.get("C") + 1);
				inputs += "C";

			}
			if(randomNumbers >= 800 && randomNumbers <= 1000) {
				empericalFreq = (empMap.get("G") + 1);
				inputs += "G";
			}
			
		}
	
		return inputs;
	}
	
	public Collection<Double> empericalFreqCalculator(int length) {
		
		String empericalFreq = "";
		
		Iterator<Map.Entry<String, Double>> q1Probabiltys = empMap.entrySet().iterator();

		while(q1Probabiltys.hasNext()) {
			Map.Entry<String, Double> q1ProbabilityEntrys = q1Probabiltys.next();
			empMap.put(q1ProbabilityEntrys.getKey(), q1ProbabilityEntrys.getValue() / (double)length);
		}
		
		return empMap.values();
	}
	
	public int numGenerator(int upperLimit) {
		
		Random randNum = new Random();
	
		int range = upperLimit;
		int random = randNum.nextInt(range) + 1;
		 	
		return random;
		  
	}
	
	public static void main(String[] args) {
		
		HashMap<String, Double> corrFreqMap = new HashMap<String, Double>();
		HashMap<String, Double> empMap = new HashMap<String, Double>();
		String fileName = "out.txt";
	    Scanner scanner = new Scanner(System.in); 

		String[] inputs = {"A", "T", "C", "G"};
		String[] probSymbols = {"x1", "x2", "x3", "x4"};
		double[] probabilitys = {0.2141, 0.2648, 0.3207, 0.2004};
		int D = 0;
		
		for(int i = 0; i < 4; i++) {
			
			corrFreqMap.put(probSymbols[i],  probabilitys[i]);
			empMap.put(inputs[i],  probabilitys[i]);

		}
		
		Q2 i = new Q2(corrFreqMap, empMap);
		HuffmanCoder huff = new HuffmanCoder();
		
		System.out.println("Correct Frequency map: " + corrFreqMap + "\nEmperical Frequency: " + empMap);

		for(int j = 2; j <= 4; j++) {
			
			System.out.println("D = " + j);
			System.out.println("Entropy for D=" + j + ": " + huff.entropyCalculator(corrFreqMap, j));
				System.out.println("Length for D= " + j + ": " +  huff.calculateExpectedLength(empMap, huff.huffmanCreater(empMap, j)));

			System.out.println("Huffman codes: " + huff.huffmanCreater(corrFreqMap, j));
		}


		String sequences = i.createSequence(10000);
		String encodedMessage = huff.encoder(sequences, huff.huffmanCreater(empMap, 2));
		
		System.out.println(huff.encoder(sequences, huff.huffmanCreater(empMap, 2)) + "\n");
		System.out.println(huff.calculateExpectedLength(empMap, huff.huffmanCreater(empMap, 2)));
		System.out.println(huff.decoder(huff.huffmanCreater(empMap, 2), encodedMessage));
		
	}
}	
