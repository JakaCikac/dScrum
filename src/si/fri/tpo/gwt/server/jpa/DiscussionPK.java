/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class DiscussionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "discussion_id")
    private int discussionId;
    @Basic(optional = false)
    @Column(name = "user_user_id")
    private int userUserId;
    @Basic(optional = false)
    @Column(name = "project_project_id")
    private int projectProjectId;

    public DiscussionPK() {
    }

    public DiscussionPK(int discussionId, int userUserId, int projectProjectId) {
        this.discussionId = discussionId;
        this.userUserId = userUserId;
        this.projectProjectId = projectProjectId;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public int getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }

    public int getProjectProjectId() {
        return projectProjectId;
    }

    public void setProjectProjectId(int projectProjectId) {
        this.projectProjectId = projectProjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) discussionId;
        hash += (int) userUserId;
        hash += (int) projectProjectId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiscussionPK)) {
            return false;
        }
        DiscussionPK other = (DiscussionPK) object;
        if (this.discussionId != other.discussionId) {
            return false;
        }
        if (this.userUserId != other.userUserId) {
            return false;
        }
        if (this.projectProjectId != other.projectProjectId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.DiscussionPK[ discussionId=" + discussionId + ", userUserId=" + userUserId + ", projectProjectId=" + projectProjectId + " ]";
    }
    
}
