import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class day02 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 01 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 01 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    int tot = 0;
    String line;

    while ((line = input.readLine()) != null) {
      tot += gamePossible(line);
    }

    return Integer.toString(tot);
  }

  private static int gamePossible(String line) {
    Pattern idpat = Pattern.compile("Game (\\d+):");
    Pattern gamepat = Pattern.compile("(\\d+) (red|green|blue)(, (\\d+) (red|green|blue))*(;|\\Z)");
    Pattern drawpat = Pattern.compile("(\\d+) (red|green|blue)");

    Matcher idmat = idpat.matcher(line);
    idmat.find();
    int id = Integer.parseInt(idmat.group(1));

    boolean valid = true;

    Matcher gamemat = gamepat.matcher(line);
    while (gamemat.find()) {
      Matcher drawmat = drawpat.matcher(line.substring(gamemat.start(), gamemat.end()));
      while (drawmat.find()) {
        int num = Integer.parseInt(drawmat.group(1));
        switch (drawmat.group(2)) {
          case "red":
            if (num > 12) {
              valid = false;
            }
            break;
          case "green":
            if (num > 13) {
              valid = false;
            }
            break;
          case "blue":
            if (num > 14) {
              valid = false;
            }
        }
      }
    }

    return valid ? id : 0;
  }

  public static String part2(RandomAccessFile input) throws IOException {
    int tot = 0;
    String line;

    while ((line = input.readLine()) != null) {
      tot += gamePower(line);
    }

    return Integer.toString(tot);
  }

  private static int gamePower(String line) {
    Pattern idpat = Pattern.compile("Game (\\d+):");
    Pattern gamepat = Pattern.compile("(\\d+) (red|green|blue)(, (\\d+) (red|green|blue))*(;|\\Z)");
    Pattern drawpat = Pattern.compile("(\\d+) (red|green|blue)");

    Matcher idmat = idpat.matcher(line);
    idmat.find();
    int id = Integer.parseInt(idmat.group(1));

    int red = 0, green = 0, blue = 0;

    Matcher gamemat = gamepat.matcher(line);
    while (gamemat.find()) {
      Matcher drawmat = drawpat.matcher(line.substring(gamemat.start(), gamemat.end()));
      while (drawmat.find()) {
        int num = Integer.parseInt(drawmat.group(1));
        switch (drawmat.group(2)) {
          case "red":
            if (num > red) {
              red = num;
            }
            break;
          case "green":
            if (num > green) {
              green = num;
            }
            break;
          case "blue":
            if (num > blue) {
              blue = num;
            }
        }
      }
    }

    return red * blue * green;
  }
}
