import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");
    String[] patterns = lines[0].trim().split(", ");
    
    int possiblePatterns = 0;
    // i = 2 because patterns start at that index
    for (int i = 2; i < lines.length; i++) {
      String pattern = lines[i].trim();
      // System.out.println(pattern);
      if (isPossible(patterns, "", pattern, new HashMap<>())) possiblePatterns++;
    }
    System.out.println(possiblePatterns);
  }

  private static boolean isPossible(String[] patterns, String cur, String goal, HashMap<String, Boolean> cache) {
    if (cache.containsKey(cur)) return cache.get(cur);

    if (cur.length() > goal.length() || !goal.startsWith(cur)) {
      cache.put(cur, false);
      return false;
    }

    if (goal.equals(cur)) {
      cache.put(cur, true);
      return true;
    }

    for (String p : patterns) {
      if (isPossible(patterns, cur + p, goal, cache)) return true;
      else cache.put(cur, false);
    }
    return false;
  }
}
