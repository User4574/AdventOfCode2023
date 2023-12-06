import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class day06 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 06 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 06 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    ArrayList<Integer[]> races = new ArrayList<>();
    Pattern digits = Pattern.compile("\\d+");
    Matcher times = digits.matcher(input.readLine());
    Matcher distances = digits.matcher(input.readLine());

    while (times.find()) {
      distances.find();
      races.add(new Integer[]{
        Integer.parseInt(times.group()),
        Integer.parseInt(distances.group())
      });
    }

    int margin = 1;
    for (Integer[] race: races) {
      int wins = 0;
      for (int hold = 1; hold < race[0]; hold++) {
        int dist = (race[0] - hold) * hold;
        if (dist > race[1]) {
          wins += 1;
        }
      }
      margin *= wins;
    }

    return Integer.toString(margin);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    long time = Long.parseLong(input.readLine().replaceAll(" ", "").substring(5));
    long distance = Long.parseLong(input.readLine().replaceAll(" ", "").substring(9));

    int wins = 0;
    for (long hold = 1; hold < time; hold++) {
      long dist = (time - hold) * hold;
      if (dist > distance) {
        wins += 1;
      }
    }

    return Integer.toString(wins);
  }
}
