package cea;

public interface Genome {
  public void crossoverS(Object structure1, Object structure2, int childNumber);
  public void crossoverT(Object topology1, Object topology2, int childNumber);
  public void mutate(double prob);
  public Object generateNextSolution(Object solution);
  public boolean isBetter(Object topology1, Object topology2);
  public boolean pivotRuleSatisfied(boolean foundBetter, int count);
  public boolean depthConditionSatisfied(int iterations);

  public Object getStructure();
  public Object getTopology();
  public void   setTopology(Object topology);
  public Object getOptimal();
  public Object getSolution();
}
