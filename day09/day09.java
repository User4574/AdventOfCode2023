import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.ArrayList;

public class day09 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 09 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 09 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    String line;
    int sum = 0;
    while ((line = input.readLine()) != null) {
      String[] linea = line.split(" ");
      int[] nums = new int[linea.length];
      for (int i = 0; i < linea.length; i++) {
        nums[i] = Integer.parseInt(linea[i]);
      }

      sum += predictNext(nums);
    }
    return Integer.toString(sum);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    String line;
    int sum = 0;
    while ((line = input.readLine()) != null) {
      String[] linea = line.split(" ");
      int[] nums = new int[linea.length];
      for (int i = 0; i < linea.length; i++) {
        nums[i] = Integer.parseInt(linea[i]);
      }

      sum += predictPre(nums);
    }
    return Integer.toString(sum);
  }

  private static int predictNext(int[] line) {
    ArrayList<ArrayList<Integer>> differences = new ArrayList<>();
    ArrayList<Integer> lastDiffs = new ArrayList<>();

    for (int i: line) {
      lastDiffs.add(i);
    }

    differences.add(lastDiffs);

    while (!allZero(lastDiffs)) {
      lastDiffs = getDifferences(lastDiffs);
      differences.add(lastDiffs);
    }

    for (int i = differences.size() - 2; i >= 0; i--) {
      ArrayList<Integer> lastrow = differences.get(i+1);
      ArrayList<Integer> row = differences.get(i);
      row.add(row.get(row.size() - 1) + lastrow.get(lastrow.size() - 1));
    }

    ArrayList<Integer> first = differences.get(0);
    return first.get(first.size() - 1);
  }

  private static int predictPre(int[] line) {
    ArrayList<ArrayList<Integer>> differences = new ArrayList<>();
    ArrayList<Integer> lastDiffs = new ArrayList<>();

    for (int i: line) {
      lastDiffs.add(i);
    }

    differences.add(lastDiffs);

    while (!allZero(lastDiffs)) {
      lastDiffs = getDifferences(lastDiffs);
      differences.add(lastDiffs);
    }

    int temp = 0;
    for (int i = differences.size() - 2; i >= 0; i--) {
      ArrayList<Integer> row = differences.get(i);
      temp = row.get(0) - temp;
    }

    return temp;
  }

  private static boolean allZero(ArrayList<Integer> list) {
    for(int i: list) {
      if (i != 0) {
        return false;
      }
    }
    return true;
  }

  private static ArrayList<Integer> getDifferences(ArrayList<Integer> last) {
    ArrayList<Integer> next = new ArrayList<>();

    for (int i = 1; i < last.size(); i++) {
      next.add(last.get(i) - last.get(i-1));
    }

    return next;
  }
}
