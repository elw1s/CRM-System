package database;

import DataStructures.AVLTree;
import DataStructures.SkipList;
import src.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DatabaseCRM {
    protected static Connection connection;

    private static final String DATABASE_URL = "jdbc:mysql://remotemysql.com:3306/cecvsYhcHb";
    private static final String USERNAME = "cecvsYhcHb";
    private static final String PASSWORD = "M8UDYowfD8";

    public static void connectDB() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Connected properly.");
        } catch (Exception e) {
            assert e instanceof SQLException;
            printSQLException((SQLException) e);
        }
    }

    /**
     * UserDB class for database CRUD operations of user.
     */
    public static class UserDB {

        /**
         * Reads all user from database table
         *
         * @throws SQLException throws a SQLException
         */
        public static void readAllUserFromDB() throws SQLException {
            String sql = "SELECT * FROM users";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int userCount = 0;

            System.out.println("All users from Database ->");
            while (result.next()) {
                String id = result.getString(1);
                String password = result.getString(2);
                String name = result.getString(3);
                String surname = result.getString(4);

                String output = "%d : %s - %s - %s - %s";

                System.out.printf((output) + "%n", ++userCount, id, password, name, surname);
            }
        }

        public static void readAllAdminsFromDB() throws SQLException {
            String sql = "SELECT * FROM users WHERE id LIKE 'A%'";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int userCount = 0;

            System.out.println("\n\nAll ADMINS from Database ->");
            while (result.next()) {
                String id = result.getString(1);
                String password = result.getString(2);
                String name = result.getString(3);
                String surname = result.getString(4);

                String output = "%d : %s - %s - %s - %s";

                System.out.printf((output) + "%n", ++userCount, id, password, name, surname);
            }
        }

        /**
         * Read a specific User from database
         *
         * @param userID ID of the user
         * @throws SQLException throws a SQLException
         */
        public static void readUserFromDB(String userID) throws SQLException {

            String sql = " SELECT * FROM users where id ='" + userID + "'";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            String id, password, name, surname;
            String output;
            User userReaded = null;
            while (result.next()) {
                id = result.getString(1);
                password = result.getString(2);
                name = result.getString(3);
                surname = result.getString(4);
                output = "%s - %s - %s - %s";
                System.out.printf((output) + "%n", id, password, name, surname);
            }
        }

        // ********* //
        /* EKLENDİ */
        // ********* //
        public static User readCustomerFromDB(String userID) throws SQLException {

            String sql = " SELECT * FROM users where id ='" + userID + "'";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            String id = null, password = null, name = null, surname = null,email = null,phone = null;
            User userReaded = null;
            while (result.next()) {
                id = result.getString(1);
                password = result.getString(2);
                name = result.getString(3);
                surname = result.getString(4);
                email = result.getString(5);
                phone = result.getString(6);
            }
            if (id != null)
                return new Customer(name, surname, id, password, email, phone);
            else
                return null;
        }
		
		// ********* //
        /* EKLENDİ */
        // ********* //
		public static void updateCustomerInDB(Customer customer) throws SQLException {
            String sql = "UPDATE users SET password=?, name=?, surname=?, email=?, phone=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(6, customer.getID());
            statement.setString(1, customer.getPassword());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getSurName());
			statement.setString(4, customer.getEmail());
			statement.setString(5, customer.getPhoneNumber());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        }

        /**
         * Delete a user from database
         *
         * @param userID ID of the user
         * @throws SQLException throws a SQLException
         */
        public static void deleteUserFromDB(String userID) throws SQLException {
            String sql = "DELETE FROM users WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
            }
        }

        /**
         * create a user in database
         *
         * @param customer
         * @throws SQLException throws a SQLException
         */
        public static void createUserInDB(User customer) throws SQLException {

            if (hasExistUser(customer.getID())) {
                System.err.println("This ID: " + customer.getID() + " has exist in Database.");
                return;
            }

            String sql = "INSERT INTO users (id, password, name, surname) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getID());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getName());
            statement.setString(4, customer.getSurName());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

        }

        /**
         * create a customer in database
         *
         * @param customer created customer object
         * @throws SQLException throws a SQLException
         */
        public static void createCustomerInDB(Customer customer) throws SQLException {

            if (hasExistUser(customer.getID())) {
                System.err.println("This ID: " + customer.getID() + " has exist in Database.");
                return;
            }

            String sql = "INSERT INTO users(id, password, name, surname, email, phone) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getID());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getName());
            statement.setString(4, customer.getSurName());
            statement.setString(5,customer.getEmail());
            statement.setString(6,customer.getPhoneNumber());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        }

        /**
         * update the user data at database
         *
         * @param user updated user object
         * @throws SQLException throws a SQLException
         */
        public static void updateUserInDB(User user) throws SQLException {
            String sql = "UPDATE users SET password=?, name=?, surname=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(4, user.getID());
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurName());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        }

        /**
         * check a user exist in database
         *
         * @param id id of the user to check existing
         * @return tru or false as a result of ResultSet
         * @throws SQLException throws a SQLException
         */
        public static boolean hasExistUser(String id) throws SQLException {
            String sql = "Select 1 from users where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }


        /**
         * @return ArrayList of Admin
         * @throws SQLException throws a SQLException
         */
        public static List<Admin> getAllAdminsFromDB() throws SQLException {
            String queryAdmin = "SELECT * FROM users WHERE id LIKE 'A%'";

            List<Admin> adminList = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryAdmin);
            while (result.next()) {
                String id = result.getString(1);
                String password = result.getString(2);
                String name = result.getString(3);
                String surname = result.getString(4);
                String email = result.getString(5);
                String phone = result.getString(6);
                adminList.add(new Admin(name, surname, id, password));
            }
            return adminList;
        }

        /**
         * @return ArrayList of  BusinessDeveloper
         * @throws SQLException throws a SQLException
         */
        public static List<BusinessDeveloper> getAllDevelopersFromDB() throws SQLException {
            String queryBusDeveloper = "SELECT * FROM users WHERE id LIKE 'B%'";

            List<BusinessDeveloper> developerList = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryBusDeveloper);
            while (result.next()) {
                String id = result.getString(1);
                String password = result.getString(2);
                String name = result.getString(3);
                String surname = result.getString(4);
                developerList.add(new BusinessDeveloper(name, surname, id, password));
            }
            return developerList;
        }

        /**
         * @return ArrayList of Customer
         * @throws SQLException throws a SQLException
         */
        public static SkipList<Customer> getAllCustomersFromDB() throws SQLException {
            String queryCustomer = "SELECT * FROM users WHERE id LIKE 'C%'";

            SkipList<Customer> customerList = new SkipList<>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryCustomer);
            while (result.next()) {
                String id = result.getString(1);
                String password = result.getString(2);
                String name = result.getString(3);
                String surname = result.getString(4);
                String email = result.getString(5);
                String phone = result.getString(6);
                customerList.add(new Customer(name, surname, id, password, email, phone));
            }
            return customerList;
        }

    }

    /**
     * ScheduleDB class for database CRUD operations of schedule.
     */
    public static class ScheduleDB {

        /**
         * Reads all schedules from database table
         *
         * @throws SQLException throws a SQLException
         */
        public static void readAllScheduleFromDB() throws SQLException {
            String sql = "SELECT * FROM schedule";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String date = String.valueOf(result.getDate(1));
                String process = result.getString(2);

                String output = "%s - %s";
                System.out.println("All schedules from Database -> ");
                System.out.printf((output) + "%n", date, process);
            }
        }

        public static Schedule readScheduleFromDB(String process) throws SQLException {

            String sql = " SELECT * FROM schedule where process ='" + process + "'";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            Date date;

            Schedule readed = null;
            while (result.next()) {
                date = result.getDate(1);
                process = result.getString(2);
                String output = "%s - %s";
                readed = new Schedule(date, process);
            }
            return readed;
        }

        /**
         * Delete a schedule from database
         *
         * @param process process of the schedule date
         * @throws SQLException throws a SQLException
         */
        public static void deleteScheduleFromDB(String process) throws SQLException {
            String sql = "DELETE FROM schedule WHERE process=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, process);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A process was deleted successfully!");
            }
        }

        /**
         * create a schedule in database
         *
         * @param schedule created schedule object
         * @throws SQLException throws a SQLException
         */
        public static void createScheduleInDB(Schedule schedule) throws SQLException {

            String sql = "INSERT INTO schedule (date, process) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, (Date) schedule.getDate());
            statement.setString(2, schedule.getProcess());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Schedule was created successfully!");
            }

        }

        /**
         * update the schedule data at database
         *
         * @param schedule updated schedule object
         * @throws SQLException throws a SQLException
         */
        public static void updateScheduleInDB(Schedule schedule) throws SQLException {
            String sql = "UPDATE schedule SET date=?, process=? WHERE process=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, (Date) schedule.getDate());
            statement.setString(2, schedule.getProcess());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing schedule was updated successfully!");
            }
        }

        /**
         * @return PriorityQueue of Schedule
         * @throws SQLException throws a SQLException
         */
        public static PriorityQueue<Schedule> getAllSchedulesFromDB() throws SQLException {
            String query = "SELECT * FROM schedule";

            PriorityQueue<Schedule> schedulePriorityQueue = new PriorityQueue<>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Date date = result.getDate(1);
                String process = result.getString(2);
                schedulePriorityQueue.add(new Schedule(date, process));
            }
            return schedulePriorityQueue;
        }

    }

    public static class MessagesDB {
	/**Reading wanted customer messages*/
        public static void readAllCustomerMessage(String customerId) throws SQLException {

            String query = "SELECT * FROM messages WHERE customer_id = '" + customerId + "';";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            String message;

            while (resultSet.next()) {
                message = resultSet.getString("message");
                System.out.println(message);
            }
        }
	    
	 /**Create a message for getting user as paramater*/
	public static void createMessageDB(User user,String message) throws SQLException {

            String sql = "INSERT INTO messages (customer_id, message) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getID());
            statement.setString(2,message);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message inserted successfully!");
            }

        }

        /**
         * @return ArrayList of Admin
         * @throws SQLException throws a SQLException
         */
        public static Queue<String> getMessagesOfCustomerFromDB(String customerID) throws SQLException {
            String query = "SELECT * FROM messages WHERE customer_id = '" + customerID + "'";

            Queue<String> messages = new LinkedList<>();
            String message;

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                message = result.getString(2);
                messages.add(message);
            }
            return messages;
        }

    }

    public static class ProductsDB {
        static class ProductDB {
            private final String productId;
            private final String productName;
            private final String category;
            private final ArrayList<String> comments;

            public ProductDB(String id, String name, String category, ArrayList<String> comments) {
                this.productId = id;
                this.productName = name;
                this.category = category;
                this.comments = comments;
            }

            public String getProductId() {
                return this.productId;
            }

            public String getProductName() {
                return this.productName;
            }

            public String getCategory() {
                return this.category;
            }

            public ArrayList<String> getComments() {
                return this.comments;
            }

            public String toString() {
                String txt = "";
                txt += ("product id \t: " + this.productId);
                txt += ("\nproduct name \t: " + this.productName);
                txt += ("\nproduct category: " + this.category);
                txt += ("\ncomments \t: " + this.comments);
                return txt;
            }
        }

        public static Product getProductFromDB(String productId) throws SQLException {
            Product product = null;
            PreparedStatement statement = connection.prepareStatement("select * from products where product_id = ?");
            statement.setString(1, productId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                product = new Product(res.getString("product_id"), res.getString("name"), res.getString("category"), getCommentbyProdIdDB(productId));
            }
            return product;
        }

        // ********* //
        /* EKLENDİ */
        // ********* //
        public static void deleteProductFromDB(String productId) throws SQLException {
            String sql = "DELETE FROM products WHERE product_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, productId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A product was deleted successfully!");
            }
        }
	    
	public static boolean createProduct(Product product) throws SQLException {
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO cecvsYhcHb.products values(?,?,?);");
		statement.setString(1, product.getID());
		statement.setString(2, product.getName());
		statement.setString(3, product.getCategory());
		int res = statement.executeUpdate();
    		return (res > 0 ) ? true : false; 
	}

        public static ArrayList<String> getAllProductId() throws SQLException {
            ArrayList<String> list = new ArrayList<>();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT product_id FROM products;");
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    list.add(res.getString("product_id"));
                }
            }
            return list;
        }

        public static boolean updateProductName(String productId, String newName) throws SQLException {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("UPDATE products set name = ? WHERE product_id = ?");
                statement.setString(1, newName);
                statement.setString(2, productId);
                int res = statement.executeUpdate();
                return res > 0;
            }
            return false;
        }

        public static boolean addCommentDB(String productId, String comment) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comments values(default,?,?);");
            statement.setString(1, productId);
            statement.setString(2, comment);
            int res = statement.executeUpdate();
            return res > 0;
        }

        public static Queue<String> getCommentbyProdIdDB(String prodId) throws SQLException {
            Queue<String> list = new PriorityQueue<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments where product_id = ?;");
            statement.setString(1, prodId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                list.add(res.getString("comments"));
            }
            return list;
        }

        public static AVLTree<Product> getAllProductsFromDB() throws SQLException {
            String query = "SELECT * FROM products";

            AVLTree<Product> productBinarySearchTree = new AVLTree<>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String ID = result.getString(1);
                String name = result.getString(2);
                String category = result.getString(3);
                productBinarySearchTree.add(new Product(name, ID, category));
            }
            return productBinarySearchTree;
        }

        public static ArrayList<Product> getAllProductsListFromDB() throws SQLException {
            String query = "SELECT * FROM products";

            ArrayList<Product> productBinarySearchTree = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String ID = result.getString(1);
                String name = result.getString(2);
                String category = result.getString(3);
                productBinarySearchTree.add(new Product(name, ID, category));
            }
            return productBinarySearchTree;
        }

    }

    /**
     * A custom exception method for SQL errors
     *
     * @param ex SQLException
     */
    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
