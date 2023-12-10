import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class day10 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 10 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 10 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    int srow = -1, scol = -1;
    ArrayList<ArrayList<Tile>> grid = new ArrayList<>();
    String line;

    while ((line = input.readLine()) != null) {
      ArrayList<Tile> row = new ArrayList<>();
      grid.add(row);
      for (char ch: line.toCharArray()) {
        switch (ch) {
          case '|':
            row.add(Tile.Vertical);
            break;
          case '-':
            row.add(Tile.Horizontal);
            break;
          case 'L':
            row.add(Tile.LBend);
            break;
          case 'J':
            row.add(Tile.JBend);
            break;
          case '7':
            row.add(Tile.SBend);
            break;
          case 'F':
            row.add(Tile.FBend);
            break;
          case '.':
            row.add(Tile.Ground);
            break;
          case 'S':
            row.add(Tile.Start);
            srow = grid.size() - 1;
            scol = row.size() - 1;
        }
      }
    }

    boolean upcon = false, leftcon = false,
            downcon = false, rightcon = false;

    if (srow > 0) {
      Tile t = grid.get(srow - 1).get(scol);
      if (t == Tile.Vertical || t == Tile.SBend || t == Tile.FBend) {
        upcon = true;
      }
    }

    if (scol > 0) {
      Tile t = grid.get(srow).get(scol - 1);
      if (t == Tile.Horizontal || t == Tile.LBend || t == Tile.FBend) {
        leftcon = true;
      }
    }

    if (srow < (grid.size() - 1)) {
      Tile t = grid.get(srow + 1).get(scol);
      if (t == Tile.Vertical || t == Tile.LBend || t == Tile.JBend) {
        downcon = true;
      }
    }

    if (scol < (grid.get(0).size() - 1)) {
      Tile t = grid.get(srow).get(scol + 1);
      if (t == Tile.Horizontal || t == Tile.JBend || t == Tile.SBend) {
        rightcon = true;
      
      }
    }

    Follower fa, fb;
    if (upcon && rightcon) {
      grid.get(srow).set(scol, Tile.LBend);
      fa = new Follower(grid, srow, scol, Dir.Up);
      fb = new Follower(grid, srow, scol, Dir.Right);
    } else if (upcon && downcon) {
      grid.get(srow).set(scol, Tile.Vertical);
      fa = new Follower(grid, srow, scol, Dir.Up);
      fb = new Follower(grid, srow, scol, Dir.Down);
    } else if (upcon && leftcon) {
      grid.get(srow).set(scol, Tile.JBend);
      fa = new Follower(grid, srow, scol, Dir.Up);
      fb = new Follower(grid, srow, scol, Dir.Left);
    } else if (rightcon && downcon) {
      grid.get(srow).set(scol, Tile.FBend);
      fa = new Follower(grid, srow, scol, Dir.Right);
      fb = new Follower(grid, srow, scol, Dir.Down);
    } else if (rightcon && leftcon) {
      grid.get(srow).set(scol, Tile.Horizontal);
      fa = new Follower(grid, srow, scol, Dir.Right);
      fb = new Follower(grid, srow, scol, Dir.Left);
    } else if (downcon && leftcon) {
      grid.get(srow).set(scol, Tile.SBend);
      fa = new Follower(grid, srow, scol, Dir.Down);
      fb = new Follower(grid, srow, scol, Dir.Left);
    } else {
      System.out.println("ERROR");
      fa = null;
      fb = null;
    }

    fa.step();
    fb.step();

    int length = 1;
    while (!(fa.row == fb.row && fa.col == fb.col)) {
      fa.step();
      fb.step();
      length += 1;
    }

    return Integer.toString(length);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    return "WIP";
  }

  static enum Tile {
    Vertical,
    Horizontal,
    LBend,
    JBend,
    SBend,
    FBend,
    Ground,
    Start
  }

  static enum Dir {
    Up,
    Down,
    Left,
    Right
  }

  static class Follower {
    ArrayList<ArrayList<Tile>> grid;
    int row, col;
    Dir dir;

    public Follower(ArrayList<ArrayList<Tile>> grid, int row, int col, Dir dir) {
      this.grid = grid;
      this.row = row;
      this.col = col;
      this.dir = dir;
    }

    public void step() {
      if (this.dir == Dir.Up) {
        this.row -= 1;
      } else if (this.dir == Dir.Down) {
        this.row += 1;
      } else if (this.dir == Dir.Left) {
        this.col -= 1;
      } else if (this.dir == Dir.Right) {
        this.col += 1;
      }

      Tile t = this.grid.get(this.row).get(this.col);

      if (t == Tile.LBend && this.dir == Dir.Down) {
        this.dir = Dir.Right;
      } else if (t == Tile.LBend && this.dir == Dir.Left) {
        this.dir = Dir.Up;
      } else if (t == Tile.JBend && this.dir == Dir.Down) {
        this.dir = Dir.Left;
      } else if (t == Tile.JBend && this.dir == Dir.Right) {
        this.dir = Dir.Up;
      } else if (t == Tile.SBend && this.dir == Dir.Up) {
        this.dir = Dir.Left;
      } else if (t == Tile.SBend && this.dir == Dir.Right) {
        this.dir = Dir.Down;
      } else if (t == Tile.FBend && this.dir == Dir.Up) {
        this.dir = Dir.Right;
      } else if (t == Tile.FBend && this.dir == Dir.Left) {
        this.dir = Dir.Down;
      }
    }
  }
}
