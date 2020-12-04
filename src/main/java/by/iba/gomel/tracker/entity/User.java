package by.iba.gomel.tracker.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NotNull(message = "Field should not be null")
    @Size(min = 3, max = 45, message = "First name must be more then 5 and less then 45")
    @NotBlank(message = "Field should not be blank!")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 45, message = "Last name must be more then 5 and less then 45")
    @NotNull(message = "Field should not be null")
    @NotBlank(message = "Field should not be blank!")
    private String lastName;

    @Column(name = "email")
    @Email(message = "Field should have email format. Example: JohnDoe@gmail.com")
    @NotNull(message = "Field should not be null")
    @NotBlank(message = "Field should not be blank!")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Field should not be null")
    @NotBlank(message = "Field should not be blank!")
    private String password;

    public User() {
        //not implemented
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
