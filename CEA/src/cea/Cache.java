package cea;

import java.util.LinkedHashMap;
import java.util.Map;


public class Cache<A, B>extends LinkedHashMap<A, B> {
// simple LRU cache

  private final int maxEntries;

  public Cache(final int maxEntries) {
    super(maxEntries+1, 1.0f, true);
    this.maxEntries = maxEntries;
  }

  @Override
  protected boolean removeEldestEntry(final Map.Entry<A, B> eldestEntry) {
    return super.size() > maxEntries;
    }
}