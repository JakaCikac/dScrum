package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

/**
 * Created by t13db on 5.4.2014.
 */
public class SprintPKDTO extends BaseModelData {

    //private int sprintId;
    //private int projectProjectId;

    public SprintPKDTO() {
    }

    public int getSprintId() {
        return get("sprintId");
    }

    public void setSprintId(int sprintId) {
        set("sprintId", sprintId);
    }

    public int getProjectProjectId() {
        return get("projectProjectId");
    }

    public void setProjectProjectId(int projectProjectId) {
        set("projectProjectId", projectProjectId);
    }
}
