package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;
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
    //private Integer preassignedUserId;
    //private List<Workload> workloadList;
    //private UserStory userStory;
    //private User userUserId;

    public TaskDTO() {
    }

    public TaskPKDTO getTaskPK() {
        return get("taskPK");
    }

    public void setTaskPK(TaskPKDTO taskPK) {
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

    public String getPreassignedUserName() {
        return get("preassignedUserName");
    }

    public void setPreassignedUserName(String preassignedUserName) {
        set("preassignedUserName", preassignedUserName);
    }

    public List<WorkloadDTO> getWorkloadList() {
        return get("workloadList");
    }

    public void setWorkloadList(List<WorkloadDTO> workloadList) {
        set("workloadList", workloadList);
    }

    public UserStoryDTO getUserStory() {
        return get("userStory");
    }

    public void setUserStory(UserStoryDTO userStory) {
        set("userStory", userStory);
    }

    public UserDTO getUserUserId() {
        return get("userUserId");
    }

    public void setUserUserId(UserDTO userUserId) {
        set("userUserId", userUserId);
    }
}
