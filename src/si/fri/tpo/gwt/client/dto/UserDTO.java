package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;
import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 3.4.2014.
 */

public class UserDTO extends BaseModelData{

    //private Integer userId;
    //private String username;
    //private String password;
    //private String firstName;
    //private String lastName;
    //private String email;
    //private boolean isAdmin;
    //private String salt;
    //private boolean isActive;
    //private Date timeCreated;
    //private List<Team> teamList;
    //private List<Workload> workloadList;
    //private List<Discussion> discussionList;
    //private List<Task> taskList;
    //private List<DailyScrumEntry> dailyScrumEntryList;
    //private List<Comment> commentList;

    public UserDTO() {
    }

    /*public UserDTO(String username, String firstName, String lastName, String email, boolean isAdmin, boolean isActive ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    } */

    public Integer getUserId() {
        return get("userId");
    }

    public void setUserId(Integer userId) {
        set("userId", userId);
    }

    public String getUsername() {
        return get("username");
    }

    public void setUsername(String username) {
        set("username", username);
    }

    public String getPassword() {
        return get("password");
    }

    public void setPassword(String password) {
        set("password", password);
    }

    public String getFirstName() {
        return get("firstName");
    }

    public void setFirstName(String firstName) {
        set("firstName", firstName);
    }

    public String getLastName() {
        return get("lastName");
    }

    public void setLastName(String lastName) {
        set("lastName", lastName);
    }

    public String getEmail() {
        return get("email");
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public boolean isAdmin() {
        return get("isAdmin");
    }

    public void setAdmin(boolean isAdmin) {
        set("isAdmin", isAdmin);
    }

    public String getSalt() {
        return get("salt");
    }

    public void setSalt(String salt) {
        set("salt", salt);
    }

    public boolean isActive() {
        return get("isActive");
    }

    public void setActive(boolean isActive) {
        set("isActive", isActive);
    }

    public Date getTimeCreated() {
        return get("timeCreated");
    }

    public void setTimeCreated(Date timeCreated) {
        set("timeCreated", timeCreated);
    }

    public List<TeamDTO> getTeamList() {
        return get("teamList");
    }

    public void setTeamList(List<TeamDTO> teamList) {
        set("teamList", teamList);
    }

    public List<WorkloadDTO> getWorkloadList() {
        return get("workloadList");
    }

    public void setWorkloadList(List<WorkloadDTO> workloadList) {
        set("workloadList", workloadList);
    }

    public List<DiscussionDTO> getDiscussionList() {
        return get("discussionList");
    }

    public void setDiscussionList(List<DiscussionDTO> discussionList) {
        set("discussionList", discussionList);
    }

    public List<TaskDTO> getTaskList() {
        return get("taskList");
    }

    public void setTaskList(List<TaskDTO> taskList) {
        set("taskList", taskList);
    }

    public List<DailyScrumEntryDTO> getDailyScrumEntryList() {
        return get("dailyScrumEntryList");
    }

    public void setDailyScrumEntryList(List<DailyScrumEntryDTO> dailyScrumEntryList) {
        set("dailyScrumEntryList", dailyScrumEntryList);
    }

    public List<CommentDTO> getCommentList() {
        return get("commentList");
    }

    public void setCommentList(List<CommentDTO> commentList) {
        set("commentList", commentList);
    }
}
