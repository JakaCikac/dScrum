package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TASK database table.
 * 
 */
@Embeddable
public class TaskPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="task_id")
	private int taskId;

	@Column(insertable=false, updatable=false)
	private int USER_STORY_story_id;

	public TaskPK() {
	}
	public int getTaskId() {
		return this.taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getUSER_STORY_story_id() {
		return this.USER_STORY_story_id;
	}
	public void setUSER_STORY_story_id(int USER_STORY_story_id) {
		this.USER_STORY_story_id = USER_STORY_story_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskPK)) {
			return false;
		}
		TaskPK castOther = (TaskPK)other;
		return 
			(this.taskId == castOther.taskId)
			&& (this.USER_STORY_story_id == castOther.USER_STORY_story_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.taskId;
		hash = hash * prime + this.USER_STORY_story_id;
		
		return hash;
	}
}