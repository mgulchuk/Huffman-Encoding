import java.util.Scanner;

/**
 * This class starts a small program that allows the
 * user to provide a string value which is then encoded
 * using the Huffman Encoding algorithm.
 *
 * @author Michael Gulchuk
 * @version 1.0
 */
public class HuffmanTest
{
    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final int MIN_BIT = 8;
    private static final int MAX_BIT = 16;
    private static String userString = "";

    /**
     * The entry point of the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)
    {
        welcomeUser();
        encodeAndDecodeString();
    }
    
    private static void welcomeUser()
    {
        //welcomes the user to the program...
        System.out.println("Welcome to my Huffman Encoding Program!");
        System.out.println("***************************************");
        System.out.println();
        System.out.println("Please enter a string: ");
        userString = CONSOLE.nextLine();
        System.out.println();
    }

    private static void encodeAndDecodeString()
    {
        //encodes and decodes the user input string...
        HuffmanEncoding huffTree = new HuffmanEncoding(userString);
        // print frequencies
        System.out.println("Character frequencies from text: ");
        huffTree.printCharacterFrequencies();
        System.out.println();
        // print encoding map
        System.out.println("Huffman encoding map for text: ");
        String encoded = huffTree.encode();
        System.out.println();
        // print out original string and bits
        System.out.println("Original text: " + userString);
        System.out.println("Original text length: " + (MIN_BIT * userString.length()) + " - " +
                (MAX_BIT * userString.length()) + " bits");
        System.out.println();
        // print encoded text
        System.out.println("Encoded text: " + encoded);
        System.out.println("Encoded text length: " + encoded.length());
        System.out.println();
        // print decoded text
        System.out.println("Decoded text: " + huffTree.decode(encoded));
    }

}

