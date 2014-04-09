package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;
/**
 * Created by t13db on 5.4.2014.
 */
public class WorkblockPKDTO extends BaseModelData {

    //private int workblockId;
    //private int workloadWorkloadId;
    //private int workloadTaskTaskId;
    //private int workloadTaskUserStoryStoryId;
    //private int workloadUserUserId;

    public WorkblockPKDTO() {
    }

    public int getWorkblockId() {
        return get("workblockId");
    }

    public void setWorkblockId(int workblockId) {
        set("workblockId", workblockId);
    }

    public int getWorkloadWorkloadId() {
        return get("workloadWorkloadId");
    }

    public void setWorkloadWorkloadId(int workloadWorkloadId) {
        set("workloadWorkloadId", workloadWorkloadId);
    }

    public int getWorkloadTaskTaskId() {
        return get("workloadTaskTaskId");
    }

    public void setWorkloadTaskTaskId(int workloadTaskTaskId) {
        set("workloadTaskTaskId", workloadTaskTaskId);
    }

    public int getWorkloadTaskUserStoryStoryId() {
        return get("workloadTaskUserStoryStoryId");
    }

    public void setWorkloadTaskUserStoryStoryId(int workloadTaskUserStoryStoryId) {
        set("workloadTaskUserStoryStoryId", workloadTaskUserStoryStoryId);
    }

    public int getWorkloadUserUserId() {
        return get("workloadUserUserId");
    }

    public void setWorkloadUserUserId(int workloadUserUserId) {
        set("workloadUserUserId", workloadUserUserId);
    }
}
