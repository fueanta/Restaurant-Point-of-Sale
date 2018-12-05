package entity.order;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty description;
    private final SimpleStringProperty place_time;
    private final SimpleStringProperty logged_user;
    private final SimpleFloatProperty total_cost;
    private final SimpleFloatProperty discount;
    private final SimpleStringProperty card_number;

    public Order(Integer id, String description, String place_time, String logged_user, Float total_cost, Float discount, String card_number) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.place_time = new SimpleStringProperty(place_time);
        this.logged_user = new SimpleStringProperty(logged_user);
        this.total_cost = new SimpleFloatProperty(total_cost);
        this.discount = new SimpleFloatProperty(discount);
        this.card_number = new SimpleStringProperty(card_number);
    }

    public static Order copyObject(Order c){
        return new Order(c.getId(),c.getDescription(),c.getPlace_time(), c.getLogged_user(),c.getTotal_cost(),c.getDiscount(),
                c.getCard_number());
    }

    public static String update_Changes(Order old,Order latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = "+latest.getId() +"";
        if(!old.getDescription().equals(latest.getDescription())) list+=", DESCRIPTION = '"+latest.getDescription() +"'";
        /*
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = formatter.format(old.getPlace_time());
        String s2 = formatter.format(latest.getPlace_time());
        if(!s1.equals(s2)) list+=", "+s2);
        s1 = formatter.format(old.getServe_time());
        s2 = formatter.format(latest.getServe_time());
        if(!s1.equals(s2)) list+=", "+s2);
        */
        if(!old.getPlace_time().equals(latest.getPlace_time())) list+=", PLACE_TIME = TO_DATE('"+latest.getPlace_time() +"', 'mm/dd/yyyy hh24:mi:ss')";
        if(!old.getLogged_user().equals(latest.getLogged_user())) list+=", LOGGED_USER = '"+latest.getLogged_user() +"'";
        if(old.getTotal_cost()!=latest.getTotal_cost()) list+=", TOTAL_PRICE = "+latest.getTotal_cost() +"";
        if(old.getDiscount()!=latest.getDiscount()) list+=", DISCOUNT = "+latest.getDiscount() +"";
        if(!old.getCard_number().equals(latest.getCard_number())) list+=", CARD_NUMBER = '"+latest.getCard_number() +"'";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_ORDER SET " + sb.toString()+ " WHERE ID = " + old.getId() + ";";
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

    public String getPlace_time() {
        return place_time.get();
    }

    public SimpleStringProperty place_timeProperty() {
        return place_time;
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

    public float getTotal_cost() {
        return total_cost.get();
    }

    public SimpleFloatProperty total_costProperty() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost.set(total_cost);
    }

    public float getDiscount() {
        return discount.get();
    }

    public SimpleFloatProperty discountProperty() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount.set(discount);
    }

    public String getCard_number() {
        return card_number.get();
    }

    public SimpleStringProperty card_numberProperty() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number.set(card_number);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", logged_user=" + logged_user +
                ", total_cost=" + total_cost +
                '}';
    }

}
