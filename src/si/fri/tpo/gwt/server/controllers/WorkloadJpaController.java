/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.controllers;

import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.gwt.server.jpa.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Integer create(Workload workload) throws PreexistingEntityException, Exception {
        int insertedWorkloadID = -1;
        if (workload.getWorkloadPK() == null) {
            workload.setWorkloadPK(new WorkloadPK());
        }
        if (workload.getWorkblockList() == null) {
            workload.setWorkblockList(new ArrayList<Workblock>());
        }
        workload.getWorkloadPK().setUserUserId(workload.getUser().getUserId());
        workload.getWorkloadPK().setTaskUserStoryStoryId(workload.getTask().getTaskPK().getUserStoryStoryId());
        workload.getWorkloadPK().setTaskTaskId(workload.getTask().getTaskPK().getTaskId());
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
            List<Workblock> attachedWorkblockList = new ArrayList<Workblock>();
            for (Workblock workblockListWorkblockToAttach : workload.getWorkblockList()) {
                workblockListWorkblockToAttach = em.getReference(workblockListWorkblockToAttach.getClass(), workblockListWorkblockToAttach.getWorkblockPK());
                attachedWorkblockList.add(workblockListWorkblockToAttach);
            }
            workload.setWorkblockList(attachedWorkblockList);
            em.persist(workload);
            if (user != null) {
                user.getWorkloadList().add(workload);
                user = em.merge(user);
            }
            if (task != null) {
                task.getWorkloadList().add(workload);
                task = em.merge(task);
            }
            for (Workblock workblockListWorkblock : workload.getWorkblockList()) {
                Workload oldWorkloadOfWorkblockListWorkblock = workblockListWorkblock.getWorkload();
                workblockListWorkblock.setWorkload(workload);
                workblockListWorkblock = em.merge(workblockListWorkblock);
                if (oldWorkloadOfWorkblockListWorkblock != null) {
                    oldWorkloadOfWorkblockListWorkblock.getWorkblockList().remove(workblockListWorkblock);
                    oldWorkloadOfWorkblockListWorkblock = em.merge(oldWorkloadOfWorkblockListWorkblock);
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
                insertedWorkloadID = workload.getWorkloadPK().getWorkloadId();
                em.close();
            }
        }
        return insertedWorkloadID;
    }

    public void edit(Workload workload) throws IllegalOrphanException, NonexistentEntityException, Exception {
        workload.getWorkloadPK().setUserUserId(workload.getUser().getUserId());
        workload.getWorkloadPK().setTaskUserStoryStoryId(workload.getTask().getTaskPK().getUserStoryStoryId());
        workload.getWorkloadPK().setTaskTaskId(workload.getTask().getTaskPK().getTaskId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workload persistentWorkload = em.find(Workload.class, workload.getWorkloadPK());
            User userOld = persistentWorkload.getUser();
            User userNew = workload.getUser();
            Task taskOld = persistentWorkload.getTask();
            Task taskNew = workload.getTask();
            List<Workblock> workblockListOld = persistentWorkload.getWorkblockList();
            List<Workblock> workblockListNew = workload.getWorkblockList();
            List<String> illegalOrphanMessages = null;
            for (Workblock workblockListOldWorkblock : workblockListOld) {
                if (!workblockListNew.contains(workblockListOldWorkblock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workblock " + workblockListOldWorkblock + " since its workload field is not nullable.");
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
            List<Workblock> attachedWorkblockListNew = new ArrayList<Workblock>();
            for (Workblock workblockListNewWorkblockToAttach : workblockListNew) {
                workblockListNewWorkblockToAttach = em.getReference(workblockListNewWorkblockToAttach.getClass(), workblockListNewWorkblockToAttach.getWorkblockPK());
                attachedWorkblockListNew.add(workblockListNewWorkblockToAttach);
            }
            workblockListNew = attachedWorkblockListNew;
            workload.setWorkblockList(workblockListNew);
            workload = em.merge(workload);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getWorkloadList().remove(workload);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getWorkloadList().add(workload);
                userNew = em.merge(userNew);
            }
            if (taskOld != null && !taskOld.equals(taskNew)) {
                taskOld.getWorkloadList().remove(workload);
                taskOld = em.merge(taskOld);
            }
            if (taskNew != null && !taskNew.equals(taskOld)) {
                taskNew.getWorkloadList().add(workload);
                taskNew = em.merge(taskNew);
            }
            for (Workblock workblockListNewWorkblock : workblockListNew) {
                if (!workblockListOld.contains(workblockListNewWorkblock)) {
                    Workload oldWorkloadOfWorkblockListNewWorkblock = workblockListNewWorkblock.getWorkload();
                    workblockListNewWorkblock.setWorkload(workload);
                    workblockListNewWorkblock = em.merge(workblockListNewWorkblock);
                    if (oldWorkloadOfWorkblockListNewWorkblock != null && !oldWorkloadOfWorkblockListNewWorkblock.equals(workload)) {
                        oldWorkloadOfWorkblockListNewWorkblock.getWorkblockList().remove(workblockListNewWorkblock);
                        oldWorkloadOfWorkblockListNewWorkblock = em.merge(oldWorkloadOfWorkblockListNewWorkblock);
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
            List<Workblock> workblockListOrphanCheck = workload.getWorkblockList();
            for (Workblock workblockListOrphanCheckWorkblock : workblockListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Workload (" + workload + ") cannot be destroyed since the Workblock " + workblockListOrphanCheckWorkblock + " in its workblockList field has a non-nullable workload field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User user = workload.getUser();
            if (user != null) {
                user.getWorkloadList().remove(workload);
                user = em.merge(user);
            }
            Task task = workload.getTask();
            if (task != null) {
                task.getWorkloadList().remove(workload);
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
