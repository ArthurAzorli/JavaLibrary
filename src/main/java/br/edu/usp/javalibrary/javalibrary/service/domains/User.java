package br.edu.usp.javalibrary.javalibrary.service.domains;

import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String emailAddress;
    private String address;

    public User(UUID id, String name, String emailAddress, String address) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    public User(String name, String emailAddress, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    public User() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final User other = (User) obj;
        return this.id == other.id &&
                this.emailAddress.trim().equalsIgnoreCase(other.emailAddress.trim()) &&
                this.name.trim().equalsIgnoreCase(other.name.trim()) &&
                this.address.trim().equalsIgnoreCase(other.emailAddress.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailAddress);
    }
}
