package CloudAllocation;

import java.util.List;

/**
 * @object A typical Normal Player is
 *
 * @attributes
 *      strategies List<Strategy>
 */
public class NormalPlayer {
    private List<Strategy> strategies;
    public NormalPlayer(List<Strategy> strategies){
        this.strategies = strategies;
    }

    public List<Strategy> getStrategiesList() {
        return strategies;
    }

    public int getStrategiesSize(){
        return strategies.size();
    }
    public Strategy getStrategyAt(int index){
        return strategies.get(index);
    }

    public void display(){
        for(Strategy s: strategies){
            System.out.print("Strategy " + (strategies.indexOf(s) + 1) + ":\t"  );
            for (int p : s.getProperties()){
                System.out.print(p + "\t");
            }
            System.out.println();
        }
    }
}
