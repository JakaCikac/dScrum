package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Created by t13db on 5.4.2014.
 */
public class TaskPKDTO extends BaseModelData {

    //private int taskId;
    //private int userStoryStoryId;

    public TaskPKDTO() {
    }

    public int getTaskId() {
        return get("taskId");
    }

    public void setTaskId(int taskId) {
        set("taskId", taskId);
    }

    public int getUserStoryStoryId() {
        return get("userStoryStoryId");
    }

    public void setUserStoryStoryId(int userStoryStoryId) {
        set("userStoryStoryId", userStoryStoryId);
    }
}
