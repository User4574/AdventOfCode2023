import java.io.IOException;
import java.io.RandomAccessFile;

public class dayXX {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("test", "r");
    System.out.println("Day XX Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day XX Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    return "WIP";
  }

  public static String part2(RandomAccessFile input) throws IOException {
    return "WIP";
  }
}
