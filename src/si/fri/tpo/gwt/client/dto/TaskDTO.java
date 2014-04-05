package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.TaskPK;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.UserStory;
import si.fri.tpo.jpa.Workload;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class TaskDTO extends BaseModelData {

    //private TaskPK id;
    //private String description;
    //private int estimatedTime;
    //private String status;
    //private int timeRemaining;
    //private User user;
    //private UserStory userStory;
    //private List<Workload> workloads;

    public TaskDTO() {
    }

    public TaskPK getId() {
        return get("id");
    }

    public void setId(TaskPK id) {
        set("id", id);
    }

    public String getDescription() {
        return get("description");
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public int getEstimatedTime() {
        return get("estimatedTime");
    }

    public void setEstimatedTime(int estimatedTime) {
        set("estimatedTime", estimatedTime);
    }

    public String getStatus() {
        return get("status");
    }

    public void setStatus(String status) {
        set("status", status);
    }

    public int getTimeRemaining() {
        return get("timeRemaining");
    }

    public void setTimeRemaining(int timeRemaining) {
        set("timeRemaining", timeRemaining);
    }

    public User getUser() {
        return get("user");
    }

    public void setUser(User user) {
        set("user", user);
    }

    public UserStory getUserStory() {
        return get("userStory");
    }

    public void setUserStory(UserStory userStory) {
        set("userStory", userStory);
    }

    public List<WorkloadDTO> getWorkloads() {
        return get("workloads");
    }

    public void setWorkloads(List<WorkloadDTO> workloads) {
        set("workloads", workloads);
    }
}