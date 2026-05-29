package br.edu.usp.javalibrary.javalibrary.service.domains;

import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String emailAddress;
    private String password;
    private boolean admin;

    public User(UUID id, String name, String emailAddress, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.admin = admin;
    }

    public User() {
    }

    public UUID getID() {
        return id;
    }

    public void setID(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equalsIgnoreCase(password); //Compare MD5 hashes
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final User other = (User) obj;
        return this.id == other.id &&
                this.emailAddress.trim().equalsIgnoreCase(other.emailAddress.trim()) &&
                this.name.trim().equalsIgnoreCase(other.name.trim()) &&
                this.admin == other.admin &&
                isPasswordCorrect(other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailAddress);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", admin=" + admin +
                '}';
    }
}
