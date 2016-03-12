// Name: Tyler Gauntlett
// NID: ty340586
// Date: 2/28/2016

// Specification: Program must have file called "input.txt"
// in the same directory as the hillcipher.java file.

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class hillcipherFromFile implements Runnable {

	static final int MAXSIZE = 10000;
	static final int PRINTSIZE = 80;
	static final int CHAROFFSET = 97;

	public static void load() throws URISyntaxException, IOException {

		URL openKeyFile = hillcipherFromFile.class.getResource("fileInput.txt");
		Scanner sc = new Scanner(new File(openKeyFile.toURI()));
		
		// Init variables.
		StringBuffer sb = new StringBuffer();
		StringBuffer encryptedData = new StringBuffer();

		int matrix = sc.nextInt();

		// Initialize key.
		int[][] key = new int[matrix][matrix];
		String storeEncrpt = new String();

		// Read input into a 2D array
		for (int i = 0; i < matrix; i++) {
			for (int j = 0; j < matrix; j++) {
				int nextInput = sc.nextInt();

				key[i][j] = nextInput;

			}
		}

		// Scan in text file.
		while (sc.hasNext()) {
			sb.append(sc.next());
		}
		
		// Create a new string of text input.
		String text = new String();
		text = sb.toString();

		// Clear old string builder
		sb.delete(0, sb.length());

		// Turn everything to lower case.
		text = text.toLowerCase();

		// Remove all non alphabetical characters.
		text = siftChars(text);

		sb.append(text);

		// Find the number of padding chars required.
		int addZChars = text.length() % matrix;

		// Pad the end with 'x' so the matrix algebra
		// works properly.
		for (int i = 0; i < addZChars; i++)
			sb.append('x');

		// Store the newly padded string back into text.
		text = sb.toString();

		// Loop over sets of matrix substrings until the
		while (!text.isEmpty()) {

			// Store the first 0 to matrix values into a string.
			storeEncrpt = text.substring(0, matrix);

			// Remove the characters saved in storeEncrpt.
			text = text.substring(matrix);

			// Initialize temp with 0's.
			int[] temp = new int[matrix];
			for (int i = 0; i < matrix; i++)
				temp[i] = 0;

			// Loop to find the int values of the matrix algebra algorithm.
			int counter = 0;
			for (int i = 0; i < matrix; i++) {
				for (int j = 0; j < matrix; j++) {
					// Store the matrix multiplication and addition into a
					// single index,
					// offset by 97.
					temp[counter] += key[i][j] * ((int) (storeEncrpt.charAt(j)) - CHAROFFSET);
				}
				// Mod the index by 26 to find the alphabetical representation.
				temp[counter] = temp[counter] % 26;
				counter++;
			}
			counter = 0;
			// Increase each index of temp by 97.
			for (int i = 0; i < matrix; i++) {
				temp[i] = temp[i] + CHAROFFSET;
				encryptedData.append((char) temp[i]);
			}

		}

		printText(encryptedData.toString());
		
		sc.close();
	}

	// Remove non-alphabetical chars.
	public static String siftChars(String input) {

		input = input.replaceAll("[^a-z]", "");

		return input;
	}

	// Prints output into rows of 80.
	public static void printText(String input) {
		char[] temp = new char[input.length()];
		temp = input.toCharArray();

		for (int i = 1; i < temp.length + 1; i++) {

			System.out.print(temp[i-1]);

			if (i % 80 == 0)
				System.out.println();
		}
	}

	public static void main(String[] args) {

		try {
			load();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// Run command to run file from command line.
	@Override
	public void run() {
		main(null);
	}

}