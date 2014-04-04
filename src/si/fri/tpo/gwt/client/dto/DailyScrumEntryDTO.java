package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.DailyScrumEntryPK;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.User;

import java.util.Date;

/**
 * Created by t13db on 4.4.2014.
 */
public class DailyScrumEntryDTO extends BaseModelData {

    //private DailyScrumEntryPK id;
    //private Date date;
    //private String futureWork;
    //private String pastWork;
    //private String problemsAndIssues;
    //private User user;
    //private Project project;

    public DailyScrumEntryDTO() {
    }

    public DailyScrumEntryPK getId() {
        return get("id");
    }

    public void setId(DailyScrumEntryPK id) {
        set("id", id);
    }

    public Date getDate() {
        return get("date");
    }

    public void setDate(Date date) {
        set("date", date);
    }

    public String getFutureWork() {
        return get("futureWork");
    }

    public void setFutureWork(String futureWork) {
        set("futureWork", futureWork);
    }

    public String getPastWork() {
        return get("pastWork");
    }

    public void setPastWork(String pastWork) {
        set("pastWork", pastWork);
    }

    public String getProblemsAndIssues() {
        return get("problemsAndIssues");
    }

    public void setProblemsAndIssues(String problemsAndIssues) {
        set("problemsAndIssues", problemsAndIssues);
    }

    public User getUser() {
        return get("user");
    }

    public void setUser(User user) {
        set("user", user);
    }

    public Project getProject() {
        return get("project");
    }

    public void setProject(Project project) {
        set("project", project);
    }
}
