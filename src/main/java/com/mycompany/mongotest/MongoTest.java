package com.mycompany.mongotest;

import DAO.PersonDAO;
import Interface.JFrameCreate;
import Interface.JFrameDelete;
import Interface.JFrameMain;
import Interface.JFrameUpDate;
import colecciones.Person;

/**
 *
 * @author Amós Helí Olguín Quiróz
 */
public class MongoTest {

    public static void main(String[] args) {

        
        
//        JFrameMain main = new JFrameMain();
//        main.setVisible(true);



        
        PersonDAO person = new PersonDAO();
        Person personPOJO = new Person();
        personPOJO.setAge(20);
        personPOJO.setMail("amosheli2004@gmail.com");
        personPOJO.setName("Amos Heli");
        personPOJO = person.createDocument(personPOJO);
        System.out.println("ID ES: " + personPOJO.getId());
        
//        
//        JFrameUpDate upDate = new JFrameUpDate();
//        upDate.setVisible(true);
//        
//        JFrameCreate create = new JFrameCreate();
//        create.setVisible(true);
//        person.createDocument();
//        person.readGreaterThan();
//        person.readPersonCollection();
//        person.readOrderByAgeName();
//        System.out.println("");
//        person.readPersonPOJO();
//        person.readOnlyName();
        
    }
}
