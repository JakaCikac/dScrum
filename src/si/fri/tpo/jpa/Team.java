/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "team")
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByTeamId", query = "SELECT t FROM Team t WHERE t.teamId = :teamId"),
    @NamedQuery(name = "Team.findByScrumMasterId", query = "SELECT t FROM Team t WHERE t.scrumMasterId = :scrumMasterId"),
    @NamedQuery(name = "Team.findByProductOwnerId", query = "SELECT t FROM Team t WHERE t.productOwnerId = :productOwnerId")})
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "team_id")
    private Integer teamId;
    @Basic(optional = false)
    @Column(name = "scrum_master_id")
    private int scrumMasterId;
    @Basic(optional = false)
    @Column(name = "product_owner_id")
    private int productOwnerId;
    @JoinTable(name = "team_has_user", joinColumns = {
        @JoinColumn(name = "team_team_id", referencedColumnName = "team_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_user_id", referencedColumnName = "user_id")})
    @ManyToMany
    private List<User> userList;
    @OneToMany(mappedBy = "teamTeamId")
    private List<Project> projectList;

    public Team() {
    }

    public Team(Integer teamId) {
        this.teamId = teamId;
    }

    public Team(Integer teamId, int scrumMasterId, int productOwnerId) {
        this.teamId = teamId;
        this.scrumMasterId = scrumMasterId;
        this.productOwnerId = productOwnerId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public int getScrumMasterId() {
        return scrumMasterId;
    }

    public void setScrumMasterId(int scrumMasterId) {
        this.scrumMasterId = scrumMasterId;
    }

    public int getProductOwnerId() {
        return productOwnerId;
    }

    public void setProductOwnerId(int productOwnerId) {
        this.productOwnerId = productOwnerId;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamId != null ? teamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.Team[ teamId=" + teamId + " ]";
    }
    
}
