package pl.edu.pw.ee;

public class Node implements Comparable {

    private char symbol;
    private int frequency;
    private Node leftSon;
    private Node rightSon;
    private final boolean type;

    public Node() {
        this.type = !HuffmanTree.LEAF;
    }

    public Node(char symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.type = HuffmanTree.LEAF;
        this.leftSon = null;
        this.rightSon = null;
    }

    @Override
    public int compareTo(Object o) {

        Node otherNode = (Node) o;
        if (this.frequency > otherNode.frequency) {
            return 1;
        } else if (this.frequency < otherNode.frequency) {
            return -1;
        }
        return 0;

    }

    public char getSymbol() {
        return symbol;
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeftSon() {
        return leftSon;
    }

    public Node getRightSon() {
        return rightSon;
    }

    public boolean getType() {
        return type;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setLeftSon(Node node) {
        this.leftSon = node;
    }

    public void setRightSon(Node node) {
        this.rightSon = node;
    }
}
