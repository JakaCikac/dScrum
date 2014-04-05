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
    @Column(name = "user_story_story_id")
    private int userStoryStoryId;

    public TaskPK() {
    }

    public TaskPK(int taskId, int userStoryStoryId) {
        this.taskId = taskId;
        this.userStoryStoryId = userStoryStoryId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserStoryStoryId() {
        return userStoryStoryId;
    }

    public void setUserStoryStoryId(int userStoryStoryId) {
        this.userStoryStoryId = userStoryStoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) taskId;
        hash += (int) userStoryStoryId;
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
        if (this.userStoryStoryId != other.userStoryStoryId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.TaskPK[ taskId=" + taskId + ", userStoryStoryId=" + userStoryStoryId + " ]";
    }
    
}
