import java.util.*;
import java.io.*;

public class WordSearch {
	private char[][]data;
	private int seed;
	private Random randgen;
	private ArrayList<String> wordsToAdd;
	private ArrayList<String> wordsAdded;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
  public WordSearch() throws FileNotFoundException{
  	data = new char[10][10];
  	for (int i = 0; i < data.length; i++) {
  		for (int j = 0; j < data[0].length; j++) {
  			data[i][j] = '_';
  		}
  	}
		seed = (int)System.currentTimeMillis();
		randgen = new Random(seed);
		wordsToAdd = new ArrayList<>();
		wordsAdded = new ArrayList<>();

		File f = new File("words.txt");
		Scanner in = new Scanner(f);
		while (in.hasNext()) {
			String line = in.next();
			wordsToAdd.add(line);
		}
		System.out.println(""+wordsToAdd.size());
		addAllWords();
  }

  public WordSearch(int rows,int cols, String filename) throws FileNotFoundException{
  	data = new char[rows][cols];
  	for (int i = 0; i < rows; i++) {
  		for (int j = 0; j < cols; j++) {
  			data[i][j] = '_';
  		}
  	}
		seed = (int)System.currentTimeMillis();
		randgen = new Random(seed);
		wordsToAdd = new ArrayList<>();
		wordsAdded = new ArrayList<>();

		File f = new File(filename);
		Scanner in = new Scanner(f);
		while (in.hasNext()) {
			String line = in.next();
			wordsToAdd.add(line);
		}
		System.out.println(""+wordsToAdd.size());
		addAllWords();
  }

	public WordSearch(int rows, int cols, String filename, int randSeed) throws FileNotFoundException{
		data = new char[rows][cols];
  	for (int i = 0; i < rows; i++) {
  		for (int j = 0; j < cols; j++) {
  			data[i][j] = '_';
  		}
  	}
		seed = randSeed;
		randgen = new Random(seed);
		wordsToAdd = new ArrayList<>();
		wordsAdded = new ArrayList<>();

		File f = new File(filename);
		Scanner in = new Scanner(f);
		while (in.hasNext()) {
			String line = in.next();
			wordsToAdd.add(line);
		}
		addAllWords();
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
			result+= "|";
  		for (int j = 0; j < data[i].length; j++) {
  			result +=data[i][j];
  			if (j < data[i].length - 1) result+= " ";
  		}
  		result+= "|\n";
  	}
  	result+= "Words: \n";
		for (int j = 0; j < wordsAdded.size(); j++) {
			result += wordsAdded.get(j);
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
  private boolean addWordHorizontal(String word,int row, int col){
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
  private boolean addWordVertical(String word,int row, int col){
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
  private boolean addWordDiagonal(String word,int row, int col){
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

  /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added in the direction rowIncrement,colIncrement
     *Words must have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
     *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
     *@return true when: the word is added successfully.
     *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
     *        OR there are overlapping letters that do not match
     */
  public boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
    if (row < 0 ||
    		col < 0 ||
    		row >= data.length ||
    		col >= data[0].length ||
    		word.length() + row > data.length ||
   			word.length() + col > data[row].length) return false;
    	//also return false if word doesnt fit backwards
    for (int i = 0; i < word.length(); i++) {
    	if (data[row + (i*rowIncrement)][col + (i*colIncrement)] != '_' &&
    			data[row + (i*rowIncrement)][col + (i*colIncrement)] != word.charAt(i)) return false;
    }
    for (int i = 0; i < word.length(); i++) {
   		data[row + (i*rowIncrement)][col + (i*colIncrement)] = word.charAt(i);
   	}
   	return true;
  } //addWord should be private

    /*[rowIncrement,colIncrement] examples:
     *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
     *[ 1,0] would add downwards because (row+1), with no col change
     *[ 0,-1] would add towards the left because (col - 1), with no row change
     */
  private void addAllWords() {
  	
  }

}
