package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class TeamDTO extends BaseModelData {

    //private Integer teamId;
    //private int scrumMasterId;
    //private int productOwnerId;
    //private List<User> userList;
    //private List<Project> projectList;

    public TeamDTO() {
    }

    public Integer getTeamId() {
        return get("teamId");
    }

    public void setTeamId(Integer teamId) {
        set("teamId", teamId);
    }

    public int getScrumMasterId() {
        return get("scrumMasterId");
    }

    public void setScrumMasterId(int scrumMasterId) {
        set("scrumMasterId", scrumMasterId);
    }

    public int getProductOwnerId() {
        return get("productOwnerId");
    }

    public void setProductOwnerId(int productOwnerId) {
        set("productOwnerId", productOwnerId);
    }

    public List<UserDTO> getUserList() {
        return get("userList");
    }

    public void setUserList(List<UserDTO> userList) {
        set("userList", userList);
    }

    public List<ProjectDTO> getProjectList() {
        return get("projectList");
    }

    public void setProjectList(List<ProjectDTO> projectList) {
        set("projectList", projectList);
    }
}
