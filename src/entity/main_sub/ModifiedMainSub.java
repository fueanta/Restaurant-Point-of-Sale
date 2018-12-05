package entity.main_sub;

public class ModifiedMainSub {
    private int id;
    private String name;
    private float unit_cost;
    private int count;
    private float total_cost;

    public ModifiedMainSub(int id, String name, float unit_cost, int count, float total_cost) {
        this.id = id;
        this.name = name;
        this.unit_cost = unit_cost;
        this.count = count;
        this.total_cost = total_cost;
    }

    public ModifiedMainSub() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(float unit_cost) {
        this.unit_cost = unit_cost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
    }

}
