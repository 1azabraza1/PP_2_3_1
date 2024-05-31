package web.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usersss")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(message = "Bad formed user name: ${validatedValue} \n" +
            "Name should starts with a capital letter and not contain symbols or numbers",
            regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Pattern(message = "Bad formed user lastname: ${validatedValue} \n" +
            "Name should starts with a capital letter and not contain symbols or numbers",
            regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$")
    @NotEmpty(message = "LastName should not be empty")
    @Size(min = 2, max = 50, message = "LastName should be between 2 and 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Pattern(message = "Bad formed user lastname: ${validatedValue} \n" +
            "Name should starts with a capital letter and must contain the @ symbol",
            regexp = "^[^\\s@]+@([^\\s@.,]+\\.)+[^\\s@.,]{2,}$")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Email should be between 2 and 50 characters")
    @Column(name = "email")
    private String email;


    public User() {
    }

    public User(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User {" +
                "id=" + getId() +
                ", firstName='" + getName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
