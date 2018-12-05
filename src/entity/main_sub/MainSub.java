package entity.main_sub;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MainSub {
    private final SimpleIntegerProperty m_id;
    private final SimpleIntegerProperty s_id;
    private final SimpleStringProperty s_name;
    private final SimpleIntegerProperty s_count;

    public MainSub(Integer m_id, Integer s_id, String s_name, Integer s_count) {
        this.m_id = new SimpleIntegerProperty(m_id);
        this.s_id = new SimpleIntegerProperty(s_id);
        this.s_name = new SimpleStringProperty(s_name);
        this.s_count = new SimpleIntegerProperty(s_count);
    }

    public static MainSub copyObject(MainSub c){
        return new MainSub(c.getM_id(),c.getS_id(), c.getS_name(), c.getS_count());
    }

    public static String update_Changes(MainSub old,MainSub latest){
        String list ="";
        if(old.getM_id()!=latest.getM_id()) list+=" M_ID = "+latest.getM_id() +"";
        if(old.getS_id()!=latest.getS_id()) list+=", S_ID = "+latest.getS_id() +"";
        if(old.getS_count()!=latest.getS_count()) list+=", S_COUNT = "+latest.getS_count() +"";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_MAIN_SUB SET " + sb.toString()+ " WHERE M_ID = " + old.getM_id() + " AND S_ID = " + old.getS_id()
                    + ";";
        }
        return "";
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

    public int getS_id() {
        return s_id.get();
    }

    public SimpleIntegerProperty s_idProperty() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id.set(s_id);
    }

    public String getS_name() {
        return s_name.get();
    }

    public SimpleStringProperty s_nameProperty() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name.set(s_name);
    }

    public int getS_count() {
        return s_count.get();
    }

    public SimpleIntegerProperty s_countProperty() {
        return s_count;
    }

    public void setS_count(int s_count) {
        this.s_count.set(s_count);
    }

    @Override
    public String toString() {
        return "MainSub{" +
                "m_id=" + m_id +
                ", s_id=" + s_id +
                ", s_count=" + s_count +
                '}';
    }

}
