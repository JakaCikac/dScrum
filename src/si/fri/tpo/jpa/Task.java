/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TASK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByTaskId", query = "SELECT t FROM Task t WHERE t.taskPK.taskId = :taskId"),
    @NamedQuery(name = "Task.findByDescription", query = "SELECT t FROM Task t WHERE t.description = :description"),
    @NamedQuery(name = "Task.findByTimeRemaining", query = "SELECT t FROM Task t WHERE t.timeRemaining = :timeRemaining"),
    @NamedQuery(name = "Task.findByEstimatedTime", query = "SELECT t FROM Task t WHERE t.estimatedTime = :estimatedTime"),
    @NamedQuery(name = "Task.findByStatus", query = "SELECT t FROM Task t WHERE t.status = :status"),
    @NamedQuery(name = "Task.findByUSERSTORYstoryid", query = "SELECT t FROM Task t WHERE t.taskPK.uSERSTORYstoryid = :uSERSTORYstoryid")})
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TaskPK taskPK;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "time_remaining")
    private int timeRemaining;
    @Basic(optional = false)
    @Column(name = "estimated_time")
    private int estimatedTime;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "USER_STORY_story_id", referencedColumnName = "story_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserStory userStory;
    @JoinColumn(name = "USER_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User uSERuserid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Collection<Workload> workloadCollection;

    public Task() {
    }

    public Task(TaskPK taskPK) {
        this.taskPK = taskPK;
    }

    public Task(TaskPK taskPK, String description, int timeRemaining, int estimatedTime, String status) {
        this.taskPK = taskPK;
        this.description = description;
        this.timeRemaining = timeRemaining;
        this.estimatedTime = estimatedTime;
        this.status = status;
    }

    public Task(int taskId, int uSERSTORYstoryid) {
        this.taskPK = new TaskPK(taskId, uSERSTORYstoryid);
    }

    public TaskPK getTaskPK() {
        return taskPK;
    }

    public void setTaskPK(TaskPK taskPK) {
        this.taskPK = taskPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public User getUSERuserid() {
        return uSERuserid;
    }

    public void setUSERuserid(User uSERuserid) {
        this.uSERuserid = uSERuserid;
    }

    @XmlTransient
    public Collection<Workload> getWorkloadCollection() {
        return workloadCollection;
    }

    public void setWorkloadCollection(Collection<Workload> workloadCollection) {
        this.workloadCollection = workloadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskPK != null ? taskPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskPK == null && other.taskPK != null) || (this.taskPK != null && !this.taskPK.equals(other.taskPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.Task[ taskPK=" + taskPK + " ]";
    }
    
}
