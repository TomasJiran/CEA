package cz.cvut.felk.cig.jcool.solver.continualEvolution;

public interface Genome {
  public void crossover(MyGenome parentGenome);
  public void mutate();
  public void setGenes(MyGenome g);
  public void randGenes();
}