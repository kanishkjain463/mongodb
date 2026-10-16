package school;

import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class WriteConcernExample {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("schoolDB");
            
            // Appends the safety concern configurations safely
            MongoCollection<Document> collection = db.getCollection("teachers")
                    .withWriteConcern(WriteConcern.MAJORITY.withJournal(true));

            collection.insertOne(new Document("name", "Priya").append("subject", "MongoDB"));
            System.out.println("Document inserted with Majority Write Concern!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
