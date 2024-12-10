import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("example.txt")).trim();
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

    while (curId >= 0) {
      // for (int n : disk) {
      //   if (n == -1) System.out.print(".");
      //   else System.out.print(n);
      // }
      // System.out.println();
      // System.out.println();

      int[] block_pos = findBlock(disk, curId);
      int block_size = block_pos[1] - block_pos[0] + 1;

      int free_space = 0; 
      int l = 0;
      while (l < block_pos[0]) {
        while (l < block_pos[0] && disk.get(l) != -1) l++;
        int start_l = l;
        while (disk.get(l) == -1) l++;
        free_space = l - start_l;
        if (free_space >= block_size) break;
      }

      curId--;
      if (free_space < block_size) continue;
      for (int i = 0; i < block_size; i++) {
        disk.set(l - free_space + i, disk.get(block_pos[0] + i));
        disk.set(block_pos[0] + i, -1);
      }
    }

    // for (int n : disk) {
    //   if (n == -1) System.out.print(".");
    //   else System.out.print(n);
    // }
    // System.out.println();


    for (int i = 0; i < disk.size(); i++) {
      if (disk.get(i) == -1) continue;
      checksum += (long)(i * disk.get(i));
    }
    System.out.println(checksum);
  }

  private static int[] findBlock(ArrayList<Integer> disk, int ID) {
    for (int i = 0; i < disk.size(); i++) {
      if (disk.get(i) != ID) continue;
      int start = i;
      while (i < disk.size() && disk.get(i) == disk.get(start)) i++;
      return new int[]{start, i - 1};
    }
    return new int[]{0, 0};
  }
}
