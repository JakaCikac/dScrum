package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.*;
import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class ProjectDTO extends BaseModelData{

    //private int projectId;
    //private String description;
    //private String name;
    //private String status;
    //private List<DailyScrumEntry> dailyScrumEntries;
    //private List<Discussion> discussions;
    //private Team team;
    //private List<Sprint> sprints;
    //private List<UserStory> userStories;

    public ProjectDTO() {
    }

    public int getProjectId() {
        return get("projectId");
    }

    public void setProjectId(int projectId) {
        set("projectId", projectId);
    }

    public String getDescription() {
        return get("description");
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getStatus() {
        return get("status");
    }

    public void setStatus(String status) {
        set("status", status);
    }

    public List<DailyScrumEntry> getDailyScrumEntries() {
        return get("dailyScrumEntries");
    }

    public void setDailyScrumEntries(List<DailyScrumEntry> dailyScrumEntries) {
        set("dailyScrumEntries", dailyScrumEntries);
    }

    public List<Discussion> getDiscussions() {
        return get("discussions");
    }

    public void setDiscussions(List<Discussion> discussions) {
        set("discussions", discussions);
    }

    public Team getTeam() {
        return get("team");
    }

    public void setTeam(Team team) {
        set("team", team);
    }

    public List<Sprint> getSprints() {
        return get("sprints");
    }

    public void setSprints(List<Sprint> sprints) {
        set("sprints", sprints);
    }

    public List<UserStory> getUserStories() {
        return get("userStories");
    }

    public void setUserStories(List<UserStory> userStories) {
        set("userStories", userStories);
    }
}
