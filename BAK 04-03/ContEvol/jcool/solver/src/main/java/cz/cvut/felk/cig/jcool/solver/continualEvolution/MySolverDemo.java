package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import cz.cvut.felk.cig.jcool.benchmark.method.gradient.qn.QuasiNewtonMethod;
import cz.cvut.felk.cig.jcool.core.StopCondition;
import cz.cvut.felk.cig.jcool.solver.OptimizationResults;
import cz.cvut.felk.cig.jcool.solver.Solver;
import cz.cvut.felk.cig.jcool.solver.SolverFactory;
import cz.cvut.felk.cig.jcool.solver.Statistics;


public class MySolverDemo {

  public static void main(String[] args) {
        // a solver allowing maximum of 10 iterations
        Solver solver = SolverFactory.getNewInstance(10);

        // solver.init() and solver.solve() can throw any kind of exception and we must react on that.
        try {
            // the test method randomly calls valueAt, gradientAt and hessianAt
            // methods on the test function
            solver.init(new MyTestFunction(), new QuasiNewtonMethod());

            // the computations is stopped on an instance of IterationStopCondition
            // after 50 iterations
            solver.solve();

            // result gathering
            OptimizationResults r = solver.getResults();

            // present the results to the world
            System.out.println(r.getSolution());

            for(StopCondition condition : r.getMetConditions()) {
                System.out.println("stopped on condition: " + condition.getClass());
            }

            Statistics stats = r.getStatistics();
            System.out.println("# of Value evaluations:    " + stats.getValueAt());
            System.out.println("# of Gradient evaluations: " + stats.getGradientAt());
            System.out.println("# of Hessian evaluations:  " + stats.getHessianAt());
        } catch (Exception e){
            e.printStackTrace();
        }

  }

}
