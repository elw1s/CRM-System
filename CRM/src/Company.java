package src;
import DataStructures.*;
import database.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.*;

public class Company extends DatabaseCRM {
    private String companyName;

    private static List<Admin> admin;
    private static List<BusinessDeveloper> businessDev;
    private static SkipList<Customer> customer;
    private static AVLTree<Product> products;
    private static Queue<UserInformationSystem> uis;

    public Company(String companyName) throws SQLException, ClassNotFoundException {
        DatabaseCRM.connectDB();
        admin = new ArrayList<>();
        QuickSort.sort(admin);
        businessDev = new ArrayList<>();
        customer = new SkipList<>();
        products = new AVLTree<>();
        uis = new LinkedList<>();
        this.companyName = companyName;
        setFields();
    }

    public String getCompanyName() {
        return companyName;
    }

    /***
     * Prints all the admins
     */
    public void showAllAdmins() {
        System.out.println("\t*******ADMINS*******");
        System.out.println(String.format("%30s\t%30s\t%10s", "Name", "Surname", "ID"));
        ;
        for (Admin value : admin) {
            System.out.println(String.format("%30s\t%30s\t%10s", value.getName(),
                    value.getSurName(),
                    value.getID()));
        }
    }

    /***
     * Prints all the business developers
     */
    public void showAllBusinessDev() {
        System.out.println("\t*******BUSINESS DEVELOPERS*******");
        System.out.println(String.format("%30s\t%30s\t%10s", "Name", "Surname", "ID"));
        NavigableMap<String, BusinessDeveloper> busDev = new TreeMap<>();

        for(int i = 0; i < businessDev.size(); i++){
            busDev.put(businessDev.get(i).getID() , businessDev.get(i));
        }

        while(busDev.size() > 0){
            Map.Entry<String , BusinessDeveloper> entry = busDev.pollFirstEntry();
            System.out.println(String.format("%30s\t%30s\t%10s", entry.getValue().getName(),
                    entry.getValue().getSurName(),
                    entry.getKey()));
        }
    }

    /***
     * Prints all the customers
     */
    public void showAllCustomers() {
        System.out.println("\t*******CUSTOMERS*******");
        System.out.println(String.format("%30s\t%30s\t%10s\t%10s", "Name", "Surname", "ID", "Phone Number"));
        ;
        SkipList.SkipListIter iter = (SkipList.SkipListIter) customer.iterator();
        while (iter.hasNext()) {
            Customer temp = (Customer) iter.next();
            System.out.println(String.format("%30s\t%30s\t%10s\t%10s", temp.getName(), temp.getSurName(),
                    temp.getID(), temp.getPhoneNumber()));

        }
    }

    private void setFields() throws SQLException {
        admin = DatabaseCRM.UserDB.getAllAdminsFromDB();
        customer = DatabaseCRM.UserDB.getAllCustomersFromDB();
        businessDev = DatabaseCRM.UserDB.getAllDevelopersFromDB();
        products = DatabaseCRM.ProductsDB.getAllProductsFromDB();
        for(int i = 0; i < customer.size(); i++){
            Queue<String> message_database = DatabaseCRM.MessagesDB.getMessagesOfCustomerFromDB(customer.get(i).getID());
            customer.get(i).setMessage(message_database);
            int size = message_database.size();
            for(int k = 0; k < size; k++){
                uis.add(new UserInformationSystem((Customer) customer.get(i) , message_database.poll()));
            }

        }

    }

    /***
     * Returns the admins
     * @return Returns the all admins in a List
     */
    public List<Admin> getAllAdmins() {
        return admin;
    }

    /***
     * Returns the products
     * @return Returns the all products in BinarySearchTree
     */
    public AVLTree<Product> getAllProducts() {
        return products;
    }

    /***
     * Returns the Business Developers
     * @return Returns the all business developers in a list
     */
    public List<BusinessDeveloper> getAllBusinessDevs() {
        return businessDev;
    }

    /***
     * Returns the UserInformationSystem objects
     * @return Returns the all UserInformationSystem objects.
     */
    public Queue<UserInformationSystem> getUis() {
        return uis;
    }

    /***
     * Returns the Customers
     * @return Returns the all customers in a Skip List
     */
    public SkipList<Customer> getAllCustomers() {
        return customer;
    }

    /**
     * Returns the admin which has the given ID
     *
     * @param ID
     * @return Admin object with the given ID
     * @throws NoSuchElementException
     */
    public static Admin getAdmin(String ID) throws NoSuchElementException {
        for (Admin value : admin) {
            if (value.getID().equals(ID)) return value;
        }
        throw new NoSuchElementException("There is no admin with the given ID");
    }

    /**
     * Returns the business developer which has the given ID
     *
     * @param ID
     * @return BusinessDev object with the given ID
     * @throws NoSuchElementException
     */
    public static BusinessDeveloper getBusinessDev(String ID) throws NoSuchElementException {
        for (BusinessDeveloper businessDeveloper : businessDev) {
            if (businessDeveloper.getID().equals(ID)) return businessDeveloper;
        }
        throw new NoSuchElementException("There is no business developer with the given ID");
    }

    /**
     * Returns the customer which has the given ID
     *
     * @param ID
     * @return Customer object with the given ID
     * @throws NoSuchElementException
     */
    public static Customer getCustomer(String ID) throws NoSuchElementException {
        SkipList.SkipListIter iter = (SkipList.SkipListIter) customer.iterator();
        while (iter.hasNext()) {
            Customer temp = (Customer) iter.next();
            if (temp.getID().equals(ID)) return temp;
        }
        throw new NoSuchElementException("There is no customer with the given ID");
    }

    /***
     * All users can login by using this method.
     * @param ID
     * @param password
     * @return
     */
    public boolean login(String ID, String password){
        if(ID.charAt(0) == 'A'){
            for(int i = 0; i < admin.size(); i++){
                if(admin.get(i).getID().equals(ID) && admin.get(i).getPassword().equals(password)) return true;
            }
        }
        else if(ID.charAt(0) == 'B'){
            for(int i = 0; i < businessDev.size(); i++){
                if(businessDev.get(i).getID().equals(ID) && businessDev.get(i).getPassword().equals(password)) return true;
            }
        }
        else if(ID.charAt(0) == 'C'){
            for(int i = 0; i < customer.size(); i++){
                if(customer.get(i).getID().equals(ID) && customer.get(i).getPassword().equals(password)) return true;
            }
        }
        return false;
    }

    public void signUp(User user) throws SQLException {
        if(user.getClass().toString().equals("class src.Admin")){
            admin.add(new Admin(user.getName(),user.getSurName(), user.getID(), user.getPassword()));
            DatabaseCRM.UserDB.createUserInDB(user);
        }
        else if(user.getClass().toString().equals("class src.BusinessDeveloper"))
        {
            businessDev.add(new BusinessDeveloper(user.getName(),user.getSurName(), user.getID(), user.getPassword()));
            DatabaseCRM.UserDB.createUserInDB(user);
        }
        else if(user.getClass().toString().equals("class src.Customer")){
            customer.add(new Customer(user.getName(),user.getSurName(), user.getID(), user.getPassword()));
            DatabaseCRM.UserDB.createCustomerInDB((Customer) user);
        }

    }

    /***
     * Adds new product into the Database and also into the list
     * @param product
     */
    public static void addProduct(Product product) throws SQLException {
        products.add(product);
        DatabaseCRM.ProductsDB.createProduct(product);
    }

    /***
     * Removes the product from the Binary Search Tree and also from Database
     * @param ID
     * @throws SQLException
     */
    public static void removeProduct(String ID) throws SQLException {
        AVLTree.Iterator iter = products.iterator();
        while(iter.hasNext()){
            Product product = (Product) iter.next();
            if(product.getID().equals(ID)){
                products.delete(product);
                DatabaseCRM.ProductsDB.deleteProductFromDB(product.getID());
                System.out.println("Product is removed!");
                return;
            }
        }
    }

    /***
     * Updates the name of the Product
     * @param product
     * @throws SQLException
     */
    public static void updateProduct(Product product) throws SQLException {
        DatabaseCRM.ProductsDB.updateProductName(product.getID(), product.getName());
        AVLTree.Iterator iter = products.iterator();
        while(iter.hasNext()){
            Product item = (Product) iter.next();
            if(item.getID().equals(product.getID())){
                products.delete(item);
                products.add(product);
            }
        }
    }

    /***
     * Searches a product in BinarySearchTree
     * @param ID
     * @return Product if it exists, otherwise null
     */
    public static Product getProduct(String ID){
        AVLTree.Iterator iter = products.iterator();

        while(iter.hasNext()){
            Product item = (Product) iter.next();
            if(item.getID().equals(ID)){
                return item;
            }
        }
        return null;
    }

    public static int numberOfProducts(){
        AVLTree.Iterator iter = products.iterator();
        int count = 0;
        while(iter.hasNext()){
            count++;
            iter.next();
        }
        return count;
    }

    /***
     * Removes admin from the list and also database
     * @param ID
     * @throws SQLException
     */
    public static void removeAdmin(String ID) throws SQLException {
        for(int i = 0; i < admin.size(); i++){
            if(admin.get(i).getID().equals(ID)){
                admin.remove(i);
                DatabaseCRM.UserDB.deleteUserFromDB(admin.get(i).getID());
                System.out.println("Admin is removed!");
                return;
            }
        }
    }

    /***
     * Removes the Customer from the SkipList and also from database
     * @param ID
     * @throws SQLException
     */
    public static void removeCustomer(String ID) throws SQLException {
        for(int i = 0; i < customer.size(); i++){
            if(customer.get(i).getID().equals(ID)){
                customer.remove(customer.get(i));
                DatabaseCRM.UserDB.deleteUserFromDB(customer.get(i).getID());
                System.out.println("Customer is removed!");
                return;
            }
        }
    }

    /***
     * Removes Business Developer from the list and also database
     * @param ID
     * @throws SQLException
     */
    public static void removeBusinessDev(String ID) throws SQLException {
        for(int i = 0; i < businessDev.size(); i++){
            if(businessDev.get(i).getID().equals(ID)){
                DatabaseCRM.UserDB.deleteUserFromDB(businessDev.get(i).getID());
                businessDev.remove(i);
                System.out.println("Business Developer is removed!");
                return;
            }
        }
    }

    /***
     * Updates the user
     * @param user
     * @throws NoSuchElementException
     * @throws SQLException
     */
    public static void updateUser(User user) throws NoSuchElementException, SQLException {
        if(user == null) throw new NoSuchElementException();
        if(user.getID().charAt(0) == 'C'){
            DatabaseCRM.UserDB.updateCustomerInDB((Customer) user);
            for(int i = 0; i < customer.size(); i++){
                if(customer.get(i).getID().equals(user.getID())){
                    customer.remove(customer.get(i));
                    customer.add((Customer) user);
                }
            }
        }
        else{
            DatabaseCRM.UserDB.updateUserInDB(user);
            if(user.getID().charAt(0) == 'A'){
                for(int i = 0; i < admin.size(); i++){
                    if(admin.get(i).getID().equals(user.getID())){
                        admin.set(i, (Admin) user);
                    }
                }
            }
            else if(user.getID().charAt(0) == 'B'){
                for(int i = 0; i < businessDev.size(); i++){
                    if(businessDev.get(i).getID().equals(user.getID())){
                        businessDev.set(i, (BusinessDeveloper) user);
                    }
                }
            }
        }

    }

    /***
     * Adds new user into the Database
     * @param user
     * @throws SQLException
     */
    public static void addUser(User user) throws SQLException {
        if(user.getID().charAt(0) == 'A'){
            admin.add((Admin) user);
            DatabaseCRM.UserDB.createUserInDB(user);
        }
        else if(user.getID().charAt(0) == 'B'){
            businessDev.add((BusinessDeveloper) user);
            DatabaseCRM.UserDB.createUserInDB(user);
        }
        else {
            customer.add((Customer) user);
            DatabaseCRM.UserDB.createCustomerInDB((Customer) user);
        }
    }

    /**
     * Adds new message into the uis field
     * @param item
     */
    protected static void addNewMessage(UserInformationSystem item) throws SQLException {
        uis.add(item);
        DatabaseCRM.MessagesDB.createMessageDB(item.getUser(),item.getMessage());
    }

    /***
     * Returns the number of the customers
     * @return Returns the number of customers
     */
    public int getNumberOfCustomers(){
        return customer.size();
    }


}