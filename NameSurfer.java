/**
 * NameSurfer.java
 *
 * Bruce Gavins
 * MSI-5030
 * Final Project
 *
 * This program analyzes baby name popularity data from the Social Security Administration.
 * It allows users to search for names, find their best ranking years, and visualize their popularity over time.
 * The program implements concepts of object-oriented programming such as encapsulation, class and object relationships
 * instance variables and methods, constructors, method overloading, array manipulation, file I/O operations.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NameSurfer {
    private NameRecord[] nameRecords;  // Array to store all name records
    private int nameCount;             // Number of names loaded
    
    /**
     * Constructor - initializes the NameSurfer and loads data from file
     */
    public NameSurfer() {
        // Allocate space for up to 10,000 names (more than enough for the dataset)
        nameRecords = new NameRecord[10000];
        nameCount = 0;
        
        // Load the data from file
        loadData("name_data.txt");
    }
    
    /**
     * Loads name data from the specified file
     * takes a parameter of the name of the file to load
     */
    private void loadData(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            
            // Read each line of the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                nameRecords[nameCount] = new NameRecord(line);
                nameCount++;
            }
            
            fileScanner.close();
            System.out.println("Successfully loaded " + nameCount + " names.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find file " + filename);
            System.exit(1);
        }
    }
    
    /**
     * Searches for a name in the records
     * takes a parameter of the name to search for
     * returns the NameRecord if found, null otherwise
     */
    private NameRecord findName(String name) {
        for (int i = 0; i < nameCount; i++) {
            if (nameRecords[i].getName().equalsIgnoreCase(name)) {
                return nameRecords[i];
            }
        }
        return null;
    }
    
    /**
     * Draws the background grid for the plot
     * This method recreates the grid that should appear after clearing
     */
    private void drawGrid() {
        // Set up the canvas if it hasn't been set up yet
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(1900, 2010);
        StdDraw.setYscale(1000, 1);
        
        // Draw vertical lines for each decade
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (int year = 1900; year <= 2010; year += 10) {
            StdDraw.line(year, 1, year, 1000);
        }
        
        // Draw horizontal lines for rank divisions
        for (int rank = 100; rank <= 1000; rank += 100) {
            StdDraw.line(1900, rank, 2010, rank);
        }
        
        // Draw decade labels at the bottom
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int year = 1900; year <= 2010; year += 10) {
            StdDraw.text(year, 1050, String.valueOf(year));
        }
        
        // Draw rank labels on the left
        for (int rank = 100; rank <= 1000; rank += 100) {
            StdDraw.textLeft(1895, rank, String.valueOf(rank));
        }
        
        // Add axis labels
        StdDraw.text(1955, 1100, "Year");
        StdDraw.textRight(1895, 500, "Rank");
        
        // Show the grid
        StdDraw.show();
    }
    
    /**
     * Displays the menu and handles user interaction
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        // Main menu loop
        while (choice != 5) {
            // Display menu for the user
            System.out.println("\n1 – Find the best year for a name");
            System.out.println("2 – Find the best rank for a name");
            System.out.println("3 – Plot the popularity of a name");
            System.out.println("4 – Clear the plot");
            System.out.println("5 – Quit");
            System.out.print("Enter your selection: ");
            
            // Get user choice
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (Exception e) {
                scanner.nextLine(); // Clear the scanner
                choice = 0;
            }
            
            // Process user choice
            switch (choice) {
                case 1: // Find best year
                    System.out.print("Enter a name: ");
                    String name1 = scanner.nextLine();
                    NameRecord record1 = findName(name1);
                    
                    if (record1 != null) {
                        int bestYear = record1.bestYear();
                        System.out.println(name1 + "'s best year was " + bestYear);
                    } else {
                        System.out.println("Error: Name '" + name1 + "' not found.");
                    }
                    break;
                    
                case 2: // Find best rank
                    System.out.print("Enter a name: ");
                    String name2 = scanner.nextLine();
                    NameRecord record2 = findName(name2);
                    
                    if (record2 != null) {
                        int bestYear = record2.bestYear();
                        int decade = (bestYear - NameRecord.START) / 10;
                        int bestRank = record2.getRank(decade);
                        System.out.println(name2 + "'s best rank was " + bestRank + " in " + bestYear);
                    } else {
                        System.out.println("Error: Name '" + name2 + "' not found.");
                    }
                    break;
                    
                case 3: // Plot popularity
                    System.out.print("Enter a name: ");
                    String name3 = scanner.nextLine();
                    NameRecord record3 = findName(name3);
                    
                    if (record3 != null) {
                        record3.plot();
                    } else {
                        System.out.println("Error: Name '" + name3 + "' not found.");
                    }
                    break;
                    
                case 4: // Clear the plot
                    // Clear the canvas
                    StdDraw.clear();
                    
                    // Redraw the grid
                    drawGrid();
                    
                    // Confirm to user
                    System.out.println("Plot cleared.");
                    break;
                    
                case 5: // Quit the application
                    System.out.println("Thanks for using NameSurfer!");
                    break;
                    
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }
        
        scanner.close();
    }
    
    /**
     * Main method that creates and runs the NameSurfer app
     */
    public static void main(String[] args) {
        NameSurfer app = new NameSurfer();
        app.run();
    }
}
