package cea;

public interface Genome {
  public Object getStructure();
  public Object getTopology();
  public Object getOptimal();
  public Object getSolution();
  
  public void crossoverS(Object structure1, Object structure2);
  public void crossoverT(Object topology1, Object topology2);
  public void mutate(double prob);
  
}
