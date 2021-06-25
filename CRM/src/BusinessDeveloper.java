package src;

import DataStructures.Edge;
import DataStructures.GenericGraph.src.GenericGraph;
import DataStructures.Graph;
import DataStructures.ListGraph;
import DataStructures.SkipList;
import database.DatabaseCRM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class BusinessDeveloper extends User {
    private PriorityQueue<Schedule> schedules;
    private PriorityQueue<Schedule> temp;
    private GenericGraph<String> productGraph;
    private int ctr=0;


    public BusinessDeveloper(String name, String surName, String ID, String password) {
        super(name, surName, ID, password);
        schedules = new PriorityQueue<>();
        temp = new PriorityQueue<>();
        productGraph = new GenericGraph<String>();
        fillGraph();
    }

    private void fillGraph(){
        productGraph.addEdge("Computer","Keyboard");
        productGraph.addEdge("Computer","Mouse");
        productGraph.addEdge("Camera","Cell Phones");
        productGraph.addEdge("Headphones","Cell Phones");
        productGraph.addEdge("Home Audio","Headphones");
        productGraph.addEdge("Office Electronics","Mouse");
        productGraph.addEdge("Television","Video");
        productGraph.addEdge("Video Game","Computer");
        productGraph.addEdge("Video Projectors","Television");
        productGraph.addEdge("Service Plans","Television");

    }

    /***
     * Sets customer name using customer ID
     * @param userID
     * @param name
     * @throws Exception
     */
    public void setCustomerName(String userID, String name) throws SQLException {
        setName(name);
        User user = new User(name , this.getSurName() , userID , this.getPassword());
        Company.updateUser(user);
    }



    /***
     * Sets customer surname using customer ID
     * @param userID
     * @param surName
     * @throws Exception
     */
    public void setCustomerSurName(String userID, String surName) throws SQLException {
        setSurName(surName);
        User user = new User(this.getName() , surName , userID , this.getPassword());
        Company.updateUser(user);
    }


    /***
     * Sets customer email using customer ID
     * @param userID
     * @param email
     * @throws Exception
     */
    public void setCustomerEmail(String userID, String email) throws SQLException {
        if (userID.charAt(0) == 'C'){
            Customer cust = (Customer) DatabaseCRM.UserDB.readCustomerFromDB(userID);
            if (cust != null){
                cust.setEmail(email);
                DatabaseCRM.UserDB.updateCustomerInDB(cust);
            }
        }
    }


    /***
     * Sets customer phoneNumber using customer ID
     * @param userID
     * @param phoneNumber
     * @throws Exception
     */
    public void setCustomerPhoneNumber(String userID, String phoneNumber) throws SQLException {
        if (userID.charAt(0) == 'C'){
            Customer cust = (Customer) DatabaseCRM.UserDB.readCustomerFromDB(userID);
            if (cust != null){
                cust.setPhoneNumber(phoneNumber);
                DatabaseCRM.UserDB.updateCustomerInDB(cust);
            }
        }
    }




    /***
     * Converts String format date to Date obj
     * @param date
     * @return Date object
     */
    private Date convertStringToDateObj(String date) {
        int day = Integer.parseInt(date.split("\\.")[0]);
        int month = Integer.parseInt(date.split("\\.")[1]);
        int year = Integer.parseInt(date.split("\\.")[2]);
        //  System.out.println(day + "  " + month + "  " + year);
        LocalDate localDate = LocalDate.of(year, month, day);
        Date dateObj = convertToDate(localDate);
        return dateObj;
    }


    /***
     * Converts LocalDate object to Date obj
     * @param dateToConvert
     * @return Date object
     */
    private Date convertToDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }


    /***
     * Updates the schedule using process
     * @param newDate
     * @param process
     */
    public void manageSchedule(String newDate, String process) throws SQLException {
        DatabaseCRM.connectDB();
        Date dateObj = convertStringToDateObj(newDate);
        Schedule updatedSch = new Schedule(dateObj, process);

        temp = schedules;
        PriorityQueue<Schedule> temp2 = new PriorityQueue<>();
        for (int i = 0; i < ctr; i++) {
            if(temp.peek().getProcess().equals(process))
                temp2.add(updatedSch);
            else
                temp2.add(temp.peek());
            temp.poll();
        }
        schedules=temp2;
        DatabaseCRM.ScheduleDB.updateScheduleInDB(updatedSch);
    }



    /***
     * Create a schedule with date and process infromation
     * @param date
     * @param process
     */
    public void addSchedule(String date, String process) throws SQLException {
        DatabaseCRM.connectDB();
        Date dateObj = convertStringToDateObj(date);

        Schedule sch= new Schedule(dateObj, process);
        DatabaseCRM.ScheduleDB.createScheduleInDB(sch);

        schedules.add(sch);
        ctr++;
    }


    /***
     * Remove a schedule using process infromation
     * @param process
     */
    public void removeSchedule(String process) throws SQLException {
        DatabaseCRM.connectDB();
        Date dateObj = convertStringToDateObj("03.07.2021"); //default
        Schedule schedule = new Schedule(dateObj, process);
        DatabaseCRM.ScheduleDB.deleteScheduleFromDB(process);
        temp = schedules;
        PriorityQueue<Schedule> temp2 = new PriorityQueue<>();
        for (int i = 0; i < ctr; i++) {
            if(temp.peek().getProcess().equals(process)){
            }
            else
                temp2.add(temp.peek());
            temp.poll();
        }
        ctr--;
        schedules=temp2;
    }

    /***
     * Remove product using ID
     * @param ID
     */
    public void removeProduct(String ID) throws SQLException {
        DatabaseCRM.ProductsDB.deleteProductFromDB(ID);
    }

    /***
     * Find product using ID
     * @param ID
     */
    public Product findProduct(String ID) throws SQLException {
        Product product = DatabaseCRM.ProductsDB.getProductFromDB(ID);
        return product;

    }

    /***
     * Returns all customers from DB
     * @return SkipList
     */
    public  SkipList<Customer> viewAllCustomers() throws SQLException {
        SkipList<Customer> cust = DatabaseCRM.UserDB.getAllCustomersFromDB();
        return cust;
    }

    /***
     * Offers recommended categories of the given input
     */
    public void manageOffering(){
        System.out.println("Enter the customer's favorite product's name: ");
        Scanner scanner = new Scanner(System.in);
        String product_name = scanner.nextLine();
        List<String> list = productGraph.getEdges(product_name);
        System.out.println("You can offer these categories: ");
        for(String edge : list)
            System.out.println(edge);
        System.out.println("\n\n");
    }


    public void addProduct(Product product) throws SQLException {
        Company.addProduct(product);

    }
}
