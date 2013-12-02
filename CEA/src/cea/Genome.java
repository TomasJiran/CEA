package cea;

public interface Genome {
  public Object getStructure();
  public Object getTopology();
  
  public void crossoverS(Object structure1, Object structure2);
  public void crossoverT(Object topology1, Object topology2);
  public void mutate(double prob);
  
}
