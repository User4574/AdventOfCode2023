import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class day07 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 07 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 07 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    Game game = new Game();

    String line;
    while((line = input.readLine()) != null) {
      game.parseHand(line);
    }

    game.rank();

    long winnings = 0;
    for (int i = 0; i < game.hands.size(); i++) {
      winnings += ((i+1) * game.hands.get(i).bid);
    }

    return Long.toString(winnings);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    Game game = new Game(true);

    String line;
    while((line = input.readLine()) != null) {
      game.parseHand(line);
    }

    game.rank();

    long winnings = 0;
    for (int i = 0; i < game.hands.size(); i++) {
      winnings += ((i+1) * game.hands.get(i).bid);
    }

    return Long.toString(winnings);
  }
}

class Game {
  ArrayList<Hand> hands;
  boolean wild;

  public Game() {
    this(false);
  }

  public Game(boolean wild) {
    this.hands = new ArrayList<>();
    this.wild = wild;
  }

  public void parseHand(String line) {
    String[] linea = line.split(" +");
    this.hands.add(
      new Hand(
        linea[0].toCharArray(),
        Long.parseLong(linea[1]),
        this.wild
      )
    );
  }

  public void rank() {
    Collections.sort(hands);
  }
}

class Hand implements Comparable<Hand> {
  int[] cards;
  long bid;
  boolean wild;

  public Hand(char[] cards, long bid, boolean wild) {
    this.wild = wild;
    this.cards = new int[5];
    for(int i = 0; i < 5; i++) {
      switch (cards[i]) {
        case '2':
          this.cards[i] = 2;
          break;
        case '3':
          this.cards[i] = 3;
          break;
        case '4':
          this.cards[i] = 4;
          break;
        case '5':
          this.cards[i] = 5;
          break;
        case '6':
          this.cards[i] = 6;
          break;
        case '7':
          this.cards[i] = 7;
          break;
        case '8':
          this.cards[i] = 8;
          break;
        case '9':
          this.cards[i] = 9;
          break;
        case 'T':
          this.cards[i] = 10;
          break;
        case 'J':
          this.cards[i] = this.wild ? 1 : 11;
          break;
        case 'Q':
          this.cards[i] = 12;
          break;
        case 'K':
          this.cards[i] = 13;
          break;
        case 'A':
          this.cards[i] = 14;
          break;
      }
    }
    this.bid = bid;
  }

  public int compareTo(Hand other) {
    if (this.type() > other.type()) {
      return 1;
    } else if (this.type() < other.type()) {
      return -1;
    }
    for (int i = 0; i < 5; i++) {
      if (this.cards[i] > other.cards[i]) {
        return 1;
      } else if (this.cards[i] < other.cards[i]) {
        return -1;
      }
    }
    return 0;
  }

  private int type() {
    int[] s = this.cards.clone();
    Arrays.sort(s);

    if (this.wild) {
      if (s[0] == 1 && s[1] == 1 && s[2] == 1 && s[3] == 1) { //4 or 5 jokers
        return 6; //5 of a kind
      } else if (s[0] == 1 && s[1] == 1 && s[2] == 1) { //3 jokers
        if (s[3] == s[4]) { //pair
          return 6; //5oak
        } else { //high
          return 5; //4oak
        }
      } else if (s[0] == 1 && s[1] == 1) { //2 jokers
        if (s[2] == s[3] && s[3] == s[4]) {//3oak
          return 6; //5oak
        } else if (s[2] == s[3] || s[3] == s[4]) { //pair
          return 5; //4oak
        } else { //high
          return 3; //3oak
        }
      } else if (s[0] == 1) { //1 jokers
        if (
            s[1] == s[2] && s[2] == s[3] && s[3] == s[4] ) { //4oak
          return 6; //5oak
        } else if (
            s[1] == s[2] && s[2] == s[3] ||
            s[2] == s[3] && s[3] == s[4] ) { //3oak
          return 5; //4oak
        } else if (s[1] == s[2] && s[3] == s[4]) { //2pair
          return 4; //full
        } else if (
            s[1] == s[2] ||
            s[2] == s[3] ||
            s[3] == s[4] ) { //pair
          return 3; //3oak
        } else { //high
          return 1;
        }
      }
    }

    //0 jokers
    if (
        s[0] == s[1] && s[1] == s[2] && s[2] == s[3] && s[3] == s[4] ) {
      return 6;
    } else if (
        s[0] == s[1] && s[1] == s[2] && s[2] == s[3] ||
        s[1] == s[2] && s[2] == s[3] && s[3] == s[4] ) {
      return 5;
    } else if (
        s[0] == s[1]    &&    s[2] == s[3] && s[3] == s[4] ||
        s[0] == s[1] && s[1] == s[2]    &&    s[3] == s[4] ) {
      return 4;
    } else if (
        s[0] == s[1] && s[1] == s[2] ||
        s[1] == s[2] && s[2] == s[3] ||
        s[2] == s[3] && s[3] == s[4] ) {
      return 3;
    } else if (
        s[0] == s[1] && s[2] == s[3] ||
        s[0] == s[1] && s[3] == s[4] ||
        s[1] == s[2] && s[3] == s[4] ) {
      return 2;
    } else if (
        s[0] == s[1] ||
        s[1] == s[2] ||
        s[2] == s[3] ||
        s[3] == s[4] ){
      return 1;
    } else {
      return 0;
    }
  }
}
