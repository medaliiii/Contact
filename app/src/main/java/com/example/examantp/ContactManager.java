package com.example.examantp;

import java.util.ArrayList;

public class ContactManager {

    private static ArrayList<Contact> listContacts = new ArrayList<>();
    private static int idCounter = 1; // Counter for generating unique IDs

    // Adding some fake contacts for demonstration purposes
    static {
        ajout("John Doe", "johndoe", "1234567890");
        ajout("Jane Smith", "janesmith", "0987654321");
        ajout("Alice Johnson", "alicejohnson", "1122334455");
        ajout("Bob Brown", "bobbrown", "2233445566");
        ajout("Charlie Davis", "charliedavis", "3344556677");
    }

    public static long ajout(String nom, String pseudo, String num) {
        Contact contact = new Contact(idCounter++, nom, pseudo, num);
        listContacts.add(contact);
        return contact.getId();
    }

    public static ArrayList<Contact> getAllContact() {
        return new ArrayList<>(listContacts); // Return a copy of the list
    }

    public static void supprimerContact(int id) {
        for (int i = 0; i < listContacts.size(); i++) {
            if (listContacts.get(i).getId() == id) {
                listContacts.remove(i); // Remove contact from list
                break;
            }
        }
    }

    public static int modifierContact(int id, String nom, String pseudo, String num) {
        for (Contact contact : listContacts) {
            if (contact.getId() == id) {
                contact.setNom(nom);
                contact.setPseudo(pseudo);
                contact.setNum(num);
                return 1; // Indicate successful update
            }
        }
        return 0; // Indicate contact not found
    }
}