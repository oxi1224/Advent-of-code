import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
  public static Pattern pattern = Pattern.compile("((do|don't)\\(\\)|mul\\(\\d+,\\d+\\))");

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    Matcher m = pattern.matcher(input);
    boolean enabled = true;
    int total = 0;
    while (m.find()) {
      String tok = m.group(1);
      if (tok.equals("do()")) {
        enabled = true;
      } else if (tok.equals("don't()")) {
        enabled = false;
      } else if (enabled) {
        int l = Integer.parseInt(tok.substring(tok.indexOf('(') + 1, tok.indexOf(',')));
        int r = Integer.parseInt(tok.substring(tok.indexOf(',') + 1, tok.indexOf(')')));
        total += l * r;
      }
    }
    System.out.println(total);
  }
}
