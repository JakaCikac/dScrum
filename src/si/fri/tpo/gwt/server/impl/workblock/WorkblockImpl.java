package si.fri.tpo.gwt.server.impl.workblock;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.WorkblockDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import si.fri.tpo.gwt.client.dto.WorkloadPKDTO;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/06/14.
 */
public class WorkblockImpl {

    public static Pair<Boolean,Integer> saveWorkblock(WorkblockDTO workblockDTO) {
        int insertedWorkblockID = -1;
        try {
            Workblock workblock = new Workblock();

            WorkblockPK workblockPK = new WorkblockPK();
            workblockPK.setWorkloadTaskTaskId(workblockDTO.getWorkblockPK().getWorkloadTaskTaskId());
            workblockPK.setWorkloadTaskUserStoryStoryId(workblockDTO.getWorkblockPK().getWorkloadTaskUserStoryStoryId());
            workblockPK.setWorkloadUserUserId(workblockDTO.getWorkblockPK().getWorkloadUserUserId());
            workblockPK.setWorkloadWorkloadId(workblockDTO.getWorkblockPK().getWorkloadWorkloadId());

            workblock.setWorkblockPK(workblockPK);

            WorkloadPKDTO workloadPKDTO = workblockDTO.getWorkload().getWorkloadPK();
            WorkloadPK workloadPK = new WorkloadPK();
            workloadPK.setUserUserId(workloadPKDTO.getUserUserId());
            workloadPK.setWorkloadId(workloadPKDTO.getWorkloadId());
            workloadPK.setTaskUserStoryStoryId(workloadPKDTO.getTaskUserStoryStoryId());
            workloadPK.setTaskTaskId(workloadPKDTO.getTaskTaskId());

            Workload workload = ProxyManager.getWorkloadProxy().findWorkload(workloadPK);
            workblock.setWorkload(workload);

            workblock.setTimeStart(workblockDTO.getTimeStart());
            workblock.setTimeStop(workblockDTO.getTimeStop());

            try {
                if (workblock == null)
                    return Pair.of(false, -1);
                insertedWorkblockID = ProxyManager.getWorkblockProxy().create(workblock);
                if (insertedWorkblockID == -1) {
                    System.out.println("ob vstavljanju s kontrolerjem je workblock id ... -1 :(");
                }
            } catch (Exception e) {
                System.err.println("Error while editing workload with message: " + e.getMessage());
                return Pair.of(false, -2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -3);
        }
        return Pair.of(true, insertedWorkblockID);
    }

    public static Pair<Boolean, Integer> updateWorkblock(WorkblockDTO workblockDTO) {

        try {
            WorkblockPK workblockPK = new WorkblockPK();
            workblockPK.setWorkloadTaskTaskId(workblockDTO.getWorkblockPK().getWorkloadTaskTaskId());
            workblockPK.setWorkloadTaskUserStoryStoryId(workblockDTO.getWorkblockPK().getWorkloadTaskUserStoryStoryId());
            workblockPK.setWorkloadUserUserId(workblockDTO.getWorkblockPK().getWorkloadUserUserId());
            workblockPK.setWorkloadWorkloadId(workblockDTO.getWorkblockPK().getWorkloadWorkloadId());
            workblockPK.setWorkblockId(workblockDTO.getWorkblockPK().getWorkblockId());
            Workblock workblock = ProxyManager.getWorkblockProxy().findWorkblock(workblockPK);

            workblock.setTimeStart(workblockDTO.getTimeStart());
            workblock.setTimeStop(workblockDTO.getTimeStop());
            try {
                if (workblock == null)
                    return Pair.of(false, -1);

                ProxyManager.getWorkblockProxy().edit(workblock);

            } catch (Exception e) {
                System.err.println("Error while editing workload with message: " + e.getMessage());
                return Pair.of(false, -2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -3);
        }
        return Pair.of(true, 0);
    }
}
