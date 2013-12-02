package cea;

public class Individual implements Comparable<Individual> {
  private final int MAX_AGE;
  
  private double fitnessValue;
  private int age;
  private double EP;
  private double RP;
  
  private Genome myGenome;
  private Fitness myFitness;  
          
  //-------------------------------------------------------------------------  
  public Individual(Genome myGenome, Fitness myFitness, int maxAge) {
    this.myGenome = myGenome;
    this.myFitness = myFitness;
    age = 0;
    this.MAX_AGE = maxAge;
    fitnessValue = myFitness.calcValue(myGenome);  // initial fitness evaluation    
  }

  //-------------------------------------------------------------------------  
  public void evaluate(double normSize, double sumFitVal) {
    // TODO: cache
    double normFV;    // normalized fitness value
    double normAge;
    fitnessValue = myFitness.calcValue(myGenome);
    normFV = fitnessValue/sumFitVal;    
    normAge = ((double)age)/MAX_AGE;

    if (age > MAX_AGE)
      EP = 1;
    else
      EP = calcEP(calcRawEP(normAge, normFV), normSize);
    RP = calcRP(calcRawRP(normAge, normFV), normSize);
  }
  
  //-------------------------------------------------------------------------  
  private double calcEP(double rawEP, double n) { // rawEP, popSize
    return Math.min(Math.max(n*rawEP, 0), 1);
  }
  
  //-------------------------------------------------------------------------  
  private double calcRP(double rawRP, double n) { // rawEP, popSize
    return Math.min(Math.max((1-n)*(1-n)*rawRP, 0), 1);
  }

  //-------------------------------------------------------------------------  
  private double calcRawEP(double a, double f) { // age, fitnessValue
    return Math.min(Math.max(f*Math.pow(a, 10) + 3*a*(1-f), 0), 1);    
  }

  //-------------------------------------------------------------------------  
  private double calcRawRP(double a, double f) { // age, fitnessValue
    return (1-a)*f + a*f/2;
  }
  
  //-------------------------------------------------------------------------  
  public void recombinate(Individual parent1, Individual parent2) {
    age = 0;
    myGenome.crossoverS(parent1.myGenome.getStructure(), parent2.myGenome.getStructure());
    myGenome.crossoverT(parent1.myGenome.getTopology(), parent2.myGenome.getTopology());    
  }

  //-------------------------------------------------------------------------  
  public void mutate(double prob) {
    myGenome.mutate(prob);
  }

  //-------------------------------------------------------------------------  
  public double getFitnessValue() {
    return fitnessValue;
  }

  //-------------------------------------------------------------------------  
  public double getEP() {
    return EP;
  }

  //-------------------------------------------------------------------------  
  public double getRP() {
    return RP;
  }

  //-------------------------------------------------------------------------  
  public void beImmortal() {
    EP = 0;
  }

  //-------------------------------------------------------------------------  
  public int getAge() {
    return age;
  }

  //-------------------------------------------------------------------------  
  public void incAge() {
    age++;
  }

  //-------------------------------------------------------------------------  
  public Genome getGenome() {
    return myGenome;
  }

  //-------------------------------------------------------------------------  
  @Override
  public int compareTo(Individual o) {
    if (this.fitnessValue > o.fitnessValue)
      return -1;
    if (this.fitnessValue < o.fitnessValue)
      return 1;
    return 0;
  }
  
  //-------------------------------------------------------------------------  
  @Override
  public String toString() {
    String s = "";
    s += String.format("fitness= %10f", fitnessValue);
    s += "  ";
    s += String.format("EP= %.10f", EP);
    s += "  ";
    s += String.format("RP= %.10f", RP);
    s += "  ";
    s += String.format("age= %2d", age);
    s += "\n";
    return s;
  }
  
}