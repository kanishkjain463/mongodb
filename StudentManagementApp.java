package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class StudentManagementApp {
    public static void main(String[] args) {
        // Your MongoDB Atlas Connection String with embedded password
        String connectionUri = "mongodb+srv://2024pceacskanishk78_db_user:kanishk@cluster0.dcuryuq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"; 

        System.out.println("Connecting to MongoDB Atlas... Please wait.");

        try (MongoClient mongoClient = MongoClients.create(connectionUri)) {
            // Connect to database 'school_db' and collection 'students'
            MongoDatabase database = mongoClient.getDatabase("school_db");
            MongoCollection<Document> collection = database.getCollection("students");
            Scanner scanner = new Scanner(System.in);
            
            boolean running = true;
            System.out.println("\n=== Connected to MongoDB Atlas successfully! ===");

            while (running) {
                // Display the Interactive Build Menu
                System.out.println("\n--- Build Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        // Add Student Operation
                        System.out.print("Enter Student Name: ");
                        String name = scanner.nextLine();
                        
                        System.out.print("Enter Student Age: ");
                        int age = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Enter Course Name: ");
                        String course = scanner.nextLine();

                        Document student = new Document("name", name)
                                                .append("age", age)
                                                .append("course", course);
                        collection.insertOne(student);
                        System.out.println("🎉 Student record added successfully to the cloud!");
                        break;

                    case "2":
                        // View Students Operation
                        System.out.println("\n--- Registered Students ---");
                        long count = collection.countDocuments();
                        
                        if (count == 0) {
                            System.out.println("No records found in the database cluster.");
                        } else {
                            for (Document doc : collection.find()) {
                                System.out.printf("ID: %s | Name: %s | Age: %d | Course: %s%n",
                                        doc.getObjectId("_id").toString(),
                                        doc.getString("name"),
                                        doc.getInteger("age"),
                                        doc.getString("course"));
                            }
                        }
                        break;

                    case "3":
                        // Exit Application Loop
                        System.out.println("Exiting the application. Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("❌ Invalid option. Please choose 1, 2, or 3.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Invalid number format entered. Please try again.");
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }
}
