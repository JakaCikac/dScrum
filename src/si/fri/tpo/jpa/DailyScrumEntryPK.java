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
public class DailyScrumEntryPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "daily_scrum_id")
    private int dailyScrumId;
    @Basic(optional = false)
    @Column(name = "user_user_id")
    private int userUserId;
    @Basic(optional = false)
    @Column(name = "project_project_id")
    private int projectProjectId;

    public DailyScrumEntryPK() {
    }

    public DailyScrumEntryPK(int dailyScrumId, int userUserId, int projectProjectId) {
        this.dailyScrumId = dailyScrumId;
        this.userUserId = userUserId;
        this.projectProjectId = projectProjectId;
    }

    public int getDailyScrumId() {
        return dailyScrumId;
    }

    public void setDailyScrumId(int dailyScrumId) {
        this.dailyScrumId = dailyScrumId;
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
        hash += (int) dailyScrumId;
        hash += (int) userUserId;
        hash += (int) projectProjectId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyScrumEntryPK)) {
            return false;
        }
        DailyScrumEntryPK other = (DailyScrumEntryPK) object;
        if (this.dailyScrumId != other.dailyScrumId) {
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
        return "si.fri.tpo.jpa.DailyScrumEntryPK[ dailyScrumId=" + dailyScrumId + ", userUserId=" + userUserId + ", projectProjectId=" + projectProjectId + " ]";
    }
    
}
