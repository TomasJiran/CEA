package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import cz.cvut.felk.cig.jcool.core.*;
import cz.cvut.felk.cig.jcool.solver.BaseObjectiveFunction;
import cz.cvut.felk.cig.jcool.utils.CentralDifferenceGradient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javabbob.JNIfgeneric;
import org.ytoh.configurations.annotations.Component;


@Component(
    name="Test function",
    description="This is test function long description.",
    shortDescription="This is test function short description."
)

// ? TODO: implements Fitness
public class MyFitness implements Function, FunctionGradient, FunctionHessian {
  JNIfgeneric fgeneric;
  int maxfunevals;
  //private MyCache3 cache;     
  private MyCache2 cache;     
  
  private int dimension;
  private CentralDifferenceGradient grad;
  private BaseObjectiveFunction objFunc;
  
  private BufferedWriter output;
  
  //-------------------------------------------------------------------------  
  public MyFitness(JNIfgeneric fgeneric, int maxfunevals, int dim) {
    this.fgeneric = fgeneric;
    this.maxfunevals = maxfunevals;
    dimension = dim;
    //cache = new MyCache3();       
    cache = new MyCache2();           
    
    try {
      File file = new File("values_for_fitness.txt");
      output = new BufferedWriter(new FileWriter(file));
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  //-------------------------------------------------------------------------  
  public MyFitness(int maxfunevals, int dim) {
    this.maxfunevals = maxfunevals;
    dimension = dim;
    //cache = new MyCache3();       
    cache = new MyCache2();           
    
    try {
      File file = new File("values_for_fitness.txt");
      output = new BufferedWriter(new FileWriter(file));
      System.out.println("File created");
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  //-------------------------------------------------------------------------  
  @Override
  public double valueAt(Point point) {
    double[] x;
    double value;
    x = point.toArray();
      value = calcValue(x);
    return value;
  } 

  //-------------------------------------------------------------------------  
  /*@Override
  public double valueAt(Point point) {
    double[] x;
    double value;
    x = point.toArray();
    // if (!cache.contains(x))    
    if (!cache.contains(x[0])) {     
      //System.out.println("no cache");
      value = calcValue(x);
      //cache.put(x, value);
      cache.put(x[0], value);
    }
    else {
      //System.out.println("yes cache");
      try {
        output.write("using cache");
        output.newLine();
      } catch (IOException ex) {
        Logger.getLogger(MyFitness.class.getName()).log(Level.SEVERE, null, ex);
      }
      //value = cache.get(x);
      value = cache.get(x[0]);
    }
    return value;
  }      */
  
  //-------------------------------------------------------------------------  
  @Override
    public int getDimension() {
      // TODO
      return dimension;
    }

  //-------------------------------------------------------------------------  
  @Override
    public Gradient gradientAt(Point point) {
        //return Gradient.valueOf(new double[] {});
      CentralDifferenceGradient grad = new CentralDifferenceGradient();
      BaseObjectiveFunction objFunc = new BaseObjectiveFunction(this);
       // if(!objFunc.hasAnalyticalGradient()) {
            objFunc.setNumericalGradient(new CentralDifferenceGradient());
       // }
      Gradient g = grad.gradientAt(objFunc, point);
      return g;
    }

  //-------------------------------------------------------------------------  
  // TODO
  @Override
    public Hessian hessianAt(Point point) {
        return Hessian.valueOf(new double[][] {});
        /*
        if(!objFunc.hasAnalyticalHessian()) {
            objFunc.setNumericalHessian(new CentralDifferenceHessian());
        }*/
    }

  //-------------------------------------------------------------------------  
  public boolean noMoreEvaluations() {
    return maxfunevals <= 0;
  }

  // PRIVATE METHODS:
  //=======================================================================  
  private double calcValue(double[] x) {
    String str;
    // TODO: stop condition
    if (maxfunevals <= 0)
      return 0;
    try {
      for (int i = 0; i < x.length; i++) {
        str = String.valueOf(x[i]);
        for (int j = str.length(); j < 18; j++) {
          str = str + 0;
        }
        output.write(str);
        output.write(", ");
      }
        output.newLine();
      } catch (IOException e) {
          e.printStackTrace();
      }
    //System.out.println(maxfunevals);
    maxfunevals--;
    //return fgeneric.evaluate(x);
    return 3*Math.sin(x[0]) + (0.1*x[0]-3)*(0.1*x[0]-3);
  }

}
