import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    
    long secretSum = 0;
    for (String n : input.split("\n")) {
      long secret = Long.parseLong(n.trim());
      for (int i = 0; i < 2000; i++) {
        secret = ((secret * 64) ^ secret) % 16777216;
        secret = ((secret / 32) ^ secret) % 16777216;
        secret = ((secret * 2048) ^ secret) % 16777216;
      }
      // System.out.printf("%s: %d\n", n.trim(), secret);
      secretSum += (long)secret;
    }
    System.out.println(secretSum);
  }
}
