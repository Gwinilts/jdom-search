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
public class Main {
    public static void main(String[] args) {
        System.out.println("gWion".matches("[gG][wW][iI][oO][nN]"));
        
        
        
        Jocument a = new Jocument();
        a.addTable("Friends");
        
        JNode row = new JNode(JodeName.JRow);
        row.attr("Name", "Joe O Conner");
        row.attr("Tel", "0872141328");
        
        a.getTable("Friends").appendChild(row);
        
        row = new JNode(JodeName.JRow);
        row.attr("Name", "Felicity McMorgan");
        row.attr("Tel", "123224583487");
        
        a.getTable("Friends").appendChild(row);
        
        System.out.println(a.stringify());
        
        Jocument b = new Jocument();
        b.parse(a.stringify());
        System.out.println(b.toString());
        
        System.out.println("\n\n\n\n\n\n");
        
        JNode h = new JNode(JodeName.JRow);
        h.attr("fname", "gwion");
        h.attr("sname", "smith");
        h.attr("age", "19");
        h.attr("sex", "male");
        
        JNode t = new JNode(JodeName.JTable);
        t.parse(h.stringify());
        
        System.out.println(h.toString(0));
        System.out.println("----------------------------------");
        System.out.println(t.toString(0));
    }
}
