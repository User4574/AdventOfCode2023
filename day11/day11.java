import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class day11 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 11 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 11 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    ArrayList<ArrayList<Pixel>> image = new ArrayList<>();

    String line;
    while ((line = input.readLine()) != null) {
      ArrayList<Pixel> linea = new ArrayList<>();
      for (char ch: line.toCharArray()) {
        if (ch == '.') {
          linea.add(Pixel.Space);
        } else if (ch == '#') {
          linea.add(Pixel.Galaxy);
        }
      }
      image.add(linea);
    }

    for (int i = image.size() - 1; i >= 0; i--) {
      if (allSpace(image.get(i))) {
        image.add(i+1, (ArrayList<Pixel>) image.get(i).clone());
      }
    }

    for (int i = image.get(0).size() - 1; i >= 0; i--) {
      ArrayList<Pixel> col = new ArrayList<>();

      for (ArrayList<Pixel> row: image) {
        col.add(row.get(i));
      }

      if (allSpace(col)) {
        for (ArrayList<Pixel> row: image) {
          row.add(i+1, row.get(i));
        }
      }
    }

    ArrayList<Integer[]> galaxies = new ArrayList<>();
    for (int r = 0; r < image.size(); r++) {
      for (int c = 0; c < image.get(0).size(); c++) {
        if (image.get(r).get(c) == Pixel.Galaxy) {
          galaxies.add(new Integer[] {r, c});
        }
      }
    }

    int sum = 0;

    for (int i = 0; i < galaxies.size() - 1; i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        sum += dist(galaxies.get(i), galaxies.get(j));
      }
    }

    return Integer.toString(sum);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    return "WIP";
  }

  static enum Pixel {
    Space,
    Galaxy
  }

  private static boolean allSpace(ArrayList<Pixel> line) {
    for (Pixel px: line) {
      if (px == Pixel.Galaxy) {
        return false;
      }
    }
    return true;
  }

  private static int dist(Integer[] i, Integer[] j) {
    return Math.abs(i[0] - j[0]) + Math.abs(i[1] - j[1]);
  }
}
