import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

public class day15 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 15 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 15 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    String[] init = input.readLine().split(",");

    int sum = 0;
    for(String s: init) {
      sum += hash(s);
    }

    return Integer.toString(sum);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    String[] init = input.readLine().split(",");
    PairList[] boxes = new PairList[256];

    for (int i = 0; i < 256; i++) {
      boxes[i] = new PairList();
    }

    for (String cmd: init) {
      if (cmd.endsWith("-")) {
        String[] cmda = cmd.split("-");
        int box = hash(cmda[0]);
        boxes[box].remove(cmda[0]);
      } else {
        String[] cmda = cmd.split("=");
        int box = hash(cmda[0]);
        boxes[box].put(cmda[0], Integer.parseInt(cmda[1]));
      }
    }

    int sum = 0;
    for (int i = 0; i < 256; i++) {
      sum += ((i + 1) * boxes[i].power());
    }

    return Integer.toString(sum);
  }

  private static int hash(String s) {
    int cur = 0;
    for (int a: s.toCharArray()) {
      cur += a;
      cur *= 17;
      cur %= 256;
    }
    return cur;
  }

  static class PairList {
    ArrayList<SimpleEntry<String, Integer>> pairlist;

    public PairList() {
      pairlist = new ArrayList<>();
    }

    public void remove(String key) {
      for (int i = 0; i < pairlist.size(); i++) {
        if (pairlist.get(i).getKey().equals(key)) {
          pairlist.remove(i);
          return;
        }
      }
    }

    public void put(String key, Integer value) {
      for (int i = 0; i < pairlist.size(); i++) {
        if (pairlist.get(i).getKey().equals(key)) {
          pairlist.get(i).setValue(value);
          return;
        }
      }
      pairlist.add(new SimpleEntry(key, value));
    }

    public int power() {
      int sum = 0;
      for (int i = 0; i < pairlist.size(); i++) {
        sum += ((i + 1) * pairlist.get(i).getValue());
      }
      return sum;
    }
  }
}
