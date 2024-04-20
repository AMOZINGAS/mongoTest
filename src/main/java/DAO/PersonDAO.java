package DAO;

import colecciones.Person;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.LinkedList;
import java.util.List;
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
public class PersonDAO {

    private final MongoDatabase dataBase;
    private final MongoCollection<Person> collection;
    
    public PersonDAO() {
    
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        ConnectionString stringConnection = new ConnectionString("mongodb://localhost/27017");
        MongoClientSettings clientsSettings = MongoClientSettings.builder().applyConnectionString(stringConnection).codecRegistry(codecRegistry).build();
        MongoClient dbServer = MongoClients.create(clientsSettings);
        this.dataBase = dbServer.getDatabase("AmosHeli");
        this.collection = dataBase.getCollection("person", Person.class);
        
    }
    
    public void readPersonCollection(){
        
        MongoCollection<Document> collectionDocument = dataBase.getCollection("person");
        try (MongoCursor<Document> cursor = collectionDocument.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        }
        
    }
    
    public void readPersonPOJO(){
        
        List<Person> listPerson = new LinkedList<>();
        
        collection.find().into(listPerson);
        
        for(Person person: listPerson){
            
            System.out.println(person.getId());
            System.out.println(person.getName());
            System.out.println(person.getAge());
            
        }
        
    }
    
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
    
}