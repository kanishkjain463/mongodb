package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class IndexValidationExample {
    public static void main(String[] args) {
        // Pointing directly to your active working MongoDB Atlas cloud cluster
        String connectionUri = "mongodb+srv://2024pceacskanishk78_db_user:kanishk@cluster0.dcuryuq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        // Fixed: Swapped hardcoded localhost with your connectionUri variable
        try (MongoClient mongoClient = MongoClients.create(connectionUri)) {
            MongoDatabase database = mongoClient.getDatabase("schoolDB");
            MongoCollection<Document> collection = database.getCollection("students");

            // Validating that the query runs specifically using the email_1 index hint
            Document result = collection.find(Filters.eq("email", "test@example.com"))
                                        .hint(new Document("email", 1)) 
                                        .first();

            if (result != null) {
                System.out.println("Found student record: " + result.getString("name"));
            } else {
                System.out.println("Query executed successfully using 'email_1' index, but no matching document was found.");
            }
        } catch (Exception e) {
            System.err.println("An execution error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
