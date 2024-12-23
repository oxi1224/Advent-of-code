import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();

    HashMap<String, HashSet<String>> connectionMap = new HashMap<>();
    for (String line : input.split("\n")) {
      line = line.trim();
      String l = line.substring(0, line.indexOf("-"));
      String r = line.substring(line.indexOf("-") + 1);
      
      connectionMap.putIfAbsent(l, new HashSet<>());
      connectionMap.putIfAbsent(r, new HashSet<>());
      connectionMap.get(l).add(r);
      connectionMap.get(r).add(l);
    }
   
    HashSet<List<String>> connectionSets = new HashSet<>();
    for (String a : connectionMap.keySet()) {
      for (String b : connectionMap.get(a)) {
        if (a.compareTo(b) >= 0) continue;
        for (String c : connectionMap.get(b)) {
          if (b.compareTo(c) >= 0 || !connectionMap.get(c).contains(a)) continue;
          connectionSets.add(Arrays.asList(a, b, c));
        }
      }
    }
    
    List<String> chiefConnections = connectionMap.keySet().stream().filter(con -> con.startsWith("t")).toList();
    System.out.println(
      connectionSets
        .stream()
        .filter(triple -> triple.stream().anyMatch(con -> chiefConnections.contains(con)))
        .toList()
        .size()
    );
  }
}
