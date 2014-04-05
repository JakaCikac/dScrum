/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "WORKBLOCK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workblock.findAll", query = "SELECT w FROM Workblock w"),
    @NamedQuery(name = "Workblock.findByWorkblockId", query = "SELECT w FROM Workblock w WHERE w.workblockPK.workblockId = :workblockId"),
    @NamedQuery(name = "Workblock.findByTimeStart", query = "SELECT w FROM Workblock w WHERE w.timeStart = :timeStart"),
    @NamedQuery(name = "Workblock.findByTimeStop", query = "SELECT w FROM Workblock w WHERE w.timeStop = :timeStop"),
    @NamedQuery(name = "Workblock.findByWORKLOADworkloadid", query = "SELECT w FROM Workblock w WHERE w.workblockPK.wORKLOADworkloadid = :wORKLOADworkloadid"),
    @NamedQuery(name = "Workblock.findByWORKLOADTASKtaskid", query = "SELECT w FROM Workblock w WHERE w.workblockPK.wORKLOADTASKtaskid = :wORKLOADTASKtaskid"),
    @NamedQuery(name = "Workblock.findByWORKLOADTASKUSERSTORYstoryid", query = "SELECT w FROM Workblock w WHERE w.workblockPK.wORKLOADTASKUSERSTORYstoryid = :wORKLOADTASKUSERSTORYstoryid"),
    @NamedQuery(name = "Workblock.findByWORKLOADUSERuserid", query = "SELECT w FROM Workblock w WHERE w.workblockPK.wORKLOADUSERuserid = :wORKLOADUSERuserid")})
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
        @JoinColumn(name = "WORKLOAD_workload_id", referencedColumnName = "workload_id", insertable = false, updatable = false),
        @JoinColumn(name = "WORKLOAD_TASK_task_id", referencedColumnName = "TASK_task_id", insertable = false, updatable = false),
        @JoinColumn(name = "WORKLOAD_TASK_USER_STORY_story_id", referencedColumnName = "TASK_USER_STORY_story_id", insertable = false, updatable = false),
        @JoinColumn(name = "WORKLOAD_USER_user_id", referencedColumnName = "USER_user_id", insertable = false, updatable = false)})
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

    public Workblock(int workblockId, int wORKLOADworkloadid, int wORKLOADTASKtaskid, int wORKLOADTASKUSERSTORYstoryid, int wORKLOADUSERuserid) {
        this.workblockPK = new WorkblockPK(workblockId, wORKLOADworkloadid, wORKLOADTASKtaskid, wORKLOADTASKUSERSTORYstoryid, wORKLOADUSERuserid);
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
        return "si.fri.tpo.jpa.Workblock[ workblockPK=" + workblockPK + " ]";
    }
    
}
