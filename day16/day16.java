import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;

public class day16 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 16 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 16 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    Grid grid = new Grid();

    String line;
    while ((line = input.readLine()) != null) {
      grid.addRow(line);
    }

    LinkedList<Beam> beams = new LinkedList<>();
    beams.add(new Beam(0, 0, Dir.Right, grid));

    while (beams.size() > 0) {
      Beam beam = beams.remove();
      LinkedList<Beam> splits = beam.stepThrough();
      beams.addAll(splits);
    }

    return Integer.toString(grid.visited());
  }

  public static String part2(RandomAccessFile input) throws IOException {
    return "WIP";
  }

  private static enum Tile {
    Mirror,
    BackMirror,
    VSplitter,
    HSplitter,
    Empty
  }

  private static enum Dir {
    Right,
    Left,
    Up,
    Down
  }

  private static class Cell {
    Tile tile;
    boolean visited;
    boolean canBlackhole;

    public Cell(Tile tile) {
      this.tile = tile;
      this.visited = false;
      this.canBlackhole = false;
    }

    public void visit() {
      this.visited = true;
    }

    public void doSplit() {
      this.canBlackhole = true;
    }
  }

  private static class Grid {
    ArrayList<ArrayList<Cell>> grid;

    public Grid() {
      this.grid = new ArrayList<>();
    }

    public int rows() {
      return this.grid.size();
    }

    public int cols() {
      return this.grid.get(0).size();
    }

    public Cell cellAt(int row, int col) {
      return this.grid.get(row).get(col);
    }

    public int visited() {
      int v = 0;
      for (ArrayList<Cell> row: this.grid) {
        for (Cell col: row) {
          if (col.visited) {
            v += 1;
          }
        }
      }
      return v;
    }

    public void addRow(String line) {
      ArrayList<Cell> row = new ArrayList<>();
      for (char ch: line.toCharArray()) {
        switch (ch) {
          case '/':
            row.add(new Cell(Tile.Mirror));
            break;
          case '\\':
            row.add(new Cell(Tile.BackMirror));
            break;
          case '|':
            row.add(new Cell(Tile.VSplitter));
            break;
          case '-':
            row.add(new Cell(Tile.HSplitter));
            break;
          case '.':
            row.add(new Cell(Tile.Empty));
            break;
        }
      }
      this.grid.add(row);
    }
  }

  private static class Beam {
    int row, col;
    Dir dir;
    Grid grid;

    public Beam(int row, int col, Dir dir, Grid grid) {
      this.row = row;
      this.col = col;
      this.dir = dir;
      this.grid = grid;
    }

    public boolean escaped() {
      return this.row < 0 ||
             this.row >= this.grid.rows() ||
             this.col < 0 ||
             this.col >= this.grid.cols();
    }

    public LinkedList<Beam> stepThrough() {
      LinkedList<Beam> splits = new LinkedList<>();

steppingLoop:
      while (!this.escaped()) {
        Cell cell = this.grid.cellAt(this.row, this.col);
        cell.visit();
        switch (cell.tile) {
          case Mirror:
            switch (this.dir) {
              case Right:
                this.dir = Dir.Up;
                this.row -= 1;
                break;
              case Left:
                this.dir = Dir.Down;
                this.row += 1;
                break;
              case Up:
                this.dir = Dir.Right;
                this.col += 1;
                break;
              case Down:
                this.dir = Dir.Left;
                this.col -= 1;
                break;
            }
            break;
          case BackMirror:
            switch (this.dir) {
              case Right:
                this.dir = Dir.Down;
                this.row += 1;
                break;
              case Left:
                this.dir = Dir.Up;
                this.row -= 1;
                break;
              case Up:
                this.dir = Dir.Left;
                this.col -= 1;
                break;
              case Down:
                this.dir = Dir.Right;
                this.col += 1;
                break;
            }
            break;
          case VSplitter:
            if (!cell.canBlackhole) {
              switch (this.dir) {
                case Right:
                case Left:
                  cell.doSplit();
                  splits.add(new Beam(this.row + 1, this.col, Dir.Down, this.grid));
                  this.dir = Dir.Up;
                  this.row -= 1;
                  break;
                case Up:
                  this.row -= 1;
                  break;
                case Down:
                  this.row += 1;
                  break;
              }
            } else {
              break steppingLoop;
            }
            break;
          case HSplitter:
            if (!cell.canBlackhole) {
              switch (this.dir) {
                case Right:
                  this.col += 1;
                  break;
                case Left:
                  this.col -= 1;
                  break;
                case Up:
                case Down:
                  cell.doSplit();
                  splits.add(new Beam(this.row, this.col + 1, Dir.Right, this.grid));
                  this.dir = Dir.Left;
                  this.col -= 1;
                  break;
              }
            } else {
              break steppingLoop;
            }
            break;
          default:
            switch (this.dir) {
              case Right:
                this.col += 1;
                break;
              case Left:
                this.col -= 1;
                break;
              case Up:
                this.row -= 1;
                break;
              case Down:
                this.row += 1;
                break;
            }
        }
      }

      return splits;
    }
  }
}
