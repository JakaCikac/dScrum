package si.fri.tpo.gwt.server.impl.workload;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.SprintPKDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 22/05/14.
 */
public class WorkloadImpl {

    public static Pair<Boolean,String> updateWorkload(WorkloadDTO workloadDTO) {
        try {
            WorkloadPK workloadPK = new WorkloadPK();
            workloadPK.setWorkloadId(workloadDTO.getWorkloadPK().getWorkloadId());
            workloadPK.setTaskTaskId(workloadDTO.getWorkloadPK().getTaskTaskId());
            workloadPK.setTaskUserStoryStoryId(workloadDTO.getWorkloadPK().getTaskUserStoryStoryId());
            workloadPK.setUserUserId(workloadDTO.getWorkloadPK().getUserUserId());
            Workload workload = ProxyManager.getWorkloadProxy().findWorkload(workloadPK);

            workload.setTimeRemaining(workloadDTO.getTimeRemaining());
            workload.setTimeSpent(workloadDTO.getTimeSpent());
            try {
                if (workload == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getWorkloadProxy().edit(workload);

            } catch (Exception e) {
                System.err.println("Error while editing workload with message: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Workload should be updated.");

    }

}
