package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORKLOAD database table.
 * 
 */
@Embeddable
public class WorkloadPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="workload_id")
	private int workloadId;

	@Column(insertable=false, updatable=false)
	private int TASK_task_id;

	@Column(insertable=false, updatable=false)
	private int TASK_USER_STORY_story_id;

	@Column(insertable=false, updatable=false)
	private int USER_user_id;

	public WorkloadPK() {
	}
	public int getWorkloadId() {
		return this.workloadId;
	}
	public void setWorkloadId(int workloadId) {
		this.workloadId = workloadId;
	}
	public int getTASK_task_id() {
		return this.TASK_task_id;
	}
	public void setTASK_task_id(int TASK_task_id) {
		this.TASK_task_id = TASK_task_id;
	}
	public int getTASK_USER_STORY_story_id() {
		return this.TASK_USER_STORY_story_id;
	}
	public void setTASK_USER_STORY_story_id(int TASK_USER_STORY_story_id) {
		this.TASK_USER_STORY_story_id = TASK_USER_STORY_story_id;
	}
	public int getUSER_user_id() {
		return this.USER_user_id;
	}
	public void setUSER_user_id(int USER_user_id) {
		this.USER_user_id = USER_user_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkloadPK)) {
			return false;
		}
		WorkloadPK castOther = (WorkloadPK)other;
		return 
			(this.workloadId == castOther.workloadId)
			&& (this.TASK_task_id == castOther.TASK_task_id)
			&& (this.TASK_USER_STORY_story_id == castOther.TASK_USER_STORY_story_id)
			&& (this.USER_user_id == castOther.USER_user_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workloadId;
		hash = hash * prime + this.TASK_task_id;
		hash = hash * prime + this.TASK_USER_STORY_story_id;
		hash = hash * prime + this.USER_user_id;
		
		return hash;
	}
}