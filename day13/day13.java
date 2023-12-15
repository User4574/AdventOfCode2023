import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class day13 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 13 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 13 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    ArrayList<Pattern> patterns = new ArrayList<>();
    Pattern pattern;
    while ((pattern = getPattern(input)) != null) {
      patterns.add(pattern);
    }

    int sum = 0;
    for (Pattern p: patterns) {
      sum += p.mirrorLine();
    }
    return Integer.toString(sum);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    ArrayList<Pattern> patterns = new ArrayList<>();
    Pattern pattern;
    while ((pattern = getPattern(input)) != null) {
      patterns.add(pattern);
    }

    int sum = 0;
    for (Pattern p: patterns) {
      int line = p.mirrorLine();
findSmudge:
      for (int r = 0; r < p.rows(); r++) {
        for (int c = 0; c < p.cols(); c++) {
          p.repair(r, c);
          int newline = p.differentMirrorLine(line);
          p.repair(r, c);
          if (newline > 0) {
            sum += newline;
            break findSmudge;
          }
        }
      }
    }
    return Integer.toString(sum);
  }

  private static Pattern getPattern(RandomAccessFile input) throws IOException {
    Pattern pattern = new Pattern();

    String line = input.readLine();
    while (line != null && line.length() != 0) {
      pattern.add(line);
      line = input.readLine();
    }

    if (pattern.rows() > 0) {
      return pattern;
    } else {
      return null;
    }
  }

  private static enum Terrain {
    Ash,
    Rock
  }

  private static class Pattern {
    ArrayList<ArrayList<Terrain>> pattern;

    public Pattern() {
      this.pattern = new ArrayList<>();
    }

    public void add(String line) {
      ArrayList<Terrain> row = new ArrayList<>();
      for (char ch: line.toCharArray()) {
        if (ch == '#') {
          row.add(Terrain.Rock);
        } else if (ch == '.') {
          row.add(Terrain.Ash);
        }
      }
      this.pattern.add(row);
    }

    public int rows() {
      return this.pattern.size();
    }

    public int cols() {
      return this.pattern.get(0).size();
    }

    public int mirrorLine() {
      for (int i = 1; i < this.cols(); i++) {
        boolean isMirrorLine = true;
        for (ArrayList<Terrain> row: this.pattern) {
          int l = i - 1, r = i;
          while (isMirrorLine && l >= 0 && r < this.cols()) {
            if (row.get(l) != row.get(r)) {
              isMirrorLine = false;
            }
            l -= 1;
            r += 1;
          }
        }
        if (isMirrorLine) {
          return i;
        }
      }
      for (int i = 1; i < this.rows(); i++) {
        boolean isMirrorLine = true;
        int a = i - 1, b = i;
        while (isMirrorLine && a >= 0 && b < this.rows()) {
          ArrayList<Terrain> ra = this.pattern.get(a);
          ArrayList<Terrain> rb = this.pattern.get(b);
          for (int j = 0; j < this.cols(); j++) {
            if (ra.get(j) != rb.get(j)) {
              isMirrorLine = false;
              break;
            }
          }
          a -= 1;
          b += 1;
        }
        if (isMirrorLine) {
          return 100 * i;
        }
      }
      return 0;
    }

    public int differentMirrorLine(int oldLine) {
      for (int i = 1; i < this.cols(); i++) {
        boolean isMirrorLine = true;
        for (ArrayList<Terrain> row: this.pattern) {
          int l = i - 1, r = i;
          while (isMirrorLine && l >= 0 && r < this.cols()) {
            if (row.get(l) != row.get(r)) {
              isMirrorLine = false;
            }
            l -= 1;
            r += 1;
          }
        }
        if (isMirrorLine && i != oldLine) {
          return i;
        }
      }
      for (int i = 1; i < this.rows(); i++) {
        boolean isMirrorLine = true;
        int a = i - 1, b = i;
        while (isMirrorLine && a >= 0 && b < this.rows()) {
          ArrayList<Terrain> ra = this.pattern.get(a);
          ArrayList<Terrain> rb = this.pattern.get(b);
          for (int j = 0; j < this.cols(); j++) {
            if (ra.get(j) != rb.get(j)) {
              isMirrorLine = false;
              break;
            }
          }
          a -= 1;
          b += 1;
        }
        if (isMirrorLine && (100 * i) != oldLine) {
          return 100 * i;
        }
      }
      return 0;
    }

    public void repair(int row, int col) {
      Terrain smudge = this.pattern.get(row).get(col);
      if (smudge == Terrain.Rock) {
        this.pattern.get(row).set(col, Terrain.Ash);
      } else {
        this.pattern.get(row).set(col, Terrain.Rock);
      }
    }
  }
}
