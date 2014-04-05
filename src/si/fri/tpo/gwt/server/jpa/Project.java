/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "project")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId"),
    @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name"),
    @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
    @NamedQuery(name = "Project.findByStatus", query = "SELECT p FROM Project p WHERE p.status = :status")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "team_team_id", referencedColumnName = "team_id")
    @ManyToOne
    private Team teamTeamId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Discussion> discussionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<DailyScrumEntry> dailyScrumEntryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Sprint> sprintList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectProjectId")
    private List<UserStory> userStoryList;

    public Project() {
    }

    public Project(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Team getTeamTeamId() {
        return teamTeamId;
    }

    public void setTeamTeamId(Team teamTeamId) {
        this.teamTeamId = teamTeamId;
    }

    public List<Discussion> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(List<Discussion> discussionList) {
        this.discussionList = discussionList;
    }

    public List<DailyScrumEntry> getDailyScrumEntryList() {
        return dailyScrumEntryList;
    }

    public void setDailyScrumEntryList(List<DailyScrumEntry> dailyScrumEntryList) {
        this.dailyScrumEntryList = dailyScrumEntryList;
    }

    public List<Sprint> getSprintList() {
        return sprintList;
    }

    public void setSprintList(List<Sprint> sprintList) {
        this.sprintList = sprintList;
    }

    public List<UserStory> getUserStoryList() {
        return userStoryList;
    }

    public void setUserStoryList(List<UserStory> userStoryList) {
        this.userStoryList = userStoryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.gwt.server.jpa.Project[ projectId=" + projectId + " ]";
    }
    
}
