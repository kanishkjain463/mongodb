package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

public class IndexTypesExample {
    public static void main(String[] args) {
        // Your MongoDB Atlas Connection URI
        String connectionUri = "mongodb+srv://2024pceacskanishk78_db_user:kanishk@cluster0.dcuryuq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(connectionUri)) {
            MongoDatabase database = mongoClient.getDatabase("schoolDB");
            MongoCollection<Document> collection = database.getCollection("students");

            // Creating the indexes
            collection.createIndex(Indexes.ascending("age"));

            collection.createIndex(Indexes.compoundIndex(
                Indexes.ascending("department"), 
                Indexes.descending("cgpa")
            ));

            collection.createIndex(Indexes.ascending("skills"));

            System.out.println("All index types created successfully on MongoDB Atlas!");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
