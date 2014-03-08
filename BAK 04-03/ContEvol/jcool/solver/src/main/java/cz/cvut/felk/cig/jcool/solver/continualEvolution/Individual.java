package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import cz.cvut.felk.cig.jcool.benchmark.method.gradient.qn.QuasiNewtonMethod2;
import cz.cvut.felk.cig.jcool.core.Function;
import cz.cvut.felk.cig.jcool.core.Point;
import cz.cvut.felk.cig.jcool.solver.BasicSolver;
import cz.cvut.felk.cig.jcool.solver.OptimizationResults;
import cz.cvut.felk.cig.jcool.solver.SolverFactory;
import static cz.cvut.felk.cig.jcool.solver.continualEvolution.MyConst.MAX_AGE;

/**
 *
 * @author admin
 * @param <G>
 */
public class Individual {//<G extends MyGenome> {
  // interface: genom
  private double fitnessValue;
  private int age;
  private double EP;            // elimination probability
  private double RP;            // reproduction probability
  
  //private G myGenome;
  
  private MyGenome myGenome;
  private Function myFitness;  
          
  //-------------------------------------------------------------------------  
  // i = new Individual(new Genome(fit...));
  // genome by obsahoval> evaluate a flag, kdy by se zmenila fitness
  // pak logovani kdzkoliv v genomu evaluate a vypsat i flag
  // klidne tridu logger
  
  // Dropbox, histogram, 
  
  // dala by se pro castecnou populaci
  
  
  
  public Individual(Function myFitness, int dim) {
    age = 0;
    myGenome = new MyGenome(dim);
    myGenome.randGenes(); 
    this.myFitness = myFitness;
  }

  //-------------------------------------------------------------------------  
  public Individual(Function myFitness, int dim, Individual parent) {
    age = 0;
    myGenome = new MyGenome(dim);
// ? TODO:  nechat ?
    myGenome.setGenes(parent.myGenome); 
    this.myFitness = myFitness;
  }

  //-------------------------------------------------------------------------  
  public void evaluate(double normSize, double sumFitVal) {
    double normFV;                     // normalized fitness value
    double normAge;                    // normalized age
// ? TODO:  valueAt(MyGenome g) ?
    fitnessValue = myFitness.valueAt(Point.at(myGenome.getGenes()));  
    normFV = 0.5 - fitnessValue/sumFitVal;    
    normAge = ((double)age)/MAX_AGE;

    if (age > MAX_AGE)   
      EP = 1;
    else
      EP = calcEP(calcRawEP(normAge, normFV), normSize);
    RP = calcRP(calcRawRP(normAge, normFV), normSize);
  }
  
  //-------------------------------------------------------------------------  
  public void crossover(Individual parent2) {
    this.age = 0;  
    myGenome.crossover(parent2.myGenome);
  }
  
  //-------------------------------------------------------------------------  
  public void mutate() {
    myGenome.mutate();
  }
  
  //-------------------------------------------------------------------------  
  public void adapt() {      
// ? TODO: myGenome.adapt();       // the local search function
    
    // a solver allowing maximum of 5 iterations
    BasicSolver solver = (BasicSolver) SolverFactory.getNewInstance(5);

    // solver.init() and solver.solve() can throw any kind of exception and we must react on that.
    try {
      // myGenome.print("Pred adapt: ", myGenome.getTopology(), fitnessValue);
       //System.out.println("adapt init");  
// ? solver.init(myFitness, new QuasiNewtonMethod(), myGenome, fitnessValue);
      solver.init(myFitness, new QuasiNewtonMethod2(), myGenome.getGenes(), fitnessValue);
       //System.out.println("adapt start");
      // the computation is stopped on an instance of IterationStopCondition after 5 iterations
      solver.solve();
       //System.out.println("adapt done");
      
      // results gathering
      OptimizationResults r = solver.getResults();
      myGenome.setResult(solver);
      // myGenome.print("Po adapt:   ", solver.getResultX());
 
  
      /* present the results to the world
      for(StopCondition condition : r.getMetConditions()) {
        System.out.println("stopped on condition: " + condition.getClass());
      }
      Statistics stats = r.getStatistics();
      System.out.println("# of Value evaluations:    " + stats.getValueAt());
      System.out.println("# of Gradient evaluations: " + stats.getGradientAt());
      System.out.println("# of Hessian evaluations:  " + stats.getHessianAt());*/
    } catch (Exception e){
      e.printStackTrace();
    }
  }
  
  //-------------------------------------------------------------------------  
  public double getFitnessValue() {
    return fitnessValue;
  }

  //-------------------------------------------------------------------------  
  public void initFitnessValue() {
// ? TODO:  valueAt(MyGenome g) ?
    fitnessValue = myFitness.valueAt(Point.at(myGenome.getGenes()));  
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
    age = 0;
  }

  //-------------------------------------------------------------------------  
  public void incAge() {
    age++;
  }

  //-------------------------------------------------------------------------  
  public MyGenome getGenome() {
    return myGenome;
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

  // PRIVATE METHODS:
  //=======================================================================  
  //-------------------------------------------------------------------------  
  private double calcEP(double rawEP, double n) { // rawEP, population size
    return Math.min(Math.max(n*rawEP, 0), 1);
  }
  
  //-------------------------------------------------------------------------  
  private double calcRP(double rawRP, double n) { // rawEP, population size
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
  
}