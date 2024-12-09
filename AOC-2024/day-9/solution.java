import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    long checksum = 0;
    int curId = 0;
    ArrayList<Integer> disk = new ArrayList<>();
    
    for (int i = 0; i < input.length(); i++) {
      int num = input.charAt(i) - '0'; // Works the same as parsing the character
      if (i % 2 == 0) {
        for (int j = 0; j < num; j++) {
          disk.add(curId);
        }
        curId++;
      } else {
        for (int j = 0; j < num; j++) {
          disk.add(-1);
        }
      }
    }

    int l = 0;
    int r = disk.size() - 1;
    while (l < r) {
      while (disk.get(r) == -1) r--;
      while (disk.get(l) != -1) l++;

      if (l < r) {
        disk.set(l, disk.get(r));
        disk.set(r, -1);
        r--;
        l++;
      }
    }

    for (int i = 0; i < disk.size(); i++) {
      if (disk.get(i) == -1) break;
      checksum += (long)(i * disk.get(i));
    }
    System.out.println(checksum);
  }
} 
