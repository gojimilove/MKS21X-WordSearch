public class Driver {

  public static void main(String[] args) {
    WordSearch puzzle = null;
    try {
      puzzle = new WordSearch(40, 40, "words.txt", 50, "key");
      System.out.println(puzzle);
    } catch (Exception e) {

    }
    
  }

}