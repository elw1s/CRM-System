package src;

public class TestDriver {

    public static void main(String[] args) {
        try {
            CRMSystem crmSystem = new CRMSystem("Mobilyacı");
            //TC_01
            crmSystem.company.showAllAdmins();
            System.out.println("TC_01 - Verify that Admin login page is works with valid ID and password.");
            System.out.println(crmSystem.company.login("A3","asdf"));
            System.out.println();


            //TC_02
            crmSystem.company.showAllBusinessDev();
            System.out.println("TC_02 - Verify that Business Developer login page is works with valid ID and password.");
            System.out.println(crmSystem.company.login("B1","B1psw"));
            System.out.println();


            //TC_03
            crmSystem.company.showAllCustomers();
            System.out.println("TC_03 - Verify that Customer login page is works with valid ID and password.");
            System.out.println(crmSystem.company.login("C2","C2psw"));
            System.out.println();


            //TC_04
            System.out.println("TC_04 - Verify that login page is works with valid ID and invalid password.");
            System.out.println(crmSystem.company.login("C2","invalid"));
            System.out.println();


            //TC_05
            System.out.println("TC_05 - Verify that login page is works with invalid ID and valid password.");
            System.out.println(crmSystem.company.login("invalid","B1psw"));
            System.out.println();


            //TC_06
            System.out.println("TC_06 - Verify that login page is works with invalid ID and invalid password.");
            System.out.println(crmSystem.company.login("invalid","invalid"));
            System.out.println();


            //TC_07 Canceled
            //TC_08 Canceled
            //TC_09 Canceled
            //TC_10 Canceled

            //TC_11
            System.out.println("TC_11 - Verify that Customer can create an account.");
            Customer customer = new Customer("Anthony","Gale","C15","ASD");
            crmSystem.company.signUp(customer);
            crmSystem.company.showAllCustomers();
            System.out.println();

            //TC_12
            System.out.println("TC_12 - Verify that BD or Admin can create account for Customer.");
            Admin admin = new Admin("admin","admin","A12","admin");
            Customer cstmr = new Customer("Dan","Cron","C23","dan123");
            System.out.println(admin.addCustomer(cstmr));
            crmSystem.company.showAllCustomers();
            System.out.println();



            //TC_13 Canceled
            //TC_14 Canceled
            //TC_15 Canceled

            //TC_16
            System.out.println("TC_16 - Verify that Admin can remove Customer from the system.");
            admin.removeCustomer("C23");
            crmSystem.company.showAllCustomers();
            System.out.println();

            //TC_17
            System.out.println("TC_17 - Verify that Admin can add Business Developer.");
            BusinessDeveloper bussDev = new BusinessDeveloper("Betty","Green","B12","betty123");
            admin.addBusinessDev(bussDev);
            crmSystem.company.showAllBusinessDev();
            System.out.println();

            //TC_18 Canceled

            //TC_19
            System.out.println("TC_19 - Verify that Admin can remove Business Developer from the system.");
            admin.removeBusinessDev("B12");
            crmSystem.company.showAllBusinessDev();
            System.out.println();

            //TC_20
            System.out.println("TC_20 - Verify that Admin / BD can add product to the system.");
            admin.addProduct(new Product("Oak Table","P555","Tables"));
            System.out.println();

        }
        catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }
}