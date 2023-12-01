
package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author katie
 */
public class User implements Serializable {

    private int id;
    private String email;
    private String password;
    private String name;
    private LocalDate dob;
    private String state;
    private Set<String> role;

    public User() {
    }

    public User(int id, String email, String password, String name, LocalDate dob, String state, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.state = state;
        this.role = roles;
    }

    public User(int i, String adminadmincom, String pssword1, String admin, String string, String ne) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRoles(Set<String> role) {
        this.role = role;
    }

    public boolean isAdmin(){
        return email.toLowerCase().contains("admin");
    }
}
