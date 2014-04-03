/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author nanorax
 */
public class AcceptanceTestJpaController implements Serializable {

    public AcceptanceTestJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AcceptanceTest acceptanceTest) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserStory userStory = acceptanceTest.getUserStory();
            if (userStory != null) {
                userStory = em.getReference(userStory.getClass(), userStory.getStoryId());
                acceptanceTest.setUserStory(userStory);
            }
            em.persist(acceptanceTest);
            if (userStory != null) {
                userStory.getAcceptanceTests().add(acceptanceTest);
                userStory = em.merge(userStory);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AcceptanceTest acceptanceTest) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AcceptanceTest persistentAcceptanceTest = em.find(AcceptanceTest.class, acceptanceTest.getAcceptanceTestId());
            UserStory userStoryOld = persistentAcceptanceTest.getUserStory();
            UserStory userStoryNew = acceptanceTest.getUserStory();
            if (userStoryNew != null) {
                userStoryNew = em.getReference(userStoryNew.getClass(), userStoryNew.getStoryId());
                acceptanceTest.setUserStory(userStoryNew);
            }
            acceptanceTest = em.merge(acceptanceTest);
            if (userStoryOld != null && !userStoryOld.equals(userStoryNew)) {
                userStoryOld.getAcceptanceTests().remove(acceptanceTest);
                userStoryOld = em.merge(userStoryOld);
            }
            if (userStoryNew != null && !userStoryNew.equals(userStoryOld)) {
                userStoryNew.getAcceptanceTests().add(acceptanceTest);
                userStoryNew = em.merge(userStoryNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = acceptanceTest.getAcceptanceTestId();
                if (findAcceptanceTest(id) == null) {
                    throw new NonexistentEntityException("The acceptanceTest with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AcceptanceTest acceptanceTest;
            try {
                acceptanceTest = em.getReference(AcceptanceTest.class, id);
                acceptanceTest.getAcceptanceTestId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acceptanceTest with id " + id + " no longer exists.", enfe);
            }
            UserStory userStory = acceptanceTest.getUserStory();
            if (userStory != null) {
                userStory.getAcceptanceTests().remove(acceptanceTest);
                userStory = em.merge(userStory);
            }
            em.remove(acceptanceTest);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AcceptanceTest> findAcceptanceTestEntities() {
        return findAcceptanceTestEntities(true, -1, -1);
    }

    public List<AcceptanceTest> findAcceptanceTestEntities(int maxResults, int firstResult) {
        return findAcceptanceTestEntities(false, maxResults, firstResult);
    }

    private List<AcceptanceTest> findAcceptanceTestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AcceptanceTest.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AcceptanceTest findAcceptanceTest(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AcceptanceTest.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcceptanceTestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AcceptanceTest> rt = cq.from(AcceptanceTest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
