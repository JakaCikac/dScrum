package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Created by t13db on 5.4.2014.
 */
public class WorkloadPKDTO extends BaseModelData {

    //private int workloadId;
    //private int taskTaskId;
    //private int taskUserStoryStoryId;
    //private int userUserId;

    public WorkloadPKDTO() {
    }

    public int getWorkloadId() {
        return get("workloadId");
    }

    public void setWorkloadId(int workloadId) {
        set("workloadId", workloadId);
    }

    public int getTaskTaskId() {
        return get("taskTaskId");
    }

    public void setTaskTaskId(int taskTaskId) {
        set("taskTaskId", taskTaskId);
    }

    public int getTaskUserStoryStoryId() {
        return get("taskUserStoryStoryId");
    }

    public void setTaskUserStoryStoryId(int taskUserStoryStoryId) {
        set("taskUserStoryStoryId", taskUserStoryStoryId);
    }

    public int getUserUserId() {
        return get("userUserId");
    }

    public void setUserUserId(int userUserId) {
        set("userUserId", userUserId);
    }
}
