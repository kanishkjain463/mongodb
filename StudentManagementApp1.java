package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudentManagementApp1 { 
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // MongoDB Atlas Connection String
        String connectionUri = "mongodb+srv://2024pceacskanishk78_db_user:kanishk@cluster0.dcuryuq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"; 

        System.out.println("Connecting to MongoDB Atlas... Please wait.");

        try (MongoClient mongoClient = MongoClients.create(connectionUri)) {
            MongoDatabase database = mongoClient.getDatabase("school_db");
            // Points to your updated collection name: students1
            MongoCollection<Document> collection = database.getCollection("students1");
            
            boolean running = true;
            System.out.println("\n=== Connected to MongoDB Atlas successfully! ===");

            while (running) {
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. Search Students (By ID, Dept, CGPA, Skill, All)");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addStudent(collection);
                        break;
                    case "2":
                        searchMenu(collection);
                        break;
                    case "3":
                        System.out.println("Exiting the application. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose 1, 2, or 3.");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }

    private static void addStudent(MongoCollection<Document> collection) {
        try {
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter Student Age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            
            System.out.print("Enter CGPA: ");
            double cgpa = Double.parseDouble(scanner.nextLine().trim());
            
            System.out.print("Enter Skills (comma-separated, e.g., Java, Python, SQL): ");
            String skillsInput = scanner.nextLine().trim();
            List<String> skills = Arrays.asList(skillsInput.split("\\s*,\\s*"));

            Document student = new Document("name", name)
                                    .append("age", age)
                                    .append("department", department)
                                    .append("cgpa", cgpa)
                                    .append("skills", skills);
                                    
            collection.insertOne(student);
            System.out.println("🎉 Student record added successfully to the cloud!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Invalid number format for Age or CGPA.");
        }
    }

    private static void searchMenu(MongoCollection<Document> collection) {
        System.out.println("\n--- Search Submenu ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Department");
        System.out.println("3. Search by Minimum CGPA");
        System.out.println("4. Search by Skill");
        System.out.println("5. View All Students");
        System.out.print("Enter your choice: ");

        String searchChoice = scanner.nextLine().trim();
        Iterable<Document> results = null;

        switch (searchChoice) {
            case "1":
                System.out.print("Enter Student ID (24-character hex string): ");
                String idInput = scanner.nextLine().trim();
                try {
                    results = collection.find(Filters.eq("_id", new ObjectId(idInput)));
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Invalid ID format.");
                    return;
                }
                break;

            case "2":
                System.out.print("Enter Department: ");
                String deptInput = scanner.nextLine().trim();
                results = collection.find(Filters.regex("department", "^" + deptInput + "$", "i"));
                break;

            case "3":
                System.out.print("Enter Minimum CGPA threshold: ");
                try {
                    double minCgpa = Double.parseDouble(scanner.nextLine().trim());
                    results = collection.find(Filters.gte("cgpa", minCgpa));
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid CGPA entry.");
                    return;
                }
                break;

            case "4":
                System.out.print("Enter Skill name: ");
                String skillInput = scanner.nextLine().trim();
                results = collection.find(Filters.all("skills", skillInput));
                break;

            case "5":
                results = collection.find();
                break;

            default:
                System.out.println("❌ Invalid search selection.");
                return;
        }

        printResults(results);
    }

    private static void printResults(Iterable<Document> results) {
        if (results == null) return;

        System.out.println("\n--- Query Results ---");
        boolean hasRecords = false;
        for (Document doc : results) {
            hasRecords = true;

            // Safe parsing logic to completely handle missing fields or integer inputs
            double cgpaValue = 0.0;
            if (doc.get("cgpa") != null) {
                cgpaValue = ((Number) doc.get("cgpa")).doubleValue();
            }

            System.out.printf("ID: %s | Name: %s | Dept: %s | CGPA: %.2f | Skills: %s%n",
                    doc.getObjectId("_id").toString(),
                    doc.getString("name") != null ? doc.getString("name") : "N/A",
                    doc.getString("department") != null ? doc.getString("department") : "N/A",
                    cgpaValue,
                    doc.get("skills") != null ? doc.get("skills").toString() : "None");
        }

        if (!hasRecords) {
            System.out.println("No matching records found.");
        }
    }
}
