package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.Task;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.Workblock;
import si.fri.tpo.jpa.WorkloadPK;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class WorkloadDTO extends BaseModelData {

    //private WorkloadPK id;
    //private String timeSpent;
    //private List<Workblock> workblocks;
    //private Task task;
    //private User user;

    public WorkloadDTO() {
    }

    public WorkloadPK getId() {
        return get("id");
    }

    public void setId(WorkloadPK id) {
        set("id", id);
    }

    public String getTimeSpent() {
        return get("timeSpent");
    }

    public void setTimeSpent(String timeSpent) {
        set("timeSpent", timeSpent);
    }

    public List<Workblock> getWorkblocks() {
        return get("workblocks");
    }

    public void setWorkblocks(List<Workblock> workblocks) {
        set("workblocks", workblocks);
    }

    public Task getTask() {
        return get("task");
    }

    public void setTask(Task task) {
        set("task", task);
    }

    public User getUser() {
        return get("user");
    }

    public void setUser(User user) {
        set("user", user);
    }
}
