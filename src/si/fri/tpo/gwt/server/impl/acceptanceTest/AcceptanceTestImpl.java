package si.fri.tpo.gwt.server.impl.acceptanceTest;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.server.jpa.AcceptanceTest;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anze on 27. 04. 14.
 */
public class AcceptanceTestImpl {
    public static Pair<Boolean, Integer> saveAcceptanceTest(AcceptanceTestDTO acceptanceTestDTO) {

        int insertedAcceptanceTestID = -1;

        try {
            AcceptanceTest acceptanceTest = new AcceptanceTest();
            acceptanceTest.setContent(acceptanceTestDTO.getContent());
            try {
                if (acceptanceTest == null)
                    return Pair.of(false, -1);
                insertedAcceptanceTestID = ProxyManager.getAcceptanceTestProxy().create(acceptanceTest);
                if (insertedAcceptanceTestID == -1) {
                    System.out.println("ob vstavljanju s kontrolerjem je AcceptanceTest id ... -1 :(");
                }
                System.out.println(insertedAcceptanceTestID);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -1);
        }
        return Pair.of(true, insertedAcceptanceTestID);
    }

    public static Pair<Boolean, List<Integer>> saveAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList) {

        List<Integer> insertedAcceptanceTestID = new ArrayList<Integer>();

        for (AcceptanceTestDTO acceptanceTestDTO : acceptanceTestDTOList) {
            try {
                AcceptanceTest acceptanceTest = new AcceptanceTest();
                acceptanceTest.setContent(acceptanceTestDTO.getContent());
                try {
                    if (acceptanceTest == null)
                        return Pair.of(false, null);
                    int id = ProxyManager.getAcceptanceTestProxy().create(acceptanceTest);
                    if (id == -1) {
                        System.out.println("ob vstavljanju s kontrolerjem je AcceptanceTest id ... -1 :(");
                    } else {
                        insertedAcceptanceTestID.add(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    return Pair.of(false, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Pair.of(false, null);
            }
        }
        return Pair.of(true, insertedAcceptanceTestID);
    }

    public static Pair<Boolean, String> updateAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList) {
        try {
            System.out.println("Number of accTest to save: " + acceptanceTestDTOList.size());
            for (AcceptanceTestDTO acceptanceTestDTO : acceptanceTestDTOList) {
                try {
                    AcceptanceTest acceptanceTest = ProxyManager.getAcceptanceTestProxy().findAcceptanceTest(acceptanceTestDTO.getAcceptanceTestId());
                    acceptanceTest.setContent(acceptanceTestDTO.getContent());
                    try {
                        if (acceptanceTest == null)
                            return Pair.of(false, "Data error.");
                        ProxyManager.getAcceptanceTestProxy().edit(acceptanceTest);
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                        return Pair.of(false, e.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return Pair.of(false, e.getMessage());
                }
            }
        } catch(Exception ex) {}
        return Pair.of(true, "Acceptance tests should be updated.");
    }

    public static Pair<Boolean, String> deleteAcceptanceTest(AcceptanceTestDTO acceptanceTestDTO) {
        try {
            if (acceptanceTestDTO == null)
                return Pair.of(false, "Data error!");
            ProxyManager.getAcceptanceTestProxy().destroy(acceptanceTestDTO.getAcceptanceTestId());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Acceptance test has been deleted successfully.");
    }
}
