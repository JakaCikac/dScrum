package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class WorkloadDTO extends BaseModelData {

    //protected WorkloadPK workloadPK;
    //private String timeSpent;
    //private String timeRemaining;
    //private Date day;
    //private Date startTime;
    //private Date stopTime;
    //private boolean started;
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

    public String getTimeRemaining() {
        return get("timeRemaining");
    }

    public void setTimeRemaining(String timeRemaining) {
        set("timeRemaining", timeRemaining);
    }

    public Date getDay() {
        return get("day");
    }

    public void setDay(Date day) {
        set("day", day);
    }

    public Date getStartTime() {
        return get("startTime");
    }

    public void setStartTime(Date startTime) {
        set("startTime", startTime);
    }

    public Date getStopTime() {
        return get("stopTime");
    }

    public void setStopTime(Date stopTime) {
        set("stopTime", stopTime);
    }

    public boolean getStarted() {
        return get("started");
    }

    public void setStarted(boolean started) {
        set("started", started);
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
