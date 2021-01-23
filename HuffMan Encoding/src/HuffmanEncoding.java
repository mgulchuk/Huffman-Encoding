import java.util.*;
/**
 * Handles all calculations for interactions
 * with the Huffman encoding tree used in this
 * application
 *
 * @author Michael Gulchuk
 * @version 1.0
 */
public class HuffmanEncoding
{
    private HuffmanNode root;
    private final String sourceText;
    private final Map<Character, Double> frequencies = new TreeMap<>();
    private final Map<Character, String> encodingMap = new TreeMap<>();

    /**
     * Stores the source string to encode.
     * @param sourceText the source string
     */
    public HuffmanEncoding(String sourceText)
    {
        this.sourceText = sourceText;

        analyzeText();
    }

    private void analyzeText()
    {
        //calculate the frequency of characters in the source string
        for(int i = 0; i < sourceText.length(); i++){
            char chars = sourceText.charAt(i);
            if(!frequencies.containsKey(chars)){
                frequencies.put(chars, 0.0);
            }
            frequencies.put(chars, (frequencies.get(chars) + 1.0)/sourceText.length());
        }
    }

    /**
     * Takes the source string and generates a huffman encoding
     * tree, creating a binary encoding of the source string.
     * @return the binary encoding of the source string
     */
    public String encode()
    {
        //assemble the huffman encoding tree
        Queue<HuffmanNode> huffmanTree = new PriorityQueue<>();
        //generate the encoding map
        for (Map.Entry<Character, Double> tree: frequencies.entrySet()){
            HuffmanNode huffman = new HuffmanNode(tree.getKey(), tree.getValue());
            huffmanTree.add(huffman);
        }
        while(huffmanTree.size() > 1){
            // retrieves node
            HuffmanNode nodeOne = huffmanTree.peek();
            // remove node
            huffmanTree.poll();
            HuffmanNode nodeTwo = huffmanTree.peek();
            huffmanTree.poll();
            // Not sure what this does, linter warning cam up
            assert nodeTwo != null;
            // Construct a new node
            HuffmanNode treeMap = new HuffmanNode('\u0000', (nodeOne.getFrequency() + nodeTwo.getFrequency()),
                    nodeOne.getLeft(), nodeTwo.getRight());
            treeMap.left = nodeOne;
            treeMap.right = nodeTwo;
            // assign last node to root
            root = treeMap;
            huffmanTree.offer(treeMap);
        }
        String codes = "";
        encoding(root, codes);
        printEncodingMap();
        //return the encoding for the source string
        StringBuilder textCodes = new StringBuilder();
        int character = 0;
        while(character < sourceText.length()){
            char key = sourceText.charAt(character);
            textCodes.append(encodingMap.get(key));
            character++;
        }
        return textCodes.toString();
    }

    private void encoding(HuffmanNode leaf, String word){
        // base case
        if(leaf == null){
            return;
        }
        // check if there is a node
        if(leaf.right == null && leaf.left == null){
            encodingMap.put(leaf.data, word);
        }
        // recursive methods
        encoding(leaf.right, word + 0);
        encoding(leaf.left, word + 1);
    }

    /**
     * Uses the stored huffman encoding tree to decode the
     * input string.
     * @param encodedText a binary string to decode
     * @return the decoded string
     */
    public String decode(String encodedText)
    {
       StringBuilder text = new StringBuilder();
       HuffmanNode temp = root;
       for(int i = 0; i < encodedText.length(); i++){
           char str = encodedText.charAt(i);
           if(str == '0') {
               temp = temp.right;
           } else {
               temp = temp.left;
           }
           if(temp.right == null){
               text.append(temp.data);
               temp = root;
           }
       }
       return text.toString();
    }

    /**
     * Prints the frequency of characters in the source string
     */
    public void printCharacterFrequencies()
    {
        //print the frequencies of characters in the source string
        for(Map.Entry<Character, Double> pair : frequencies.entrySet()){
            System.out.println(pair.getKey() + " --> " + pair.getValue());
        }
    }

    /**
     * Prints the binary encodings determined by the huffman
     * encoding tree generated based
     */
    public void printEncodingMap()
    {
        //print the binary digits of characters in the encoding map
        for(Map.Entry<Character, String> pair : encodingMap.entrySet()){
            System.out.println(pair.getKey() + " --> " + pair.getValue());
        }
    }

    /**
     * This node class represents a huffman pair, with frequency and
     * character.
     * @author Michael Gulchuk
     * @version 1.0
     */
    public static class HuffmanNode implements Comparable<HuffmanNode>
    {
        private final char data;
        private final double frequency;
        private HuffmanNode left;
        private HuffmanNode right;

        //add constructor(s), getters/setters and toString() method

        @Override
        public String toString() {
            return "HuffmanNode{" +
                    "data=" + data +
                    ", frequency=" + frequency +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

        /**
         * @param data character of string
         * @param frequency frequency of characters
         */
        public HuffmanNode(char data, double frequency) {
            this.data = data;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

        /**
         * @param data character in string
         * @param frequency frequency of characters
         * @param left left Node in tree
         * @param right right Node in tree
         */
        public HuffmanNode(char data, double frequency, HuffmanNode left, HuffmanNode right) {
            this.data = data;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        /**
         * @return frequency of characters
         */
        public double getFrequency() {
            return frequency;
        }

        /**
         * @return left side of Huffman Node
         */
        public HuffmanNode getLeft() {
            return left;
        }

        /**
         * @return right side of Huffman Node
         */
        public HuffmanNode getRight() {
            return right;
        }

        /**
         * Compares two huffman nodes based on the frequency of the
         * nodes.
         * @param other the other node
         * @return a negative number if this frequency is smaller,
         * a positive number if this frequency is larger, or otherwise
         * zero
         */
        @Override
        public int compareTo(HuffmanNode other)
        {
            if((this.frequency - other.frequency) < 0){
                return -1;
            }else if((this.frequency - other.frequency) > 0){
                return 1;
            }else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "HuffmanEncoding{" +
                "root=" + root +
                ", sourceText='" + sourceText + '\'' +
                ", frequencies=" + frequencies +
                ", encodingMap=" + encodingMap +
                '}';
    }
}
