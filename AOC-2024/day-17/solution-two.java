import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    ArrayList<Integer> program = new ArrayList<>();
    // Program always starts at index = 9
    for (String op : lines[4].substring(9).trim().split(",")) program.add(Integer.parseInt(op.trim()));
    String goal = listToString(program);

    long A = 0L;
    while (true) {
      String current = getOutput(program, A, 0L, 0L);
      if (current.equals(goal)) break;
      if (goal.endsWith(current)) A *= 8L;
      else A++;
    }

    System.out.println(A);
  }

  private static String getOutput(List<Integer> program, long A, long B, long C) {
    ArrayList<Long> output = new ArrayList<>();
    
    int programCounter = 0;
    while (programCounter < program.size()) {
      int operation = program.get(programCounter);
      int operand = program.get(programCounter + 1);

      long combo = operand;
      if (operand == 4) combo = A;
      else if (operand == 5) combo = B;
      else if (operand == 6) combo = C;

      switch (operation) {
        case 0:
          A = A / (int)(Math.pow(2, combo));
          break;
        case 1:
          B ^= operand;
          break;
        case 2:
          B = combo % 8;
          break;
        case 3:
          if (A != 0) programCounter = operand;
          else programCounter += 2;
          break;
        case 4:
          B ^= C;
          break;
        case 5:
          output.add(combo % 8);
          break;
        case 6:
          B = A / (int)(Math.pow(2, combo));
          break;
        case 7:
          C = A / (int)(Math.pow(2, combo));
          break;
        default:
          throw new Error("Unknown operation received");
      }
      if (operation != 3) programCounter += 2; // operation and operand 
    }
    return listToString(output);
  }

  static private String listToString(List<?> list) {
    StringBuilder sb = new StringBuilder();
    String delim = "";
    for (Object el : list) {
      sb.append(delim);
      sb.append(el);
      delim = ",";
    }
    return sb.toString();
  }
}
