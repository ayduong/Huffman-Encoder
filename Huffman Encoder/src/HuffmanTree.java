
/**
 * Andrew Duong
 * Huffman Encoder Project
 */


import java.io.*;
import java.util.*;

public class HuffmanTree {
	private HuffmanNode root;

	/**
	 * The constructor for part 1. This takes an array of ints that represent the
	 * frequencies of the corresponding characters of a text file. The constructor
	 * will make a HuffmanNode for all characters that appear at least once and
	 * place them in a PriorityQueue. The method buildTree will then be called to
	 * build the tree.
	 * 
	 * @param count the array of frequencies of characters in a text file.
	 */
	public HuffmanTree(int[] count) {
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
		int max = -1;

		// insert all the characters into the PriorityQueue
		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) {
				HuffmanNode current = new HuffmanNode(i, count[i]);
				queue.add(current);
				if (max < i) {
					max = i;
				}
			}
		}
		HuffmanNode endOfFile = new HuffmanNode(count.length, 1);
		queue.add(endOfFile);
		buildTree(queue);
	}

	/**
	 * The constructor for part 2. This takes a Scanner for a file that has a tree
	 * in standard form. For each pair of lines, the method calls the buildTree
	 * method to put the node in the tree at the right spot.
	 * 
	 * @param input The scanner that reads a file with a tree in standard format
	 */
	public HuffmanTree(Scanner input) {
		root = new HuffmanNode(-1, 0);
		while (input.hasNext()) {
			int code = Integer.parseInt(input.nextLine());
			String path = input.nextLine();
			buildTree(code, path);
		}
	}

	/**
	 * This method takes a path and places a node in the tree based on the path. If
	 * the nodes on the path do not exist, the method will make the nodes.
	 * 
	 * @param code this is the integer code of the node that will be created at the
	 *             end of the path
	 * @param path this is the path of 1's and 0's that will be used to place the
	 *             node in the right place.
	 */
	private void buildTree(int code, String path) {
		HuffmanNode current = root;

		// while there are at least two more steps in the path
		while (path.length() > 1) {
			// use the path to determine which way to go. If the next node doesn't exist, a
			// node is made with the arbitrary values of -1 and 0
			if (path.substring(0, 1).equals("0")) {
				if (current.left == null) {
					current.left = new HuffmanNode(-1, 0);
				}
				current = current.left;
			} else {
				if (current.right == null) {
					current.right = new HuffmanNode(-1, 0);
				}
				current = current.right;
			}
			// path is updated to not include the last step
			path = path.substring(1);
		}
		// place the node on the right side
		if (path.equals("0")) {
			current.left = new HuffmanNode(code, 1);
		} else {
			current.right = new HuffmanNode(code, 1);
		}
	}

	/**
	 * This method builds the tree from a PriorityQueue. This is the ethod used in
	 * part 1 of the project.
	 * 
	 * @param queue the queue that contains all of the nodes
	 */
	private void buildTree(PriorityQueue<HuffmanNode> queue) {
		do {
			// take two nodes, combine them, and add them back into the queue
			HuffmanNode left = queue.remove();
			HuffmanNode right = queue.remove();
			int freq = left.freq + right.freq;
			HuffmanNode current = new HuffmanNode(freq, left, right);
			queue.add(current);
		} while (queue.size() > 1);

		// after the code above, there will be only one node, which is the root
		root = queue.remove();
	}

	/**
	 * This file takes a code file and uses the HuffmanNode Tree to convert the code
	 * file into the original text.
	 * 
	 * @param input  This object will be used to return the next bit from the code
	 *               file
	 * @param output This will write the characters out to the output file
	 * @param eof    the end of file int that signifies when the file has ended. it
	 *               is not written out to the output
	 */
	public void decode(BitInputStream input, PrintStream output, int eof) {
		HuffmanNode current = root;
		int code = -1;

		// while we still haven't reached the end of file
		while (code != eof) {
			int bit = input.readBit();
			if (bit == 0) {
				current = current.left;
			} else if (bit == 1) {
				current = current.right;
			}
			code = current.value;

			// since only leaf nodes have values above zero, this is how we check if a node
			// is a leaf node.
			if (code > 0) {
				output.write(code);
				current = root;
			}

		}
	}

	/**
	 * This method calls the helper method to write the tree to the output file
	 * 
	 * @param output will write to the output file
	 */
	public void write(PrintStream output) {
		write(root, "", output);
	}

	/**
	 * This method uses recursion to write out the tree to the output file in
	 * standard format.
	 * 
	 * @param node   the HuffmanNode that will be checked
	 * @param path   the path of 1's and 0's that will be written to the output file
	 *               if the node is a leaf node
	 * @param output the PrintStream object that will be used to write to the output
	 *               file
	 * @return
	 */
	private String write(HuffmanNode node, String path, PrintStream output) {
		if (node == null) {
			return null;
		}

		// if the node is a leaf node
		if (node.value > 0) {
			output.print(node.value + "\n");
			output.print(path + "\n");
			return null;
		}

		// continue down both sides of the tree, adding a 0 or 1 to the path
		write(node.left, path + "0", output);
		write(node.right, path + "1", output);
		return null;
	}

}
