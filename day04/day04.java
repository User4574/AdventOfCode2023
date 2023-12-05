import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class day04 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 04 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 04 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    String line;
    int tot = 0;
    while ((line = input.readLine()) != null) {
      int points = cardMatches(parseCard(line));
      if (points > 0) {
        tot += 1 << (points - 1);
      }
    }

    return Integer.toString(tot);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    ArrayList<String[][]> cards = new ArrayList<>();
    LinkedList<Integer> queue = new LinkedList<>();

    String line;
    while ((line = input.readLine()) != null) {
      cards.add(parseCard(line));
    }

    for (int i = 0; i < cards.size(); i++) {
      queue.add(i);
    }

    int tot = 0;
    while (queue.size() > 0) {
      int id = queue.remove();
      int matches = cardMatches(cards.get(id));
      for (int i = id + 1; i < id + matches + 1; i++) {
        queue.add(i);
      }
      tot += 1;
    }

    return Integer.toString(tot);
  }

  private static String[][] parseCard(String line) {
    String[] linea = line.split(": +");
    String[] card = linea[1].split(" \\| +");
    String[] neednums = card[0].split(" +");
    String[] havenums = card[1].split(" +");
    return new String[][]{neednums, havenums};
  }

  private static int cardMatches(String[][] card) {
    HashSet<String> winnums = new HashSet<>(Arrays.asList(card[1]));
    winnums.retainAll(Arrays.asList(card[0]));
    return winnums.size();
  }
}
