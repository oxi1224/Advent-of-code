import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");
    // changes -> how many bananas saved
    HashMap<List<Integer>, Integer> savingsMap = new HashMap<>();

    for (int i = 0; i < lines.length; i++) {
      long secret = Long.parseLong(lines[i].trim());
      HashSet<List<Integer>> usedPatterns = new HashSet<>();
      
      Deque<Integer> changes = new ArrayDeque<>(4);
      int prevPrice = (int)(secret % 10);
      for (int j = 0; j < 2000; j++) {
        secret = ((secret * 64) ^ secret) % 16777216;
        secret = ((secret / 32) ^ secret) % 16777216;
        secret = ((secret * 2048) ^ secret) % 16777216;

        int curPrice = (int)(secret % 10);
        int diff = curPrice - prevPrice;
        prevPrice = curPrice;

        // Must be atleast 4 changes
        if (j < 4) {
          changes.add(diff);
          continue;
        }
        changes.removeFirst();
        changes.addLast(diff);

        List<Integer> pattern = List.copyOf(changes);
        if (!usedPatterns.contains(pattern)) savingsMap.put(pattern, savingsMap.getOrDefault(pattern, 0) + curPrice);
        usedPatterns.add(pattern);
        prevPrice = curPrice;
      }
    }
    
    Map.Entry<List<Integer>, Integer> max = savingsMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
    System.out.printf("%s: %d", max.getKey().toString(), max.getValue());
  }
}
