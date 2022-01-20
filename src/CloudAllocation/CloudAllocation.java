package CloudAllocation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
public class CloudAllocation implements Problem{

	/**
	 * The number of clouds
	 */
	protected int clouds;
	
	/**
	 * The number of request
	 */
	protected int requests;
	
	/**
	 * Entry profit[i][j] is the profit from including request j
	 * in cloud i.
	 */
	protected int[][] profit;
	
	/**
	 * Entry weight is the weight incurred from including request j
	 *  in sack i.
	 */
	protected int[][] weight;
	
	/**
	 * Entry capacity[i] is the weight capacity of cloud i
	 */
	protected int[] capacity;
	
	/**
	 * Constructs a multiobjective 0/1 CloudAllocation problem instance loaded from
	 * the specified file.
	 * 
	 * @param file the file containing the knapsack problem instance
	 * @throws IOException if an I/O error occurred
	 */
	public CloudAllocation(File file) throws IOException {
		this(new FileReader(file));
	}
	/**
	 * Constructs a multiobjective 0/1 CloudAllocation problem instance loaded from
	 * the specified input stream.
	 * 
	 * @param inputStream the input stream containing the CloudAllocation problem
	 *        instance
	 * @throws IOException if an I/O error occurred
	 */
	public CloudAllocation(InputStream inputStream) throws IOException {
		this(new InputStreamReader(inputStream));
	}
	/**
	 * Constructs a multiobjective 0/1 CloudAllocation problem instance loaded from
	 * the specified reader.
	 * 
	 * @param reader the reader containing the CloudAllocation problem instance
	 * @throws IOException if an I/O error occurred
	 */
	public CloudAllocation(Reader reader) throws IOException {
		super();
		
		load(reader);
	}

	/**
	 * Loads the CloudAllocation problem instance from the specified reader.
	 * 
	 * @param reader the file containing the CloudAllocation problem instance
	 * @throws IOException if an I/O error occurred
	 */
	private void load(Reader reader) throws IOException {
		Pattern specificationLine = Pattern.compile("cloudallocation problem specification \\((\\d+) clouds, (\\d+) requests\\)");
		Pattern capacityLine = Pattern.compile(" capacity: \\+(\\d+)");
		Pattern weightLine = Pattern.compile("  size: \\+(\\d+)");
		Pattern profitLine = Pattern.compile("  profit: \\+(\\d+)");

		CommentedLineReader lineReader = null;
		String line = null;
		Matcher matcher = null;
		
		try {
			lineReader = new CommentedLineReader(reader);
			line = lineReader.readLine(); // the problem specification line
			matcher = specificationLine.matcher(line);
			
			if (matcher.matches()) {
				clouds = Integer.parseInt(matcher.group(1));
				requests = Integer.parseInt(matcher.group(2));
			} else {
				throw new IOException("cloudAllocation data file not properly formatted: invalid specification line");
			}
			
			capacity = new int[clouds];
			profit = new int[clouds][requests];
			weight = new int[clouds][requests];
	
			for (int i = 0; i < clouds; i++) {
				line = lineReader.readLine(); // line containing "="
				line = lineReader.readLine(); // line containing "cloud i:"
				line = lineReader.readLine(); // the cloud capacity
				matcher = capacityLine.matcher(line);
				
				if (matcher.matches()) {
					capacity[i] = Integer.parseInt(matcher.group(1));
				} else {
					throw new IOException("cloudAllocation data file not properly formatted: invalid capacity line");
				}
	
				for (int j = 0; j < requests; j++) {
					line = lineReader.readLine(); // line containing "item j:"
					line = lineReader.readLine(); // the item weight
					matcher = weightLine.matcher(line);
					
					if (matcher.matches()) {
						weight[i][j] = Integer.parseInt(matcher.group(1));
					} else {
						throw new IOException("cloudAllocation data file not properly formatted: invalid weight line");
					}
	
					line = lineReader.readLine(); // the item profit
					matcher = profitLine.matcher(line);
					
					if (matcher.matches()) {
						profit[i][j] = Integer.parseInt(matcher.group(1));
					} else {
						throw new IOException("cloudAllocation data file not properly formatted: invalid profit line");
					}
				}
			}
		} finally {
			if (lineReader != null) {
				lineReader.close();
			}
		}
	}

	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CloudAllocation";
	}

	@Override
	public int getNumberOfVariables() {
		// TODO Auto-generated method stub
		return clouds;
	}

	@Override
	public int getNumberOfObjectives() {
		// TODO Auto-generated method stub
		return clouds;
	}

	@Override
	public int getNumberOfConstraints() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void evaluate(Solution solution) {
		boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
		double[] f = new double[clouds];
		double[] g = new double[clouds];

		// calculate the profits and weights for the knapsacks
		for (int i = 0; i < requests; i++) {
			if (d[i]) {
				for (int j = 0; j < clouds; j++) {
					f[j] += profit[j][i];
					g[j] += weight[j][i];
				}
			}
		}

		// check if any weights exceed the capacities
		for (int j = 0; j < clouds; j++) {
			if (g[j] <= capacity[j]) {
				g[j] = 0.0;
			} else {
				g[j] = g[j] - capacity[j];
			}
		}

		// negate the objectives since Knapsack is maximization
		solution.setObjectives(Vector.negate(f));
		solution.setConstraints(g);		
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, clouds, clouds);
		solution.setVariable(0, EncodingUtils.newBinary(requests));
		return solution;
	}

	@Override
	public void close() {
		//do nothing	
	}
	
}
