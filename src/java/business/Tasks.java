/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.util.Date;

/**
 *
 * @author katie
 */
public class Tasks {

    private int id;
    private String email;
    private String title;
    private String description;
    private Date timestamp;
    private Date dueDate;
    private boolean isCompleted;

    public Tasks(String title, Date timestamp) {
        this.title = title;
        this.timestamp = timestamp;
    }

    public Tasks() {

    }

    public Tasks(int id, String email, String title, String description, Date timestamp, Date dueDate, boolean isCompleted) {
        this.id = id;
        this.email = email;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
