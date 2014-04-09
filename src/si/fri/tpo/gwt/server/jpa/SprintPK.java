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
public class SprintPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "sprint_id")
    private int sprintId;
    @Basic(optional = false)
    @Column(name = "project_project_id")
    private int projectProjectId;

    public SprintPK() {
    }

    public SprintPK(int sprintId, int projectProjectId) {
        this.sprintId = sprintId;
        this.projectProjectId = projectProjectId;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
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
        hash += (int) sprintId;
        hash += (int) projectProjectId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprintPK)) {
            return false;
        }
        SprintPK other = (SprintPK) object;
        if (this.sprintId != other.sprintId) {
            return false;
        }
        if (this.projectProjectId != other.projectProjectId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.SprintPK[ sprintId=" + sprintId + ", projectProjectId=" + projectProjectId + " ]";
    }
    
}
