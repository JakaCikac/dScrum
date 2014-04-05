/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class WorkblockPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "workblock_id")
    private int workblockId;
    @Basic(optional = false)
    @Column(name = "WORKLOAD_workload_id")
    private int wORKLOADworkloadid;
    @Basic(optional = false)
    @Column(name = "WORKLOAD_TASK_task_id")
    private int wORKLOADTASKtaskid;
    @Basic(optional = false)
    @Column(name = "WORKLOAD_TASK_USER_STORY_story_id")
    private int wORKLOADTASKUSERSTORYstoryid;
    @Basic(optional = false)
    @Column(name = "WORKLOAD_USER_user_id")
    private int wORKLOADUSERuserid;

    public WorkblockPK() {
    }

    public WorkblockPK(int workblockId, int wORKLOADworkloadid, int wORKLOADTASKtaskid, int wORKLOADTASKUSERSTORYstoryid, int wORKLOADUSERuserid) {
        this.workblockId = workblockId;
        this.wORKLOADworkloadid = wORKLOADworkloadid;
        this.wORKLOADTASKtaskid = wORKLOADTASKtaskid;
        this.wORKLOADTASKUSERSTORYstoryid = wORKLOADTASKUSERSTORYstoryid;
        this.wORKLOADUSERuserid = wORKLOADUSERuserid;
    }

    public int getWorkblockId() {
        return workblockId;
    }

    public void setWorkblockId(int workblockId) {
        this.workblockId = workblockId;
    }

    public int getWORKLOADworkloadid() {
        return wORKLOADworkloadid;
    }

    public void setWORKLOADworkloadid(int wORKLOADworkloadid) {
        this.wORKLOADworkloadid = wORKLOADworkloadid;
    }

    public int getWORKLOADTASKtaskid() {
        return wORKLOADTASKtaskid;
    }

    public void setWORKLOADTASKtaskid(int wORKLOADTASKtaskid) {
        this.wORKLOADTASKtaskid = wORKLOADTASKtaskid;
    }

    public int getWORKLOADTASKUSERSTORYstoryid() {
        return wORKLOADTASKUSERSTORYstoryid;
    }

    public void setWORKLOADTASKUSERSTORYstoryid(int wORKLOADTASKUSERSTORYstoryid) {
        this.wORKLOADTASKUSERSTORYstoryid = wORKLOADTASKUSERSTORYstoryid;
    }

    public int getWORKLOADUSERuserid() {
        return wORKLOADUSERuserid;
    }

    public void setWORKLOADUSERuserid(int wORKLOADUSERuserid) {
        this.wORKLOADUSERuserid = wORKLOADUSERuserid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) workblockId;
        hash += (int) wORKLOADworkloadid;
        hash += (int) wORKLOADTASKtaskid;
        hash += (int) wORKLOADTASKUSERSTORYstoryid;
        hash += (int) wORKLOADUSERuserid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkblockPK)) {
            return false;
        }
        WorkblockPK other = (WorkblockPK) object;
        if (this.workblockId != other.workblockId) {
            return false;
        }
        if (this.wORKLOADworkloadid != other.wORKLOADworkloadid) {
            return false;
        }
        if (this.wORKLOADTASKtaskid != other.wORKLOADTASKtaskid) {
            return false;
        }
        if (this.wORKLOADTASKUSERSTORYstoryid != other.wORKLOADTASKUSERSTORYstoryid) {
            return false;
        }
        if (this.wORKLOADUSERuserid != other.wORKLOADUSERuserid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.WorkblockPK[ workblockId=" + workblockId + ", wORKLOADworkloadid=" + wORKLOADworkloadid + ", wORKLOADTASKtaskid=" + wORKLOADTASKtaskid + ", wORKLOADTASKUSERSTORYstoryid=" + wORKLOADTASKUSERSTORYstoryid + ", wORKLOADUSERuserid=" + wORKLOADUSERuserid + " ]";
    }
    
}
