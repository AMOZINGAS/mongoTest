package colecciones;

import org.bson.types.ObjectId;

/**
 *
 * @author Amós Helí Olguín Quiróz
 */
public class Person {
    
    private ObjectId id;
    private String name;
    private int age;
    private String mail;

    public Person() {}
    
    
    
    public Person(String name, int age, String mail){
        
        this.age = age;
        this.name = name;
        this.mail = mail;
        
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString(){
        
        return age + "\n" + name + "\n" + id;
        
    }
    
}
