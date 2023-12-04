import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class day03 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 01 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 01 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    String line;
    int row = 0;
    ArrayList<Number> numbers = new ArrayList<>();
    ArrayList<Symbol> symbols = new ArrayList<>();

    while ((line = input.readLine()) != null) {
      getNumbers(numbers, row, line);
      getSymbols(symbols, row, line);
      row += 1;
    }

    int tot = 0;
    for (Number num: numbers) {
      boolean part = false;
      for (Symbol sym: symbols) {
        for (int col = num.colstart - 1; col <= num.colend + 1; col++) {
          if (sym.row == num.row - 1 && sym.col == col) {
            part = true;
          }
          if (sym.row == num.row + 1 && sym.col == col) {
            part = true;
          }
        }
        if (sym.row == num.row && sym.col == num.colstart - 1) {
          part = true;
        }
        if (sym.row == num.row && sym.col == num.colend + 1) {
          part = true;
        }
      }
      if (part) {
        tot += num.value;
      }
    }

    return Integer.toString(tot);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    String line;
    int row = 0;
    ArrayList<Number> numbers = new ArrayList<>();
    ArrayList<Symbol> symbols = new ArrayList<>();

    while ((line = input.readLine()) != null) {
      getNumbers(numbers, row, line);
      getSymbols(symbols, row, line);
      row += 1;
    }

    int tot = 0;
    for (Symbol sym : symbols) {
      if (!sym.symbol.equals("*")) {
        continue;
      }

      ArrayList<Number> neighs = new ArrayList<>();

      for (Number num: numbers) {
        if (num.contains(sym.row - 1, sym.col - 1) ||
            num.contains(sym.row - 1, sym.col    ) ||
            num.contains(sym.row - 1, sym.col + 1) ||
            num.contains(sym.row    , sym.col - 1) ||
            num.contains(sym.row    , sym.col + 1) ||
            num.contains(sym.row + 1, sym.col - 1) ||
            num.contains(sym.row + 1, sym.col    ) ||
            num.contains(sym.row + 1, sym.col + 1)) {
          if (!neighs.contains(num)) {
            neighs.add(num);
          }
        }
      }

      if (!(neighs.size() == 2)) {
        continue;
      }

      int ratio = neighs.get(0).value * neighs.get(1).value;
      tot += ratio;
    }

    return Integer.toString(tot);
  }

  private static void getNumbers(ArrayList<Number> numbers, int row, String line) {
    Pattern pat = Pattern.compile("\\d+");
    Matcher mat = pat.matcher(line);
    while (mat.find()) {
      numbers.add(
        new Number(
          row,
          mat.start(),
          mat.end() - 1,
          Integer.parseInt(mat.group())
        )
      );
    }
  }

  private static void getSymbols(ArrayList<Symbol> symbols, int row, String line) {
    Pattern pat = Pattern.compile("[^0-9.]");
    Matcher mat = pat.matcher(line);
    while (mat.find()) {
      symbols.add(
        new Symbol(
          row,
          mat.start(),
          mat.group()
        )
      );
    }
  }
}

class Number {
  int row, colstart, colend, value;

  public Number(int row, int colstart, int colend, int value) {
    this.row = row;
    this.colstart = colstart;
    this.colend = colend;
    this.value = value;
  }

  public boolean contains(int row, int col) {
    return (row == this.row) && (this.colstart <= col && col <= this.colend);
  }

  public String toString() {
    return "[" + this.row + ", " + this.colstart + "-" + this.colend + "]: " + this.value;
  }
}

class Symbol {
  int row, col;
  String symbol;

  public Symbol(int row, int col, String symbol) {
    this.row = row;
    this.col = col;
    this.symbol = symbol;
  }

  public String toString() {
    return "[" + this.row + ", " + this.col + "]: " + this.symbol;
  }
}
