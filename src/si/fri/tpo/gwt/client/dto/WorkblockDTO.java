package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.WorkblockPK;
import si.fri.tpo.jpa.Workload;

import java.util.Date;

/**
 * Created by t13db on 4.4.2014.
 */
public class WorkblockDTO extends BaseModelData {

    //private WorkblockPK id;
    //private Date timeStart;
    //private Date timeStop;
    //private Workload workload;

    public WorkblockDTO() {
    }

    public WorkblockPK getId() {
        return get("id");
    }

    public void setId(WorkblockPK id) {
        set("id", id);
    }

    public Date getTimeStart() {
        return get("timeStart");
    }

    public void setTimeStart(Date timeStart) {
        set("timeStart", timeStart);
    }

    public Date getTimeStop() {
        return get("timeStop");
    }

    public void setTimeStop(Date timeStop) {
        set("timeStop", timeStop);
    }

    public Workload getWorkload() {
        return get("workload");
    }

    public void setWorkload(Workload workload) {
        set("workload", workload);
    }
}
