package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Created by t13db on 5.4.2014.
 */
public class DailyScrumEntryPKDTO extends BaseModelData {

    //private int dailyScrumId;
    //private int userUserId;
    //private int projectProjectId;

    public DailyScrumEntryPKDTO() {
    }

    public int getDailyScrumId() {
        return get("dailyScrumId");
    }

    public void setDailyScrumId(int dailyScrumId) {
        set("dailyScrumId", dailyScrumId);
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
