package school;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ReplicaSetConnection {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs0";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("schoolDB");
            System.out.println("Connected to Replica Set successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
