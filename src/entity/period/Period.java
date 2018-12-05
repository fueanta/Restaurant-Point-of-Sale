package entity.period;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Period {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty user_id;
    private final SimpleStringProperty login_time;
    private final SimpleStringProperty logout_time;

    public Period(Integer id, Integer user_id, String login_time, String logout_time) {
        this.id = new SimpleIntegerProperty(id);
        this.user_id = new SimpleIntegerProperty(user_id);
        this.login_time = new SimpleStringProperty(login_time);
        this.logout_time = new SimpleStringProperty(logout_time);
    }

    public static Period copyObject(Period c){
        return new Period(c.getId(),c.getUser_id(),c.getLogin_time(),c.getLogout_time());
    }

    public static String update_Changes(Period old,Period latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = " +latest.getId() +"";
        if(old.getId()!=latest.getUser_id()) list+=", USER_ID = " +latest.getUser_id() +"";
        /*
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = formatter.format(old.getLogin_time());
        String s2 = formatter.format(latest.getLogin_time());
        if(!s1.equals(s2)) list+=", " +s2);
        s1 = formatter.format(old.getLogout_time());
        s2 = formatter.format(latest.getLogout_time());
        if(!s1.equals(s2)) list+=", " +s2);
        */
        if(!old.getLogin_time().equals(latest.getLogin_time())) list+=", LOGIN_TIME = TO_DATE('" +latest.getLogin_time() +"', 'yyyy/mm/dd hh24:mi:ss')";
        if(!old.getLogout_time().equals(latest.getLogout_time())) list+=", LOGOUT_TIME = TO_DATE('" +latest.getLogout_time() +"', 'yyyy/mm/dd hh24:mi:ss')";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_PERIOD SET " + sb.toString()+ " WHERE ID = " + old.getId();
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

    public String getLogin_time() {
        return login_time.get();
    }

    public SimpleStringProperty login_timeProperty() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time.set(login_time);
    }

    public String getLogout_time() {
        return logout_time.get();
    }

    public SimpleStringProperty logout_timeProperty() {
        return logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.logout_time.set(logout_time);
    }

    @Override
    public String toString() {
        return "Period{" +
                "user_id=" + user_id +
                ", login_time=" + login_time +
                ", logout_time=" + logout_time +
                '}';
    }

}
