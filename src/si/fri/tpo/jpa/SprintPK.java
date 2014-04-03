package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPRINT database table.
 * 
 */
@Embeddable
public class SprintPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="sprint_id")
	private int sprintId;

	@Column(insertable=false, updatable=false)
	private int PROJECT_project_id;

	public SprintPK() {
	}
	public int getSprintId() {
		return this.sprintId;
	}
	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}
	public int getPROJECT_project_id() {
		return this.PROJECT_project_id;
	}
	public void setPROJECT_project_id(int PROJECT_project_id) {
		this.PROJECT_project_id = PROJECT_project_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SprintPK)) {
			return false;
		}
		SprintPK castOther = (SprintPK)other;
		return 
			(this.sprintId == castOther.sprintId)
			&& (this.PROJECT_project_id == castOther.PROJECT_project_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sprintId;
		hash = hash * prime + this.PROJECT_project_id;
		
		return hash;
	}
}