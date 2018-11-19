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

  public WordSearch(int rows,int cols, String filename) throws FileNotFoundException, NumberFormatException{
  	data = new char[rows][cols];
  	for (int i = 0; i < rows; i++) {
  		for (int j = 0; j < cols; j++) {
  			data[i][j] = '_';
  		}
  	}
		seed = ((int)System.currentTimeMillis()) % 10001;
		randgen = new Random(seed);
		wordsToAdd = new ArrayList<>();
		wordsAdded = new ArrayList<>();

		File f = new File(filename);
		Scanner in = new Scanner(f);
		while (in.hasNext()) {
			String line = in.next();
			wordsToAdd.add(line.toUpperCase());
		}
		addAllWords();
		addLetters();
  }

	public WordSearch(int rows, int cols, String filename, int randSeed) throws FileNotFoundException, NumberFormatException{
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
			wordsToAdd.add(line.toUpperCase());
		}
		addAllWords();
		addLetters();
	}

	public WordSearch(int rows, int cols, String filename, int randSeed, String answers) throws FileNotFoundException, NumberFormatException{
		data = new char[rows][cols];
  	for (int i = 0; i < rows; i++) {
  		for (int j = 0; j < cols; j++) {
  			if (answers.equals("key")) {
  				data[i][j] = ' ';
  			} else {
  				data[i][j] = '_'; 
  			}
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
			wordsToAdd.add(line.toUpperCase());
		}
		addAllWords();
		if (!answers.equals("key")) addLetters();
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

  	result+= "Words added: ";
		for (int j = 0; j < wordsAdded.size(); j++) {
			result += wordsAdded.get(j);
			if (j < wordsAdded.size() - 1) result+= ", ";
		}

		result = result+" (seed: "+seed+")\n";

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
  private boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
    if ((word.length()*rowIncrement) + row > data.length ||
    		(word.length()*colIncrement) + col > data[0].length ||
    		(word.length()*rowIncrement) + row < 0 ||
    		(word.length()*colIncrement) + col < 0 ||
				(rowIncrement == 0 && colIncrement == 0)) return false;
    	//also return false if word doesnt fit backwards
    for (int i = 0; i < word.length(); i++) {
    	if (data[row + (i*rowIncrement)][col + (i*colIncrement)] != '_' &&
    			data[row + (i*rowIncrement)][col + (i*colIncrement)] != ' ' &&
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
  	for (int i = 0; i < wordsToAdd.size();){
			int rowInc = randgen.nextInt() % 2;
			int colInc = randgen.nextInt() % 2;
			int tries = 20;
			int row = Math.abs(randgen.nextInt() % (data.length - 1));
			int col = Math.abs(randgen.nextInt() % (data[0].length - 1));
			while (tries > 0 && !addWord(wordsToAdd.get(i), row, col, rowInc, colInc)) {
				//System.out.println(addWord(wordsToAdd.get(i), row, col, rowInc, colInc));
				row = Math.abs(randgen.nextInt() % (data.length - 1));
				col = Math.abs(randgen.nextInt() % (data[0].length - 1));
				tries--;
			}
			if (addWord(wordsToAdd.get(i), row, col, rowInc, colInc)) {
				wordsAdded.add(wordsToAdd.get(i));
			}
			wordsToAdd.remove(i);
  	}
  }

  private void addLetters() {
		int range = (int)(Math.random());
  	for (int i = 0; i < data.length; i++) {
  		for (int j = 0; j < data[0].length; j++) {
  			if (data[i][j] == '_' || data[i][j] == ' ') data[i][j] = ((char)('A' + (Math.abs(randgen.nextInt() % 26))));
  		}
  	}
  }

  public static void main(String[] args) {
  	//System.out.println(Arrays.toString(args));
  	int row = 0;
  	int col = 0;
  	String filename = "";
  	int randSeed = 0;
  	String answers = "";
		WordSearch puzzle = null;

  	if (args.length > 2) {
  		try {
  			row = Integer.parseInt(args[0]);
  			col = Integer.parseInt(args[1]);
  		} catch (NumberFormatException e) {
  			System.out.println("=============");
  			System.out.println("Something went wrong: improper number formatting. Please check to make sure row and col are integers.");
  			System.out.println("=============");
  			System.exit(1);
  		}

  		filename = args[2];

  		if (row <= 0 || col <= 0) {
  			System.out.println("=============");
  			System.out.println("Something went wrong: either your row or col (or both) is out of bounds. Please check to make sure that your row and col are both greater than 0. ");
  			System.out.println("=============");
  			System.exit(1);
  		}
  		else if (args.length > 3) {
  			try {
  				randSeed = Integer.parseInt(args[3]);
  			} catch (NumberFormatException e) {
  				System.out.println("=============");
  				System.out.println("Something went wrong: improper number formatting. Please check to make sure randSeed is an integer.");
  				System.out.println("=============");
  				System.exit(1);
  			}
  			if (randSeed < 0 || randSeed > 10000) {
  				System.out.println("=============");
  				System.out.println("Something went wrong: randSeed is out of bounds. Please check to make sure that your seed is between 0 and 10,000 (inclusive).");
  				System.out.println("=============");
  				System.exit(1);
  			}
  			
  			else if (args.length > 4) {
  				answers = args[4];
  				try {
      			puzzle = new WordSearch(row, col, filename, randSeed, answers);
      			System.out.println(puzzle);
    			} catch (FileNotFoundException e) {
    				System.out.println("=============");
  					System.out.println("Something went wrong: please enter a valid file name, "+args[2]+" does not seem to exist!");
  					System.out.println("=============");
    			}
  			}

  			else {
  				try {
      			puzzle = new WordSearch(row, col, filename, randSeed);
      			System.out.println(puzzle);
    			} catch (FileNotFoundException e) {
    				System.out.println("=============");
  					System.out.println("Something went wrong: please enter a valid file name, "+args[2]+" does not seem to exist!");
  					System.out.println("=============");
    			}
  			}
  		}

  		else {
  			try {
      		puzzle = new WordSearch(row, col, filename);
      		System.out.println(puzzle);
    		} catch (FileNotFoundException e) {
    			System.out.println("=============");
  				System.out.println("Something went wrong: please enter a valid file name, "+args[2]+" does not seem to exist!");
  				System.out.println("=============");
    		}
  		}
  	}
  	else {
  		System.out.println("=============");
  		System.out.println("Something went wrong: there must be at least 3 command line arguments to create the word search! That means at least 2 integers that define the size of the puzzle and the name of the file that the words in the puzzle come from.");
  		System.out.println("Ex: $java WordSearch 20 20 words.txt");
  		System.out.println("\nIn addition, you can also include an integer to determine the seed of the puzzle and a string, \"key\", which will trigger answer mode.");
  		System.out.println("Ex: $java WordSearch 20 20 words.txt 100 key");
  		System.out.println("=============");
  	}
  }

}
