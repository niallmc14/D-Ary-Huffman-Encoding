import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class HuffmanCoder {
	

	int number;
	double expectedLength = 0.0;
	int d;
	private Map<String, String> map;
	
	private HuffmanCoder(Map<String, Double> map, int number) {
		this.map = huffmanCreater(map, number);
	};
	

	
	public HuffmanCoder() {
		// TODO Auto-generated constructor stub
	}



	private static class HuffmanNode {
        String strings;
        double frequency;
        HuffmanNode[] childrenNodes;

        HuffmanNode(String input, double probability, HuffmanNode[] children) {
            this.strings = input;
            this.frequency = probability;
            this.childrenNodes = children;
          
        }
    }
	
	public double entropyCalculator(Map<String, Double> huffmanMap, int D) {
		
		 double entropy = 0;
		 double prob;
		 double logOfD;
		 
		 for (double count: huffmanMap.values()){
		      prob = (Math.log(count));
		      logOfD = (Math.log(D));
		      entropy -= count * (prob / logOfD);
		      
		    }
		 
		    return entropy;
		
	}
    
    public double calculateExpectedLength(Map<String, Double> map, Map<String, String> huffmanCodes) {
    	
    		double calExpected = 0.0;
    		
    		//Removes duplicate elements
    		Set<String> setSymbols = map.keySet();
    		
    		for(String codeSymbols: setSymbols) {
    			double valueProbability = map.get(codeSymbols);
    			int codeSymbolLength = huffmanCodes.get(codeSymbols).length();
    			calExpected += valueProbability * codeSymbolLength;
    		}
		return calExpected;
	}
    
    private class HuffmanComparator implements Comparator<HuffmanNode>{

			@Override
			public int compare(HuffmanNode firstNode, HuffmanNode secondNode) {
				// TODO Auto-generated method stub
				if(firstNode.frequency >= secondNode.frequency) {
					return 1;
				}
				else {
					return -1;
				}
			}
    }
    
    
    private HuffmanNode buildTree(PriorityQueue<HuffmanNode> nodePriorityQueue) {
    	
    		double probability = 0.0;
    		double dummyCheck = (int)(nodePriorityQueue.size() - 1/ (d - 1));
//
//    		if(dummyCheck % 1 ==  0) {
//    			nodePriorityQueue.add(new HuffmanNode("d", 0, null));
//    			dummyCheck = (int)(nodePriorityQueue.size() - 1 / d - 1);
//    		} 		
    		while(dummyCheck != (int)dummyCheck) {
    			nodePriorityQueue.add(new HuffmanNode("d", 0, null));
    			dummyCheck = (int)(nodePriorityQueue.size() - 1 / d - 1);

    		}
    		
    		while(nodePriorityQueue.size() > d - 1) {
    			
    			HuffmanNode[] huffmanArray = new HuffmanNode[d];
    			
        		for(int j = 0; j < d; j++) {
    				huffmanArray[j] = nodePriorityQueue.remove();
    				probability += huffmanArray[j].frequency;
    			
        		}
        		
			HuffmanNode newNode = new HuffmanNode(null, probability, huffmanArray);
			nodePriorityQueue.add(newNode);
    		
    		}
        return nodePriorityQueue.remove();
    	
    }
   
    public HashMap<String, String> huffmanCreater(Map<String, Double> map, int D) {
    	
		if(D < 2) {
			throw new IllegalArgumentException("D must be ≥ to 2 or ≤ 4.");
		}
		
		this.d = D;
		
		PriorityQueue<HuffmanNode> nodePriorityQueue = nodeQueue(map);
		HashMap<String, String> huffmanCodes = new HashMap<String, String>();
	
		HuffmanNode treeRoot = buildTree(nodePriorityQueue);
		
		generateCodes(treeRoot, huffmanCodes, "");
		
		return huffmanCodes;
		
}

    private PriorityQueue<HuffmanNode> nodeQueue(Map<String, Double> map) {
    	
    		PriorityQueue<HuffmanNode> nodePriorityQueue = new PriorityQueue<HuffmanNode>(new HuffmanComparator());
    	
    		for(Entry<String, Double> entries : map.entrySet()) {
    			
    			nodePriorityQueue.add(new HuffmanNode(entries.getKey(), entries.getValue(), null));
    			
    		}
    		
    		return nodePriorityQueue;
    }
    
    public void generateCodes(HuffmanNode huffNode, Map<String, String> map, String huffCodes) {
    	
    		if(huffNode.childrenNodes == null) {
    			if(huffNode.strings != "d") {
    				map.put(huffNode.strings, huffCodes);
        			return;
    			}
    			return;
    		}else {
    			for(int i = 0; i < d; i++ ){
    				generateCodes(huffNode.childrenNodes[i], map, huffCodes + i);
    			}
    		}
    }
    
	public String encoder(String messageForEncoding, Map<String, String> map) {
        
    	StringBuilder stringBuilder = new StringBuilder();
    	
    		//For loop that takes in the code words and encodes it to what its frequency is 
    	
        for (int i = 0; i < messageForEncoding.length(); i++) {
            stringBuilder.append(map.get(String.valueOf(messageForEncoding.charAt(i))));
        }
        
        //Returns the encoded message
        return stringBuilder.toString();    
    }
    
    public String decoder(Map<String, String> map, String messageEncoded){
         
    		Map<String, String> decodedHuffman = new HashMap<String, String>();
        StringBuilder stringBuilder = new StringBuilder();
        String newDecode = "";


    		for(Entry<String, String> entries : map.entrySet()) {
    			decodedHuffman.put(entries.getValue(), entries.getKey());
    		}
    		
    		for(int i = 0; i < messageEncoded.length(); i++) {
    			newDecode += messageEncoded.charAt(i);
    			
    			if(decodedHuffman.containsKey(newDecode)) {
    				stringBuilder.append(decodedHuffman.get(newDecode));
    				newDecode = "";
    			}
    		}
    		
    		return stringBuilder.toString();
    }
    
    public HashMap<String, Double> frequencyMapCreator(String message){
    	
  // 		String mapSymbol;
    	    HashMap<String, Double> freqMap = new HashMap<String, Double>();
    		
    		for(int i = 0; i < message.length(); i++) {
    			
    			String mapSymbol = String.valueOf(message.charAt(i));
        		
    			if(freqMap.containsKey(mapSymbol)) {
    				freqMap.put(mapSymbol, 1.0 / message.length());
    			}
    			else {
    				double frequency = freqMap.get(mapSymbol);
    				frequency += 1.0 / message.length();
    				freqMap.put(mapSymbol, frequency);
    			}
    		}
		return freqMap;
    }

    
    public static void main(String[] args) {
    		
    	
//    	String[] symbols = {"x1", "x2", "x3", "x4", "x5", "x6"};
//    	Double[] probabilties = {0.2141, 0.2648, 0.3207, 0.2004};
    	
		String[] symbols = {"x1", "x2", "x3", "x4"};  // ArrayList of symbols
		double[] probabilities = {0.2, 0.3, 0.1, 0.4};
		HuffmanCoder huffman = new HuffmanCoder();
		double probabilitySum = 0;
		HashMap<String, Double> huffMap = new HashMap<>(); // HashMap to link each symbol with its probability
		

		for(double prob : probabilities) { // check to ensure probabilities don't exceed 1
			probabilitySum += prob;
		}
		
		System.out.println(probabilitySum);
		
		if(probabilitySum >= 1) {
			throw new IllegalArgumentException("Invalid arguments entered. Sum of all probabilities cannot be greater than or equal to 1");
		}

		for(int i = 0; i < symbols.length; i++) {
			huffMap.put(symbols[i], probabilities[i]);
		}
		
		for(int d = 2; d <= 4; d++) {
			HashMap<String, String> huffCode = huffman.huffmanCreater(huffMap, d); // createHuffmanCode for D and the symbol-probability Map
			System.out.println(huffCode);

		}
    }
}
