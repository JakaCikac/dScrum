/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "sprint")
@NamedQueries({
    @NamedQuery(name = "Sprint.findAll", query = "SELECT s FROM Sprint s"),
    @NamedQuery(name = "Sprint.findBySprintId", query = "SELECT s FROM Sprint s WHERE s.sprintPK.sprintId = :sprintId"),
    @NamedQuery(name = "Sprint.findBySeqNumber", query = "SELECT s FROM Sprint s WHERE s.seqNumber = :seqNumber"),
    @NamedQuery(name = "Sprint.findByStartDate", query = "SELECT s FROM Sprint s WHERE s.startDate = :startDate"),
    @NamedQuery(name = "Sprint.findByEndDate", query = "SELECT s FROM Sprint s WHERE s.endDate = :endDate"),
    @NamedQuery(name = "Sprint.findByVelocity", query = "SELECT s FROM Sprint s WHERE s.velocity = :velocity"),
    @NamedQuery(name = "Sprint.findByStatus", query = "SELECT s FROM Sprint s WHERE s.status = :status"),
    @NamedQuery(name = "Sprint.findByProjectProjectId", query = "SELECT s FROM Sprint s WHERE s.sprintPK.projectProjectId = :projectProjectId")})
public class Sprint implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SprintPK sprintPK;
    @Column(name = "seq_number")
    private Integer seqNumber;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "velocity")
    private Integer velocity;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "project_project_id", referencedColumnName = "project_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @OneToMany(mappedBy = "sprint")
    private List<UserStory> userStoryList;

    public Sprint() {
    }

    public Sprint(SprintPK sprintPK) {
        this.sprintPK = sprintPK;
    }

    public Sprint(SprintPK sprintPK, Date startDate, Date endDate) {
        this.sprintPK = sprintPK;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Sprint(int sprintId, int projectProjectId) {
        this.sprintPK = new SprintPK(sprintId, projectProjectId);
    }

    public SprintPK getSprintPK() {
        return sprintPK;
    }

    public void setSprintPK(SprintPK sprintPK) {
        this.sprintPK = sprintPK;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<UserStory> getUserStoryList() {
        return userStoryList;
    }

    public void setUserStoryList(List<UserStory> userStoryList) {
        this.userStoryList = userStoryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprintPK != null ? sprintPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) object;
        if ((this.sprintPK == null && other.sprintPK != null) || (this.sprintPK != null && !this.sprintPK.equals(other.sprintPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Sprint[ sprintPK=" + sprintPK + " ]";
    }
    
}
