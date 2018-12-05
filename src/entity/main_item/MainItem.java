package entity.main_item;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MainItem {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleFloatProperty price;
    private final SimpleStringProperty description;
    private final SimpleIntegerProperty type_id;
    private final SimpleFloatProperty discount_rate;
    private final SimpleStringProperty availability;
    private final SimpleFloatProperty inventory_cost;
    private final SimpleIntegerProperty stock_count;
    private String picture;

    public MainItem(Integer id, String name, Float price, String description, Integer type_id, Float discount_rate, String availability,
                    Float inventory_cost, Integer stock_count, String picture) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleFloatProperty(price);
        this.description = new SimpleStringProperty(description);
        this.type_id = new SimpleIntegerProperty(type_id);
        this.discount_rate = new SimpleFloatProperty(discount_rate);
        this.availability = new SimpleStringProperty(availability);
        this.inventory_cost = new SimpleFloatProperty(inventory_cost);
        this.stock_count = new SimpleIntegerProperty(stock_count);
        this.picture = picture;
    }

    public static MainItem copyObject(MainItem c){
        return new MainItem(c.getId(),c.getName(),c.getPrice(),c.getDescription(),c.getType_id(),c.getDiscount_rate(),
                c.getAvailability(),c.getInventory_cost(),c.getStock_count(),c.getPicture());
    }

    public static String update_Changes(MainItem old, MainItem latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = " +latest.getId()+"";
        if(!old.getName().equals(latest.getName())) list+=", NAME = '" +latest.getName() +"'";
        if(old.getPrice()!=latest.getPrice()) list+=", PRICE = " +latest.getPrice() +"";
        if(!old.getDescription().equals(latest.getDescription())) list+=", DESCRIPTION = '" +latest.getDescription() +"'";
        if(old.getType_id()!=latest.getType_id()) list+=", TYPE_ID = " +latest.getType_id() +"";
        if(old.getDiscount_rate()!=latest.getDiscount_rate()) list+=", DISCOUNT_RATE = " +latest.getDiscount_rate() +"";
        if(!old.getAvailability().equals(latest.getAvailability())) list+=", AVAILABILITY = '" +latest.getAvailability() +"'";
        if(old.getInventory_cost()!=latest.getInventory_cost()) list+=", INVENTORY_COST = " +latest.getInventory_cost() +"";
        if(old.getStock_count()!=latest.getStock_count()) list+=", STOCK_COUNT = " +latest.getStock_count()+"";

        if (old.getPicture() != null && !old.getPicture().equals("") && latest.getPicture() != null
                && !latest.getPicture().equals("")) {
            if(!old.getPicture().equals(latest.getPicture())) list+=", PICTURE = '" +latest.getPicture() +"'";
        }

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_MAIN_ITEM SET " + sb.toString()+ " WHERE ID = " + old.getId() + ";";
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

    public float getPrice() {
        return price.get();
    }

    public SimpleFloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
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

    public int getType_id() {
        return type_id.get();
    }

    public SimpleIntegerProperty type_idProperty() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id.set(type_id);
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

    public String getAvailability() {
        return availability.get();
    }

    public SimpleStringProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability.set(availability);
    }

    public float getInventory_cost() {
        return inventory_cost.get();
    }

    public SimpleFloatProperty inventory_costProperty() {
        return inventory_cost;
    }

    public void setInventory_cost(float inventory_cost) {
        this.inventory_cost.set(inventory_cost);
    }

    public int getStock_count() {
        return stock_count.get();
    }

    public SimpleIntegerProperty stock_countProperty() {
        return stock_count;
    }

    public void setStock_count(int stock_count) {
        this.stock_count.set(stock_count);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "MainItem{" +
                "id=" + id +
                ", name=" + name +
                ", stock_count=" + stock_count +
                '}';
    }

}
