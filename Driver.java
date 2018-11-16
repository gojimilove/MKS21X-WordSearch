public class Driver {

  public static void main(String[] args) {
    WordSearch puzzle = null;
    try {
      puzzle = new WordSearch(20, 20, "words.txt", 50);
      System.out.println(puzzle);
    } catch (Exception e) {

    }

  }

}
