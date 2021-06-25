package src;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * The type Crm system.
 */
public class CRMSystem{
    /*Bu objeyi (company) kullanarak kullanıcılara, ürünlere erişebilirsiniz. Bu class bi nevi driver olacak.
     * User kendi yazacağı mainde bu classın objesini oluşturacak. Bütün işlemler bu class üzerinden gerçekleşecek.
     * */

    /**
     * The constant ANSI_RESET.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * Ansi escape codes
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * Ansi escape codes
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * Ansi escape codes
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * Ansi escape codes
     */
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * The constant ANSI_PURPLE.
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /**
     * The Company.
     */
    Company company;

    /**
     * Instantiates a new Crm system.
     *
     * @param company_name the company name
     * @throws SQLException           the sql exception
     * @throws ClassNotFoundException the class not found exception
     */
    public CRMSystem(String company_name) throws SQLException, ClassNotFoundException {
        company = new Company(company_name);
    }

    /**
     * Login user.
     *
     * @param userID the user id
     * @return the user
     */
    public User login(String userID){
        Scanner input = new Scanner(System.in);
        String password;
        if (userID.charAt(0)== 'A'){
            Admin admin = company.getAdmin(userID);
            if(admin != null){
                System.out.print(ANSI_PURPLE + "Enter Password : " + ANSI_RESET);
                password = input.next();
                if(admin.getPassword().equals(password))
                    return admin;
            }
        }

        else if (userID.charAt(0)== 'B'){
            BusinessDeveloper busdev = company.getBusinessDev(userID);
            if(busdev != null){
                System.out.print(ANSI_PURPLE + "Enter Password : " + ANSI_RESET);
                password = input.next();
                if(busdev.getPassword().equals(password))
                    return busdev;
            }
        }
        else if (userID.charAt(0)== 'C'){
            Customer customer = company.getCustomer(userID);
            if(customer != null){
                System.out.print(ANSI_PURPLE + "Enter Password : " + ANSI_RESET);
                password = input.next();
                if(customer.getPassword().equals(password))
                    return customer;
            }
        }
        return null;
    }

    /**
     * Show menu.
     *
     *
     * @throws Exception the exception
     */
    public void showMenu() throws Exception {
        System.out.println(ANSI_BLUE +"Welcome To CRM System" + ANSI_RESET);
        Scanner input = new Scanner(System.in);
        String id = "null";
        User user = null;
        while (user == null) {
            System.out.print(ANSI_PURPLE + "Enter Id : " + ANSI_RESET);
            id = input.next();
            user = login(id);

            if(user == null) {
                System.out.println(ANSI_RED + "There is no user with this ID or Password is wrong" + ANSI_RESET);
                System.out.println(ANSI_RED + "Please Enter Again" + ANSI_RESET);
            }

        }
        if(user != null) {
            if (id.charAt(0) == 'A')
                adminMenu((Admin) user);
            else if (id.charAt(0) == 'B')
                developerMenu((BusinessDeveloper) user);
            else if (id.charAt(0) == 'C')
                customerMenu((Customer) user);
        }

    }

    private void adminMenu(Admin admin) throws Exception {
        Scanner input = new Scanner(System.in);
        String id;
        int select = -2;
        while (select != -1){
            System.out.println("1 - Add Business Developer ");
            System.out.println("2 - Remove Business Developer");
            System.out.println("3 - Add Customer ");
            System.out.println("4 - Remove Customer ");
            System.out.println("5 - Add Product ");
            System.out.println("6 - Remove Product ");
            System.out.println("7 - Manage Schedule ");
            System.out.println("8 - Add Schedule");
            System.out.println("9 - Remove Schedule");
            System.out.println("10 - Manage Customer Feedbacks");
            System.out.println("11 - View All Users");
            System.out.println("12 - Find Product \n\n");
            System.out.println("13 - Set User Name");
            System.out.println("14 - Set User Surname \n\n");
            System.out.println("0 - Logout");
            System.out.println("-1 - Quit");

            System.out.print(ANSI_GREEN + "Enter Selection : " + ANSI_RESET);
            select = input.nextInt();
            switch (select){
                case 1:
                    System.out.println(ANSI_YELLOW + "Adding Business Developer Page" + ANSI_RESET);
                    System.out.print("Enter Name : ");
                    String name = input.next();
                    System.out.print("Enter Surname : ");
                    String surname = input.next();
                    System.out.print("Enter ID : ");
                    String ID = input.next();
                    System.out.print("Enter Password : ");
                    String password = input.next();
                    admin.addBusinessDev(new BusinessDeveloper(name,surname,ID,password));
                    break;
                case 2:
                    System.out.println(ANSI_YELLOW + "Remove Business Developer Page" + ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    admin.removeBusinessDev(id);
                    break;
                case 3:
                    System.out.println(ANSI_YELLOW + "Adding Customer Page"+ ANSI_RESET);
                    System.out.print("Enter Name : ");
                    name = input.next();
                    System.out.print("Enter Surname : ");
                    surname = input.next();
                    System.out.print("Enter ID : ");
                    ID = input.next();
                    System.out.print("Enter Password : ");
                    password = input.next();

                    admin.addCustomer(new Customer(name,surname,ID,password));
                    break;
                case 4:
                    System.out.println(ANSI_YELLOW + "Remove Customer Page"+ ANSI_RESET);
                    System.out.print("Enter ID : ");
                    name = input.next();
                    admin.removeCustomer(name);
                    break;
                case 5:
                    System.out.println(ANSI_YELLOW + "Adding Product Page"+ ANSI_RESET);
                    System.out.print("Enter Name : ");
                    name = input.next();
                    System.out.print("Enter Id : ");
                    ID = input.next();
                    System.out.print("Enter ID : ");
                    String category = input.next();
                    System.out.print("Enter Password : ");
                    password = input.next();
                    admin.addProduct(new Product(name,ID,category));
                    break;
                case 6:
                    System.out.println(ANSI_YELLOW + "Remove Product Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    admin.removeProduct(id);
                    break;
                // düzenlendi @murat
                case 7:
                    System.out.println(ANSI_YELLOW + "Manage Schedule Page"+ANSI_RESET);
                    admin.printsSchedule();
                    System.out.println("Enter the process you want to edit(as integer): ");
                    int num = input.nextInt();
                    System.out.println("Enter new year value: ");
                    int year = input.nextInt();
                    admin.manageSchedule(num, year);
                    break;
                // düzenlendi @murat
                case 8:
                    System.out.println(ANSI_YELLOW + "Add Schedule Page"+ANSI_RESET);
                    System.out.println("Enter Process: ");
                    String process = input.next();
                    Date date = new Date();
                    if (process != null){
                        Schedule schedule = new Schedule(date, process);
                        admin.addSchedule(schedule);
                    }
                    else
                        System.out.println("Error!");
                    break;
                // düzenlendi @murat
                case 9:
                    System.out.println(ANSI_YELLOW + "Remove Schedule Page"+ANSI_RESET);
                    admin.printsSchedule();
                    System.out.println("Enter the process you want to delete (as integer): ");
                    int num1 = input.nextInt();
                    admin.removeSchedule(num1);
                    break;
                case 10:
                    System.out.println(ANSI_YELLOW + "Manage Customer Feedbacks Page"+ANSI_RESET);
                    admin.manageCustomersFeedBack();
                case 11:
                    System.out.println(ANSI_YELLOW + "View All Users Page"+ANSI_RESET);
                    admin.viewAllUsers();
                    break;
                case 12:
                    System.out.println(ANSI_YELLOW + "Find Product Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    admin.findProduct(id);
                case 13:
                    System.out.println(ANSI_YELLOW + "Set User Name Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    System.out.print("Enter Name : ");
                    name = input.next();
                    admin.setUserName(id,name);
                    break;
                case 14:
                    System.out.println(ANSI_YELLOW + "Set User Surname Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    System.out.print("Enter Surname : ");
                    name = input.next();
                    admin.setUserName(id,name);
                    break;
                case 0:
                    showMenu();
                    break;
                case -1:
                    System.out.println(ANSI_RED + "System is closing..."+ANSI_RESET);
                    Thread.sleep(2000);
                    System.out.println(ANSI_RED + "System Closed"+ANSI_RESET);
                    return;

            }
        }System.out.println(ANSI_RED + "System is closing..."+ANSI_RESET);
        Thread.sleep(2000);
        System.out.println(ANSI_RED + "System Closed"+ANSI_RESET);
        return;

    }
    private void developerMenu(BusinessDeveloper busDev) throws Exception {
        Scanner input = new Scanner(System.in);
        String id,name,password;
        Product pro;
        int select = -2;
        while (select != -1) {
            System.out.println("1 - Add Product ");
            System.out.println("2 - Remove Product ");
            System.out.println("3 - Manage Schedule ");
            System.out.println("4 - Add Schedule");
            System.out.println("5 - Remove Schedule");
            System.out.println("6 - View All Users");
            System.out.println("7 - Find Product \n");
            System.out.println("8 - Set Customer Name");
            System.out.println("9 - Set Customer Surname ");
            System.out.println("10 - Set Customer Email");
            System.out.println("11 - Set Customer Phone Number");
            System.out.println("0 - Logout\n");
            System.out.println("-1 - Quit");

            System.out.print(ANSI_GREEN + "Enter Selection : " + ANSI_RESET);
            select = input.nextInt();
            switch (select) {
                case 1:
                    System.out.println(ANSI_YELLOW + "Adding Product Page"+ANSI_RESET);
                    System.out.print("Enter Name : ");
                    name = input.next();
                    System.out.print("Enter Id : ");
                    id = input.next();
                    System.out.print("Enter ID : ");
                    String category = input.next();
                    busDev.addProduct(new Product(name, id, category));
                    break;
                case 2:
                    System.out.println(ANSI_YELLOW + "Remove Product Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    pro = Company.getProduct(id);
                    //busDev.removeProduct(pro);
                    break;
                case 3:
                    System.out.println(ANSI_YELLOW + "Manage Schedule Page"+ANSI_RESET);
                    System.out.print("Enter Process : ");
                    String p = input.next();
                    System.out.print("Enter Day : ");
                    String d = input.next();
                    System.out.print("Enter Month : ");
                    String m = input.next();
                    System.out.print("Enter Year : ");
                    String y = input.next();
                    String dat = d + "//" + m + "//" + y;

                    busDev.manageSchedule(dat,p);
                    break;
                case 4:
                    System.out.println(ANSI_YELLOW + "Add Schedule Page"+ANSI_RESET);
                    System.out.print("Enter Day : ");
                    String day = input.next();
                    System.out.print("Enter Month : ");
                    String month = input.next();
                    System.out.print("Enter Year : ");
                    String year = input.next();
                    String date = day + "//" + month + "//" + year;
                    System.out.print("Enter Process : ");
                    String proce = input.next();

                    busDev.addSchedule(date, proce);
                    break;
                case 5:
                    System.out.println(ANSI_YELLOW + "Remove Schedule Page"+ANSI_RESET);
                    System.out.print("Enter process : ");
                    String process = input.next();
                    busDev.removeSchedule(process);
                    break;
                case 6:
                    System.out.println(ANSI_YELLOW + "View All Customers Page"+ANSI_RESET);
                    busDev.viewAllCustomers();
                    break;
                case 7:
                    System.out.println(ANSI_YELLOW + "Find Product Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    busDev.findProduct(id);
                    break;
                case 8:
                    System.out.println(ANSI_YELLOW + "Set Customer Name Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    System.out.print("Enter Name : ");
                    name = input.next();

                    busDev.setCustomerName(id, name);
                    break;
                case 9:
                    System.out.println(ANSI_YELLOW + "Set Customer Surname Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    System.out.print("Enter Surname : ");
                    name = input.next();
                    busDev.setCustomerSurName(id, name);
                    break;
                case 10:
                    System.out.println(ANSI_YELLOW + "Set Customer Email Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    System.out.print("Enter Surname : ");
                    name = input.next();
                    busDev.setCustomerEmail(id, name);
                    break;
                case 11:
                    System.out.println(ANSI_YELLOW + "Set Customer Phone Number Page"+ANSI_RESET);
                    System.out.print("Enter ID : ");
                    id = input.next();
                    System.out.print("Enter Surname : ");
                    name = input.next();
                    busDev.setCustomerPhoneNumber(id, name);
                    break;
                case 0:
                    showMenu();
                    break;
                case -1:
                    System.out.println(ANSI_RED +"System is closing..." +ANSI_RESET);
                    Thread.sleep(2000);
                    System.out.println(ANSI_RED +"System Closed"+ANSI_RESET);
                    return;
            }
        }
        System.out.println(ANSI_RED +"System is closing..."+ANSI_RESET);
        Thread.sleep(2000);
        System.out.println(ANSI_RED +"System Closed"+ANSI_RESET);
        return;

    }
    private void customerMenu(Customer customer) throws Exception {
        Scanner input = new Scanner(System.in);
        String id,name,password,message,mail;
        int select = -2;
        Product pro;
        while (select != -1){
            System.out.println("1 - Add Comment to Product");
            System.out.println("2 - Send Message\n");
            System.out.println("3 - Set Name");
            System.out.println("4 - Set Email ");
            System.out.println("5 - Set Phone Number ");
            System.out.println("6 - Set Password ");
            System.out.println("0 - Logout\n");
            System.out.println("-1 - Quit");

            System.out.print(ANSI_GREEN + "Enter Selection : " + ANSI_RESET);
            select = input.nextInt();

            switch (select){
                case 1:
                    System.out.println(ANSI_YELLOW + "Add Comment Page "+ANSI_RESET);
                    System.out.print("Enter Product Id : ");
                    id = input.next();
                    System.out.print("Enter Comment : ");
                    message = input.next();
                    pro = Company.getProduct(id);
                    customer.addCommentProduct(pro,message);
                    break;
                case 2:
                    System.out.println(ANSI_YELLOW + "Send Message Page "+ANSI_RESET);
                    System.out.print("Enter Message : ");
                    message = input.next();
                    customer.sendMessage(message);
                    break;
                case 3:
                    System.out.println(ANSI_YELLOW + "Set Name Page "+ANSI_RESET);
                    System.out.print("Enter Name : ");
                    name = input.next();
                    customer.setName(name);
                    break;
                case 4:
                    System.out.println(ANSI_YELLOW + "Set Surname Page "+ANSI_RESET);
                    System.out.print("Enter Name : ");
                    name = input.next();
                    customer.setSurName(name);
                    break;
                case 5:
                    System.out.println(ANSI_YELLOW + "Set Email Page "+ANSI_RESET);
                    System.out.print("Enter Mail : ");
                    mail = input.next();
                    customer.setEmail(mail);
                    break;
                case 6:
                    System.out.println(ANSI_YELLOW + "Set Password Page " +ANSI_RESET);
                    System.out.print("Enter Name : ");
                    password = input.next();
                    customer.setPassword(password);
                    break;
                case 0:
                    showMenu();
                    break;
                case -1:
                    System.out.println(ANSI_RED +"System is closing..."+ ANSI_RESET);
                    Thread.sleep(2000);
                    System.out.println(ANSI_RED +"System Closed"+ ANSI_RESET);
                    return;
            }
        }
        System.out.println(ANSI_RED + "System is closing..."+ ANSI_RESET);
        Thread.sleep(2000);
        System.out.println(ANSI_RED +"System Closed"+ ANSI_RESET);
        return;
    }

    /**
     * Sing up boolean.
     *
     * @param name        the name
     * @param surName     the sur name
     * @param id          the id
     * @param password the phone number
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean singUp(String name, String surName, String id, String password) throws SQLException {
/*
        Scanner input = new Scanner(System.in);
        //String name,password,num,mail;

        System.out.print("Enter Name : ");
        name = input.next();
        System.out.print("Enter Surname : ");
        surName = input.next();
        System.out.print("Enter Id : ");
        id = input.next();
        System.out.print("Enter Phone Number : ");
        phoneNumber = input.next();

 */
        company.signUp(new User(name,surName,id,password));

        return true;
    }

}

