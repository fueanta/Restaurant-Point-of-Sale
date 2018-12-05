package entity.purchase;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Purchase {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty description;
    private final SimpleStringProperty time;
    private final SimpleStringProperty logged_user;
    private final SimpleIntegerProperty supp_id;
    private final SimpleFloatProperty total_cost;
    private final SimpleFloatProperty discount_rate;

    public Purchase(Integer id, String description, String time, String logged_user, Integer supp_id, Float total_cost,
                    Float discount_rate) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.time = new SimpleStringProperty(time);
        this.logged_user = new SimpleStringProperty(logged_user);
        this.supp_id = new SimpleIntegerProperty(supp_id);
        this.total_cost = new SimpleFloatProperty(total_cost);
        this.discount_rate = new SimpleFloatProperty(discount_rate);
    }

    public static Purchase copyObject(Purchase c){
        return new Purchase(c.getId(),c.getDescription(),c.getTime(),c.getLogged_user(),c.getSupp_id(),c.getTotal_cost(),c.getDiscount_rate());
    }

    public static String update_Changes(Purchase old,Purchase latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = " +latest.getId() +"";
        if(!old.getDescription().equals(latest.getDescription())) list+=", DESCRIPTION = '" +latest.getDescription() +"'";
        if(!old.getTime().equals(latest.getTime())) list+=", TIME = TO_DATE('" +latest.getTime() +"', 'mm/dd/yyyy hh24:mi:ss')";
        if(!old.getLogged_user().equals(latest.getLogged_user())) list+=", LOGGED_USER = '" +latest.getLogged_user() +"'";
        if(old.getSupp_id()!=latest.getSupp_id()) list+=", SUPP_ID = " +latest.getSupp_id() +"";
        if(old.getTotal_cost()!=latest.getTotal_cost()) list+=", TOTAL_COST = " +latest.getTotal_cost() +"";
        if(old.getDiscount_rate()!=latest.getDiscount_rate()) list+=", DISCOUNT_RATE = " +latest.getDiscount_rate() +"";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_PURCHASE SET " + sb.toString()+ " WHERE ID = " + old.getId() + ";";
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getLogged_user() {
        return logged_user.get();
    }

    public SimpleStringProperty logged_userProperty() {
        return logged_user;
    }

    public void setLogged_user(String logged_user) {
        this.logged_user.set(logged_user);
    }

    public int getSupp_id() {
        return supp_id.get();
    }

    public SimpleIntegerProperty supp_idProperty() {
        return supp_id;
    }

    public void setSupp_id(int supp_id) {
        this.supp_id.set(supp_id);
    }

    public float getTotal_cost() {
        return total_cost.get();
    }

    public SimpleFloatProperty total_costProperty() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost.set(total_cost);
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
        return "Purchase{" +
                "id=" + id +
                ", time=" + time +
                ", total_cost=" + total_cost +
                '}';
    }

}
