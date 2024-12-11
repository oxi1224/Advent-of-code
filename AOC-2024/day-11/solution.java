import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Solution {
  public static final int BLINKS = 25; 

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();

    ArrayList<Long> stones = new ArrayList<>();
    for (String n : input.split(" ")) stones.add(Long.parseLong(n));

    for (int b = 0; b < BLINKS; b++) {
      for (int i = 0; i < stones.size(); i++) {
        long stone = stones.get(i);
        if (stone == 0) {
          stones.set(i, 1L);
        } else if (countDigits(stone) % 2 == 0) {
          String firstHalf = ""; 
          String secondHalf = "";
          int digitCount = countDigits(stone);

          for (int j = 0; j < digitCount; j++) {
            if (j < digitCount / 2) {
              secondHalf = secondHalf + (stone % 10);
            } else {
              firstHalf = firstHalf + (stone % 10);
            }
            stone = (long)Math.floor(stone / 10);
          }
          stones.set(i, Long.parseLong(new StringBuilder(firstHalf).reverse().toString()));
          stones.add(i + 1, Long.parseLong(new StringBuilder(secondHalf).reverse().toString()));
          i++;
        } else {
          stones.set(i, stone * 2024);
        }
      }
    }
    
    System.out.println(stones.size());
  }
  
  public static int countDigits(long n) {
    int c = 0;
    while (n > 0) {
      n = (long)Math.floor(n / 10);
      c++;
    }
    return c;
  }
}
