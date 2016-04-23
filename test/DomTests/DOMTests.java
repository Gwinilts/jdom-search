package DomTests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import jatajase.JNode;
import jatajase.JodeName;
import jatajase.TestJode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *s
 * @author gwion
 */
public class DOMTests {
    
    public DOMTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /*
    Run this case whenever the JNode and TestJode classes change in any way
    If they don't work, the whole project is failing
    */
    
    @Test 
    public void testNodeMatchesTest() {
        JNode theNode = new JNode(JodeName.JRow);
        TestJode search = new TestJode();
        
        theNode.attr("isHappy", false);
        theNode.attr("someString", "stringy");
        theNode.attr("ColumnName", "ColumnValue");
        theNode.id(12);
        
        search.attr("isHappy", false);
        
        assertEquals(search.matches(theNode), true);
        
        
        search.attr("someString", ":like::%:");
        assertEquals(search.matches(theNode), true);
        
        
        search.attr("someString", "stringy");
        assertEquals(search.matches(theNode), true);
        
        search.id(11);
        assertEquals(search.matches(theNode), false);
        
        search.id(12);
        assertEquals(search.matches(theNode), true);
    }
}
