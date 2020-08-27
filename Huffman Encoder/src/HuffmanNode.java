import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
	public HuffmanNode left;
	public HuffmanNode right;

	// the integer value of the character
	public int value;

	// the frequency of the character
	public int freq;

	/**
	 * This is the constructor for a leaf node.
	 * 
	 * @param value the integer value of the character
	 * @param freq  the frequency of the character
	 */
	public HuffmanNode(int value, int freq) {
		this.value = value;
		this.freq = freq;
	}

	/**
	 * This constructor constructs a HuffmanNode object for a non-leaf node. All
	 * non-leaf nodes will have a value of -1. This is how we will know which nodes
	 * are leaf nodes.
	 * 
	 * @param freq  The added frequency of the nodes below this node
	 * @param left  The left node
	 * @param right The right node
	 */
	public HuffmanNode(int freq, HuffmanNode left, HuffmanNode right) {
		this.freq = freq;
		this.left = left;
		this.right = right;
		this.value = -1;
	}

	/**
	 * This method implements the compareTo method from the comparable interface. If
	 * this object is "less" than the node passed in as a parameter, the method will
	 * return -1. If it is greater it will return 1, if they are equal it will
	 * return 0.
	 */
	public int compareTo(HuffmanNode node) {
		// if this object is less, return -1
		if (node.freq > this.freq) {
			return -1;
		}
		// if this object is greater, return 1
		if (node.freq < this.freq) {
			return 1;
		}
		// if the objects are equal, return 0
		return 0;
	}
}
