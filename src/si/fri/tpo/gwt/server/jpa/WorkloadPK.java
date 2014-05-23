/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class WorkloadPK implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workload_id")
    private int workloadId;
    @Basic(optional = false)
    @Column(name = "task_task_id")
    private int taskTaskId;
    @Basic(optional = false)
    @Column(name = "task_user_story_story_id")
    private int taskUserStoryStoryId;
    @Basic(optional = false)
    @Column(name = "user_user_id")
    private int userUserId;

    public WorkloadPK() {
    }

    public WorkloadPK(int workloadId, int taskTaskId, int taskUserStoryStoryId, int userUserId) {
        this.workloadId = workloadId;
        this.taskTaskId = taskTaskId;
        this.taskUserStoryStoryId = taskUserStoryStoryId;
        this.userUserId = userUserId;
    }

    public int getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(int workloadId) {
        this.workloadId = workloadId;
    }

    public int getTaskTaskId() {
        return taskTaskId;
    }

    public void setTaskTaskId(int taskTaskId) {
        this.taskTaskId = taskTaskId;
    }

    public int getTaskUserStoryStoryId() {
        return taskUserStoryStoryId;
    }

    public void setTaskUserStoryStoryId(int taskUserStoryStoryId) {
        this.taskUserStoryStoryId = taskUserStoryStoryId;
    }

    public int getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) workloadId;
        hash += (int) taskTaskId;
        hash += (int) taskUserStoryStoryId;
        hash += (int) userUserId;
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
        if (this.taskTaskId != other.taskTaskId) {
            return false;
        }
        if (this.taskUserStoryStoryId != other.taskUserStoryStoryId) {
            return false;
        }
        if (this.userUserId != other.userUserId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.WorkloadPK[ workloadId=" + workloadId + ", taskTaskId=" + taskTaskId + ", taskUserStoryStoryId=" + taskUserStoryStoryId + ", userUserId=" + userUserId + " ]";
    }
    
}
