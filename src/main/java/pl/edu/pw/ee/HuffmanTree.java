package pl.edu.pw.ee;

import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanTree {
    
    private Node root;
    public static final boolean LEAF = true;
    private final HashMap<String, String> mapOfCodes;
    
    public HuffmanTree() {
        mapOfCodes = new HashMap<>();
    }
    
    public HuffmanTree(int[] symbolOccurancies) {
        createTree(symbolOccurancies);
        mapOfCodes = new HashMap<>();
    }
    
    private void createTree(int[] symbolOccurancies) {
        
        PriorityQueue<Node> pq = createMinFreqPQ(symbolOccurancies);
        
        if (pq.isEmpty()) {
            throw new IllegalArgumentException("No root = No tree = No possibility to do anything");
        }
        
        int amountOfMerges = pq.size() - 1;
        
        for (int i = 0; i < amountOfMerges; i++) {
            Node node = new Node();
            
            node.setLeftSon(pq.poll());
            node.setRightSon(pq.poll());
            
            int leftSonFreqency = node.getLeftSon().getFrequency();
            int rightSonFreqency = node.getRightSon().getFrequency();
            
            node.setFrequency(leftSonFreqency + rightSonFreqency);
            
            pq.add(node);
        }
        
        root = pq.poll();
    }
    
    private static PriorityQueue<Node> createMinFreqPQ(int[] symbolOccurancies) {
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for (int i = 0; i < symbolOccurancies.length; i++) {
            
            if (symbolOccurancies[i] != 0) {
                char symbol = (char) i;
                pq.add(new Node(symbol, symbolOccurancies[i]));
            }
            
        }
        
        return pq;
        
    }
    
    public String writeTree() {
        
        String writtenTree = "";
        
        if (root.getType() == LEAF) {
            writtenTree += (root.getSymbol() + ":1;;");
        } else {
            writtenTree = findCodesInTree(root, "", writtenTree);
        }
        
        return writtenTree;
    }
    
    private String findCodesInTree(Node node, String currentCode, String writtenTree) {
        
        if (node.getType() == LEAF) {
            writtenTree += (node.getSymbol() + ":" + currentCode + ";;");
        } else {
            writtenTree = findCodesInTree(node.getLeftSon(), currentCode + "0", writtenTree);
            writtenTree = findCodesInTree(node.getRightSon(), currentCode + "1", writtenTree);
        }
        
        return writtenTree;
    }
    
    public void fillMapOfCodes(String writtenTree, boolean compress) {
        
        if (writtenTree.equals("")) {
            throw new IllegalArgumentException("No writtenTree = No map of Codes");
        }
        
        String[] splittedTree = writtenTree.split(";;");
        int amountOfLeafs = splittedTree.length;
        
        if (compress == true) {
            for (int i = 0; i < amountOfLeafs; i++) {
                String symbol = splittedTree[i].substring(0, 1);
                String code = splittedTree[i].substring(2);
                mapOfCodes.put(symbol, code);
            }
        } else {
            for (int i = 0; i < amountOfLeafs; i++) {
                String symbol = splittedTree[i].substring(0, 1);;
                String code = splittedTree[i].substring(2);
                mapOfCodes.put(code, symbol);
            }
        }
    }
    
    public HashMap<String, String> getMapOfCodes() {
        return mapOfCodes;
    }
    
}
