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
    @Column(name = "workload_workload_id")
    private int workloadWorkloadId;
    @Basic(optional = false)
    @Column(name = "workload_task_task_id")
    private int workloadTaskTaskId;
    @Basic(optional = false)
    @Column(name = "workload_task_user_story_story_id")
    private int workloadTaskUserStoryStoryId;
    @Basic(optional = false)
    @Column(name = "workload_user_user_id")
    private int workloadUserUserId;

    public WorkblockPK() {
    }

    public WorkblockPK(int workblockId, int workloadWorkloadId, int workloadTaskTaskId, int workloadTaskUserStoryStoryId, int workloadUserUserId) {
        this.workblockId = workblockId;
        this.workloadWorkloadId = workloadWorkloadId;
        this.workloadTaskTaskId = workloadTaskTaskId;
        this.workloadTaskUserStoryStoryId = workloadTaskUserStoryStoryId;
        this.workloadUserUserId = workloadUserUserId;
    }

    public int getWorkblockId() {
        return workblockId;
    }

    public void setWorkblockId(int workblockId) {
        this.workblockId = workblockId;
    }

    public int getWorkloadWorkloadId() {
        return workloadWorkloadId;
    }

    public void setWorkloadWorkloadId(int workloadWorkloadId) {
        this.workloadWorkloadId = workloadWorkloadId;
    }

    public int getWorkloadTaskTaskId() {
        return workloadTaskTaskId;
    }

    public void setWorkloadTaskTaskId(int workloadTaskTaskId) {
        this.workloadTaskTaskId = workloadTaskTaskId;
    }

    public int getWorkloadTaskUserStoryStoryId() {
        return workloadTaskUserStoryStoryId;
    }

    public void setWorkloadTaskUserStoryStoryId(int workloadTaskUserStoryStoryId) {
        this.workloadTaskUserStoryStoryId = workloadTaskUserStoryStoryId;
    }

    public int getWorkloadUserUserId() {
        return workloadUserUserId;
    }

    public void setWorkloadUserUserId(int workloadUserUserId) {
        this.workloadUserUserId = workloadUserUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) workblockId;
        hash += (int) workloadWorkloadId;
        hash += (int) workloadTaskTaskId;
        hash += (int) workloadTaskUserStoryStoryId;
        hash += (int) workloadUserUserId;
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
        if (this.workloadWorkloadId != other.workloadWorkloadId) {
            return false;
        }
        if (this.workloadTaskTaskId != other.workloadTaskTaskId) {
            return false;
        }
        if (this.workloadTaskUserStoryStoryId != other.workloadTaskUserStoryStoryId) {
            return false;
        }
        if (this.workloadUserUserId != other.workloadUserUserId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.WorkblockPK[ workblockId=" + workblockId + ", workloadWorkloadId=" + workloadWorkloadId + ", workloadTaskTaskId=" + workloadTaskTaskId + ", workloadTaskUserStoryStoryId=" + workloadTaskUserStoryStoryId + ", workloadUserUserId=" + workloadUserUserId + " ]";
    }
    
}
