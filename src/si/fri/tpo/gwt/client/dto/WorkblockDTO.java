package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.gwt.server.jpa.WorkblockPK;
import si.fri.tpo.gwt.server.jpa.Workload;

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

    public WorkblockPK getWorkblockPK() {
        return get("workblockPK");
    }

    public void setWorkblockPK(WorkblockPK workblockPK) {
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

    public Workload getWorkload() {
        return get("workload");
    }

    public void setWorkload(Workload workload) {
        set("workload", workload);
    }
}
