package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Created by t13db on 5.4.2014.
 */
public class DiscussionPKDTO extends BaseModelData {

    //private int discussionId;
    //private int userUserId;
    //private int projectProjectId;

    public DiscussionPKDTO() {
    }

    public int getDiscussionId() {
        return get("discussionId");
    }

    public void setDiscussionId(int discussionId) {
        set("discussionId", discussionId);
    }

    public int getUserUserId() {
        return get("userUserId");
    }

    public void setUserUserId(int userUserId) {
        set("userUserId", userUserId);
    }

    public int getProjectProjectId() {
        return get("projectProjectId");
    }

    public void setProjectProjectId(int projectProjectId) {
        set("projectProjectId", projectProjectId);
    }
}
