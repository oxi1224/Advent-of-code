import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

class Solution {
  public static final int BLINKS = 75;

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();

    HashMap<String, Long> cache = new HashMap<>();
    long stoneCount = 0;
    for (String stone : input.split(" ")) stoneCount += blink(stone, 0, cache);

    System.out.println(stoneCount);
  }

  public static long blink(String stone, int blink, HashMap<String, Long> cache) {
    String key = stone + "," + blink;
    long result = 0;
    if (cache.containsKey(key)) return cache.get(key);

    if (blink == BLINKS) result = 1;
    else if (stone.equals("0")) result = blink("1", blink + 1, cache);
    else if (stone.length() % 2 == 0) {
      String firstStone = Long.toString(Long.parseLong(stone.substring(0, stone.length() / 2)));
      String secondStone = Long.toString(Long.parseLong(stone.substring(stone.length() / 2)));
      result = blink(firstStone, blink + 1, cache) + blink(secondStone, blink + 1, cache);
    } else {
      result = blink(Long.toString(Long.parseLong(stone) * 2024), blink + 1, cache);
    }

    cache.putIfAbsent(key, result);
    return result;
  }
}
