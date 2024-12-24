import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    List<List<String>> finishedOperations = new ArrayList<>();

    for (List<String> op : operations) {
      if (finishedOperations.contains(op)) continue;
      performOperation(op, wireMap, operations, finishedOperations);
    }

    List<Map.Entry<String, Integer>> zEntries = wireMap.entrySet()
      .stream()
      .filter(kvp -> kvp.getKey().startsWith("z"))
      .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
      .toList();
    int[] bits = zEntries.stream().mapToInt(kvp -> kvp.getValue()).toArray();
    long output = 0;
    for (int i = 0; i < bits.length; i++) {
      if (bits[i] == 1) output += Math.pow(2, i);
    }
    System.out.println(output);
  }

  private static void performOperation(List<String> operation, HashMap<String, Integer> wireMap, List<List<String>> allOperations, List<List<String>> finishedOperations) {
    if (!wireMap.containsKey(operation.get(0))) {
      List<String> missingOperation = allOperations.stream().filter(l -> l.get(3).equals(operation.get(0))).findFirst().get();
      performOperation(missingOperation, wireMap, allOperations, finishedOperations);
      finishedOperations.add(missingOperation);
    }

    if (!wireMap.containsKey(operation.get(2))) {
      List<String> missingOperation = allOperations.stream().filter(l -> l.get(3).equals(operation.get(2))).findFirst().get();
      performOperation(missingOperation, wireMap, allOperations, finishedOperations);
      finishedOperations.add(missingOperation);
    }

    int result = 0;
    int l = wireMap.get(operation.get(0));
    int r = wireMap.get(operation.get(2));

    if (operation.get(1).equals("AND")) result = l & r;
    else if (operation.get(1).equals("OR")) result = l | r;
    else result = l ^ r;

    wireMap.put(operation.get(3), result);
    finishedOperations.add(operation);
  }
}
