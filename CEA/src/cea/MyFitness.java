package cea;

public class MyFitness implements Fitness {

  //-------------------------------------------------------------------------  
  @Override
  public double getValue(Genome genome) {
    double x = (double) genome.getTopology();
    return calcValue(x);
            
  }
  
  @Override
  public double getMaxValue(Genome genome) {
    double x = (double) genome.getOptimal();
    return calcValue(x);
  }
  
  private double calcValue(double x) {
    return (x+2)*(x-1)*(x-3)+24;
  }
  
  @Override
  public String toString() {
    String s = "Find local maximum of function";
    s += "y = (x+2)(x-1)(x-3) + 24; x in <-3, 3): \n";
    return s;
  }

}