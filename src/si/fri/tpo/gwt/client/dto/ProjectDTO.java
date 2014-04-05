package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class ProjectDTO extends BaseModelData{

    //private Integer projectId;
    //private String name;
    //private String description;
    //private String status;
    //private Team teamTeamId;
    //private List<Discussion> discussionList;
    //private List<DailyScrumEntry> dailyScrumEntryList;
    //private List<Sprint> sprintList;
    //private List<UserStory> userStoryList;

    public ProjectDTO() {
    }

    public Integer getProjectId() {
        return get("projectId");
    }

    public void setProjectId(Integer projectId) {
        set("projectId", projectId);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getDescription() {
        return get("description");
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public String getStatus() {
        return get("status");
    }

    public void setStatus(String status) {
        set("status", status);
    }

    public TeamDTO getTeamTeamId() {
        return get("teamTeamId");
    }

    public void setTeamTeamId(TeamDTO teamTeamId) {
       set("teamTeamId", teamTeamId);
    }

    public List<DiscussionDTO> getDiscussionList() {
        return get("discussionList");
    }

    public void setDiscussionList(List<DiscussionDTO> discussionList) {
        set("discussionList", discussionList);
    }

    public List<DailyScrumEntryDTO> getDailyScrumEntryList() {
        return get("dailyScrumEntryList");
    }

    public void setDailyScrumEntryList(List<DailyScrumEntryDTO> dailyScrumEntryList) {
        set("dailyScrumEntryList", dailyScrumEntryList);
    }

    public List<SprintDTO> getSprintList() {
        return get("sprintList");
    }

    public void setSprintList(List<SprintDTO> sprintList) {
        set("sprintList", sprintList);
    }

    public List<UserStoryDTO> getUserStoryList() {
        return get("userStoryList");
    }

    public void setUserStoryList(List<UserStoryDTO> userStoryList) {
        set("userStoryList", userStoryList);
    }
}
