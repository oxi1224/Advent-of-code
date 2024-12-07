import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    Long totalResult = 0L;
    
    for (String line : input.split("\n")) {
      String[] parts = line.split(":");
      long result = Long.parseLong(parts[0].trim());
      ArrayList<Long> numbers = new ArrayList<>();
      for (String n : parts[1].trim().split(" ")) numbers.add(Long.parseLong(n.trim()));

      if (isValid(numbers, result, 0, numbers.get(0))) totalResult += result;
    }

    System.out.println(totalResult);
  }

  private static boolean isValid(ArrayList<Long> numbers, long goal, int index, long cur) {
    if (index == numbers.size() - 1) return cur == goal;
    if (isValid(numbers, goal, index + 1, cur + numbers.get(index + 1))) return true;
    if (isValid(numbers, goal, index + 1, cur * numbers.get(index + 1))) return true;
    if (isValid(numbers, goal, index + 1, Long.parseLong(Long.toString(cur) + Long.toString(numbers.get(index + 1))))) return true;
    return false;
  }
}
