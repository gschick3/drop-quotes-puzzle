package puzzle;
import java.util.Arrays;
import java.util.Random;

class Array {
	/** Return 2d array given 1d array and number of rows */
	public static char[][] dimensionalize(char[] paddedArray, int rows) {
		int columns = (paddedArray.length + (rows - 1)) / rows; // number of characters in each row (includes
																// whitespace)

		char[][] newArray = new char[rows][columns]; // new multidimensional array

		int start = 0;
		for (int i = 0; i < newArray.length; i++) {
			System.arraycopy(paddedArray, start, newArray[i], 0, columns); // add segments of 1d array to 2d array
			start += columns;
		}
		return newArray;
	}

	public static char[] joinArrays(char[] array1, char[] array2) {
		// currently unused
		
		char[] newArray = new char[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static char[][] transposeArray(char[][] array) {
		char[][] newArray = new char[array[0].length][array.length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++)
				newArray[j][i] = array[i][j];
		}
		return newArray;
	}

	/** Randomizes order of elements within inner arrays of 2D array */
	public static char[][] shuffleArray(char[][] array) {
		// currently unused

		Random rand = new Random();

		for (int r = 0; r < array.length; r++) {
			for (int c = array[0].length - 1; c > 0; c--) {
				int flipWith = rand.nextInt(c + 1); // array element to flip current element with
				char tmpFlipWith = array[r][flipWith];
				array[r][flipWith] = array[r][c]; // flipWith <- current
				array[r][c] = tmpFlipWith; // current <- tmp
			}
		}
		return array;
	}

	public static char[][] sortArray(char[][] array) {
		for (char[] row : array) {
			Arrays.sort(row);
		}
		return array;
	}
}