package cea;

public interface Fitness {
  public double getValue(Genome genome);
  public double getTopologyValue(Object topology);
  public double getMaxValue(Genome genome);
}
