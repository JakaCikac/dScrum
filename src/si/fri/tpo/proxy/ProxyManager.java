package si.fri.tpo.proxy;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by nanorax on 05/04/14.
 */
public class ProxyManager {

    private static EntityManagerFactory emf = null;
    private static UserProxy userProxy = null;

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DScrum");
        }

        return emf;
    }

    public static UserProxy getUserProxy() {
        if (userProxy == null) {
            userProxy = new UserProxy(getEmf());
        }

        return userProxy;
    }
}
