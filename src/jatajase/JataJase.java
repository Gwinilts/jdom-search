/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatajase;

/**
 *
 * @author gwion
 */
public class JataJase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JNode target = new JNode(JodeName.JRow);
        TestJode search = new TestJode();
        
        search.attr("something", ":like::%:");
        target.attr("something", "happy go");
        
        System.out.println(search.matches(target));
        
        
        
        
        Jocument db = new Jocument();
        db.addTable("Customers", true);
        JNode customer = new JNode(JodeName.JRow);
        System.out.println(customer.attr("name", "Gwion Smith"));
        customer.attr("telephone", "0871234253");
        customer.attr("hometown", "Hazelwood Ave");
        db.add("Customers", customer);
        customer = new JNode(JodeName.JRow);
        customer.attr("name", "Foxy Megan");
        customer.attr("telephone", "0429346574");
        customer.attr("hometown", "Hazelwood Ave");
        db.add("Customers", customer);
        customer = new JNode(JodeName.JRow);
        customer.attr("name", "Philly Fingers");
        customer.attr("hometown", "Not HazelWood Ave At All");
        db.add("Customers", customer);
        
        TestJode matcher = new TestJode();
        matcher.attr("name", "billy");
        
        AbstractJode customers = db.getTable("Customers");
        
        JodeList searchTest = db.search("Customers", matcher);
        
        for (AbstractJode j: searchTest) {
            System.out.println(j.toString());
        }
        
    }
    
}
