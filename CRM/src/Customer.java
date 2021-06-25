package src;
import DataStructures.AVLTree;
import DataStructures.BinarySearchTree;
import database.DatabaseCRM;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Customer extends User implements Comparable<Customer> {
    private String email;
    private String phoneNumber;
    private Queue<String> message;


    public Customer(String name, String surName, String ID, String password, String email, String phoneNumber) throws SQLException {
        super(name, surName, ID, password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        message = DatabaseCRM.MessagesDB.getMessagesOfCustomerFromDB(ID);
    }

    public Customer(String name, String surName, String ID, String password) throws SQLException {
        super(name,surName,ID,password);
        message = DatabaseCRM.MessagesDB.getMessagesOfCustomerFromDB(ID);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMessage(Queue<String> message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /***
     * Customer can add a comment to a product by using this method. It throws an exception if the product is null and comment is empty string
     * @param product
     * @param comment
     * @throws Exception
     */
    public void addCommentProduct(Product product, String comment) throws Exception {
        if (product == null) throw new NoSuchElementException("Product does not exist.");
        product.addComment(comment);
    }

    /***
     * Customer can send message to the seller by using this method. The msg will be added into the private field of the Customer
     * @param msg
     * @throws Exception
     */
    public void sendMessage(String msg) throws Exception {
        if (msg.length() == 0) throw new Exception("Message cannot be an empty string");
        message.add(msg);
        Company.addNewMessage(new UserInformationSystem(this,msg));

        System.out.println("Message has been sent. The seller is going to respond it within 48 days.");
    }

    /***
     * Returns the private field which is "message"
     * @return Returns the private field which is "message"
     */
    public Queue<String> getMessage() {
        return message;
    }

    /***
     * Searches the product in the products binarySearchTree
     * @param products
     * @param ID
     * @return Product if it exists, otherwise null
     */
    public Product findProduct(AVLTree<Product> products, String ID) {
        AVLTree.Iterator iter = products.iterator();
        while(iter.hasNext()){
            Product product = (Product) iter.next();
            if(product.getID().equals(ID)){
                return product;
            }
        }
        return null;
    }

    /***
     * Compares two customers values by their names
     * @param o
     * @return 0 when names are equal, 1 when the calling customer's name is higher. Returns -1 otherwise.
     */
    @Override
    public int compareTo(Customer o) {
        if (this.getName().compareTo(o.getName()) == 0) {
            return 0;
        } else if (this.getName().compareTo(o.getName()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
