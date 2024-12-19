import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");
    String[] patterns = lines[0].trim().split(", ");
    
    long totalWays = 0;
    // i = 2 because patterns start at that index
    for (int i = 2; i < lines.length; i++) {
      String pattern = lines[i].trim();
      totalWays += possibleWays(patterns, "", pattern, new HashMap<>());
    }
    System.out.println(totalWays);
  }

  private static long possibleWays(String[] patterns, String cur, String goal, HashMap<String, Long> cache) {
    long count = 0;
    if (cache.containsKey(cur)) return cache.get(cur);

    if (cur.length() > goal.length() || !goal.startsWith(cur)) {
      return 0;
    }

    if (goal.equals(cur)) {
      return 1;
    }

    for (String p : patterns) {
      long ret = possibleWays(patterns, cur + p, goal, cache);
      count += ret; 
      cache.put(cur, cache.getOrDefault(cur, 0L) + ret);
    }
    return count;
  }
}
