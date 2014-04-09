package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.Date;

/**
 * Created by t13db on 4.4.2014.
 */
public class DailyScrumEntryDTO extends BaseModelData {

    //protected DailyScrumEntryPK dailyScrumEntryPK;
    //private Date date;
    //private String pastWork;
    //private String futureWork;
    //private String problemsAndIssues;
    //private Project project;
    //private User user;

    public DailyScrumEntryDTO() {
    }

    public DailyScrumEntryPKDTO getDailyScrumEntryPK() {
        return get("dailyScrumEntryPK");
    }

    public void setDailyScrumEntryPK(DailyScrumEntryPKDTO dailyScrumEntryPK) {
        set("dailyScrumEntryPK", dailyScrumEntryPK);
    }

    public Date getDate() {
        return get("date");
    }

    public void setDate(Date date) {
        set("date", date);
    }

    public String getPastWork() {
        return get("pastWork");
    }

    public void setPastWork(String pastWork) {
        set("pastWork", pastWork);
    }

    public String getFutureWork() {
        return get("futureWork");
    }

    public void setFutureWork(String futureWork) {
        set("futureWork", futureWork);
    }

    public String getProblemsAndIssues() {
        return get("problemsAndIssues");
    }

    public void setProblemsAndIssues(String problemsAndIssues) {
        set("problemsAndIssues", problemsAndIssues);
    }

    public ProjectDTO getProject() {
        return get("project");
    }

    public void setProject(ProjectDTO project) {
        set("project", project);
    }

    public UserDTO getUser() {
        return get("user");
    }

    public void setUser(UserDTO user) {
        set("user", user);
    }
}
