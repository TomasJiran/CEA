package cz.cvut.felk.cig.jcool.solver.myDemo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cz.cvut.felk.cig.jcool.experiment.BasicExperimentRunner;
import cz.cvut.felk.cig.jcool.experiment.ExperimentRunner;
import cz.cvut.felk.cig.jcool.experiment.ExperimentRun;
import cz.cvut.felk.cig.jcool.benchmark.method.gradient.qn.QuasiNewtonMethod;
import cz.cvut.felk.cig.jcool.solver.SolverFactory;
import cz.cvut.felk.cig.jcool.core.ValuePointTelemetry;

//import cz.cvut.felk.cig.jcool.core.StopCondition;
//import cz.cvut.felk.cig.jcool.solver.OptimizationResults;
//import cz.cvut.felk.cig.jcool.solver.Solver;
//import cz.cvut.felk.cig.jcool.solver.Statistics;

public class MySolverDemo2 {
  
  public static void main(String[] args) {
    //create a new thread
    final ExecutorService es = Executors.newSingleThreadExecutor();
    //create a new experiment
    final ExperimentRunner runner = new BasicExperimentRunner(es);
    //set the function to minimize
    runner.setFunction(new MyTestFunction2());
    //choose a method
    runner.setMethod(new QuasiNewtonMethod());
    //set solver limited by 1000 iterations
    runner.setSolver(SolverFactory.getNewInstance(1000));
    //start optimization
    runner.startExperiment();

    //get results
    ExperimentRun run = runner.getExperimentResults();
    System.out.println("====================");
    System.out.println("Function: " + run.getFunction().getName());
    System.out.println("Method: " + run.getMethod().getName());
    System.out.println("Solver: " + run.getSolver().getName());
    System.out.println("--------------------");
    ValuePointTelemetry telemetry = (ValuePointTelemetry) run.getResults().getSolution();
    System.out.println("Solution: " + telemetry.getValue().getValue() + " at " + telemetry.getValue().getPoint());
    System.out.println("# of iterations: " + run.getResults().getNumberOfIterations());
    //let's see, how many function value, gradient and hessian matrix evaluations were needed
    System.out.println("Statistics: " + run.getResults().getStatistics());

    //release the thread
    es.shutdown();
  }  
}