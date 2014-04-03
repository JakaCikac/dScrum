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
public class WorkblockJpaController implements Serializable {

    public WorkblockJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Workblock workblock) throws PreexistingEntityException, Exception {
        if (workblock.getId() == null) {
            workblock.setId(new WorkblockPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workload workload = workblock.getWorkload();
            if (workload != null) {
                workload = em.getReference(workload.getClass(), workload.getId());
                workblock.setWorkload(workload);
            }
            em.persist(workblock);
            if (workload != null) {
                workload.getWorkblocks().add(workblock);
                workload = em.merge(workload);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWorkblock(workblock.getId()) != null) {
                throw new PreexistingEntityException("Workblock " + workblock + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workblock workblock) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workblock persistentWorkblock = em.find(Workblock.class, workblock.getId());
            Workload workloadOld = persistentWorkblock.getWorkload();
            Workload workloadNew = workblock.getWorkload();
            if (workloadNew != null) {
                workloadNew = em.getReference(workloadNew.getClass(), workloadNew.getId());
                workblock.setWorkload(workloadNew);
            }
            workblock = em.merge(workblock);
            if (workloadOld != null && !workloadOld.equals(workloadNew)) {
                workloadOld.getWorkblocks().remove(workblock);
                workloadOld = em.merge(workloadOld);
            }
            if (workloadNew != null && !workloadNew.equals(workloadOld)) {
                workloadNew.getWorkblocks().add(workblock);
                workloadNew = em.merge(workloadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                WorkblockPK id = workblock.getId();
                if (findWorkblock(id) == null) {
                    throw new NonexistentEntityException("The workblock with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(WorkblockPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workblock workblock;
            try {
                workblock = em.getReference(Workblock.class, id);
                workblock.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workblock with id " + id + " no longer exists.", enfe);
            }
            Workload workload = workblock.getWorkload();
            if (workload != null) {
                workload.getWorkblocks().remove(workblock);
                workload = em.merge(workload);
            }
            em.remove(workblock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Workblock> findWorkblockEntities() {
        return findWorkblockEntities(true, -1, -1);
    }

    public List<Workblock> findWorkblockEntities(int maxResults, int firstResult) {
        return findWorkblockEntities(false, maxResults, firstResult);
    }

    private List<Workblock> findWorkblockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workblock.class));
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

    public Workblock findWorkblock(WorkblockPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workblock.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkblockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workblock> rt = cq.from(Workblock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
