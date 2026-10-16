package com.example;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

public class IndexCreationExample {
    public static void main(String[] args) {
        String connectionUri = "mongodb+srv://2024pceacskanishk78_db_user:kanishk@cluster0.dcuryuq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        
        // Fixed: Now using the cloud connectionUri variable instead of localhost string
        try (MongoClient mongoClient = MongoClients.create(connectionUri)) {
            MongoDatabase database = mongoClient.getDatabase("schoolDB");
            MongoCollection<Document> collection = database.getCollection("students");

            String result = collection.createIndex(Indexes.ascending("email"));
            
            System.out.println("Index created successfully: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
