import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class day08 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 08 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 08 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    char[] instr = input.readLine().toCharArray();
    input.readLine();

    Pattern nodepat = Pattern.compile("[A-Z]{3}");

    HashMap<String, Node> nodes = new HashMap<>();

    String line;
    while ((line = input.readLine()) != null) {
      Matcher mat = nodepat.matcher(line);
      mat.find();
      String name = mat.group();
      mat.find();
      String left = mat.group();
      mat.find();
      String right = mat.group();

      Node node;
      if (nodes.containsKey(name)) {
        node = nodes.get(name);
      } else {
        node = new Node(name);
        nodes.put(name, node);
      }
      if (nodes.containsKey(left)) {
        node.left = nodes.get(left);
      } else {
        node.left = new Node(left);
        nodes.put(left, node.left);
      }
      if (nodes.containsKey(right)) {
        node.right = nodes.get(right);
      } else {
        node.right = new Node(right);
        nodes.put(right, node.right);
      }
    }

    Node at = nodes.get("AAA");
    int count = 0, ip = 0;
    while (!(at.name.equals("ZZZ"))) {
      if (instr[ip] == 'L') {
        at = at.left;
      } else {
        at = at.right;
      }
      count += 1;
      ip += 1;
      ip %= instr.length;
    }

    return Integer.toString(count);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    return "WIP";
    /*
    char[] instr = input.readLine().toCharArray();
    input.readLine();

    Pattern nodepat = Pattern.compile("[A-Z]{3}");

    HashMap<String, Node> nodes = new HashMap<>();

    String line;
    while ((line = input.readLine()) != null) {
      Matcher mat = nodepat.matcher(line);
      mat.find();
      String name = mat.group();
      mat.find();
      String left = mat.group();
      mat.find();
      String right = mat.group();

      Node node;
      if (nodes.containsKey(name)) {
        node = nodes.get(name);
      } else {
        node = new Node(name);
        nodes.put(name, node);
      }
      if (nodes.containsKey(left)) {
        node.left = nodes.get(left);
      } else {
        node.left = new Node(left);
        nodes.put(left, node.left);
      }
      if (nodes.containsKey(right)) {
        node.right = nodes.get(right);
      } else {
        node.right = new Node(right);
        nodes.put(right, node.right);
      }
    }

    ArrayList<Node> ats = new ArrayList<>();
    for (String name: nodes.keySet()) {
      if (name.charAt(2) == 'A') {
        ats.add(nodes.get(name));
      }
    }

    int count = 0, ip = 0;
    boolean allatz = false;
    while (!allatz) {
      if (instr[ip] == 'L') {
        for (int i = 0; i < ats.size(); i++) {
          ats.set(i, ats.get(i).left);
        }
      } else {
        for (int i = 0; i < ats.size(); i++) {
          ats.set(i, ats.get(i).right);
        }
      }
      count += 1;
      ip += 1;
      ip %= instr.length;
      allatz = true;
      for (Node at: ats) {
        if (!(at.name.charAt(2) == 'Z')) {
          allatz = false;
        }
      }
    }

    return Integer.toString(count);
    */
  }
}

class Node {
  String name;
  Node left, right;

  public Node(String name) {
    this.name = name;
    this.left = null;
    this.right = null;
  }

  public String toString() {
    return this.name + " = (" + this.left.name + ", " + this.right.name + ")";
  }
}
