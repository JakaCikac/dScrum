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
@Table(name = "workblock")
@NamedQueries({
    @NamedQuery(name = "Workblock.findAll", query = "SELECT w FROM Workblock w"),
    @NamedQuery(name = "Workblock.findByWorkblockId", query = "SELECT w FROM Workblock w WHERE w.workblockPK.workblockId = :workblockId"),
    @NamedQuery(name = "Workblock.findByTimeStart", query = "SELECT w FROM Workblock w WHERE w.timeStart = :timeStart"),
    @NamedQuery(name = "Workblock.findByTimeStop", query = "SELECT w FROM Workblock w WHERE w.timeStop = :timeStop"),
    @NamedQuery(name = "Workblock.findByWorkloadWorkloadId", query = "SELECT w FROM Workblock w WHERE w.workblockPK.workloadWorkloadId = :workloadWorkloadId"),
    @NamedQuery(name = "Workblock.findByWorkloadTaskTaskId", query = "SELECT w FROM Workblock w WHERE w.workblockPK.workloadTaskTaskId = :workloadTaskTaskId"),
    @NamedQuery(name = "Workblock.findByWorkloadTaskUserStoryStoryId", query = "SELECT w FROM Workblock w WHERE w.workblockPK.workloadTaskUserStoryStoryId = :workloadTaskUserStoryStoryId"),
    @NamedQuery(name = "Workblock.findByWorkloadUserUserId", query = "SELECT w FROM Workblock w WHERE w.workblockPK.workloadUserUserId = :workloadUserUserId")})
public class Workblock implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkblockPK workblockPK;
    @Basic(optional = false)
    @Column(name = "time_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStart;
    @Basic(optional = false)
    @Column(name = "time_stop")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStop;
    @JoinColumns({
        @JoinColumn(name = "workload_workload_id", referencedColumnName = "workload_id", insertable = false, updatable = false),
        @JoinColumn(name = "workload_task_task_id", referencedColumnName = "task_task_id", insertable = false, updatable = false),
        @JoinColumn(name = "workload_task_user_story_story_id", referencedColumnName = "task_user_story_story_id", insertable = false, updatable = false),
        @JoinColumn(name = "workload_user_user_id", referencedColumnName = "user_user_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Workload workload;

    public Workblock() {
    }

    public Workblock(WorkblockPK workblockPK) {
        this.workblockPK = workblockPK;
    }

    public Workblock(WorkblockPK workblockPK, Date timeStart, Date timeStop) {
        this.workblockPK = workblockPK;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
    }

    public Workblock(int workblockId, int workloadWorkloadId, int workloadTaskTaskId, int workloadTaskUserStoryStoryId, int workloadUserUserId) {
        this.workblockPK = new WorkblockPK(workblockId, workloadWorkloadId, workloadTaskTaskId, workloadTaskUserStoryStoryId, workloadUserUserId);
    }

    public WorkblockPK getWorkblockPK() {
        return workblockPK;
    }

    public void setWorkblockPK(WorkblockPK workblockPK) {
        this.workblockPK = workblockPK;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Date timeStop) {
        this.timeStop = timeStop;
    }

    public Workload getWorkload() {
        return workload;
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workblockPK != null ? workblockPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workblock)) {
            return false;
        }
        Workblock other = (Workblock) object;
        if ((this.workblockPK == null && other.workblockPK != null) || (this.workblockPK != null && !this.workblockPK.equals(other.workblockPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Workblock[ workblockPK=" + workblockPK + " ]";
    }
    
}
