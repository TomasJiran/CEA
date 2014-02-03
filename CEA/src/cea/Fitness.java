package cea;

public interface Fitness {
  public double getValue(MyGenome genome);
  public boolean noMoreEvaluations();
  public double getTopologyValue(Object topology);
}
