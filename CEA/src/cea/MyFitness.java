package cea;

public class MyFitness implements Fitness {

  //-------------------------------------------------------------------------  
  @Override
  public double calcValue(Genome genome) {
    double x = (double) genome.getTopology();
    return (x+2)*(x-1)*(x-3)+24;
            
  }

 }
