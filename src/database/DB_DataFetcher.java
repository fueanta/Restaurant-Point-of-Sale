package database;

import entity.main_item.MainItem;
import entity.main_sub.ModifiedMainSub;
import entity.order.Order;
import entity.purchase.Purchase;
import entity.sub_item.SubItem;
import entity.supplier.Supplier;
import entity.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.jdbc.OracleTypes;
import resource.utility.UtilityProvider;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DB_DataFetcher {

    public static String getAccess(String username, String password) {
        String access = "";
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call log_in_checker(?,?)}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setString(2, username);
            cstmt.setString(3, password);
            cstmt.executeUpdate();
            access = cstmt.getString(1);
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return (access);
    }

    public static String[] verifyRequirements(String username, String password) {
        String title = "Succeeded", message = "Requirement validation passed!";
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call verify_requirements(?,?)}");
            cstmt.setString(1, username);
            cstmt.setString(2, password);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            title = "Failed";
            switch (se.getErrorCode()) {
                case 20001: message = "Username must have at least 4 characters.";
                    break;
                case 20002: message = "Password and Username cannot be similar.";
                    break;
                case 20003: message = "Password is too simple or common to guess.";
                    break;
                case 20004: message = "Password cannot be less than 10 characters.";
                    break;
                case 20005: message = "Password should contain at least one digit.";
                    break;
                case 20006: message = "Password should contain at least one lowercase letter.";
                    break;
                case 20007: message = "Password should contain at least one uppercase letter.";
                    break;
                case 20008: message = "Password should contain at least one punctuation character.";
                    break;
                default: message = se.getMessage();
                    break;
            }
        }
        return new String[] {title, message};
    }

    public static ObservableList<User> getAllUsers() {
        String query = "SELECT * FROM POS_USER ORDER BY ID";
        ObservableList<User> users = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {

                Date date = table.getDate(8);
                SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");

                User user = new User(table.getInt(1), table.getString(2), table.getString(3),
                        table.getString(4), table.getString(5), table.getFloat(6),
                        table.getFloat(7), simpleFormat.format(date), table.getString(9));

                users.add(user);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the users due to:" + se.getMessage());
        }
        return users;
    }

    public static ObservableList<MainItem> getTopSuppliers() {
        String query = "SELECT * FROM TOP_SUPPLIER";
        ObservableList<MainItem> suppliers = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {

                MainItem supplier = new MainItem(
                        0, table.getString(1), table.getFloat(2), "",
                        0,0f,"",0f,0,""
                );

                suppliers.add(supplier);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the users due to:" + se.getMessage());
        }
        return suppliers;
    }

    public static ObservableList<MainItem> getDailySale() {
        String query = "SELECT * FROM DAILY_SALE";
        ObservableList<MainItem> sales = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {

                MainItem sale = new MainItem(
                        0, table.getString(1), table.getFloat(2), "",
                        0,table.getFloat(3),"",table.getFloat(4),0,""
                );

                sales.add(sale);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the users due to:" + se.getMessage());
        }
        return sales;
    }

    public static ObservableList<Supplier> getAllSuppliers() {
        String query = "SELECT * FROM POS_SUPPLIER ORDER BY ID";
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {
                Date date = table.getDate(7);
                SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");
                Supplier supplier = new Supplier(
                        table.getInt(1), table.getString(2), table.getString(3),
                        table.getString(4), table.getString(5), table.getString(6),
                        simpleFormat.format(date)
                );
                suppliers.add(supplier);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the suppliers due to:" + se.getMessage());
        }
        return suppliers;
    }

    public static ObservableList<SubItem> getAllSubitems() {
        String query = "SELECT * FROM POS_SUB_ITEM ORDER BY ID";
        ObservableList<SubItem> subItems = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {
                SubItem subItem = new SubItem(table.getInt(1), table.getString(2),
                        table.getString(3), table.getFloat(4), table.getInt(5));
                subItems.add(subItem);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the sub items due to:" + se.getMessage());
        }
        return subItems;
    }

    public static ObservableList<Purchase> getAllPurchases() {
        String query = "SELECT * FROM POS_PURCHASE ORDER BY ID";

        CallableStatement cstmt = null;
        try {
            cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call update_Total_Purchase_Cost}");
            cstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Purchase> purchases = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);  //update_Total_Purchase_Cost
        try {
            while (table.next()) {
                Date date = table.getDate(3);
                String dateStr = UtilityProvider.getStringDate(date);

                Purchase purchase = new Purchase(table.getInt(1), table.getString(2), dateStr,
                        table.getString(4), table.getInt(5), table.getFloat(6),
                        table.getFloat(7));
                purchases.add(purchase);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the purchases due to:" + se.getMessage());
        }
        return purchases;
    }


    public static ObservableList<Order> getAllOrders() {
        String query = "SELECT * FROM POS_ORDER ORDER BY ID";

        CallableStatement cstmt = null;
        try {
            cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call update_Total_Order_Price}");
            cstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<Order> orders = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {
                Date date = table.getDate(3);
                String dateStr = UtilityProvider.getStringDate(date);

                Order order = new Order(table.getInt(1), table.getString(2), dateStr,
                        table.getString(4), table.getFloat(5),
                        table.getFloat(6), table.getString(7));
                orders.add(order);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the orders due to:" + se.getMessage());
        }
        return orders;
    }





    public static ObservableList<MainItem> getAllMainItems2() {
        String query = "SELECT * FROM POS_MAIN_ITEM ORDER BY ID";
        ObservableList<MainItem> items = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {
                //Image picture = new Image(new File(table.getString(9)).toURI().toString());
                MainItem item = new MainItem(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        table.getString(4), table.getInt(5), table.getFloat(6),
                        table.getString(7), table.getFloat(8), table.getInt(9),
                        table.getString(10)
                        );
                items.add(item);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the Main Items due to:" + se.getMessage());
        }
        return items;
    }

    private static ResultSet getTable(String query) {
        ResultSet table = null;
        try
        {
            Connection connection = DB_ConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            table = statement.executeQuery(query);
            System.out.println(query + " has been executed!");
        }
        catch(SQLException ex)
        {
            System.out.println("Unable to fetch table from -> " + query);
        }
        return table;
    }

    public static ObservableList<MainItem> getAllMainItems() {
        ObservableList<MainItem> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call getMainItems}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //Image picture = new Image(new File(table.getString(9)).toURI().toString());
                MainItem item = new MainItem(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        table.getString(4), table.getInt(5), table.getFloat(6),
                        table.getString(7), table.getFloat(8), table.getInt(9),
                        table.getString(10)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println("Unable to fetch all the Main Items due to:" + se.getMessage());
        }
        return items;
    }

    public static ObservableList<ModifiedMainSub> getOtherSubs(int id) {
        ObservableList<ModifiedMainSub> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call get_other_subs(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, id);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //System.out.println(table.getString(2));
                //int id, String name, float unit_cost, int count, float total_cost
                ModifiedMainSub item = new ModifiedMainSub(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        1, table.getFloat(3)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return items;
    }

    public static ObservableList<ModifiedMainSub> getSubs(int id) {
        ObservableList<ModifiedMainSub> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call get_subs(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, id);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //System.out.println(table.getString(2));
                //int id, String name, float unit_cost, int count, float total_cost
                ModifiedMainSub item = new ModifiedMainSub(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        table.getInt(4), table.getFloat(5)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return items;
    }

    public static ObservableList<ModifiedMainSub> getOtherPurchaseSubs(int id) {
        ObservableList<ModifiedMainSub> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call get_other_puchase_subs(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, id);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //System.out.println(table.getString(2));
                //int id, String name, float unit_cost, int count, float total_cost
                ModifiedMainSub item = new ModifiedMainSub(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        1, table.getFloat(3)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return items;
    }

    public static ObservableList<ModifiedMainSub> getPurchaseSubs(int id) {
        ObservableList<ModifiedMainSub> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call get_purchase_subs(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, id);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //System.out.println(table.getString(2));
                //int id, String name, float unit_cost, int count, float total_cost
                ModifiedMainSub item = new ModifiedMainSub(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        table.getInt(4), table.getFloat(5)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return items;
    }

    public static ObservableList<ModifiedMainSub> getOtherOrderItems(int id) {
        ObservableList<ModifiedMainSub> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call get_other_order_items(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, id);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //System.out.println(table.getString(2));
                //int id, String name, float unit_cost, int count, float total_cost
                ModifiedMainSub item = new ModifiedMainSub(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        1, table.getFloat(3)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return items;
    }

    public static ObservableList<ModifiedMainSub> getOrderItems(int id) {
        ObservableList<ModifiedMainSub> items = FXCollections.observableArrayList();
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{? = call get_order_items(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.setInt(2, id);
            cstmt.executeUpdate();
            ResultSet table = (ResultSet) cstmt.getObject(1);
            while (table.next()) {
                //System.out.println(table.getString(2));
                //int id, String name, float unit_cost, int count, float total_cost
                ModifiedMainSub item = new ModifiedMainSub(
                        table.getInt(1), table.getString(2), table.getFloat(3),
                        table.getInt(4), table.getFloat(5)
                );
                items.add(item);
            }
            table.close();
            cstmt.close();
        }
        catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return items;
    }

}















/*String query = "SELECT * FROM POS_PURCHASE ORDER BY ID";
        ObservableList<Purchase> purchases = FXCollections.observableArrayList();
        ResultSet table = DB_DataFetcher.getTable(query);
        try {
            while (table.next()) {
                Date date = table.getDate(3);
                String dateStr = UtilityProvider.getStringDate(date);

                Purchase purchase = new Purchase(table.getInt(1), table.getString(2), dateStr,
                        table.getString(4), table.getInt(5), table.getFloat(6),
                        table.getFloat(7));
                purchases.add(purchase);
            }
        }
        catch(SQLException se) {
            System.out.println("Unable to fetch all the purchases due to:" + se.getMessage());
        }
        return purchases;*/