/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.Task;
import si.fri.tpo.jpa.Workblock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.jpa.Workload;
import si.fri.tpo.jpa.WorkloadPK;

/**
 *
 * @author Administrator
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
        if (workload.getWorkloadPK() == null) {
            workload.setWorkloadPK(new WorkloadPK());
        }
        if (workload.getWorkblockCollection() == null) {
            workload.setWorkblockCollection(new ArrayList<Workblock>());
        }
        workload.getWorkloadPK().setUSERuserid(workload.getUser().getUserId());
        workload.getWorkloadPK().setTASKtaskid(workload.getTask().getTaskPK().getTaskId());
        workload.getWorkloadPK().setTASKUSERSTORYstoryid(workload.getTask().getTaskPK().getUSERSTORYstoryid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = workload.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                workload.setUser(user);
            }
            Task task = workload.getTask();
            if (task != null) {
                task = em.getReference(task.getClass(), task.getTaskPK());
                workload.setTask(task);
            }
            Collection<Workblock> attachedWorkblockCollection = new ArrayList<Workblock>();
            for (Workblock workblockCollectionWorkblockToAttach : workload.getWorkblockCollection()) {
                workblockCollectionWorkblockToAttach = em.getReference(workblockCollectionWorkblockToAttach.getClass(), workblockCollectionWorkblockToAttach.getWorkblockPK());
                attachedWorkblockCollection.add(workblockCollectionWorkblockToAttach);
            }
            workload.setWorkblockCollection(attachedWorkblockCollection);
            em.persist(workload);
            if (user != null) {
                user.getWorkloadCollection().add(workload);
                user = em.merge(user);
            }
            if (task != null) {
                task.getWorkloadCollection().add(workload);
                task = em.merge(task);
            }
            for (Workblock workblockCollectionWorkblock : workload.getWorkblockCollection()) {
                Workload oldWorkloadOfWorkblockCollectionWorkblock = workblockCollectionWorkblock.getWorkload();
                workblockCollectionWorkblock.setWorkload(workload);
                workblockCollectionWorkblock = em.merge(workblockCollectionWorkblock);
                if (oldWorkloadOfWorkblockCollectionWorkblock != null) {
                    oldWorkloadOfWorkblockCollectionWorkblock.getWorkblockCollection().remove(workblockCollectionWorkblock);
                    oldWorkloadOfWorkblockCollectionWorkblock = em.merge(oldWorkloadOfWorkblockCollectionWorkblock);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWorkload(workload.getWorkloadPK()) != null) {
                throw new PreexistingEntityException("Workload " + workload + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workload workload) throws IllegalOrphanException, NonexistentEntityException, Exception {
        workload.getWorkloadPK().setUSERuserid(workload.getUser().getUserId());
        workload.getWorkloadPK().setTASKtaskid(workload.getTask().getTaskPK().getTaskId());
        workload.getWorkloadPK().setTASKUSERSTORYstoryid(workload.getTask().getTaskPK().getUSERSTORYstoryid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workload persistentWorkload = em.find(Workload.class, workload.getWorkloadPK());
            User userOld = persistentWorkload.getUser();
            User userNew = workload.getUser();
            Task taskOld = persistentWorkload.getTask();
            Task taskNew = workload.getTask();
            Collection<Workblock> workblockCollectionOld = persistentWorkload.getWorkblockCollection();
            Collection<Workblock> workblockCollectionNew = workload.getWorkblockCollection();
            List<String> illegalOrphanMessages = null;
            for (Workblock workblockCollectionOldWorkblock : workblockCollectionOld) {
                if (!workblockCollectionNew.contains(workblockCollectionOldWorkblock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workblock " + workblockCollectionOldWorkblock + " since its workload field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                workload.setUser(userNew);
            }
            if (taskNew != null) {
                taskNew = em.getReference(taskNew.getClass(), taskNew.getTaskPK());
                workload.setTask(taskNew);
            }
            Collection<Workblock> attachedWorkblockCollectionNew = new ArrayList<Workblock>();
            for (Workblock workblockCollectionNewWorkblockToAttach : workblockCollectionNew) {
                workblockCollectionNewWorkblockToAttach = em.getReference(workblockCollectionNewWorkblockToAttach.getClass(), workblockCollectionNewWorkblockToAttach.getWorkblockPK());
                attachedWorkblockCollectionNew.add(workblockCollectionNewWorkblockToAttach);
            }
            workblockCollectionNew = attachedWorkblockCollectionNew;
            workload.setWorkblockCollection(workblockCollectionNew);
            workload = em.merge(workload);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getWorkloadCollection().remove(workload);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getWorkloadCollection().add(workload);
                userNew = em.merge(userNew);
            }
            if (taskOld != null && !taskOld.equals(taskNew)) {
                taskOld.getWorkloadCollection().remove(workload);
                taskOld = em.merge(taskOld);
            }
            if (taskNew != null && !taskNew.equals(taskOld)) {
                taskNew.getWorkloadCollection().add(workload);
                taskNew = em.merge(taskNew);
            }
            for (Workblock workblockCollectionNewWorkblock : workblockCollectionNew) {
                if (!workblockCollectionOld.contains(workblockCollectionNewWorkblock)) {
                    Workload oldWorkloadOfWorkblockCollectionNewWorkblock = workblockCollectionNewWorkblock.getWorkload();
                    workblockCollectionNewWorkblock.setWorkload(workload);
                    workblockCollectionNewWorkblock = em.merge(workblockCollectionNewWorkblock);
                    if (oldWorkloadOfWorkblockCollectionNewWorkblock != null && !oldWorkloadOfWorkblockCollectionNewWorkblock.equals(workload)) {
                        oldWorkloadOfWorkblockCollectionNewWorkblock.getWorkblockCollection().remove(workblockCollectionNewWorkblock);
                        oldWorkloadOfWorkblockCollectionNewWorkblock = em.merge(oldWorkloadOfWorkblockCollectionNewWorkblock);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                WorkloadPK id = workload.getWorkloadPK();
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

    public void destroy(WorkloadPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workload workload;
            try {
                workload = em.getReference(Workload.class, id);
                workload.getWorkloadPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workload with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Workblock> workblockCollectionOrphanCheck = workload.getWorkblockCollection();
            for (Workblock workblockCollectionOrphanCheckWorkblock : workblockCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Workload (" + workload + ") cannot be destroyed since the Workblock " + workblockCollectionOrphanCheckWorkblock + " in its workblockCollection field has a non-nullable workload field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User user = workload.getUser();
            if (user != null) {
                user.getWorkloadCollection().remove(workload);
                user = em.merge(user);
            }
            Task task = workload.getTask();
            if (task != null) {
                task.getWorkloadCollection().remove(workload);
                task = em.merge(task);
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
