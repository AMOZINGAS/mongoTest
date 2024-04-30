package DAO;

import colecciones.Comment;
import colecciones.Person;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.Block;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.gt;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Sorts.ascending;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import conexion.DBConnection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Amós Helí Olguín Quiróz
 */
public class PersonDAO implements IPersonDAO{

    private final MongoDatabase dataBase;
    private final MongoCollection<Person> collection;
    
    public PersonDAO() {
        
        dataBase = DBConnection.getDataBase();
        collection = dataBase.getCollection("person", Person.class);
        
//        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//        ConnectionString stringConnection = new ConnectionString("mongodb://localhost/27017");
//        MongoClientSettings clientsSettings = MongoClientSettings.builder().applyConnectionString(stringConnection).codecRegistry(codecRegistry).build();
//        //Aqui usamos el MongoClient para poder crear las conexiones en la base de datos de mongo
//        MongoClient dbServer = MongoClients.create(clientsSettings);
//        //Inicializamos a la base de datos con el dbserver que es la conexion a mongo y ahora a la base de nosotros
//        this.dataBase = dbServer.getDatabase("Example");
//        //Mas abajo podemos crear la conexion de una vez si vamos a utilizar los
//        //POJOs como conexion de collections
//        this.collection = dataBase.getCollection("person", Person.class);
        
    }
    
    @Override
    public void readPersonCollection(){
        
        //Podemos crear nuestra propia conexion con el documento con un 
        //documento un poco abstracto
        MongoCollection<Document> collectionDocument = dataBase.getCollection("person");
        //el mongo cusros lo usamos para iterar sobre los datos que tiene esa
        //coleccion de la base de datos
        try (MongoCursor<Document> cursor = collectionDocument.find().iterator()) {
            while (cursor.hasNext()) {
                //es donde pondremos nuestro dato con la informacion de la base 
                //de datos de mongo
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        }
        
    }
    
    @Override
    public List<Person> readPersonPOJO(){
        
        List<Person> listPerson = new LinkedList<>();
        
        collection.find().into(listPerson);
        
        for(Person person: listPerson){
            
            System.out.println(person.getId());
            System.out.println(person.getName());
            System.out.println(person.getAge());
            
        }
        
        return listPerson;
        
    }
    
    @Override
    public void readOrderByAgeName(){
        
        List<Person> listOrderBy = new LinkedList<>();
        
        collection.find().sort(ascending("age", "name")).into(listOrderBy);
        for(Person person: listOrderBy){
            
            System.out.println("Age: " + person.getAge()+ "\nName: " + person.getName());
            
        }
        
    }
    
    @Override
    public Person upDateDocument(Person person){
        
        Document filter = new Document("_id", person.getId());
        System.out.println(person.toString());
        Document upDate = new Document();
        
        upDate.append("$set", new Document("age", 18).append("name", "Modificado"));
        person.setAge(18);
        person.setName("Modificado");
        
        collection.updateOne(filter, upDate);
        
        return person;
        
    }
    
    @Override
    public String deletePerson(Person person){
        
        Document filter = new Document("_id", person.getId());
        
        collection.deleteOne(filter);
        
        JOptionPane.showMessageDialog(null, "La persona eliminada es: " + person.getName(), "EXITO!!", JOptionPane.INFORMATION_MESSAGE);
        return person.getName();
        
    }

    @Override
    public Person findUniquePerson(String name){
        
        Document filtro = new Document("name", name);
        Person person = collection.find(filtro).first();
        if(person != null){
            
            return person;
            
        }else{
            
            return null;
            
        }
        
    }
    
    @Override
    public Person createDocument(Person person){
        
        collection.insertOne(person);
        return person;
        
        
    }
    
    @Override
    public void readGreaterThan(){
        
        List<Person> listGreaterThan = new LinkedList<>();
        
        collection.find(gt("age", 18)).into(listGreaterThan);
        
        for(Person person: listGreaterThan){
            
            System.out.println("Age: " + person.getAge()+ "\nName: " + person.getName());
            
        }
        
    }
    
    @Override
    public void readOnlyName(){
        
        List<Person> listPersonOnlyName = new LinkedList<>();
        
        Document projection = new Document("name", 1).append("_id", 0);

        collection.find().projection(projection).into(listPersonOnlyName);
        
        for(Person person: listPersonOnlyName){
            
            System.out.println(person.getAge());
            System.out.println(person.getName());
            System.out.println(person.getId());
            
        }
        
    }

    @Override
    public void aggregate() {
    
        MongoCollection<Person> collectionDocument = dataBase.getCollection("person", Person.class);

        // Definir la operación de agregación con $lookup
        Document lookupStage = new Document("$lookup",
                new Document("from", "comments")
                        .append("localField", "comments")
                        .append("foreignField", "_id")
                        .append("as", "comments"));

        // Ejecutar la agregación
        Iterable<Person> results = collectionDocument.aggregate(Arrays.asList(lookupStage));
        for(Person person: results){
            
            System.out.println("Name: " + person.getName());
            System.out.println("Age: " + person.getAge());
            System.out.println("Mail: " + person.getMail());
            int count = 0;
            for(Comment comment: person.getComments()){
                count ++;
                System.out.println("Comment " + count + ": " + comment.getComment());
                
            }
            
        }
        
    }

    @Override
    public void readGroupByAge(){
    
        MongoCollection<Document> collectionPro = dataBase.getCollection("person");
        Document matchStage = new Document("$match", new Document("age", new Document("$gt", 20)));
        Document groupStage = new Document("$group", new Document("_id", null).append("total", new Document("$sum", 1)));
        Document result = collectionPro.aggregate(Arrays.asList(matchStage, groupStage)).first();
        
        int total = result.getInteger("total", 0);
        
        System.out.println(total);
        
    }
    
    
    
}
