/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.AcceptanceTest;
import si.fri.tpo.jpa.UserStory;

/**
 *
 * @author Administrator
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
            UserStory USERSTORYstoryid = acceptanceTest.getUSERSTORYstoryid();
            if (USERSTORYstoryid != null) {
                USERSTORYstoryid = em.getReference(USERSTORYstoryid.getClass(), USERSTORYstoryid.getStoryId());
                acceptanceTest.setUSERSTORYstoryid(USERSTORYstoryid);
            }
            em.persist(acceptanceTest);
            if (USERSTORYstoryid != null) {
                USERSTORYstoryid.getAcceptanceTestCollection().add(acceptanceTest);
                USERSTORYstoryid = em.merge(USERSTORYstoryid);
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
            UserStory USERSTORYstoryidOld = persistentAcceptanceTest.getUSERSTORYstoryid();
            UserStory USERSTORYstoryidNew = acceptanceTest.getUSERSTORYstoryid();
            if (USERSTORYstoryidNew != null) {
                USERSTORYstoryidNew = em.getReference(USERSTORYstoryidNew.getClass(), USERSTORYstoryidNew.getStoryId());
                acceptanceTest.setUSERSTORYstoryid(USERSTORYstoryidNew);
            }
            acceptanceTest = em.merge(acceptanceTest);
            if (USERSTORYstoryidOld != null && !USERSTORYstoryidOld.equals(USERSTORYstoryidNew)) {
                USERSTORYstoryidOld.getAcceptanceTestCollection().remove(acceptanceTest);
                USERSTORYstoryidOld = em.merge(USERSTORYstoryidOld);
            }
            if (USERSTORYstoryidNew != null && !USERSTORYstoryidNew.equals(USERSTORYstoryidOld)) {
                USERSTORYstoryidNew.getAcceptanceTestCollection().add(acceptanceTest);
                USERSTORYstoryidNew = em.merge(USERSTORYstoryidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = acceptanceTest.getAcceptanceTestId();
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            UserStory USERSTORYstoryid = acceptanceTest.getUSERSTORYstoryid();
            if (USERSTORYstoryid != null) {
                USERSTORYstoryid.getAcceptanceTestCollection().remove(acceptanceTest);
                USERSTORYstoryid = em.merge(USERSTORYstoryid);
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

    public AcceptanceTest findAcceptanceTest(Integer id) {
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
