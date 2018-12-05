package entity.change_log;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ChangeLog {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty user_id;
    private final SimpleStringProperty description;

    public ChangeLog(Integer id, Integer user_id, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.user_id = new SimpleIntegerProperty(user_id);
        this.description = new SimpleStringProperty(description);
    }

    public static ChangeLog copyObject(ChangeLog c){
        return new ChangeLog(c.getId(),c.getUser_id(),c.getDescription());
    }

    public static String update_Changes(ChangeLog old,ChangeLog latest ){
        String changes ="";
        if(old.getId()!=latest.getId()) changes+=" ID = "+latest.getId()+"";
        if(old.getUser_id()!=latest.getUser_id()) changes+=", U_ID = "+latest.getUser_id()+"";
        if(!old.getDescription().equals(latest.getDescription())) changes+=", DESCRIPTION = '"+latest.getDescription()+"'";

        if (!changes.equals("")) {
            StringBuilder sb = new StringBuilder(changes);
            sb.deleteCharAt(0);
            return "UPDATE POS_CHANGE_LOG SET " + sb.toString() + " WHERE ID = " + old.getId();
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

    public int getUser_id() {
        return user_id.get();
    }

    public SimpleIntegerProperty user_idProperty() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id.set(user_id);
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

    @Override
    public String toString() {
        return "ChangeLog{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", description=" + description +
                '}';
    }

}
