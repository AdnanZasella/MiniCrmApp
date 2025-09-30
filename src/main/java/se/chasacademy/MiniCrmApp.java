package se.chasacademy;

import java.util.*;

/**
 * Mini CRM console app. Uses Map<String, Customer> as in-memory storage.
 */
public class MiniCrmApp {


    // --- Storage & IO ---

    /** customerId -> Customer */
    private final Map<String, Customer> store = new HashMap<>();

    /** reads user input from console */
    private final Scanner scanner = new Scanner(System.in);


    // --- ID generation ---

    /** simple counter for new ids (C-0004 and up) */
    private int nextId = 4;



    // --- Entry point ---

    public static void main(String[] args) {
        new MiniCrmApp().run();
    }

    /**
     * Main loop: show menu, read choice, execute action.
     */
    private void run() {

        initializeCustomers(); // seed demo data once

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> { addCustomerWithEmail(); }
                case "2" -> editCustomer();
                case "3" -> showCustomer();
                case "4" -> listAll();
                case "5" -> deleteCustomer();
                case "0" -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Unknown choice");
            }
        }
    }

    // --- Helpers / setup ---

    /**
     * Preload a few customers for testing.
     */
    private void initializeCustomers() {
        Customer c1 = new Customer("C-0001", "Alice");
        c1.addEmail("alice@example.com");
        store.put(c1.id(), c1);

        Customer c2 = new Customer("C-0002", "Bob");
        c2.addEmail("bob@example.com");
        store.put(c2.id(), c2);

        Customer c3 = new Customer("C-0003", "Charlie");
        c3.addEmail("charlie@example.com");
        store.put(c3.id(), c3);
    }

    /**
     * Make a new id like C-0004, C-0005, ...
     */
    private String generateId() {
        String id = String.format("C-%04d", nextId); // always 4 digits
        nextId++; // move counter forward
        return id;
    }

    // --- UI (menu) ---

    /**
     * Print the command menu.
     */
    private void printMenu() {
        System.out.println("\nMini-CRM");
        System.out.println("1) Add customer");
        System.out.println("2) Edit customer");
        // System.out.println("2) Add tag to customer");
        // System.out.println("3) Add note to customer");
        System.out.println("3) Show customer by id");
        System.out.println("4) List all customers");
        System.out.println("5) Delete customer");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    // --- Actions (CRUD) ---

    /**
     * Create a new customer and (optionally) first email.
     */
    private void addCustomerWithEmail() {
        String id = generateId();
        System.out.println("Generated Customer id: " + id);

        if (store.containsKey(id)) {
            System.out.println("Customer already exists (try again).");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Customer c = new Customer(id, name);
        if (!email.isEmpty()) {
            c.addEmail(email);
        }

        store.put(id, c);
        System.out.println("Customer added with id " + id + " and email.");
    }

    /**
     * Add a tag to an existing customer.
     */
    private void addTag() {
        System.out.print("Customer id: ");
        String id = scanner.nextLine().trim();
        Customer c = store.get(id);
        if (c == null) { System.out.println("Not found."); return; }
        System.out.print("Tag: ");
        c.addTag(scanner.nextLine().trim());
        System.out.println("Tag added.");
    }

    /**
     * Add a note to an existing customer.
     */
    private void addNote() {
        System.out.print("Customer id: ");
        String id = scanner.nextLine().trim();
        Customer c = store.get(id);
        if (c == null) { System.out.println("Not found."); return; }
        System.out.print("Note: ");
        c.addNote(scanner.nextLine());
        System.out.println("Note added.");
    }

    /**
     * Show one customer by id (prints toString()).
     */
    private void showCustomer() {
        System.out.print("Customer id: ");
        String id = scanner.nextLine().trim();
        Customer c = store.get(id);
        System.out.println(c == null ? "Not found." : c);
    }

    /**
     * Simple editor: change name or add tag/note/email.
     */
    private void editCustomer() {
        System.out.print("Customer id: ");
        String id = scanner.nextLine().trim();
        Customer c = store.get(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Edit customer: " + c.name());
        System.out.println("1) Change name");
        System.out.println("2) Add tag");
        System.out.println("3) Add note");
        System.out.println("4) Add email");
        System.out.println("5) Remove tag");
        System.out.println("6) Remove note");
        System.out.println("7) Remove email");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("New name: ");
                c.setName(scanner.nextLine().trim());
                System.out.println("Name updated.");
            }
            case "2" -> {
                System.out.print("New tag: ");
                c.addTag(scanner.nextLine().trim());
                System.out.println("Tag added.");
            }
            case "3" -> {
                System.out.print("New note: ");
                c.addNote(scanner.nextLine());
                System.out.println("Note added.");
            }
            case "4" -> {
                System.out.print("New email: ");
                c.addEmail(scanner.nextLine().trim());
                System.out.println("Email added.");
            }
            case "5" -> {
                System.out.print("Tag to remove: ");
                String tag = scanner.nextLine().trim();
                if (c.removeTag(tag)) {
                    System.out.println("Tag removed.");
                } else {
                    System.out.println("Tag not found.");
                }
            }
            case "6" -> {
                System.out.print("Note to remove: ");
                String note = scanner.nextLine();
                if (c.removeNote(note)) {
                    System.out.println("Note removed.");
                } else {
                    System.out.println("Note not found.");
                }
            }
            case "7" -> {
                System.out.print("Email to remove: ");
                String email = scanner.nextLine().trim();
                if (c.removeEmail(email)) {
                    System.out.println("Email removed.");
                } else {
                    System.out.println("Email not found.");
                }
            }
            default -> System.out.println("Unknown choice.");
        }
    }

    /**
     * Print all customers (sorted by id).
     */
    private void listAll() {
        if (store.isEmpty()) {
            System.out.println("No customers.");
            return;
        }

                store.values().stream()
                     .sorted(Comparator.comparing(Customer::id)) // sort by id
                     .forEach(c -> System.out.println(
                        c.id() + " (" + c.name() + ") " +
                                "emails: " + c.emails().size() + ", " +
                                "tags: " + c.tags().size() + ", " +
                                "notes: " + c.notes().size()
                ));
    }

    /**
     * Delete a customer after a Y/N confirmation.
     */
    private void deleteCustomer() {
        System.out.print("Customer id: ");
        String id = scanner.nextLine().trim();
        Customer c = store.get(id);

        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        // show who will be removed
        System.out.println("Found customer: " + c);

        String answer;
        while (true) {
            System.out.print("Are you sure you want to delete this customer? (Y/N): ");
            answer = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            if (answer.equals("Y") || answer.equals("N")) break;
            System.out.println("Invalid input. Please type Y or N.");
        }

        if (answer.equals("Y")) {
            store.remove(id);
            System.out.println(" Customer has been deleted.");
        } else {
            System.out.println(" Action cancelled.");
        }
    }
}
