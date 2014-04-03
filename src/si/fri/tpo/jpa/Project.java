package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="project_id")
	private int projectId;

	private String description;

	private String name;

	private String status;

	//bi-directional many-to-one association to DailyScrumEntry
	@OneToMany(mappedBy="project")
	private List<DailyScrumEntry> dailyScrumEntries;

	//bi-directional many-to-one association to Discussion
	@OneToMany(mappedBy="project")
	private List<Discussion> discussions;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	//bi-directional many-to-one association to Sprint
	@OneToMany(mappedBy="project")
	private List<Sprint> sprints;

	//bi-directional many-to-one association to UserStory
	@OneToMany(mappedBy="project")
	private List<UserStory> userStories;

	public Project() {
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DailyScrumEntry> getDailyScrumEntries() {
		return this.dailyScrumEntries;
	}

	public void setDailyScrumEntries(List<DailyScrumEntry> dailyScrumEntries) {
		this.dailyScrumEntries = dailyScrumEntries;
	}

	public DailyScrumEntry addDailyScrumEntry(DailyScrumEntry dailyScrumEntry) {
		getDailyScrumEntries().add(dailyScrumEntry);
		dailyScrumEntry.setProject(this);

		return dailyScrumEntry;
	}

	public DailyScrumEntry removeDailyScrumEntry(DailyScrumEntry dailyScrumEntry) {
		getDailyScrumEntries().remove(dailyScrumEntry);
		dailyScrumEntry.setProject(null);

		return dailyScrumEntry;
	}

	public List<Discussion> getDiscussions() {
		return this.discussions;
	}

	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}

	public Discussion addDiscussion(Discussion discussion) {
		getDiscussions().add(discussion);
		discussion.setProject(this);

		return discussion;
	}

	public Discussion removeDiscussion(Discussion discussion) {
		getDiscussions().remove(discussion);
		discussion.setProject(null);

		return discussion;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Sprint> getSprints() {
		return this.sprints;
	}

	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}

	public Sprint addSprint(Sprint sprint) {
		getSprints().add(sprint);
		sprint.setProject(this);

		return sprint;
	}

	public Sprint removeSprint(Sprint sprint) {
		getSprints().remove(sprint);
		sprint.setProject(null);

		return sprint;
	}

	public List<UserStory> getUserStories() {
		return this.userStories;
	}

	public void setUserStories(List<UserStory> userStories) {
		this.userStories = userStories;
	}

	public UserStory addUserStory(UserStory userStory) {
		getUserStories().add(userStory);
		userStory.setProject(this);

		return userStory;
	}

	public UserStory removeUserStory(UserStory userStory) {
		getUserStories().remove(userStory);
		userStory.setProject(null);

		return userStory;
	}

}