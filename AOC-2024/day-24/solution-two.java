import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    HashMap<String, Integer> wireMap = new HashMap<>();
    // List<String> -> [wireA, operation, wireB, outputWire]
    List<List<String>> operations = new ArrayList<>();
    boolean readingWires = true;
     
    for (String line : input.split("\n")) {
      line = line.trim();

      if (readingWires && line.isBlank()) {
        readingWires = false;
        continue;
      }

      if (readingWires) {
        String wire = line.substring(0, line.indexOf(':'));
        int signal = Integer.parseInt(line.substring(line.indexOf(' ') + 1));
        wireMap.put(wire, signal);
      }

      if (readingWires) continue;
      String[] split = line.split(" ");
      operations.add(Arrays.asList(split[0], split[1], split[2], split[4]));
    }
    
    List<String> faulty = new ArrayList<>();
    for (List<String> op : operations) {
      String left = op.get(0);
      String operator = op.get(1);
      String right = op.get(2);
      String out = op.get(3);
      
      // Found on reddit, from https://www.reddit.com/user/ash30342/
      /**
       * The outputs ARE faulty when:
       *  a) If output wire is z but not the last one (45) and the operator IS NOT XOR
       *  b) If output wire is NOT z and the inputs are NOT x and y and operator IS XOR
       *  c) If inputs ARE x and y but not first (both) and the operator IS XOR,
       *     but the output wire IS NOT an input of another XOR
       *  d) If inputs ARE x and y but not first (both) and the operator IS AND,
       *     but the output wire IS NOT an input of an OR 
       */
      if (
        out.startsWith("z") &&
        !out.endsWith("z45") &&
        !operator.equals("XOR")
      ) faulty.add(out);
      else if (
        !out.startsWith("z") &&
        !(left.startsWith("y") || left.startsWith("x")) &&
        !(right.startsWith("y") || right.startsWith("x")) &&
        operator.equals("XOR")
      ) faulty.add(out);
      else if (
        (left.startsWith("y") || left.startsWith("x")) &&
        (right.startsWith("y") || right.startsWith("x")) &&
        !left.endsWith("00") && !right.endsWith("00") &&
        operator.equals("XOR")
      ) {
        List<String> isInput = operations
          .stream()
          .filter(l -> (l.get(0).equals(out) || l.get(2).equals(out)) && !l.equals(op) && l.get(1).equals("XOR"))
          .findFirst()
          .orElse(null);
        if (isInput == null) faulty.add(out);
      } else if (
        (left.startsWith("y") || left.startsWith("x")) &&
        (right.startsWith("y") || right.startsWith("x")) &&
        !left.endsWith("00") && !right.endsWith("00") &&
        operator.equals("AND")
      ) {
        List<String> isInput = operations
          .stream()
          .filter(l -> (l.get(0).equals(out) || l.get(2).equals(out)) && !l.equals(op) && l.get(1).equals("OR"))
          .findFirst()
          .orElse(null);
        if (isInput == null) faulty.add(out);
      }
    }
    Collections.sort(faulty);
    System.out.println(String.join(",", faulty));
  }
}
