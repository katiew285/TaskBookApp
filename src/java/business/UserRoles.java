
package business;

import java.io.Serializable;

/**
 *
 * @author katie
 */
public class UserRoles implements Serializable {
    
    private int id;
    private String email;
    private String role;
    private boolean isAdmin;

    public UserRoles() {
    }

    public UserRoles(int id, String email, String role, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.isAdmin = isAdmin;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public boolean isAdmin(){
        return "admin".equalsIgnoreCase(role);
    }
}
