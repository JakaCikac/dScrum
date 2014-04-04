package si.fri.tpo.gwt.client.dto;

import si.fri.tpo.jpa.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 3.4.2014.
 */

// TODO: dodaj Base model kot lib (extend)
// TODO: zakomentiraj private in popravi metode za get in set

public class UserDTO implements Serializable{

    private int userId;
    private String email;
    private String firstName;
    private byte isActive;
    private byte isAdmin;
    private String lastName;
    private String password;
    private String salt;
    private Date timeCreated;
    private String username;
    private List<DailyScrumEntry> dailyScrumEntries;
    private List<Discussion> discussions;
    private List<Task> tasks;
    private List<Team> teams;
    private List<Workload> workloads;

    public UserDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<DailyScrumEntry> getDailyScrumEntries() {
        return dailyScrumEntries;
    }

    public void setDailyScrumEntries(List<DailyScrumEntry> dailyScrumEntries) {
        this.dailyScrumEntries = dailyScrumEntries;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Workload> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(List<Workload> workloads) {
        this.workloads = workloads;
    }
}
