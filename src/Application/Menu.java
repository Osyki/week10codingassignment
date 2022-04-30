package Application;

import DAO.AddressBookDao;
import Entity.Contact;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final AddressBookDao addressBookDao;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Sets up connections to address book database
     */
    public Menu() {
        addressBookDao = new AddressBookDao();
    }

    /**
     * Welcome Message
     */
    public void start() {
        System.out.println("Welcome to your address book!");
        printMenu();
    }

    /**
     * Main Menu
     */
    private void printMenu() {
        System.out.println("\r\n************************");
        System.out.println("C - Create a new entry");
        System.out.println("R - Read existing entries");
        System.out.println("U - Update an existing entry");
        System.out.println("D - Delete an existing entry");
        System.out.println("Q - Quit");
        System.out.println("************************");
        System.out.print("\r\nEnter option: ");
        String userOption = scanner.nextLine().toLowerCase();

        try {
            switch(userOption) {
                case "c":
                    createEntry();
                    printMenu();
                    break;

                case "r":
                    readEntry();
                    printMenu();
                    break;

                case "u":
                    updateEntry();
                    printMenu();
                    break;

                case "d":
                    deleteEntry();
                    printMenu();
                    break;

                case "q":
                    break;

                default:
                    System.out.println("\r\nInvalid selection. Please choose from the following:");
                    printMenu();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes existing entry for given ID
     * Does NOT check if ID exists
     */
    private void deleteEntry() {
        System.out.println("Enter an ID to delete: ");
        int entryID = scanner.nextInt();
        try {
            addressBookDao.deleteContact(entryID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
    }

    /**
     * Updates existing address for given ID
     * Does NOT check if ID exists
     */
    private void updateEntry() {
        System.out.println("Enter an ID to update: ");
        int entryID = scanner.nextInt();
        System.out.println("Enter new address: ");
        scanner.nextLine();
        String address = scanner.nextLine();
        try {
            addressBookDao.updateContact(entryID,address);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all entries in address book
     * @throws SQLException
     */
    private void readEntry() throws SQLException {
        List<Contact> contacts = addressBookDao.getContacts();

        if (contacts.isEmpty()) {
            System.out.println("\r\n**Address book is empty.**");
        } else {
            System.out.println("\r\n------------------------");
            for (Contact contact : contacts) {
                System.out.println("Entry ID: " + contact.getId());
                System.out.println(contact.getFirstName() + " " + contact.getLastName());
                System.out.println(contact.getAddress());
                System.out.println("------------------------");
            }
        }
    }

    /**
     * Creates new entry in address book
     */
    private void createEntry() {
        System.out.print("\r\nEnter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.println("\r\nYou entered:\r\n" +
                firstName + " " + lastName + "\r\n" + address + "\r\n");
        System.out.print("Is this correct? (y/n) : ");
        String userOption = scanner.nextLine().toLowerCase();

        if (userOption.equals("y")) {
            try {
                addressBookDao.createContact(firstName,lastName,address);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (userOption.equals("n")) {
            createEntry();
        }
    }
}
