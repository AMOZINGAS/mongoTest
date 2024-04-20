package com.mycompany.mongotest;

import DAO.PersonDAO;

/**
 *
 * @author Amós Helí Olguín Quiróz
 */
public class MongoTest {

    public static void main(String[] args) {
        
        PersonDAO person = new PersonDAO();
//        person.readPersonCollection();
//        System.out.println("");
//        person.readPersonPOJO();
        person.readOnlyName();
        
    }
}
