package school;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class PrimaryWriteExample {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("schoolDB");
            MongoCollection<Document> collection = db.getCollection("teachers");

            Document teacher = new Document("name", "Rahul").append("subject", "Java");
            collection.insertOne(teacher);
            System.out.println("Data written successfully to Primary node!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
