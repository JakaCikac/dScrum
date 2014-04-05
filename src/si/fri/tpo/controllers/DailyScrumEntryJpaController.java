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
import si.fri.tpo.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.jpa.DailyScrumEntry;
import si.fri.tpo.jpa.DailyScrumEntryPK;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.User;

/**
 *
 * @author Administrator
 */
public class DailyScrumEntryJpaController implements Serializable {

    public DailyScrumEntryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DailyScrumEntry dailyScrumEntry) throws PreexistingEntityException, Exception {
        if (dailyScrumEntry.getDailyScrumEntryPK() == null) {
            dailyScrumEntry.setDailyScrumEntryPK(new DailyScrumEntryPK());
        }
        dailyScrumEntry.getDailyScrumEntryPK().setProjectProjectId(dailyScrumEntry.getProject().getProjectId());
        dailyScrumEntry.getDailyScrumEntryPK().setUserUserId(dailyScrumEntry.getUser().getUserId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = dailyScrumEntry.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                dailyScrumEntry.setProject(project);
            }
            User user = dailyScrumEntry.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                dailyScrumEntry.setUser(user);
            }
            em.persist(dailyScrumEntry);
            if (project != null) {
                project.getDailyScrumEntryList().add(dailyScrumEntry);
                project = em.merge(project);
            }
            if (user != null) {
                user.getDailyScrumEntryList().add(dailyScrumEntry);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDailyScrumEntry(dailyScrumEntry.getDailyScrumEntryPK()) != null) {
                throw new PreexistingEntityException("DailyScrumEntry " + dailyScrumEntry + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DailyScrumEntry dailyScrumEntry) throws NonexistentEntityException, Exception {
        dailyScrumEntry.getDailyScrumEntryPK().setProjectProjectId(dailyScrumEntry.getProject().getProjectId());
        dailyScrumEntry.getDailyScrumEntryPK().setUserUserId(dailyScrumEntry.getUser().getUserId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DailyScrumEntry persistentDailyScrumEntry = em.find(DailyScrumEntry.class, dailyScrumEntry.getDailyScrumEntryPK());
            Project projectOld = persistentDailyScrumEntry.getProject();
            Project projectNew = dailyScrumEntry.getProject();
            User userOld = persistentDailyScrumEntry.getUser();
            User userNew = dailyScrumEntry.getUser();
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                dailyScrumEntry.setProject(projectNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                dailyScrumEntry.setUser(userNew);
            }
            dailyScrumEntry = em.merge(dailyScrumEntry);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getDailyScrumEntryList().remove(dailyScrumEntry);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getDailyScrumEntryList().add(dailyScrumEntry);
                projectNew = em.merge(projectNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDailyScrumEntryList().remove(dailyScrumEntry);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDailyScrumEntryList().add(dailyScrumEntry);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DailyScrumEntryPK id = dailyScrumEntry.getDailyScrumEntryPK();
                if (findDailyScrumEntry(id) == null) {
                    throw new NonexistentEntityException("The dailyScrumEntry with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DailyScrumEntryPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DailyScrumEntry dailyScrumEntry;
            try {
                dailyScrumEntry = em.getReference(DailyScrumEntry.class, id);
                dailyScrumEntry.getDailyScrumEntryPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dailyScrumEntry with id " + id + " no longer exists.", enfe);
            }
            Project project = dailyScrumEntry.getProject();
            if (project != null) {
                project.getDailyScrumEntryList().remove(dailyScrumEntry);
                project = em.merge(project);
            }
            User user = dailyScrumEntry.getUser();
            if (user != null) {
                user.getDailyScrumEntryList().remove(dailyScrumEntry);
                user = em.merge(user);
            }
            em.remove(dailyScrumEntry);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DailyScrumEntry> findDailyScrumEntryEntities() {
        return findDailyScrumEntryEntities(true, -1, -1);
    }

    public List<DailyScrumEntry> findDailyScrumEntryEntities(int maxResults, int firstResult) {
        return findDailyScrumEntryEntities(false, maxResults, firstResult);
    }

    private List<DailyScrumEntry> findDailyScrumEntryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DailyScrumEntry.class));
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

    public DailyScrumEntry findDailyScrumEntry(DailyScrumEntryPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DailyScrumEntry.class, id);
        } finally {
            em.close();
        }
    }

    public int getDailyScrumEntryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DailyScrumEntry> rt = cq.from(DailyScrumEntry.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
