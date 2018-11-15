import java.util.*;
import java.io.*;

public class Tester {
  public static void main(String[]args) {
    int seed = (int)(Math.random()*100000);
    Random randgen = new Random(seed);
    for (int i = 0; i < 10; i++) {
      System.out.println(randgen.nextInt() % 2);
    }
  }
}
