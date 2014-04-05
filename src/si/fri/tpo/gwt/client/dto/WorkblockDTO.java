package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

import java.util.Date;

/**
 * Created by t13db on 4.4.2014.
 */
public class WorkblockDTO extends BaseModelData {

    //protected WorkblockPK workblockPK;
    //private Date timeStart;
    //private Date timeStop;
    //private Workload workload;

    public WorkblockDTO() {
    }

    public WorkblockPKDTO getWorkblockPK() {
        return get("workblockPK");
    }

    public void setWorkblockPK(WorkblockPKDTO workblockPK) {
        set("workblockPK", workblockPK);
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

    public WorkloadDTO getWorkload() {
        return get("workload");
    }

    public void setWorkload(WorkloadDTO workload) {
        set("workload", workload);
    }
}
