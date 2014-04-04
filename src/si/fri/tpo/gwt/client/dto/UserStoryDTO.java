package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.*;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class UserStoryDTO extends BaseModelData {

    //private int storyId;
    //private int businessValue;
    //private String content;
    //private String name;
    //private List<AcceptanceTest> acceptanceTests;
    //private List<Task> tasks;
    //private Project project;
    //private Priority priority;
    //private Sprint sprint;

    public UserStoryDTO() {
    }

    public int getStoryId() {
        return get("storyId");
    }

    public void setStoryId(int storyId) {
        set("storyId", storyId);
    }

    public int getBusinessValue() {
        return get("businessValue");
    }

    public void setBusinessValue(int businessValue) {
        set("businessValue", businessValue);
    }

    public String getContent() {
        return get("content");
    }

    public void setContent(String content) {
        set("content", content);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public List<AcceptanceTest> getAcceptanceTests() {
        return get("acceptanceTests");
    }

    public void setAcceptanceTests(List<AcceptanceTest> acceptanceTests) {
        set("acceptanceTests", acceptanceTests);
    }

    public List<Task> getTasks() {
        return get("tasks");
    }

    public void setTasks(List<Task> tasks) {
        set("tasks", tasks);
    }

    public Project getProject() {
        return get("project");
    }

    public void setProject(Project project) {
        set("project", project);
    }

    public Priority getPriority() {
        return get("priority");
    }

    public void setPriority(Priority priority) {
        set("priority", priority);
    }

    public Sprint getSprint() {
        return get("sprint");
    }

    public void setSprint(Sprint sprint) {
        set("sprint", sprint);
    }
}
