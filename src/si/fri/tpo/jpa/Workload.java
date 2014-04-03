package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the WORKLOAD database table.
 * 
 */
@Entity
@NamedQuery(name="Workload.findAll", query="SELECT w FROM Workload w")
public class Workload implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WorkloadPK id;

	@Lob
	@Column(name="time_spent")
	private String timeSpent;

	//bi-directional many-to-one association to Workblock
	@OneToMany(mappedBy="workload")
	private List<Workblock> workblocks;

	//bi-directional many-to-one association to Task
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="TASK_task_id", referencedColumnName="task_id"),
		@JoinColumn(name="TASK_USER_STORY_story_id", referencedColumnName="USER_STORY_story_id")
		})
	private Task task;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Workload() {
	}

	public WorkloadPK getId() {
		return this.id;
	}

	public void setId(WorkloadPK id) {
		this.id = id;
	}

	public String getTimeSpent() {
		return this.timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

	public List<Workblock> getWorkblocks() {
		return this.workblocks;
	}

	public void setWorkblocks(List<Workblock> workblocks) {
		this.workblocks = workblocks;
	}

	public Workblock addWorkblock(Workblock workblock) {
		getWorkblocks().add(workblock);
		workblock.setWorkload(this);

		return workblock;
	}

	public Workblock removeWorkblock(Workblock workblock) {
		getWorkblocks().remove(workblock);
		workblock.setWorkload(null);

		return workblock;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}