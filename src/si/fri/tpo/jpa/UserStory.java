/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

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
import javax.persistence.JoinColumns;
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
@Table(name = "user_story")
@NamedQueries({
    @NamedQuery(name = "UserStory.findAll", query = "SELECT u FROM UserStory u"),
    @NamedQuery(name = "UserStory.findByStoryId", query = "SELECT u FROM UserStory u WHERE u.storyId = :storyId"),
    @NamedQuery(name = "UserStory.findByName", query = "SELECT u FROM UserStory u WHERE u.name = :name"),
    @NamedQuery(name = "UserStory.findByContent", query = "SELECT u FROM UserStory u WHERE u.content = :content"),
    @NamedQuery(name = "UserStory.findByBusinessValue", query = "SELECT u FROM UserStory u WHERE u.businessValue = :businessValue")})
public class UserStory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "story_id")
    private Integer storyId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "content")
    private String content;
    @Column(name = "business_value")
    private Integer businessValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userStory")
    private List<Task> taskList;
    @OneToMany(mappedBy = "userStoryStoryId")
    private List<AcceptanceTest> acceptanceTestList;
    @JoinColumns({
        @JoinColumn(name = "sprint_sprint_id", referencedColumnName = "sprint_id"),
        @JoinColumn(name = "sprint_project_project_id", referencedColumnName = "project_project_id")})
    @ManyToOne
    private Sprint sprint;
    @JoinColumn(name = "priority_priority_id", referencedColumnName = "priority_id")
    @ManyToOne(optional = false)
    private Priority priorityPriorityId;
    @JoinColumn(name = "project_project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Project projectProjectId;

    public UserStory() {
    }

    public UserStory(Integer storyId) {
        this.storyId = storyId;
    }

    public UserStory(Integer storyId, String name, String content) {
        this.storyId = storyId;
        this.name = name;
        this.content = content;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(Integer businessValue) {
        this.businessValue = businessValue;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<AcceptanceTest> getAcceptanceTestList() {
        return acceptanceTestList;
    }

    public void setAcceptanceTestList(List<AcceptanceTest> acceptanceTestList) {
        this.acceptanceTestList = acceptanceTestList;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Priority getPriorityPriorityId() {
        return priorityPriorityId;
    }

    public void setPriorityPriorityId(Priority priorityPriorityId) {
        this.priorityPriorityId = priorityPriorityId;
    }

    public Project getProjectProjectId() {
        return projectProjectId;
    }

    public void setProjectProjectId(Project projectProjectId) {
        this.projectProjectId = projectProjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storyId != null ? storyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserStory)) {
            return false;
        }
        UserStory other = (UserStory) object;
        if ((this.storyId == null && other.storyId != null) || (this.storyId != null && !this.storyId.equals(other.storyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.fri.tpo.jpa.UserStory[ storyId=" + storyId + " ]";
    }
    
}
