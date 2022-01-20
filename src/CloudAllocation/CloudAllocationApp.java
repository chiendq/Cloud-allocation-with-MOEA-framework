package CloudAllocation;
import java.io.IOException;
import java.io.InputStream;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.util.Vector;

public class CloudAllocationApp {
	/**
	 * Starts the example running the CloudAllocation problem.
	 * 
	 * @param args the command line arguments
	 * @throws IOException if an I/O error occurred
	 */
	public static void main(String[] args) throws IOException {
		// open the file containing the CloudAllocation problem instance
		InputStream input = CloudAllocation.class.getResourceAsStream(
				"cloudallocation");
		
		if (input == null) {
			System.err.println("Unable to find the file cloudallocation");
			System.exit(-1);
		}
				
		// solve using MinMaxDominanceComparator
		NondominatedPopulation result = new Executor()
				.withProblemClass(CloudAllocation.class, input)
				.withAlgorithm("MinMaxDominanceComparator")
				.withMaxEvaluations(50000)
				.distributeOnAllCores()
				.run();
		// print the results
		for (int i = 0; i < result.size(); i++) {
			Solution solution = result.get(i);
			double[] objectives = solution.getObjectives();
					
			// negate objectives to return them to their maximized form
			objectives = Vector.negate(objectives);
					
			System.out.println("Solution " + (i+1) + ":");
			System.out.println("Execution time: " + objectives[0]);
			System.out.println("Waiting time: " + objectives[1]);
			System.out.println("Completion time: " + Math.round((double) objectives[0] + objectives[1]));

		}

	}
}
