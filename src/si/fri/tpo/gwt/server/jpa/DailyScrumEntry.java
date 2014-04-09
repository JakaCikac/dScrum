/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "daily_scrum_entry")
@NamedQueries({
    @NamedQuery(name = "DailyScrumEntry.findAll", query = "SELECT d FROM DailyScrumEntry d"),
    @NamedQuery(name = "DailyScrumEntry.findByDailyScrumId", query = "SELECT d FROM DailyScrumEntry d WHERE d.dailyScrumEntryPK.dailyScrumId = :dailyScrumId"),
    @NamedQuery(name = "DailyScrumEntry.findByDate", query = "SELECT d FROM DailyScrumEntry d WHERE d.date = :date"),
    @NamedQuery(name = "DailyScrumEntry.findByPastWork", query = "SELECT d FROM DailyScrumEntry d WHERE d.pastWork = :pastWork"),
    @NamedQuery(name = "DailyScrumEntry.findByFutureWork", query = "SELECT d FROM DailyScrumEntry d WHERE d.futureWork = :futureWork"),
    @NamedQuery(name = "DailyScrumEntry.findByProblemsAndIssues", query = "SELECT d FROM DailyScrumEntry d WHERE d.problemsAndIssues = :problemsAndIssues"),
    @NamedQuery(name = "DailyScrumEntry.findByUserUserId", query = "SELECT d FROM DailyScrumEntry d WHERE d.dailyScrumEntryPK.userUserId = :userUserId"),
    @NamedQuery(name = "DailyScrumEntry.findByProjectProjectId", query = "SELECT d FROM DailyScrumEntry d WHERE d.dailyScrumEntryPK.projectProjectId = :projectProjectId")})
public class DailyScrumEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DailyScrumEntryPK dailyScrumEntryPK;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "past_work")
    private String pastWork;
    @Basic(optional = false)
    @Column(name = "future_work")
    private String futureWork;
    @Basic(optional = false)
    @Column(name = "problems_and_issues")
    private String problemsAndIssues;
    @JoinColumn(name = "project_project_id", referencedColumnName = "project_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public DailyScrumEntry() {
    }

    public DailyScrumEntry(DailyScrumEntryPK dailyScrumEntryPK) {
        this.dailyScrumEntryPK = dailyScrumEntryPK;
    }

    public DailyScrumEntry(DailyScrumEntryPK dailyScrumEntryPK, Date date, String pastWork, String futureWork, String problemsAndIssues) {
        this.dailyScrumEntryPK = dailyScrumEntryPK;
        this.date = date;
        this.pastWork = pastWork;
        this.futureWork = futureWork;
        this.problemsAndIssues = problemsAndIssues;
    }

    public DailyScrumEntry(int dailyScrumId, int userUserId, int projectProjectId) {
        this.dailyScrumEntryPK = new DailyScrumEntryPK(dailyScrumId, userUserId, projectProjectId);
    }

    public DailyScrumEntryPK getDailyScrumEntryPK() {
        return dailyScrumEntryPK;
    }

    public void setDailyScrumEntryPK(DailyScrumEntryPK dailyScrumEntryPK) {
        this.dailyScrumEntryPK = dailyScrumEntryPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPastWork() {
        return pastWork;
    }

    public void setPastWork(String pastWork) {
        this.pastWork = pastWork;
    }

    public String getFutureWork() {
        return futureWork;
    }

    public void setFutureWork(String futureWork) {
        this.futureWork = futureWork;
    }

    public String getProblemsAndIssues() {
        return problemsAndIssues;
    }

    public void setProblemsAndIssues(String problemsAndIssues) {
        this.problemsAndIssues = problemsAndIssues;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dailyScrumEntryPK != null ? dailyScrumEntryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyScrumEntry)) {
            return false;
        }
        DailyScrumEntry other = (DailyScrumEntry) object;
        if ((this.dailyScrumEntryPK == null && other.dailyScrumEntryPK != null) || (this.dailyScrumEntryPK != null && !this.dailyScrumEntryPK.equals(other.dailyScrumEntryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.DailyScrumEntry[ dailyScrumEntryPK=" + dailyScrumEntryPK + " ]";
    }
    
}
