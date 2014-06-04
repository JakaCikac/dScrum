/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "workload")
@NamedQueries({
    @NamedQuery(name = "Workload.findAll", query = "SELECT w FROM Workload w"),
    @NamedQuery(name = "Workload.findByWorkloadId", query = "SELECT w FROM Workload w WHERE w.workloadPK.workloadId = :workloadId"),
    @NamedQuery(name = "Workload.findByTaskTaskId", query = "SELECT w FROM Workload w WHERE w.workloadPK.taskTaskId = :taskTaskId"),
    @NamedQuery(name = "Workload.findByTaskUserStoryStoryId", query = "SELECT w FROM Workload w WHERE w.workloadPK.taskUserStoryStoryId = :taskUserStoryStoryId"),
    @NamedQuery(name = "Workload.findByUserUserId", query = "SELECT w FROM Workload w WHERE w.workloadPK.userUserId = :userUserId")})
public class Workload implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkloadPK workloadPK;
    @Lob
    @Column(name = "time_spent")
    private String timeSpent;
    @Lob
    @Column(name = "time_remaining")
    private String timeRemaining;
    @Column(name = "day")
    @Temporal(TemporalType.TIMESTAMP)
    private Date day;
    @Column(name = "startTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "stopTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;
    @Basic(optional = false)
    @Column(name = "started")
    private boolean started;
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumns({
        @JoinColumn(name = "task_task_id", referencedColumnName = "task_id", insertable = false, updatable = false),
        @JoinColumn(name = "task_user_story_story_id", referencedColumnName = "user_story_story_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Task task;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workload")
    private List<Workblock> workblockList;

    public Workload() {
    }

    public Workload(WorkloadPK workloadPK) {
        this.workloadPK = workloadPK;
    }

    public Workload(int workloadId, int taskTaskId, int taskUserStoryStoryId, int userUserId) {
        this.workloadPK = new WorkloadPK(workloadId, taskTaskId, taskUserStoryStoryId, userUserId);
    }

    public WorkloadPK getWorkloadPK() {
        return workloadPK;
    }

    public void setWorkloadPK(WorkloadPK workloadPK) {
        this.workloadPK = workloadPK;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Workblock> getWorkblockList() {
        return workblockList;
    }

    public void setWorkblockList(List<Workblock> workblockList) {
        this.workblockList = workblockList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workloadPK != null ? workloadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workload)) {
            return false;
        }
        Workload other = (Workload) object;
        if ((this.workloadPK == null && other.workloadPK != null) || (this.workloadPK != null && !this.workloadPK.equals(other.workloadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Workload[ workloadPK=" + workloadPK + " ]";
    }
    
}
