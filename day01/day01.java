import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class day01 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 01 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 01 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    boolean done = false;
    int tot = 0;
    int first = -1, last = -1;

    while (!done) {
      int ch = input.read();
      if (ch == -1) {
        done = true;
      } else if (ch == 10) {
        tot += (first * 10 + last);
        first = -1;
      } else if (ch >= 48 && ch <= 57) {
        if (first == -1) {
          first = ch - 48;
        }
        last = ch - 48;
      }
    }

    return Integer.toString(tot);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    Pattern pat = Pattern.compile("(0|1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)");

    int tot = 0;
    String line = input.readLine();
    while (line != null) {
      Matcher mat = pat.matcher(line);
      int first = -1, last = -1, end = 0;
      while(!mat.hitEnd()) {
        if (mat.find(end)) {
          int ths = parseInt(mat.group());
          if (first == -1) {
            first = ths;
          }
          last = ths;
          if (mat.end() - mat.start() == 1) {
            end = mat.end();
          } else {
            end = mat.end() - 1;
          }
        }
      }
      tot += (first * 10 + last);

      line = input.readLine();
    }

    return Integer.toString(tot);
  }

  private static int parseInt(String word) {
    int num = -1;
    switch (word) {
      case "0":
        num = 0;
        break;
      case "1":
      case "one":
        num = 1;
        break;
      case "2":
      case "two":
        num = 2;
        break;
      case "3":
      case "three":
        num = 3;
        break;
      case "4":
      case "four":
        num = 4;
        break;
      case "5":
      case "five":
        num = 5;
        break;
      case "6":
      case "six":
        num = 6;
        break;
      case "7":
      case "seven":
        num = 7;
        break;
      case "8":
      case "eight":
        num = 8;
        break;
      case "9":
      case "nine":
        num = 9;
        break;
    }
    return num;
  }
}
