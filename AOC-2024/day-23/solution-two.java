import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    List<HashSet<String>> cliques = new ArrayList<>();
    BronKerbosch(new HashSet<>(), new HashSet<>(connectionMap.keySet()), new HashSet<>(), connectionMap, cliques);
    ArrayList<String> maxClique = new ArrayList<>(cliques.stream().max(Comparator.comparingInt(a -> a.size())).orElseThrow());
    Collections.sort(maxClique);
    System.out.println(String.join(",", maxClique));
  }

  private static void BronKerbosch(
    HashSet<String> R,
    HashSet<String> P,
    HashSet<String> X,
    HashMap<String, HashSet<String>> connections,
    List<HashSet<String>> cliques
  ) {
    if (P.isEmpty() && X.isEmpty()) {
      cliques.add(new HashSet<>(R));
      return;
    }
    
    for (String v : new HashSet<>(P)) {
      R.add(v);
      BronKerbosch(R, intersect(P, connections.get(v)), intersect(X, connections.get(v)), connections, cliques);
      R.remove(v);
      P.remove(v);
      X.add(v);
    }
  }

  private static HashSet<String> intersect(HashSet<String> one, HashSet<String> two) {
    HashSet<String> cpy = new HashSet<>(one);
    cpy.retainAll(two);
    return cpy;
  }
}
