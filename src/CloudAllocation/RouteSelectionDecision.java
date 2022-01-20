package CloudAllocation;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.util.Vector;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.moeaframework.util.grammar.Parser.load;

public class RouteSelectionDecision extends AbstractProblem {
    /**
     * The number of player
     */
    private int commuters;

    /**
     * The number of strategies
     */
    private int nstrategies;
    /**
     * Properties of each strategy
     */
    private int properties;
    /**
     * Entry weight is the weight from each property of each strategy of player
     */
    private int[][] profit;

    private int[][] weight;

    private int[] capacity;


    public RouteSelectionDecision(int numberOfVariables, int numberOfObjectives) {
        super(numberOfVariables, numberOfObjectives);
        load();
    }

    @Override
    public void evaluate(Solution solution) {
        boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
        double[] f = new double[commuters];
        double[] g = new double[nstrategies];

         // calculate the profits and weights for the cloud
        for (int i = 0; i < nstrategies; i++) {
            if(d[i]) {
                for (int j = 0; j < commuters; j++) {
                    f[j] += profit[j][i];
                    g[j] += weight[j][i];
                    }
                }
            }
                // check if any weights exceed the capacities
        for (int j = 0; j < commuters; j++) {
            if (g[j] <= capacity[j]) {
                g[j] = 0.0;
                } else {
                g[j] = g[j] - capacity[j];
                }
            }
                // negate the objectives since Cloud is maximization
        solution.setObjectives(Vector.negate(f));
        solution.setConstraints(g);
    }

    @Override
    public String getName() {
        return "MyProblem";
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(1, commuters,commuters);
        solution.setVariable(0, EncodingUtils.newBinary(nstrategies));
        return solution;
    }

    @Override
    public void close(){
    }

    public void load(){

    }
}
