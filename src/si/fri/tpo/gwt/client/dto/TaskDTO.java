package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.gwt.server.jpa.TaskPK;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.jpa.UserStory;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class TaskDTO extends BaseModelData {

    //protected TaskPK taskPK;
    //private String description;
    //private int timeRemaining;
    //private int estimatedTime;
    //private String status;
    //private List<Workload> workloadList;
    //private UserStory userStory;
    //private User userUserId;

    public TaskDTO() {
    }

    public TaskPK getTaskPK() {
        return get("taskPK");
    }

    public void setTaskPK(TaskPK taskPK) {
        set("taskPK", taskPK);
    }

    public String getDescription() {
        return get("description");
    }

    public void setDescription(String description) {
       set("description", description);
    }

    public int getTimeRemaining() {
        return get("timeRemaining");
    }

    public void setTimeRemaining(int timeRemaining) {
        set("timeRemaining", timeRemaining);
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

    public List<WorkloadDTO> getWorkloadList() {
        return get("workloadList");
    }

    public void setWorkloadList(List<WorkloadDTO> workloadList) {
        set("workloadList", workloadList);
    }

    public UserStory getUserStory() {
        return get("userStory");
    }

    public void setUserStory(UserStory userStory) {
        set("userStory", userStory);
    }

    public User getUserUserId() {
        return get("userUserId");
    }

    public void setUserUserId(User userUserId) {
        set("userUserId", userUserId);
    }
}
