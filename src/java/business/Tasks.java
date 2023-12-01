/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author katie
 */
public class Tasks implements Serializable {

    private int id;
    private String email;
    private String title;
    private String description;
    private Timestamp timestamp;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Tasks(String title, Timestamp timestamp) {
        this.title = title;
        this.timestamp = timestamp;
    }

    public Tasks() {

    }
    
    public Tasks(int id, String email, String title, String description, Timestamp timestamp, boolean isCompleted) {
        this(id, email, title, description, timestamp, null, isCompleted);
    }

    public Tasks(int id, String email, String title, String description, Timestamp timestamp, LocalDate dueDate, boolean isCompleted) {
        this.id = id;
        this.email = (email != null) ? email : "";
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
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
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void getDueDate(LocalDate toLocalDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
