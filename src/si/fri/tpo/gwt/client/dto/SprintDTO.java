package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.SprintPK;
import si.fri.tpo.jpa.UserStory;

import java.util.Date;
import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class SprintDTO extends BaseModelData {

    //private SprintPK id;
    //private Date endDate;
    //private int seqNumber;
    //private Date startDate;
    //private String status;
    //private int velocity;
    //private Project project;
    //private List<UserStory> userStories;

    public SprintDTO() {
    }

    public SprintPK getId() {
        return get("id");
    }

    public void setId(SprintPK id) {
        set("id", id);
    }

    public Date getEndDate() {
        return get("endDate");
    }

    public void setEndDate(Date endDate) {
        set("endDate", endDate);
    }

    public int getSeqNumber() {
        return get("seqNumber");
    }

    public void setSeqNumber(int seqNumber) {
        set("seqNumber", seqNumber);
    }

    public Date getStartDate() {
        return get("startDate");
    }

    public void setStartDate(Date startDate) {
        set("startDate", startDate);
    }

    public String getStatus() {
        return get("status");
    }

    public void setStatus(String status) {
        set("status", status);
    }

    public int getVelocity() {
        return get("velocity");
    }

    public void setVelocity(int velocity) {
        set("velocity", velocity);
    }

    public Project getProject() {
        return get("project");
    }

    public void setProject(Project project) {
        set("project", project);
    }

    public List<UserStory> getUserStories() {
        return get("userStories");
    }

    public void setUserStories(List<UserStory> userStories) {
        set("userStories", userStories);
    }
}
