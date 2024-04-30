/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import colecciones.Person;
import java.util.List;

/**
 *
 * @author lv1822
 */
public interface IPersonDAO {
    
    public void readPersonCollection();
    
    public List<Person> readPersonPOJO();
    
    public void readOrderByAgeName();
    
    public Person upDateDocument(Person person);
    
    public String deletePerson(Person person);
    
    public Person findUniquePerson(String name);//
    
    public Person createDocument(Person person);
    
    public void readGreaterThan();
    
    public void readOnlyName();

    public void aggregate();

    public void readGroupByAge();
    
}











