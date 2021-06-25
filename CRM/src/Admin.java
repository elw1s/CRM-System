package src;
import DataStructures.BinarySearchTree;
import database.DatabaseCRM;

import java.sql.SQLException;
import java.util.*;

public class Admin extends User implements Comparable<Admin>{
    private PriorityQueue<Schedule> schedules;

    public Admin(String name, String surName, String ID, String password) {
        super(name, surName, ID, password);
        schedules = new PriorityQueue<>();
    }

    public boolean addBusinessDev(User user) throws SQLException {
        if(user == null){
            System.out.println("Invalid input!");
            return false;
        }
        if (user.getID().charAt(0) == 'B'){
            boolean hasExist = DatabaseCRM.UserDB.hasExistUser(user.getID());
            if (hasExist){
                System.out.println("This ID is already in use!");
                return false;
            }
            else {
                Company.addUser(user);
                return true;
            }
        }
        else{
            System.out.println("Invalid user!");
            return false;
        }
    }

    /**
     * Adds a new customer to the system
     * @param user
     * @return
     */
    public boolean addCustomer(User user) throws SQLException {
        if(user == null){
            System.out.println("Invalid input!");
            return false;
        }
        if (user.getID().charAt(0) == 'C'){
            boolean hasExist = DatabaseCRM.UserDB.hasExistUser(user.getID());
            if (hasExist){
                System.out.println("This ID is already in use!");
                return false;
            }
            else {
                Company.addUser(user);
                return true;
            }
        }
        else{
            System.out.println("Invalid user!");
            return false;
        }

    }
    

    /**
     * Removes the customer from the system
     * @param ID
     * @return
     */
    public boolean removeCustomer(String ID) throws SQLException {
        if (ID == null){
            System.out.println("Invalid ID!");
            return false;
        }
        if (ID.charAt(0) == 'C'){
            Customer temp =  (Customer) DatabaseCRM.UserDB.readCustomerFromDB(ID);
            if (temp == null){
                System.out.println("Invalid ID!!");
                return false;
            }
            Company.removeCustomer(ID);
           // DatabaseCRM.UserDB.deleteUserFromDB(ID);
            return true;
        }
        else {
            System.out.println("Invalid ID!");
            return false;
        }
    }

    /**
     * Removes the business developer from the system
     * @param ID
     * @return
     */
    public boolean removeBusinessDev(String ID) throws SQLException {
        if (ID == null){
            System.out.println("Invalid ID!");
            return false;
        }
        if (ID.charAt(0) == 'B'){
            //BusinessDeveloper temp =  (BusinessDeveloper) DatabaseCRM.UserDB.readUserFromDB(userID);
           /* if (temp == null){
                System.out.println("Invalid ID!!");
                return false;
            }*/
            Company.removeBusinessDev(ID);
            //DatabaseCRM.UserDB.deleteUserFromDB(ID);
            return true;
        }
        else {
            System.out.println("Invalid ID!");
            return false;
        }
    }

    /**
     *
     * @param productId
     * @return
     */
    public boolean removeProduct(String productId) throws SQLException {
        if (productId == null){
            System.out.println("Invalid ID!");
            return false;
        }
        Product temp = DatabaseCRM.ProductsDB.getProductFromDB(productId);
        if (temp == null){
            System.out.println("Product not found");
            return false;
        }
        Company.removeProduct(productId);
        //DatabaseCRM.ProductsDB.deleteProductFromDB(productId);
        return true;
    }

    /**
     * Update the user's name
     * @param userID
     * @param name
     */
    public void setUserName(String userID, String name) throws SQLException {
        if (name == null || userID == null){
            System.out.println("Invalid ID or name!");
            return;
        }
        User temp = null;
        if(userID.charAt(0) == 'A')
            temp = Company.getAdmin(userID);
        else if(userID.charAt(0) == 'B')
            temp = Company.getBusinessDev(userID);
        else if(userID.charAt(0) == 'C')
            temp = Company.getCustomer(userID);

        if (temp != null) {
            temp.setName(name);
            Company.updateUser(temp);
        }
    }

    /**
     * Update the user's surname
     * @param userID
     * @param surName
     */
    public void setUserSurName(String userID, String surName) throws SQLException {
        if (surName == null || userID == null){
            System.out.println("Invalid ID or surname!");
            return;
        }
        User temp = null;
        if(userID.charAt(0) == 'A')
            temp = Company.getAdmin(userID);
        else if(userID.charAt(0) == 'B')
            temp = Company.getBusinessDev(userID);
        else if(userID.charAt(0) == 'C')
            temp = Company.getCustomer(userID);

        if (temp != null) {
            temp.setSurName(surName);
            Company.updateUser(temp);
        }
    }

    /**
     * Update the customer's email
     * @param userID
     * @param email
     */

    // TODO: Bu fonksiyon şuan çalışmıyor çünkü Database içerisinde çağırılan fonksiyon email kaydetmesi yapmıyor.
    public void setCustomerEmail(String userID, String email) throws SQLException {
        if (email == null || userID == null){
            System.out.println("Invalid ID or email!");
            return;
        }
        if (userID.charAt(0) == 'C'){
            Customer temp = Company.getCustomer(userID);
            /*if (temp == null){
                System.out.println("Invalid ID!!");
                return;
            }*/
            temp.setEmail(email);
            Company.updateUser(temp);
        }
        else
            System.out.println("Invalid ID!");
    }

    /**
     * Update the customer's phone number
     * @param userID
     * @param phoneNumber
     */

    public void setCustomerPhoneNumber(String userID, String phoneNumber) throws SQLException {
        if (phoneNumber == null || userID == null){
            System.out.println("Invalid ID or email!");
            return;
        }
        if (userID.charAt(0) == 'C'){
            Customer temp = Company.getCustomer(userID);
            temp.setPhoneNumber(phoneNumber);
            Company.updateUser(temp);
        }
        else
            System.out.println("Invalid ID!");
    }

    /**
     * Prints all users in the system
     */
    public void viewAllUsers() throws SQLException {
        DatabaseCRM.UserDB.readAllUserFromDB();
    }

    /**
     * Returns all products in the system
     * @return all products in the system
     */
    public BinarySearchTree<Product> getAllProducts() throws SQLException {
        BinarySearchTree<Product> allProducts = DatabaseCRM.ProductsDB.getAllProductsFromDB();
        return allProducts;
    }

    /**
     * Search for the product specified in the system.
     * If the product exists in the system, it returns
     * the Product object otherwise returns null
     * @param productId
     * @return null or Product object
     */
    public Product findProduct(String productId) throws SQLException {
        if (productId == null){
            System.out.println("Invalid productId!");
            return null;
        }
        return Company.getProduct(productId);
    }


    /**
     * Adds a product to the system
     * @param product
     */
    public void addProduct(Product product) throws SQLException {
        if (product == null){
            System.out.println("Invalid");
            return;
        }
        Company.addProduct(product);
    }

    /**
     *
     * @param schedule
     */
    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
    }

    /**
     *
     * @param scheduleNum
     */
    public void removeSchedule(int scheduleNum){
        if (scheduleNum <= schedules.size() && scheduleNum>0){
            Iterator<Schedule> iter = schedules.iterator();
            Schedule removed = null;
            for (int i=0; i<scheduleNum; i++)
                removed = iter.next();
            schedules.remove(removed);
        }
        else
            System.out.println("Invalid Input!");
    }

    /**
     *
     * @param scheduleNum
     */
    public void manageSchedule(int scheduleNum, int year){
        if (scheduleNum <= schedules.size() && scheduleNum>0){
            Iterator<Schedule> iter = schedules.iterator();
            Schedule schedule = null;
            for (int i=0; i<scheduleNum; i++)
                schedule = iter.next();
            schedules.remove(schedule);
            Date date1 = schedule.getDate();
            date1.setYear(year - 1900);
            schedule.setDate(date1);
            schedules.add(schedule);
        }
        else
            System.out.println("Invalid Input!");
    }

    /**
     *
     */
    public void printsSchedule(){
        if (schedules.isEmpty())
            System.out.println("EMPTY");
        else {
            int i=0;
            for (Schedule ss: schedules) {
                int year = ss.getDate().getYear() + 1900;
                int month = ss.getDate().getMonth() + 1;
                int day = ss.getDate().getDate();
                String str = year + "/" + month + "/" + day;
                System.out.println(++i + " " +str+ " " + ss.getProcess() );
            }
        }
    }

    // TODO:
    public void manageCustomersFeedBack(){}



    /***
     * Compares two admins values by their names
     * @param o
     * @return 0 when names are equal, 1 when the calling admin's name is higher. Returns -1 otherwise.
     */

    @Override
    public int compareTo(Admin o) {
        if (this.getName().compareTo(o.getName()) == 0) {
            return 0;
        } else if (this.getName().compareTo(o.getName()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
