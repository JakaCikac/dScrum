package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.Task;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.WorkloadPK;

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

    public WorkloadPK getWorkloadPK() {
        return get("workloadPK");
    }

    public void setWorkloadPK(WorkloadPK workloadPK) {
        set("workloadPK", workloadPK);
    }

    public String getTimeSpent() {
        return get("timeSpent");
    }

    public void setTimeSpent(String timeSpent) {
        set("timeSpent", timeSpent);
    }

    public User getUser() {
        return get("user");
    }

    public void setUser(User user) {
        set("user", user);
    }

    public Task getTask() {
        return get("task");
    }

    public void setTask(Task task) {
        set("task", task);
    }

    public List<WorkblockDTO> getWorkblockList() {
        return get("workblockList");
    }

    public void setWorkblockList(List<WorkblockDTO> workblockList) {
        set("workblockList", workblockList);
    }
}
