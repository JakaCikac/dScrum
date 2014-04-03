package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORKBLOCK database table.
 * 
 */
@Embeddable
public class WorkblockPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="workblock_id")
	private int workblockId;

	@Column(insertable=false, updatable=false)
	private int WORKLOAD_workload_id;

	@Column(insertable=false, updatable=false)
	private int WORKLOAD_TASK_task_id;

	@Column(insertable=false, updatable=false)
	private int WORKLOAD_TASK_USER_STORY_story_id;

	@Column(insertable=false, updatable=false)
	private int WORKLOAD_USER_user_id;

	public WorkblockPK() {
	}
	public int getWorkblockId() {
		return this.workblockId;
	}
	public void setWorkblockId(int workblockId) {
		this.workblockId = workblockId;
	}
	public int getWORKLOAD_workload_id() {
		return this.WORKLOAD_workload_id;
	}
	public void setWORKLOAD_workload_id(int WORKLOAD_workload_id) {
		this.WORKLOAD_workload_id = WORKLOAD_workload_id;
	}
	public int getWORKLOAD_TASK_task_id() {
		return this.WORKLOAD_TASK_task_id;
	}
	public void setWORKLOAD_TASK_task_id(int WORKLOAD_TASK_task_id) {
		this.WORKLOAD_TASK_task_id = WORKLOAD_TASK_task_id;
	}
	public int getWORKLOAD_TASK_USER_STORY_story_id() {
		return this.WORKLOAD_TASK_USER_STORY_story_id;
	}
	public void setWORKLOAD_TASK_USER_STORY_story_id(int WORKLOAD_TASK_USER_STORY_story_id) {
		this.WORKLOAD_TASK_USER_STORY_story_id = WORKLOAD_TASK_USER_STORY_story_id;
	}
	public int getWORKLOAD_USER_user_id() {
		return this.WORKLOAD_USER_user_id;
	}
	public void setWORKLOAD_USER_user_id(int WORKLOAD_USER_user_id) {
		this.WORKLOAD_USER_user_id = WORKLOAD_USER_user_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkblockPK)) {
			return false;
		}
		WorkblockPK castOther = (WorkblockPK)other;
		return 
			(this.workblockId == castOther.workblockId)
			&& (this.WORKLOAD_workload_id == castOther.WORKLOAD_workload_id)
			&& (this.WORKLOAD_TASK_task_id == castOther.WORKLOAD_TASK_task_id)
			&& (this.WORKLOAD_TASK_USER_STORY_story_id == castOther.WORKLOAD_TASK_USER_STORY_story_id)
			&& (this.WORKLOAD_USER_user_id == castOther.WORKLOAD_USER_user_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workblockId;
		hash = hash * prime + this.WORKLOAD_workload_id;
		hash = hash * prime + this.WORKLOAD_TASK_task_id;
		hash = hash * prime + this.WORKLOAD_TASK_USER_STORY_story_id;
		hash = hash * prime + this.WORKLOAD_USER_user_id;
		
		return hash;
	}
}