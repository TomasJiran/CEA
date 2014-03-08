package cz.cvut.felk.cig.jcool.solver.continualEvolution;

public interface Fitness  {
  public double getValue(MyGenome genome);
  public boolean noMoreEvaluations();
  public double getTopologyValue(Object topology);
}
