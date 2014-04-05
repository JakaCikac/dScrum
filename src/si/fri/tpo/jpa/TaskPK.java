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
public class TaskPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "task_id")
    private int taskId;
    @Basic(optional = false)
    @Column(name = "USER_STORY_story_id")
    private int uSERSTORYstoryid;

    public TaskPK() {
    }

    public TaskPK(int taskId, int uSERSTORYstoryid) {
        this.taskId = taskId;
        this.uSERSTORYstoryid = uSERSTORYstoryid;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUSERSTORYstoryid() {
        return uSERSTORYstoryid;
    }

    public void setUSERSTORYstoryid(int uSERSTORYstoryid) {
        this.uSERSTORYstoryid = uSERSTORYstoryid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) taskId;
        hash += (int) uSERSTORYstoryid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaskPK)) {
            return false;
        }
        TaskPK other = (TaskPK) object;
        if (this.taskId != other.taskId) {
            return false;
        }
        if (this.uSERSTORYstoryid != other.uSERSTORYstoryid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.TaskPK[ taskId=" + taskId + ", uSERSTORYstoryid=" + uSERSTORYstoryid + " ]";
    }
    
}
