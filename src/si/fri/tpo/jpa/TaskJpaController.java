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
public class TaskJpaController implements Serializable {

    public TaskJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Task task) throws PreexistingEntityException, Exception {
        if (task.getId() == null) {
            task.setId(new TaskPK());
        }
        if (task.getWorkloads() == null) {
            task.setWorkloads(new ArrayList<Workload>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = task.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                task.setUser(user);
            }
            UserStory userStory = task.getUserStory();
            if (userStory != null) {
                userStory = em.getReference(userStory.getClass(), userStory.getStoryId());
                task.setUserStory(userStory);
            }
            List<Workload> attachedWorkloads = new ArrayList<Workload>();
            for (Workload workloadsWorkloadToAttach : task.getWorkloads()) {
                workloadsWorkloadToAttach = em.getReference(workloadsWorkloadToAttach.getClass(), workloadsWorkloadToAttach.getId());
                attachedWorkloads.add(workloadsWorkloadToAttach);
            }
            task.setWorkloads(attachedWorkloads);
            em.persist(task);
            if (user != null) {
                user.getTasks().add(task);
                user = em.merge(user);
            }
            if (userStory != null) {
                userStory.getTasks().add(task);
                userStory = em.merge(userStory);
            }
            for (Workload workloadsWorkload : task.getWorkloads()) {
                Task oldTaskOfWorkloadsWorkload = workloadsWorkload.getTask();
                workloadsWorkload.setTask(task);
                workloadsWorkload = em.merge(workloadsWorkload);
                if (oldTaskOfWorkloadsWorkload != null) {
                    oldTaskOfWorkloadsWorkload.getWorkloads().remove(workloadsWorkload);
                    oldTaskOfWorkloadsWorkload = em.merge(oldTaskOfWorkloadsWorkload);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTask(task.getId()) != null) {
                throw new PreexistingEntityException("Task " + task + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Task task) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task persistentTask = em.find(Task.class, task.getId());
            User userOld = persistentTask.getUser();
            User userNew = task.getUser();
            UserStory userStoryOld = persistentTask.getUserStory();
            UserStory userStoryNew = task.getUserStory();
            List<Workload> workloadsOld = persistentTask.getWorkloads();
            List<Workload> workloadsNew = task.getWorkloads();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                task.setUser(userNew);
            }
            if (userStoryNew != null) {
                userStoryNew = em.getReference(userStoryNew.getClass(), userStoryNew.getStoryId());
                task.setUserStory(userStoryNew);
            }
            List<Workload> attachedWorkloadsNew = new ArrayList<Workload>();
            for (Workload workloadsNewWorkloadToAttach : workloadsNew) {
                workloadsNewWorkloadToAttach = em.getReference(workloadsNewWorkloadToAttach.getClass(), workloadsNewWorkloadToAttach.getId());
                attachedWorkloadsNew.add(workloadsNewWorkloadToAttach);
            }
            workloadsNew = attachedWorkloadsNew;
            task.setWorkloads(workloadsNew);
            task = em.merge(task);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getTasks().remove(task);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getTasks().add(task);
                userNew = em.merge(userNew);
            }
            if (userStoryOld != null && !userStoryOld.equals(userStoryNew)) {
                userStoryOld.getTasks().remove(task);
                userStoryOld = em.merge(userStoryOld);
            }
            if (userStoryNew != null && !userStoryNew.equals(userStoryOld)) {
                userStoryNew.getTasks().add(task);
                userStoryNew = em.merge(userStoryNew);
            }
            for (Workload workloadsOldWorkload : workloadsOld) {
                if (!workloadsNew.contains(workloadsOldWorkload)) {
                    workloadsOldWorkload.setTask(null);
                    workloadsOldWorkload = em.merge(workloadsOldWorkload);
                }
            }
            for (Workload workloadsNewWorkload : workloadsNew) {
                if (!workloadsOld.contains(workloadsNewWorkload)) {
                    Task oldTaskOfWorkloadsNewWorkload = workloadsNewWorkload.getTask();
                    workloadsNewWorkload.setTask(task);
                    workloadsNewWorkload = em.merge(workloadsNewWorkload);
                    if (oldTaskOfWorkloadsNewWorkload != null && !oldTaskOfWorkloadsNewWorkload.equals(task)) {
                        oldTaskOfWorkloadsNewWorkload.getWorkloads().remove(workloadsNewWorkload);
                        oldTaskOfWorkloadsNewWorkload = em.merge(oldTaskOfWorkloadsNewWorkload);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TaskPK id = task.getId();
                if (findTask(id) == null) {
                    throw new NonexistentEntityException("The task with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TaskPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task task;
            try {
                task = em.getReference(Task.class, id);
                task.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The task with id " + id + " no longer exists.", enfe);
            }
            User user = task.getUser();
            if (user != null) {
                user.getTasks().remove(task);
                user = em.merge(user);
            }
            UserStory userStory = task.getUserStory();
            if (userStory != null) {
                userStory.getTasks().remove(task);
                userStory = em.merge(userStory);
            }
            List<Workload> workloads = task.getWorkloads();
            for (Workload workloadsWorkload : workloads) {
                workloadsWorkload.setTask(null);
                workloadsWorkload = em.merge(workloadsWorkload);
            }
            em.remove(task);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Task> findTaskEntities() {
        return findTaskEntities(true, -1, -1);
    }

    public List<Task> findTaskEntities(int maxResults, int firstResult) {
        return findTaskEntities(false, maxResults, firstResult);
    }

    private List<Task> findTaskEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Task.class));
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

    public Task findTask(TaskPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Task.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaskCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Task> rt = cq.from(Task.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
