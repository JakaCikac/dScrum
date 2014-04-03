package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the WORKBLOCK database table.
 * 
 */
@Entity
@NamedQuery(name="Workblock.findAll", query="SELECT w FROM Workblock w")
public class Workblock implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WorkblockPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_start")
	private Date timeStart;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_stop")
	private Date timeStop;

	//bi-directional many-to-one association to Workload
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="WORKLOAD_TASK_task_id", referencedColumnName="TASK_task_id"),
		@JoinColumn(name="WORKLOAD_TASK_USER_STORY_story_id", referencedColumnName="TASK_USER_STORY_story_id"),
		@JoinColumn(name="WORKLOAD_USER_user_id", referencedColumnName="USER_user_id"),
		@JoinColumn(name="WORKLOAD_workload_id", referencedColumnName="workload_id")
		})
	private Workload workload;

	public Workblock() {
	}

	public WorkblockPK getId() {
		return this.id;
	}

	public void setId(WorkblockPK id) {
		this.id = id;
	}

	public Date getTimeStart() {
		return this.timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeStop() {
		return this.timeStop;
	}

	public void setTimeStop(Date timeStop) {
		this.timeStop = timeStop;
	}

	public Workload getWorkload() {
		return this.workload;
	}

	public void setWorkload(Workload workload) {
		this.workload = workload;
	}

}