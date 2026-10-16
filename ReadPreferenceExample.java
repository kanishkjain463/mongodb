package school;

import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ReadPreferenceExample {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("schoolDB");
            
            MongoCollection<Document> collection = db.getCollection("teachers")
                    .withReadPreference(ReadPreference.secondaryPreferred());

            long count = collection.countDocuments();
            System.out.println("Total teachers read from replica: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
