import java.util.*;
import java.io.*;
public class WordSearch {
	private char[][]data;
	private int seed;
	private Random randgen;
	private ArrayList<String>wordsToAdd;
	private ArrayList<String>wordsAdded;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
  public WordSearch(int rows,int cols, String filename) {
  	data = new char[rows][cols];
  	for (int i = 0; i < rows; i++) {
  		for (int j = 0; j < cols; j++) {
  			data[i][j] = '_';
  		}
  	}

		// choose a randSeed using the clock random
		randgen = new Random();

		// read in the word text file
		File f = new File(filename);
		Scanner in = new Scanner(f);
		while (in.hasNext()) {
			String line = in.nextLine();
			wordsToAdd.add(line);
		}

		//addAllWords()
  }

	public WordSearch(int rows, int cols, String filename, int randSeed) {
		data = new char[rows][cols];
  	for (int i = 0; i < rows; i++) {
  		for (int j = 0; j < cols; j++) {
  			data[i][j] = '_';
  		}
  	}

		// read in the word text file
		// run addAllWords()
	}

    /**Set all values in the WordSearch to underscores'_'*/
  private void clear(){
  	for (int i = 0; i < data.length; i++) {
  		for (int j = 0; j < data[i].length; j++) {
  			data[i][j] = '_';
  		}
  	}
  }

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
  public String toString(){
  	String result = "";
  	for (int i = 0; i < data.length; i++) {
  		for (int j = 0; j < data[i].length; j++) {
  			result +=data[i][j];
  			if (j < data[i].length - 1) result+= " ";
  		}
  		if (i < data.length - 1) result+= "\n";
  	}
  	return result;
  }


    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */
  public boolean addWordHorizontal(String word,int row, int col){
  	if (row < 0 ||
  			col < 0 ||
  			row >= data.length ||
  			col >= data[0].length ||
  			word.length() + col > data[row].length) return false;
  	for (int i = 0; i < word.length(); i++) {
  		if (data[row][col + i] != '_' && word.charAt(i) != data[row][col + i]) return false;
  	}
  	for (int i = 0; i < word.length(); i++) {
  		data[row][col + i] = word.charAt(i);
  	}
  	return true;
  }

   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
  public boolean addWordVertical(String word,int row, int col){
  	if (row < 0 ||
  			col < 0 ||
  			row >= data.length ||
  			col >= data[0].length ||
  			word.length() + row > data.length) return false;
  	for (int i = 0; i < word.length(); i++) {
  		if (data[row + i][col] != '_' && word.charAt(i) != data[row + i][col]) return false;
  	}
  	for (int i = 0; i < word.length(); i++) {
  		data[row + i][col] = word.charAt(i);
  	}
  	return true;
  }

  	/**Attempts to add a given word to the specified position of the WordGrid.
   	*The word is added from top left to bottom right, must fit on the WordGrid,
   	*and must have a corresponding letter to match any letters that it overlaps.
   	*
   	*@param word is any text to be added to the word grid.
   	*@param row is the vertical locaiton of where you want the word to start.
   	*@param col is the horizontal location of where you want the word to start.
   	*@return true when the word is added successfully. When the word doesn't fit,
   	*or there are overlapping letters that do not match, then false is returned.
   	*/
  public boolean addWordDiagonal(String word,int row, int col){
   	if (row < 0 ||
   			col < 0 ||
   			row >= data.length ||
   			col >= data[0].length ||
   			word.length() + row > data.length ||
   			word.length() + col > data[row].length) return false;
   	for (int i = 0; i < word.length(); i++) {
   		if (data[row + i][col + i] != '_' && data[row + i][col + i] != word.charAt(i)) return false;
   	}
   	for (int i = 0; i < word.length(); i++) {
   		data[row + i][col + i] = word.charAt(i);
   	}
   	return true;
  }

}
