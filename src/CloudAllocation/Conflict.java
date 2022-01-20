package CloudAllocation;

public class Conflict {
    private int leftPlayer ;
    private int rightPlayer ;
    private int leftPlayerStrategy;
    private int rightPlayerStrategy;

    public Conflict(String conflict)  {
        String[] temp = conflict.split(",");
        leftPlayer = Integer.parseInt(temp[0]);
        leftPlayerStrategy = Integer.parseInt(temp[1]);
        rightPlayer = Integer.parseInt(temp[2]);
        rightPlayerStrategy = Integer.parseInt(temp[3]);
    }

    public String toString(){
        return String.format("%s,%s,%s,%s",leftPlayer,leftPlayerStrategy,rightPlayer,rightPlayerStrategy);
    }
}
