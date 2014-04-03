/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.jpa.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author nanorax
 */
public class WorkloadJpaController implements Serializable {

    public WorkloadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Workload workload) throws PreexistingEntityException, Exception {
        if (workload.getId() == null) {
            workload.setId(new WorkloadPK());
        }
        if (workload.getWorkblocks() == null) {
            workload.setWorkblocks(new ArrayList<Workblock>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task task = workload.getTask();
            if (task != null) {
                task = em.getReference(task.getClass(), task.getId());
                workload.setTask(task);
            }
            User user = workload.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                workload.setUser(user);
            }
            List<Workblock> attachedWorkblocks = new ArrayList<Workblock>();
            for (Workblock workblocksWorkblockToAttach : workload.getWorkblocks()) {
                workblocksWorkblockToAttach = em.getReference(workblocksWorkblockToAttach.getClass(), workblocksWorkblockToAttach.getId());
                attachedWorkblocks.add(workblocksWorkblockToAttach);
            }
            workload.setWorkblocks(attachedWorkblocks);
            em.persist(workload);
            if (task != null) {
                task.getWorkloads().add(workload);
                task = em.merge(task);
            }
            if (user != null) {
                user.getWorkloads().add(workload);
                user = em.merge(user);
            }
            for (Workblock workblocksWorkblock : workload.getWorkblocks()) {
                Workload oldWorkloadOfWorkblocksWorkblock = workblocksWorkblock.getWorkload();
                workblocksWorkblock.setWorkload(workload);
                workblocksWorkblock = em.merge(workblocksWorkblock);
                if (oldWorkloadOfWorkblocksWorkblock != null) {
                    oldWorkloadOfWorkblocksWorkblock.getWorkblocks().remove(workblocksWorkblock);
                    oldWorkloadOfWorkblocksWorkblock = em.merge(oldWorkloadOfWorkblocksWorkblock);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWorkload(workload.getId()) != null) {
                throw new PreexistingEntityException("Workload " + workload + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workload workload) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workload persistentWorkload = em.find(Workload.class, workload.getId());
            Task taskOld = persistentWorkload.getTask();
            Task taskNew = workload.getTask();
            User userOld = persistentWorkload.getUser();
            User userNew = workload.getUser();
            List<Workblock> workblocksOld = persistentWorkload.getWorkblocks();
            List<Workblock> workblocksNew = workload.getWorkblocks();
            if (taskNew != null) {
                taskNew = em.getReference(taskNew.getClass(), taskNew.getId());
                workload.setTask(taskNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                workload.setUser(userNew);
            }
            List<Workblock> attachedWorkblocksNew = new ArrayList<Workblock>();
            for (Workblock workblocksNewWorkblockToAttach : workblocksNew) {
                workblocksNewWorkblockToAttach = em.getReference(workblocksNewWorkblockToAttach.getClass(), workblocksNewWorkblockToAttach.getId());
                attachedWorkblocksNew.add(workblocksNewWorkblockToAttach);
            }
            workblocksNew = attachedWorkblocksNew;
            workload.setWorkblocks(workblocksNew);
            workload = em.merge(workload);
            if (taskOld != null && !taskOld.equals(taskNew)) {
                taskOld.getWorkloads().remove(workload);
                taskOld = em.merge(taskOld);
            }
            if (taskNew != null && !taskNew.equals(taskOld)) {
                taskNew.getWorkloads().add(workload);
                taskNew = em.merge(taskNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getWorkloads().remove(workload);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getWorkloads().add(workload);
                userNew = em.merge(userNew);
            }
            for (Workblock workblocksOldWorkblock : workblocksOld) {
                if (!workblocksNew.contains(workblocksOldWorkblock)) {
                    workblocksOldWorkblock.setWorkload(null);
                    workblocksOldWorkblock = em.merge(workblocksOldWorkblock);
                }
            }
            for (Workblock workblocksNewWorkblock : workblocksNew) {
                if (!workblocksOld.contains(workblocksNewWorkblock)) {
                    Workload oldWorkloadOfWorkblocksNewWorkblock = workblocksNewWorkblock.getWorkload();
                    workblocksNewWorkblock.setWorkload(workload);
                    workblocksNewWorkblock = em.merge(workblocksNewWorkblock);
                    if (oldWorkloadOfWorkblocksNewWorkblock != null && !oldWorkloadOfWorkblocksNewWorkblock.equals(workload)) {
                        oldWorkloadOfWorkblocksNewWorkblock.getWorkblocks().remove(workblocksNewWorkblock);
                        oldWorkloadOfWorkblocksNewWorkblock = em.merge(oldWorkloadOfWorkblocksNewWorkblock);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                WorkloadPK id = workload.getId();
                if (findWorkload(id) == null) {
                    throw new NonexistentEntityException("The workload with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(WorkloadPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workload workload;
            try {
                workload = em.getReference(Workload.class, id);
                workload.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workload with id " + id + " no longer exists.", enfe);
            }
            Task task = workload.getTask();
            if (task != null) {
                task.getWorkloads().remove(workload);
                task = em.merge(task);
            }
            User user = workload.getUser();
            if (user != null) {
                user.getWorkloads().remove(workload);
                user = em.merge(user);
            }
            List<Workblock> workblocks = workload.getWorkblocks();
            for (Workblock workblocksWorkblock : workblocks) {
                workblocksWorkblock.setWorkload(null);
                workblocksWorkblock = em.merge(workblocksWorkblock);
            }
            em.remove(workload);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Workload> findWorkloadEntities() {
        return findWorkloadEntities(true, -1, -1);
    }

    public List<Workload> findWorkloadEntities(int maxResults, int firstResult) {
        return findWorkloadEntities(false, maxResults, firstResult);
    }

    private List<Workload> findWorkloadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workload.class));
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

    public Workload findWorkload(WorkloadPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workload.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkloadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workload> rt = cq.from(Workload.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
