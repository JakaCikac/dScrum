package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.*;

import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 3.4.2014.
 */

public class UserDTO extends BaseModelData{

    //private int userId;
    //private String email;
    //private String firstName;
    //private byte isActive;
    //private byte isAdmin;
    //private String lastName;
    //private String password;
    //private String salt;
    //private Date timeCreated;
    //private String username;
    //private List<DailyScrumEntry> dailyScrumEntries;
    //private List<Discussion> discussions;
    //private List<Task> tasks;
    //private List<Team> teams;
    //private List<Workload> workloads;

    public UserDTO() {
    }

    public int getUserId() {
        return get("userId");
    }

    public void setUserId(int userId) {
        set("userId", userId);
    }

    public String getEmail() {
        return get("email");
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public String getFirstName() {
        return get("firstName");
    }

    public void setFirstName(String firstName) {
        set("firstName", firstName);
    }

    public byte getIsActive() {
        return get("isActive");
    }

    public void setIsActive(byte isActive) {
        set("isActive", isActive);
    }

    public byte getIsAdmin() {
        return get("isAdmin");
    }

    public void setIsAdmin(byte isAdmin) {
        set("isAdmin", isAdmin);
    }

    public String getLastName() {
        return get("lastName");
    }

    public void setLastName(String lastName) {
        set("lastName", lastName);
    }

    public String getPassword() {
        return get("password");
    }

    public void setPassword(String password) {
        set("password", password);
    }

    public String getSalt() {
        return get("salt");
    }

    public void setSalt(String salt) {
        set("salt", salt);
    }

    public Date getTimeCreated() {
        return get("timeCreated");
    }

    public void setTimeCreated(Date timeCreated) {
        set("timeCreated", timeCreated);
    }

    public String getUsername() {
        return get("username");
    }

    public void setUsername(String username) {
        set("username", username);
    }

    public List<DailyScrumEntry> getDailyScrumEntries() {
        return get("dailyScrumEntries");
    }

    public void setDailyScrumEntries(List<DailyScrumEntry> dailyScrumEntries) {
        set("dailyScrumEntries", dailyScrumEntries);
    }

    public List<Discussion> getDiscussions() {
        return get("discussions");
    }

    public void setDiscussions(List<Discussion> discussions) {
        set("discussions", discussions);
    }

    public List<Task> getTasks() {
        return get("tasks");
    }

    public void setTasks(List<Task> tasks) {
        set("tasks", tasks);
    }

    public List<Team> getTeams() {
        return get("teams");
    }

    public void setTeams(List<Team> teams) {
        set("teams", teams);
    }

    public List<Workload> getWorkloads() {
        return get("workloads");
    }

    public void setWorkloads(List<Workload> workloads) {
        set("workloads", workloads);
    }
}
