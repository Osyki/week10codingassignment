package DAO;

import Entity.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDao {
    private final Connection connection;

    public AddressBookDao() {
        connection = DBConnection.getConnection();
    }

    public List<Contact> getContacts() throws SQLException {
        String GET_CONTACTS_QUERY = "SELECT * FROM contacts";
        ResultSet rs = connection.prepareStatement(GET_CONTACTS_QUERY).executeQuery();
        List<Contact> contacts = new ArrayList<>();

        while (rs.next()) {
            contacts.add(populateContact(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        return contacts;
    }

    private Contact populateContact(int id, String firstName, String lastName, String address) {
        return new Contact(id, firstName, lastName, address);
    }

    public void createContact(String firstName, String lastName, String address) throws SQLException {
        String CREATE_CONTACT_QUERY = "INSERT INTO contacts (first_name, last_name, address) VALUES(?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(CREATE_CONTACT_QUERY);
        stmt.setString(1,firstName);
        stmt.setString(2,lastName);
        stmt.setString(3,address);
        stmt.executeUpdate();
    }

    public void deleteContact(int entryID) throws SQLException {
        String DELETE_CONTACT_QUERY = "DELETE FROM contacts WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(DELETE_CONTACT_QUERY);
        stmt.setInt(1,entryID);
        stmt.executeUpdate();
    }

    public void updateContact(int entryID, String address) throws SQLException {
        String UPDATE_CONTACT_QUERY = "UPDATE contacts SET address = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(UPDATE_CONTACT_QUERY);
        stmt.setString(1,address);
        stmt.setInt(2,entryID);
        stmt.executeUpdate();
    }
}
