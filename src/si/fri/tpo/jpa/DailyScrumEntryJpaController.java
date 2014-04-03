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
import si.fri.tpo.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author nanorax
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
        if (dailyScrumEntry.getId() == null) {
            dailyScrumEntry.setId(new DailyScrumEntryPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = dailyScrumEntry.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                dailyScrumEntry.setUser(user);
            }
            Project project = dailyScrumEntry.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                dailyScrumEntry.setProject(project);
            }
            em.persist(dailyScrumEntry);
            if (user != null) {
                user.getDailyScrumEntries().add(dailyScrumEntry);
                user = em.merge(user);
            }
            if (project != null) {
                project.getDailyScrumEntries().add(dailyScrumEntry);
                project = em.merge(project);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDailyScrumEntry(dailyScrumEntry.getId()) != null) {
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DailyScrumEntry persistentDailyScrumEntry = em.find(DailyScrumEntry.class, dailyScrumEntry.getId());
            User userOld = persistentDailyScrumEntry.getUser();
            User userNew = dailyScrumEntry.getUser();
            Project projectOld = persistentDailyScrumEntry.getProject();
            Project projectNew = dailyScrumEntry.getProject();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                dailyScrumEntry.setUser(userNew);
            }
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                dailyScrumEntry.setProject(projectNew);
            }
            dailyScrumEntry = em.merge(dailyScrumEntry);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDailyScrumEntries().remove(dailyScrumEntry);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDailyScrumEntries().add(dailyScrumEntry);
                userNew = em.merge(userNew);
            }
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getDailyScrumEntries().remove(dailyScrumEntry);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getDailyScrumEntries().add(dailyScrumEntry);
                projectNew = em.merge(projectNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DailyScrumEntryPK id = dailyScrumEntry.getId();
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
                dailyScrumEntry.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dailyScrumEntry with id " + id + " no longer exists.", enfe);
            }
            User user = dailyScrumEntry.getUser();
            if (user != null) {
                user.getDailyScrumEntries().remove(dailyScrumEntry);
                user = em.merge(user);
            }
            Project project = dailyScrumEntry.getProject();
            if (project != null) {
                project.getDailyScrumEntries().remove(dailyScrumEntry);
                project = em.merge(project);
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
