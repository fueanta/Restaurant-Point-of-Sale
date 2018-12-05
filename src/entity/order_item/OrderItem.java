package entity.order_item;

import javafx.beans.property.SimpleIntegerProperty;

public class OrderItem {
    private final SimpleIntegerProperty o_id;
    private final SimpleIntegerProperty m_id;
    private final SimpleIntegerProperty quantity;

    public OrderItem(Integer o_id, Integer m_id, Integer quantity) {
        this.o_id = new SimpleIntegerProperty(o_id);
        this.m_id = new SimpleIntegerProperty(m_id);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public static OrderItem copyObject(OrderItem c){
        return new OrderItem(c.getO_id(),c.getM_id(),c.getQuantity());
    }

    public static String update_Changes(OrderItem old,OrderItem latest ){
        String list ="";
        if(old.getO_id()!=latest.getO_id()) list+=" O_ID = " +latest.getO_id() +"";
        if(old.getM_id()!=latest.getM_id()) list+=", M_ID = " +latest.getM_id() +"";
        if(old.getQuantity()!=latest.getQuantity()) list+=", QUANTITY = " +latest.getQuantity() +"";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_ORDER_ITEM SET " + sb.toString()+ " WHERE O_ID = " + old.getO_id() + " AND M_ID = " + old.getM_id()
                    + ";";
        }
        return "";
    }

    public int getO_id() {
        return o_id.get();
    }

    public SimpleIntegerProperty o_idProperty() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id.set(o_id);
    }

    public int getM_id() {
        return m_id.get();
    }

    public SimpleIntegerProperty m_idProperty() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id.set(m_id);
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
        return "OrderItem{" +
                "o_id=" + o_id +
                ", m_id=" + m_id +
                ", quantity=" + quantity +
                '}';
    }

}
