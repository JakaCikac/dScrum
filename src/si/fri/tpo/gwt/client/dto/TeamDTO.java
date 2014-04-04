package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.User;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class TeamDTO extends BaseModelData {

    //private int teamId;
    //private byte isProductOwner;
    //private byte isScrumMaster;
    //private String userId;
    //private List<Project> projects;
    //private List<User> users;

    public TeamDTO() {
    }

    public int getTeamId() {
        return get("teamId");
    }

    public void setTeamId(int teamId) {
        set("teamId", teamId);
    }

    public byte getIsProductOwner() {
        return get("isProductOwner");
    }

    public void setIsProductOwner(byte isProductOwner) {
        set("isProductOwner;", isProductOwner);
    }

    public byte getIsScrumMaster() {
        return get("isScrumMaster");
    }

    public void setIsScrumMaster(byte isScrumMaster) {
        set("isScrumMaster", isScrumMaster);
    }

    public String getUserId() {
        return get("userId");
    }

    public void setUserId(String userId) {
        set("userId", userId);
    }

    public List<Project> getProjects() {
        return get("projects");
    }

    public void setProjects(List<Project> projects) {
        set("projects", projects);
    }

    public List<User> getUsers() {
        return get("users");
    }

    public void setUsers(List<User> users) {
        set("users", users);
    }
}
