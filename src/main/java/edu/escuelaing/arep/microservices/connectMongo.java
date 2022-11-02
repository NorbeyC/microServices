
package edu.escuelaing.arep.microservices;

/**
 *
 * @author norbey.cardona
 */
import com.mongodb.*;
import com.mongodb.client.*;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class connectMongo {
    public static ArrayList insertar(String args) {
       
        String connstr ="mongodb+srv://norbey:12345@mongo.35r6x71.mongodb.net/?retryWrites=true&w=majority";
        
        //String connstr ="mongodb://localhost:40000/?retryWrites=true&w=majority";
        ConnectionString connectionString = new ConnectionString(connstr);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        
        //Crea una instancia del cliente mongo conectado a la base de datos
        MongoClient mongoClient = MongoClients.create(settings);
        
        //Obtiene una lista de objetos bson representando las base de datos disponibles
        // bson es una versión binaria de json creada para mejorar desempeño de mongo.
        List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
        databases.forEach(db -> System.out.println(db.toJson()));
       
        //Obtener objeto base de datos. Si no existe lo crea
        MongoDatabase database = mongoClient.getDatabase("bdMongo");
        //Obtener objeto colección. Si no existe lo crea
        MongoCollection<Document> customers = database.getCollection("tweet");
        
        //Obtiene un iterable
        FindIterable<Document> iterable = customers.find();
        MongoCursor<Document> cursor = iterable.iterator();
        
        //Recorre el iterador obtenido del iterable
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        
        
        //Crea un documento BSON con el cliente
        Document customer = new Document("_id", new ObjectId());
        customer.append("post", args);

        //Agrega el nuevo cliente a la colección
        customers.insertOne(customer);

        //Lee el iterable directamente para imprimir documentos
        ArrayList documents = new ArrayList();
        for (Document d : iterable) {
            System.out.println(d);
            documents.add(d);
        }
        return documents;
    }
}
