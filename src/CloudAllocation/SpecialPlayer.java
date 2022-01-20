package CloudAllocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @object A typical SpecialPlayer is
 *
 * @attributes
 *      numberOfProperties int
 *      properties         List<Integer>
 *      weights            List<Integer>
 */
public class SpecialPlayer {
    private int numberOfProperties;
    private List<Integer> properties = new ArrayList<>();
    private List<Integer> weights = new ArrayList<>();
    public SpecialPlayer( ){
    }

    /**
     *
     * @return properties size
     */
    public int getNumberOfProperties() {
        return numberOfProperties;
    }

    /**
     * @modifies properties
     * @effects
     *      add more property into properties
     */
    public void addProperty(int property){
        properties.add(property);
    }
    /**
     * @modifies weight
     * @effects
     *      add more weight into weights
     */
    public void addWeight(int weight){
        weights.add(weight);
    }
    
    /**
     * @modifies properties
     * @effects
     *      set number of Properties
     */
    public void setNumberOfProperties(int numberOfProperties){
        this.numberOfProperties = numberOfProperties;
    }

    /**
     *
     */
    public void displayInf(){
        System.out.println("Properties : ");
        properties.forEach(x -> System.out.print(x + "\t"));
        System.out.println("\n" + "Weight");
        weights.forEach(x -> System.out.print(x + "\t"));
    }
}
