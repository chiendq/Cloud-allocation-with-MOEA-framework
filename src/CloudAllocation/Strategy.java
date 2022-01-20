package CloudAllocation;

import java.util.ArrayList;
import java.util.List;

public class Strategy{
    private List<Integer> properties = new ArrayList<>();

    public List<Integer> getProperties() {
        return properties;
    }

    public int getPropertyAt(int index){
        return properties.get(index);
    }

    public void addProperty(int property){
        properties.add(property);
    }
}