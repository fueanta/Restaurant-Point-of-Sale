package entity.user;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty admin_access;
    private final SimpleStringProperty description;
    private final SimpleFloatProperty salary;
    private final SimpleFloatProperty commission;
    private final SimpleStringProperty hire_date;
    private String picture;

    public User(Integer id, String username, String password, String admin_access, String description,
                Float salary, Float commission, String hire_date, String picture) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.admin_access = new SimpleStringProperty(admin_access);
        this.description = new SimpleStringProperty(description);
        this.salary = new SimpleFloatProperty(salary);
        this.commission = new SimpleFloatProperty(commission);
        this.hire_date = new SimpleStringProperty(hire_date);
        this.picture = picture;
    }

    public User(Integer id, String username, String password, String admin_access, String description,
                Float salary, Float commission, String hire_date) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.admin_access = new SimpleStringProperty(admin_access);
        this.description = new SimpleStringProperty(description);
        this.salary = new SimpleFloatProperty(salary);
        this.commission = new SimpleFloatProperty(commission);
        this.hire_date = new SimpleStringProperty(hire_date);
    }

    public static User copyObject(User c){
        return new User(c.getId(),c.getUsername(),c.getPassword(),c.getAdmin_access(),c.getDescription(),
                c.getSalary(),c.getCommission(),c.getHire_date(),c.getPicture());
    }

    public static String update_Changes(User old, User latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = " +latest.getId()+"";
        if(!old.getUsername().equals(latest.getUsername())) list+=", USERNAME = '" +latest.getUsername()+"'";
        if(!old.getPassword().equals(latest.getPassword())) list+=", PASSWORD = '" +latest.getPassword()+"'";
        if(!old.getAdmin_access().equals(latest.getAdmin_access())) list+=", ADMIN_ACCESS = '" +latest.getAdmin_access()+"'";
        if(!old.getDescription().equals(latest.getDescription())) list+=", DESCRIPTION = '" +latest.getDescription()+"'";
        if(old.getSalary()!=latest.getSalary()) list+=", SALARY = " +latest.getSalary()+"";
        if(old.getCommission()!=latest.getCommission()) list+=", COMMISSION = " +latest.getCommission()+"";
        /*
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = formatter.format(old.getHire_date());
        String s2 = formatter.format(latest.getHire_date());
        if(!s1.equals(s2)) list+=", " +s2);
        */
        if(!old.getHire_date().equals(latest.getHire_date())) list+=", HIRE_DATE = TO_DATE('" +latest.getHire_date() +"', 'mm/dd/yyyy')";

        if (old.getPicture() != null && !old.getPicture().equals("") && latest.getPicture() != null
                && !latest.getPicture().equals("")) {
            if(!old.getPicture().equals(latest.getPicture())) list+=", PICTURE = '" +latest.getPicture() +"'";
        }

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_USER SET " + sb.toString()+ " WHERE ID = " + old.getId() + ";";
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

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getAdmin_access() {
        return admin_access.get();
    }

    public SimpleStringProperty admin_accessProperty() {
        return admin_access;
    }

    public void setAdmin_access(String admin_access) {
        this.admin_access.set(admin_access);
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

    public float getSalary() {
        return salary.get();
    }

    public SimpleFloatProperty salaryProperty() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary.set(salary);
    }

    public float getCommission() {
        return commission.get();
    }

    public SimpleFloatProperty commissionProperty() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission.set(commission);
    }

    public String getPicture() {
        return picture;
    }

    public String getHire_date() {
        return hire_date.get();
    }

    public SimpleStringProperty hire_dateProperty() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date.set(hire_date);
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", admin_access=" + admin_access +
                '}';
    }

}