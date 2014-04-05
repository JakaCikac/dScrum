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
public class WorkloadPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "workload_id")
    private int workloadId;
    @Basic(optional = false)
    @Column(name = "TASK_task_id")
    private int tASKtaskid;
    @Basic(optional = false)
    @Column(name = "TASK_USER_STORY_story_id")
    private int tASKUSERSTORYstoryid;
    @Basic(optional = false)
    @Column(name = "USER_user_id")
    private int uSERuserid;

    public WorkloadPK() {
    }

    public WorkloadPK(int workloadId, int tASKtaskid, int tASKUSERSTORYstoryid, int uSERuserid) {
        this.workloadId = workloadId;
        this.tASKtaskid = tASKtaskid;
        this.tASKUSERSTORYstoryid = tASKUSERSTORYstoryid;
        this.uSERuserid = uSERuserid;
    }

    public int getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(int workloadId) {
        this.workloadId = workloadId;
    }

    public int getTASKtaskid() {
        return tASKtaskid;
    }

    public void setTASKtaskid(int tASKtaskid) {
        this.tASKtaskid = tASKtaskid;
    }

    public int getTASKUSERSTORYstoryid() {
        return tASKUSERSTORYstoryid;
    }

    public void setTASKUSERSTORYstoryid(int tASKUSERSTORYstoryid) {
        this.tASKUSERSTORYstoryid = tASKUSERSTORYstoryid;
    }

    public int getUSERuserid() {
        return uSERuserid;
    }

    public void setUSERuserid(int uSERuserid) {
        this.uSERuserid = uSERuserid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) workloadId;
        hash += (int) tASKtaskid;
        hash += (int) tASKUSERSTORYstoryid;
        hash += (int) uSERuserid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkloadPK)) {
            return false;
        }
        WorkloadPK other = (WorkloadPK) object;
        if (this.workloadId != other.workloadId) {
            return false;
        }
        if (this.tASKtaskid != other.tASKtaskid) {
            return false;
        }
        if (this.tASKUSERSTORYstoryid != other.tASKUSERSTORYstoryid) {
            return false;
        }
        if (this.uSERuserid != other.uSERuserid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.WorkloadPK[ workloadId=" + workloadId + ", tASKtaskid=" + tASKtaskid + ", tASKUSERSTORYstoryid=" + tASKUSERSTORYstoryid + ", uSERuserid=" + uSERuserid + " ]";
    }
    
}
