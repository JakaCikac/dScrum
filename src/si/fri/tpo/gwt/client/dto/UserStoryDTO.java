package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class UserStoryDTO extends BaseModelData {

    //private Integer storyId;
    //private String name;
    //private String content;
    //private Integer businessValue;
    //private String status;
    //private Double estimateTime;
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

    public String getStatus() {
        return get("status");
    }

    public void setStatus(String status){
        set("status", status);
    }

    public Double getEstimateTime(){
        return get("estimateTime");
    }

    public void setEstimateTime(Double estimateTime){
        set("estimateTime", estimateTime);
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

    public SprintDTO getSprint() {
        return get("sprint");
    }

    public void setSprint(SprintDTO sprint) {
        set("sprint", sprint);
    }

    public PriorityDTO getPriorityPriorityId() {
        return get("priorityPriorityId");
    }

    public void setPriorityPriorityId(PriorityDTO priorityPriorityId) {
        set("priorityPriorityId", priorityPriorityId);
    }

    public ProjectDTO getProjectProjectId() {
        return get("projectProjectId");
    }

    public void setProjectProjectId(ProjectDTO projectProjectId) {
        set("projectProjectId", projectProjectId);
    }
}
