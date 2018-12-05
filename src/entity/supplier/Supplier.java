package entity.supplier;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name, address, contact_number, email, details, date_added;

    public Supplier(int id, String name, String address, String contact_number, String email, String details, String date_added) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.contact_number = new SimpleStringProperty(contact_number);
        this.email = new SimpleStringProperty(email);
        this.details = new SimpleStringProperty(details);
        this.date_added = new SimpleStringProperty(date_added);
    }

    public static Supplier copyObject(Supplier c){
        return new Supplier(c.getId(),c.getName(),c.getAddress(),c.getContact_number(),c.getEmail(),c.getDetails(),c.getDate_added());
    }

    public static String update_Changes(Supplier old, Supplier latest ){
        String list ="";
        if(old.getId()!=latest.getId()) list+=" ID = "+latest.getId()+" ";
        if(!old.getName().equals(latest.getName())) list+=", NAME = '"+latest.getName()+"'";
        if(!old.getAddress().equals(latest.getAddress())) list+=", ADDRESS = '"+latest.getAddress()+"'";
        if(!old.getContact_number().equals(latest.getContact_number())) list+=", CONTACT_NUMBER = '"+latest.getContact_number()+"'";
        if(!old.getEmail().equals(latest.getEmail())) list+=", EMAIL = '"+latest.getEmail()+"'";
        if(!old.getDetails().equals(latest.getDetails())) list+=", DETAILS = '"+latest.getDetails()+"'";
        if(!old.getDate_added().equals(latest.getDate_added())) list+=", DATE_ADDED = TO_DATE('"+latest.getDate_added()+"', 'mm/dd/yyyy')";

        if (!list.equals("")) {
            StringBuilder sb = new StringBuilder(list);
            sb.deleteCharAt(0);
            return "UPDATE POS_SUPPLIER SET " + sb.toString()+ " WHERE ID = " + old.getId() + ";";
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getContact_number() {
        return contact_number.get();
    }

    public SimpleStringProperty contact_numberProperty() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number.set(contact_number);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDetails() {
        return details.get();
    }

    public SimpleStringProperty detailsProperty() {
        return details;
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public String getDate_added() {
        return date_added.get();
    }

    public SimpleStringProperty date_addedProperty() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added.set(date_added);
    }

    @Override
    public String toString() {
        return "Supplier: " +
                "id = " + id +
                ", name = " + name + ".";
    }

}