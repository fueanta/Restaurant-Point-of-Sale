package entity.purchase_item;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PurchaseItem {
    private final SimpleIntegerProperty p_id;
    private final SimpleIntegerProperty s_id;
    private final SimpleFloatProperty price;
    private final SimpleIntegerProperty quantity;

    public PurchaseItem(Integer p_id, Integer s_id, Float price, Integer quantity) {
        this.p_id = new SimpleIntegerProperty(p_id);
        this.s_id = new SimpleIntegerProperty(s_id);
        this.price = new SimpleFloatProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public static PurchaseItem copyObject(PurchaseItem c){
        return new PurchaseItem(c.getP_id(),c.getS_id(),c.getPrice(),c.getQuantity());
    }

    public static String update_Changes(PurchaseItem old, PurchaseItem latest ){
        String list ="";
        if(old.getP_id()!=latest.getP_id()) list+=" P_ID = " +latest.getP_id()+"";
        if(old.getS_id()!=latest.getS_id()) list+=", S_ID = " +latest.getS_id()+"";
        if(old.getPrice()!=latest.getPrice()) list+=", PRICE = " +latest.getPrice()+"";
        if(old.getQuantity()!=latest.getQuantity()) list+=", QUANTITY = " +latest.getQuantity()+"";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_PURCHASE_ITEM SET " + sb.toString()+ " WHERE P_ID = " + old.getP_id() + " AND S_ID = "
                    + old.getS_id() + ";";
        }
        return "";
    }

    public int getP_id() {
        return p_id.get();
    }

    public SimpleIntegerProperty p_idProperty() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id.set(p_id);
    }

    public int getS_id() {
        return s_id.get();
    }

    public SimpleIntegerProperty s_idProperty() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id.set(s_id);
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

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    @Override
    public String toString() {
        return "PurchaseItem{" +
                "p_id=" + p_id +
                ", s_id=" + s_id +
                ", quantity=" + quantity +
                '}';
    }

}
