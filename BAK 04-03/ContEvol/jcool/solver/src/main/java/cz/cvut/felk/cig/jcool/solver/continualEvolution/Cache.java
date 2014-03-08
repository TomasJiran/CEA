package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

// TODO: Hashovani
public class Cache<A, B> extends LinkedHashMap<A, B> {
  //Map<A, B> hm = new HashMap<A, B>(); 
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
  
  public boolean contains(A key) {
    if (this.isEmpty())
      return false;  
    //System.out.println(this.get((A) key));
    Set keys = this.keySet();
    //System.out.println(this.keySet().contains(key));
    A k;
    
    for (Iterator it = keys.iterator(); it.hasNext();) {
        k = (A) it.next();
        System.out.println(k);
        A d;
        if (k.equals(key))
        //if (Arrays.equals(k, key))
          return true;  
    }
    return false;
  }

}