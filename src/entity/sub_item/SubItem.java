package entity.sub_item;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SubItem {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleFloatProperty unit_cost;
    private final SimpleIntegerProperty count;

    public SubItem(int id, String name, String description, float unit_cost, int count) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.unit_cost = new SimpleFloatProperty(unit_cost);
        this.count = new SimpleIntegerProperty(count);
    }

    public static SubItem copyObject(SubItem c){
        return new SubItem(c.getId(),c.getName(),c.getDescription(),c.getUnit_cost(),c.getCount());
    }

    public static String update_Changes(SubItem old, SubItem latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = " +latest.getId()+"";
        if(!old.getName().equals(latest.getName())) list+=", NAME = '" +latest.getName()+"'";
        if(!old.getDescription().equals(latest.getDescription())) list+=", DESCRIPTION = '" +latest.getDescription()+"'";
        if(old.getUnit_cost()!=latest.getUnit_cost()) list+=", UNIT_COST = " +latest.getUnit_cost()+" ";
        if(old.getCount()!=latest.getCount()) list+=", S_COUNT = " +latest.getCount()+" ";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_SUB_ITEM SET " + sb.toString()+ " WHERE ID = " + old.getId() + ";";
        }
        return "";
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public float getUnit_cost() {
        return unit_cost.get();
    }

    public SimpleFloatProperty unit_costProperty() {
        return unit_cost;
    }

    public void setUnit_cost(float unit_cost) {
        this.unit_cost.set(unit_cost);
    }

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
