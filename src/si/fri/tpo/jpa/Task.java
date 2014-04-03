package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TASK database table.
 * 
 */
@Entity
@NamedQuery(name="Task.findAll", query="SELECT t FROM Task t")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskPK id;

	private String description;

	@Column(name="estimated_time")
	private int estimatedTime;

	private String status;

	@Column(name="time_remaining")
	private int timeRemaining;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to UserStory
	@ManyToOne
	@JoinColumn(name="USER_STORY_story_id")
	private UserStory userStory;

	//bi-directional many-to-one association to Workload
	@OneToMany(mappedBy="task")
	private List<Workload> workloads;

	public Task() {
	}

	public TaskPK getId() {
		return this.id;
	}

	public void setId(TaskPK id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTimeRemaining() {
		return this.timeRemaining;
	}

	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserStory getUserStory() {
		return this.userStory;
	}

	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
	}

	public List<Workload> getWorkloads() {
		return this.workloads;
	}

	public void setWorkloads(List<Workload> workloads) {
		this.workloads = workloads;
	}

	public Workload addWorkload(Workload workload) {
		getWorkloads().add(workload);
		workload.setTask(this);

		return workload;
	}

	public Workload removeWorkload(Workload workload) {
		getWorkloads().remove(workload);
		workload.setTask(null);

		return workload;
	}

}