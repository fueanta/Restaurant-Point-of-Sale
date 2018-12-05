package entity.type;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Type {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleFloatProperty discount_rate;

    public Type(Integer id, String name, String description, Float discount_rate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.discount_rate = new SimpleFloatProperty(discount_rate);
    }

    public static Type copyObject(Type c){
        return new Type(c.getId(),c.getName(),c.getDescription(),c.getDiscount_rate());
    }

    public static String update_Changes(Type old, Type latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = "+latest.getId()+"";
        if(!old.getName().equals(latest.getName())) list+=", NAME = '"+latest.getName()+"'";
        if(!old.getDescription().equals(latest.getDescription())) list+=", DESCRIPTION = '"+latest.getDescription()+"'";
        if(old.getDiscount_rate()!=latest.getDiscount_rate()) list+=", DISCOUNT_RATE = "+latest.getDiscount_rate()+"";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_TYPE SET " + sb.toString()+ " WHERE ID = " + old.getId();
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

    public float getDiscount_rate() {
        return discount_rate.get();
    }

    public SimpleFloatProperty discount_rateProperty() {
        return discount_rate;
    }

    public void setDiscount_rate(float discount_rate) {
        this.discount_rate.set(discount_rate);
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", discount_rate=" + discount_rate +
                '}';
    }

}
