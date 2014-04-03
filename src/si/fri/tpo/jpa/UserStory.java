package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USER_STORY database table.
 * 
 */
@Entity
@Table(name="USER_STORY")
@NamedQuery(name="UserStory.findAll", query="SELECT u FROM UserStory u")
public class UserStory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="story_id")
	private int storyId;

	@Column(name="business_value")
	private int businessValue;

	private String content;

	private String name;

	//bi-directional many-to-one association to AcceptanceTest
	@OneToMany(mappedBy="userStory")
	private List<AcceptanceTest> acceptanceTests;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="userStory")
	private List<Task> tasks;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	//bi-directional many-to-one association to Priority
	@ManyToOne
	private Priority priority;

	//bi-directional many-to-one association to Sprint
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="SPRINT_PROJECT_project_id", referencedColumnName="PROJECT_project_id"),
		@JoinColumn(name="SPRINT_sprint_id", referencedColumnName="sprint_id")
		})
	private Sprint sprint;

	public UserStory() {
	}

	public int getStoryId() {
		return this.storyId;
	}

	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}

	public int getBusinessValue() {
		return this.businessValue;
	}

	public void setBusinessValue(int businessValue) {
		this.businessValue = businessValue;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AcceptanceTest> getAcceptanceTests() {
		return this.acceptanceTests;
	}

	public void setAcceptanceTests(List<AcceptanceTest> acceptanceTests) {
		this.acceptanceTests = acceptanceTests;
	}

	public AcceptanceTest addAcceptanceTest(AcceptanceTest acceptanceTest) {
		getAcceptanceTests().add(acceptanceTest);
		acceptanceTest.setUserStory(this);

		return acceptanceTest;
	}

	public AcceptanceTest removeAcceptanceTest(AcceptanceTest acceptanceTest) {
		getAcceptanceTests().remove(acceptanceTest);
		acceptanceTest.setUserStory(null);

		return acceptanceTest;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setUserStory(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setUserStory(null);

		return task;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Sprint getSprint() {
		return this.sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

}