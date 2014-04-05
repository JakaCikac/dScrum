package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.SprintPK;

import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class SprintDTO extends BaseModelData {

    //protected SprintPK sprintPK;
    //private Integer seqNumber;
    //private Date startDate;
    //private Date endDate;
    //private Integer velocity;
    //private String status;
    //private Project project;
    //private List<UserStory> userStoryList;

    public SprintDTO() {
    }

    public SprintPK getSprintPK() {
        return get("sprintPK");
    }

    public void setSprintPK(SprintPK sprintPK) {
        set("sprintPK", sprintPK);
    }

    public Integer getSeqNumber() {
        return get("seqNumber");
    }

    public void setSeqNumber(Integer seqNumber) {
        set("seqNumber", seqNumber);
    }

    public Date getStartDate() {
        return get("startDate");
    }

    public void setStartDate(Date startDate) {
        set("startDate", startDate);
    }

    public Date getEndDate() {
        return get("endDate");
    }

    public void setEndDate(Date endDate) {
        set("endDate", endDate);
    }

    public Integer getVelocity() {
        return get("velocity");
    }

    public void setVelocity(Integer velocity) {
        set("velocity", velocity);
    }

    public String getStatus() {
        return get("status");
    }

    public void setStatus(String status) {
        set("status", status);
    }

    public Project getProject() {
        return get("project");
    }

    public void setProject(Project project) {
        set("project", project);
    }

    public List<UserStoryDTO> getUserStoryList() {
        return get("userStoryList");
    }

    public void setUserStoryList(List<UserStoryDTO> userStoryList) {
        set("userStoryList", userStoryList);
    }
}
