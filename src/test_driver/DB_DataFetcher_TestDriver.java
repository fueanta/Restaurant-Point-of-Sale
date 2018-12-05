package test_driver;

import database.DB_DataFetcher;
import entity.user.User;

public class DB_DataFetcher_TestDriver {

    private static void test_log_in_checker_procedure() {
        System.out.println(DB_DataFetcher.getAccess("taqui", "Taqui@101_Pass"));
        System.out.println(DB_DataFetcher.getAccess("fahim", "Fahim@103_Pass"));
        System.out.println(DB_DataFetcher.getAccess("taqui", "fahim_pass"));
        //DB_ConnectionProvider.turnConnectionOff();
    }

    private static void test_verify_requirements_function() {
        for (String s : DB_DataFetcher.verifyRequirements("taqui", "Taqui@101_Pass")) {
            System.out.print(s + " ");
        }
        //DB_ConnectionProvider.turnConnectionOff();
    }

    private static void test_getAllUsers() {
        for (User user : DB_DataFetcher.getAllUsers()) {
            System.out.println(user.getId() + " " + user.getHire_date());
        }
    }

    public static void main(String[] args) {
        test_getAllUsers();
        //test_log_in_checker_procedure();
        //test_verify_requirements_function();
    }

}
