package puzzle;

import java.util.Arrays;

class Array {
	/** Return 2d array given 1d array and number of rows */
	public static char[][] dimensionalize(char[] paddedArray, int rows) {
		int columns = (paddedArray.length + (rows - 1)) / rows; // number of characters in each row (includes
																// whitespace)

		char[][] newArray = new char[rows][columns]; // new multidimensional array

		int start = 0;
		for (char[] chars : newArray) {
			System.arraycopy(paddedArray, start, chars, 0, columns); // add segments of 1d array to 2d array
			start += columns;
		}
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

	public static char[][] sortArray(char[][] array) {
		for (char[] row : array) {
			Arrays.sort(row);
		}
		return array;
	}
}