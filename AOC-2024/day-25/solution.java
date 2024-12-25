import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    List<List<Integer>> keys = new ArrayList<>();
    List<List<Integer>> locks = new ArrayList<>();

    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      if (line.isBlank()) continue;
      
      List<Integer> heights = new ArrayList<>(List.of(0, 0, 0, 0, 0));
      for (int j = i; j < i + 7; j++) {
        char[] chars = lines[j].trim().toCharArray();
        for (int k = 0; k < chars.length; k++) {
          if (chars[k] == '.') continue;
          heights.set(k, heights.get(k) + 1);
        }
      }
      i += 6;
      
      if (line.equals("#####")) locks.add(heights);
      else keys.add(heights);
    }

    HashSet<List<Integer>> uniquePairs = new HashSet<>();
    for (List<Integer> key : keys) {
      for (List<Integer> lock : locks) {
        if (overlap(key, lock, 7)) continue;
        List<Integer> pair = new ArrayList<>();
        pair.addAll(key);
        pair.addAll(lock);
        uniquePairs.add(pair);
      }
    }
    System.out.println(uniquePairs.size());
  }

  private static boolean overlap(List<Integer> one, List<Integer> two, int threshold) {
    if (one.size() != two.size()) return true;
    for (int i = 0; i < one.size(); i++) {
      if (one.get(i) + two.get(i) > threshold) return true;
    }
    return false;
  }
}
