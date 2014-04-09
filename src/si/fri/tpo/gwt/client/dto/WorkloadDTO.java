package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class WorkloadDTO extends BaseModelData {

    //protected WorkloadPK workloadPK;
    //private String timeSpent;
    //private User user;
    //private Task task;
    //private List<Workblock> workblockList;

    public WorkloadDTO() {
    }

    public WorkloadPKDTO getWorkloadPK() {
        return get("workloadPK");
    }

    public void setWorkloadPK(WorkloadPKDTO workloadPK) {
        set("workloadPK", workloadPK);
    }

    public String getTimeSpent() {
        return get("timeSpent");
    }

    public void setTimeSpent(String timeSpent) {
        set("timeSpent", timeSpent);
    }

    public UserDTO getUser() {
        return get("user");
    }

    public void setUser(UserDTO user) {
        set("user", user);
    }

    public TaskDTO getTask() {
        return get("task");
    }

    public void setTask(TaskDTO task) {
        set("task", task);
    }

    public List<WorkblockDTO> getWorkblockList() {
        return get("workblockList");
    }

    public void setWorkblockList(List<WorkblockDTO> workblockList) {
        set("workblockList", workblockList);
    }
}
