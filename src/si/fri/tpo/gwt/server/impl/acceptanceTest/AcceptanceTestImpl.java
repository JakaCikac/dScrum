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
            //UserStoryDTO userStoryDTO = acceptanceTestDTO.getUserStoryStoryId();
            //UserStory userStory = new UserStory();
            //userStory.setStoryId(userStoryDTO.getStoryId());
            //acceptanceTest.setUserStoryStoryId();
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
                        System.out.println(id);
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
                } catch (NullPointerException e) {
                    saveAcceptanceTest(acceptanceTestDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Pair.of(false, e.getMessage());
                }
            }
        } catch(Exception ex) {}
        return Pair.of(true, "Acceptance tests should be updated.");
    }
}
