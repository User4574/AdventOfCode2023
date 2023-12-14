import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class day14 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 14 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 14 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    ArrayList<ArrayList<Tile>> platform = new ArrayList<>();

    String line;
    while ((line = input.readLine()) != null) {
      ArrayList<Tile> row = new ArrayList<>();
      for (char ch: line.toCharArray()) {
        switch (ch) {
          case 'O':
            row.add(Tile.Round);
            break;
          case '#':
            row.add(Tile.Cube);
            break;
          case '.':
            row.add(Tile.Empty);
            break;
        }
      }
      platform.add(row);
    }

    tipNorth(platform);

    int load = 0, rows = platform.size();
    for (int r = 0; r < rows; r++) {
      for (Tile t: platform.get(r)) {
        if (t == Tile.Round) {
          load += (rows - r);
        }
      }
    }

    return Integer.toString(load);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    return "WIP";
    /*
    ArrayList<ArrayList<Tile>> platform = new ArrayList<>();

    String line;
    while ((line = input.readLine()) != null) {
      ArrayList<Tile> row = new ArrayList<>();
      for (char ch: line.toCharArray()) {
        switch (ch) {
          case 'O':
            row.add(Tile.Round);
            break;
          case '#':
            row.add(Tile.Cube);
            break;
          case '.':
            row.add(Tile.Empty);
            break;
        }
      }
      platform.add(row);
    }

    for (long l = 0; l < 1000000000; l++) {
      tipNorth(platform);
      tipWest(platform);
      tipSouth(platform);
      tipEast(platform);
    }

    int load = 0, rows = platform.size();
    for (int r = 0; r < rows; r++) {
      for (Tile t: platform.get(r)) {
        if (t == Tile.Round) {
          load += (rows - r);
        }
      }
    }

    return Integer.toString(load);
    */
  }

  static enum Tile {
    Round,
    Cube,
    Empty
  }

  private static void tipNorth(ArrayList<ArrayList<Tile>> platform) {
    int rows = platform.size(), cols = platform.get(0).size();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (platform.get(r).get(c) != Tile.Round) {
          continue;
        }

        int rat = r;
        while (rat > 0) {
          if (platform.get(rat - 1).get(c) == Tile.Empty) {
            platform.get(rat - 1).set(c, Tile.Round);
            platform.get(rat).set(c, Tile.Empty);
            rat -= 1;
          } else {
            break;
          }
        }
      }
    }
  }

  /*
  private static void tipSouth(ArrayList<ArrayList<Tile>> platform) {
    int rows = platform.size(), cols = platform.get(0).size();
    for (int r = (rows - 1); r >= 0; r--) {
      for (int c = 0; c < cols; c++) {
        if (platform.get(r).get(c) != Tile.Round) {
          continue;
        }

        int rat = r;
        while (rat < (rows - 1)) {
          if (platform.get(rat + 1).get(c) == Tile.Empty) {
            platform.get(rat + 1).set(c, Tile.Round);
            platform.get(rat).set(c, Tile.Empty);
            rat += 1;
          } else {
            break;
          }
        }
      }
    }
  }

  private static void tipWest(ArrayList<ArrayList<Tile>> platform) {
    int rows = platform.size(), cols = platform.get(0).size();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (platform.get(r).get(c) != Tile.Round) {
          continue;
        }

        int cat = c;
        while (cat > 0) {
          if (platform.get(r).get(cat - 1) == Tile.Empty) {
            platform.get(r).set(cat - 1, Tile.Round);
            platform.get(r).set(cat, Tile.Empty);
            cat -= 1;
          } else {
            break;
          }
        }
      }
    }
  }

  private static void tipEast(ArrayList<ArrayList<Tile>> platform) {
    int rows = platform.size(), cols = platform.get(0).size();
    for (int r = 0; r < rows; r++) {
      for (int c = (cols - 1); c >= 0; c--) {
        if (platform.get(r).get(c) != Tile.Round) {
          continue;
        }

        int cat = c;
        while (cat < (cols - 1)) {
          if (platform.get(r).get(cat + 1) == Tile.Empty) {
            platform.get(r).set(cat + 1, Tile.Round);
            platform.get(r).set(cat, Tile.Empty);
            cat += 1;
          } else {
            break;
          }
        }
      }
    }
  }
  */
}
