package database;

import entity.main_sub.ModifiedMainSub;
import gui.generic.errorbox.ErrorBox_Controller;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_DataModifier {

    public static void updateAnyTable(String updateQuery) {
        String plSQL = "" +
                " BEGIN " +
                updateQuery +
                " END;";
        System.out.println("PL/SQL: " + plSQL);
        try {
            PreparedStatement ps = DB_ConnectionProvider.getConnection().prepareStatement(plSQL);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Updating Failed! ", se.getMessage());
        }
    }

    public static void insertMainItem(String name, float price, String description, int type_id, float discount_rate, String availability,
                                      float inventory_cost, int stock_count, String picture) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call insertMainItem(?,?,?,?,?,?,?,?,?)}");

            cstmt.setString(1, name);
            cstmt.setFloat(2, price);
            cstmt.setString(3, description);
            cstmt.setInt(4, type_id);
            cstmt.setFloat(5, discount_rate);
            cstmt.setString(6, availability);
            cstmt.setFloat(7, inventory_cost);
            cstmt.setInt(8, stock_count);
            cstmt.setString(9, picture);

            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Insertion Failed!", se.getMessage());
        }
    }

    public static void deleteMainItem(int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call deleteMainItem(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
    }

    public static void update_main_sub(ObservableList<ModifiedMainSub> list, int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_main_sub(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
        for (ModifiedMainSub item : list) {
            try {
                CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                        .prepareCall("{call insert_main_sub(?,?,?)}");
                cstmt.setInt(1, id);
                cstmt.setInt(2, item.getId());
                cstmt.setInt(3, item.getCount());
                cstmt.executeUpdate();
                cstmt.close();
            }
            catch (SQLException se) {
                ErrorBox_Controller.showError("Updating Failed!", se.getMessage());
            }
        }
    }

    public static void insertSubItem(String name, String description, float unit_cost, int count) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call insert_sub_item(?,?,?,?)}");

            cstmt.setString(1, name);
            cstmt.setString(2, description);
            cstmt.setFloat(3, unit_cost);
            cstmt.setInt(4, count);

            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Insertion Failed!", se.getMessage());
        }
    }

    public static void deleteSubItem(int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_sub_item(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
    }

    public static void insertSupplier(String name, String address, String contact_number, String email,
                                      String details, String date_added) {

        //System.out.println(date_added);
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call insert_supplier(?,?,?,?,?,?)}");

            cstmt.setString(1, name);
            cstmt.setString(2, address);
            cstmt.setString(3, contact_number);
            cstmt.setString(4, email);
            cstmt.setString(5, details);
            cstmt.setString(6, date_added);

            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Insertion Failed!", se.getMessage());
        }
    }

    public static void deleteSupplier(int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_suppliers(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
    }

    public static void insertUser(String username, String password, String admin_access, String description,
                                      Float salary, Float commission, String hire_date, String picture) {

        //System.out.println(hire_date);
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call insert_user(?,?,?,?,?,?,?,?)}");

            cstmt.setString(1, username);
            cstmt.setString(2, password);
            cstmt.setString(3, admin_access);
            cstmt.setString(4, description);
            cstmt.setFloat(5, salary);
            cstmt.setFloat(6, commission);
            cstmt.setString(7, hire_date);
            cstmt.setString(8, picture);

            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Insertion Failed!", se.getMessage());
        }
    }

    public static void deleteUser(int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_user(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
    }

    public static void insertPurchase(String description, String time, String logged_user, Integer supp_id,
                                      Float total_cost, Float discount_rate) {
        System.out.println(time);
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call insert_purchase(?,?,?,?,?,?)}");

            cstmt.setString(1, description);
            cstmt.setString(2, time);
            cstmt.setString(3, logged_user);
            cstmt.setInt(4, supp_id);
            cstmt.setFloat(5, total_cost);
            cstmt.setFloat(6, discount_rate);

            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Insertion Failed!", se.getMessage());
            se.printStackTrace();
        }
    }

    public static void deletePurchase(int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_purchase(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
    }

    public static void update_purchase_item(ObservableList<ModifiedMainSub> list, int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_purchase_item(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
        for (ModifiedMainSub item : list) {
            try {
                CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                        .prepareCall("{call insert_purchase_item(?,?,?)}");
                cstmt.setInt(1, id);
                cstmt.setInt(2, item.getId());
                cstmt.setInt(3, item.getCount());
                cstmt.executeUpdate();
                cstmt.close();
            }
            catch (SQLException se) {
                ErrorBox_Controller.showError("Updating Failed!", se.getMessage());
            }
        }
    }

    public static void insertOrder(String description, String place_time, String logged_user, Float total_cost,
                                   Float discount, String card_number) {
        //System.out.println(time);
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call insert_order(?,?,?,?,?,?)}");

            cstmt.setString(1, description);
            cstmt.setString(2, place_time);
            cstmt.setString(3, logged_user);
            cstmt.setFloat(4, total_cost);
            cstmt.setFloat(5, discount);
            cstmt.setString(6, card_number);

            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Insertion Failed!", se.getMessage());
            se.printStackTrace();
        }
    }

    public static void deleteOrder(int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_order(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
    }

    public static void update_order_item(ObservableList<ModifiedMainSub> list, int id) {
        try {
            CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                    .prepareCall("{call delete_order_item(?)}");
            cstmt.setInt(1, id);
            cstmt.executeUpdate();
            cstmt.close();
        }
        catch (SQLException se) {
            ErrorBox_Controller.showError("Deletion Failed!", se.getMessage());
        }
        for (ModifiedMainSub item : list) {
            try {
                CallableStatement cstmt = DB_ConnectionProvider.getConnection()
                        .prepareCall("{call insert_order_item(?,?,?)}");
                cstmt.setInt(1, id);
                cstmt.setInt(2, item.getId());
                cstmt.setInt(3, item.getCount());
                cstmt.executeUpdate();
                cstmt.close();
            }
            catch (SQLException se) {
                ErrorBox_Controller.showError("Updating Failed!", se.getMessage());
            }
        }
    }

    public static void updateLogin(String username) {
        String query = "UPDATE POS_USER SET LOGGED = 'Y' WHERE USERNAME = '" + username + "'";
        Statement statement = null;
        try {
            statement = DB_ConnectionProvider.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLogout(String username) {
        String query = "UPDATE POS_USER SET LOGGED = 'N' WHERE USERNAME = '" + username + "'";
        Statement statement = null;
        try {
            statement = DB_ConnectionProvider.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
