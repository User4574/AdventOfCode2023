import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class day05 {
  public static void main(String args[]) throws IOException {
    RandomAccessFile input = new RandomAccessFile("input", "r");
    System.out.println("Day 05 Part 1: " + part1(input));
    input.seek(0);
    System.out.println("Day 05 Part 2: " + part2(input));
    input.close();
  }

  public static String part1(RandomAccessFile input) throws IOException {
    Garden garden = parseGarden(input);
    
    long min_loc = Long.MAX_VALUE;
    for (long seed: garden.seeds) {
      long loc = garden.location(seed);
      if (loc < min_loc) {
        min_loc = loc;
      }
    }

    return Long.toString(min_loc);
  }

  public static String part2(RandomAccessFile input) throws IOException {
    Garden garden = parseGarden(input);
    
    long min_loc = Long.MAX_VALUE;
    long start = -1;
    long length = -1;
    for (long seed: garden.seeds) {
      if (start == -1) {
        start = seed;
        continue;
      }
      length = seed;

      for(long s = start; s < start + length; s++) {
        long loc = garden.location(s);
        if (loc < min_loc) {
          min_loc = loc;
        }
      }
      start = -1;
    }

    return Long.toString(min_loc);
  }

  private static Garden parseGarden(RandomAccessFile input) throws IOException {
    Garden garden = new Garden();

    String line;
    int state = -1;
    while ((line = input.readLine()) != null) {
      if (Pattern.matches("\\Aseeds:.*", line)) {
        String[] seeds = line.split(": ")[1].split(" ");
        for (String seed: seeds) {
          garden.seeds.add(Long.parseLong(seed));
        }
      } else if (Pattern.matches(".* map:\\z", line)) {
        state += 1;
      } else if (Pattern.matches("\\d+ \\d+ \\d+", line)) {
        String[] linea = line.split(" ");
        long dst = Long.parseLong(linea[0]);
        long src = Long.parseLong(linea[1]);
        long len = Long.parseLong(linea[2]);
        switch (state) {
          case 0:
            garden.almanac.s2s.add(dst, src, len);
            break;
          case 1:
            garden.almanac.s2f.add(dst, src, len);
            break;
          case 2:
            garden.almanac.f2w.add(dst, src, len);
            break;
          case 3:
            garden.almanac.w2l.add(dst, src, len);
            break;
          case 4:
            garden.almanac.l2t.add(dst, src, len);
            break;
          case 5:
            garden.almanac.t2h.add(dst, src, len);
            break;
          case 6:
            garden.almanac.h2l.add(dst, src, len);
            break;
        }
      }
    }

    return garden;
  }
}

class Garden {
  ArrayList<Long> seeds;
  Almanac almanac;

  public Garden() {
    this.seeds = new ArrayList<>();
    this.almanac = new Almanac();
  }

  public long location(long seed) {
    long at = seed;
    for (Map map: this.almanac.maps) {
      for (MapEntry ent: map.mapEntries) {
        if (ent.contains(at)) {
          at = ent.map(at);
          break;
        }
      }
    }
    return at;
  }
}

class Almanac {
  Map s2s, s2f, f2w, w2l, l2t, t2h, h2l;
  ArrayList<Map> maps;

  public Almanac() {
    this.s2s = new Map();
    this.s2f = new Map();
    this.f2w = new Map();
    this.w2l = new Map();
    this.l2t = new Map();
    this.t2h = new Map();
    this.h2l = new Map();

    ArrayList<Map> maps = new ArrayList<>();
    maps.add(this.s2s);
    maps.add(this.s2f);
    maps.add(this.f2w);
    maps.add(this.w2l);
    maps.add(this.l2t);
    maps.add(this.t2h);
    maps.add(this.h2l);
    this.maps = maps;
  }
}

class Map {
  ArrayList<MapEntry> mapEntries;

  public Map() {
    mapEntries = new ArrayList<>();
  }

  public void add(long dst, long src, long len) {
    mapEntries.add(new MapEntry(dst, src, len));
  }
}

class MapEntry {
  long dst, src, len;

  public MapEntry(long dst, long src, long len) {
    this.dst = dst;
    this.src = src;
    this.len = len;
  }

  public boolean contains(long src) {
    return src >= this.src && src < (this.src + this.len);
  }

  public long map(long src) {
    return this.dst + (src - this.src);
  }
}
