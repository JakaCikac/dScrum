package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.gwt.server.jpa.*;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class UserStoryDTO extends BaseModelData {

    //private Integer storyId;
    //private String name;
    //private String content;
    //private Integer businessValue;
    //private List<Task> taskList;
    //private List<AcceptanceTest> acceptanceTestList;
    //private Sprint sprint;
    //private Priority priorityPriorityId;
    //private Project projectProjectId;

    public UserStoryDTO() {
    }

    public Integer getStoryId() {
        return get("storyId");
    }

    public void setStoryId(Integer storyId) {
        set("storyId", storyId);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getContent() {
        return get("content");
    }

    public void setContent(String content) {
        set("content", content);
    }

    public Integer getBusinessValue() {
        return get("businessValue");
    }

    public void setBusinessValue(Integer businessValue) {
        set("businessValue", businessValue);
    }

    public List<TaskDTO> getTaskList() {
        return get("taskList");
    }

    public void setTaskList(List<TaskDTO> taskList) {
        set("taskList", taskList);
    }

    public List<AcceptanceTestDTO> getAcceptanceTestList() {
        return get("acceptanceTestList");
    }

    public void setAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestList) {
        set("acceptanceTestList", acceptanceTestList);
    }

    public Sprint getSprint() {
        return get("sprint");
    }

    public void setSprint(Sprint sprint) {
        set("sprint", sprint);
    }

    public Priority getPriorityPriorityId() {
        return get("priorityPriorityId");
    }

    public void setPriorityPriorityId(Priority priorityPriorityId) {
        set("priorityPriorityId", priorityPriorityId);
    }

    public Project getProjectProjectId() {
        return get("projectProjectId");
    }

    public void setProjectProjectId(Project projectProjectId) {
        set("projectProjectId", projectProjectId);
    }
}
