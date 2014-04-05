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
import si.fri.tpo.jpa.UserStory;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.Workload;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.jpa.Task;
import si.fri.tpo.jpa.TaskPK;

/**
 *
 * @author Administrator
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
        if (task.getTaskPK() == null) {
            task.setTaskPK(new TaskPK());
        }
        if (task.getWorkloadCollection() == null) {
            task.setWorkloadCollection(new ArrayList<Workload>());
        }
        task.getTaskPK().setUSERSTORYstoryid(task.getUserStory().getStoryId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserStory userStory = task.getUserStory();
            if (userStory != null) {
                userStory = em.getReference(userStory.getClass(), userStory.getStoryId());
                task.setUserStory(userStory);
            }
            User USERuserid = task.getUSERuserid();
            if (USERuserid != null) {
                USERuserid = em.getReference(USERuserid.getClass(), USERuserid.getUserId());
                task.setUSERuserid(USERuserid);
            }
            Collection<Workload> attachedWorkloadCollection = new ArrayList<Workload>();
            for (Workload workloadCollectionWorkloadToAttach : task.getWorkloadCollection()) {
                workloadCollectionWorkloadToAttach = em.getReference(workloadCollectionWorkloadToAttach.getClass(), workloadCollectionWorkloadToAttach.getWorkloadPK());
                attachedWorkloadCollection.add(workloadCollectionWorkloadToAttach);
            }
            task.setWorkloadCollection(attachedWorkloadCollection);
            em.persist(task);
            if (userStory != null) {
                userStory.getTaskCollection().add(task);
                userStory = em.merge(userStory);
            }
            if (USERuserid != null) {
                USERuserid.getTaskCollection().add(task);
                USERuserid = em.merge(USERuserid);
            }
            for (Workload workloadCollectionWorkload : task.getWorkloadCollection()) {
                Task oldTaskOfWorkloadCollectionWorkload = workloadCollectionWorkload.getTask();
                workloadCollectionWorkload.setTask(task);
                workloadCollectionWorkload = em.merge(workloadCollectionWorkload);
                if (oldTaskOfWorkloadCollectionWorkload != null) {
                    oldTaskOfWorkloadCollectionWorkload.getWorkloadCollection().remove(workloadCollectionWorkload);
                    oldTaskOfWorkloadCollectionWorkload = em.merge(oldTaskOfWorkloadCollectionWorkload);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTask(task.getTaskPK()) != null) {
                throw new PreexistingEntityException("Task " + task + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Task task) throws IllegalOrphanException, NonexistentEntityException, Exception {
        task.getTaskPK().setUSERSTORYstoryid(task.getUserStory().getStoryId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task persistentTask = em.find(Task.class, task.getTaskPK());
            UserStory userStoryOld = persistentTask.getUserStory();
            UserStory userStoryNew = task.getUserStory();
            User USERuseridOld = persistentTask.getUSERuserid();
            User USERuseridNew = task.getUSERuserid();
            Collection<Workload> workloadCollectionOld = persistentTask.getWorkloadCollection();
            Collection<Workload> workloadCollectionNew = task.getWorkloadCollection();
            List<String> illegalOrphanMessages = null;
            for (Workload workloadCollectionOldWorkload : workloadCollectionOld) {
                if (!workloadCollectionNew.contains(workloadCollectionOldWorkload)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workload " + workloadCollectionOldWorkload + " since its task field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userStoryNew != null) {
                userStoryNew = em.getReference(userStoryNew.getClass(), userStoryNew.getStoryId());
                task.setUserStory(userStoryNew);
            }
            if (USERuseridNew != null) {
                USERuseridNew = em.getReference(USERuseridNew.getClass(), USERuseridNew.getUserId());
                task.setUSERuserid(USERuseridNew);
            }
            Collection<Workload> attachedWorkloadCollectionNew = new ArrayList<Workload>();
            for (Workload workloadCollectionNewWorkloadToAttach : workloadCollectionNew) {
                workloadCollectionNewWorkloadToAttach = em.getReference(workloadCollectionNewWorkloadToAttach.getClass(), workloadCollectionNewWorkloadToAttach.getWorkloadPK());
                attachedWorkloadCollectionNew.add(workloadCollectionNewWorkloadToAttach);
            }
            workloadCollectionNew = attachedWorkloadCollectionNew;
            task.setWorkloadCollection(workloadCollectionNew);
            task = em.merge(task);
            if (userStoryOld != null && !userStoryOld.equals(userStoryNew)) {
                userStoryOld.getTaskCollection().remove(task);
                userStoryOld = em.merge(userStoryOld);
            }
            if (userStoryNew != null && !userStoryNew.equals(userStoryOld)) {
                userStoryNew.getTaskCollection().add(task);
                userStoryNew = em.merge(userStoryNew);
            }
            if (USERuseridOld != null && !USERuseridOld.equals(USERuseridNew)) {
                USERuseridOld.getTaskCollection().remove(task);
                USERuseridOld = em.merge(USERuseridOld);
            }
            if (USERuseridNew != null && !USERuseridNew.equals(USERuseridOld)) {
                USERuseridNew.getTaskCollection().add(task);
                USERuseridNew = em.merge(USERuseridNew);
            }
            for (Workload workloadCollectionNewWorkload : workloadCollectionNew) {
                if (!workloadCollectionOld.contains(workloadCollectionNewWorkload)) {
                    Task oldTaskOfWorkloadCollectionNewWorkload = workloadCollectionNewWorkload.getTask();
                    workloadCollectionNewWorkload.setTask(task);
                    workloadCollectionNewWorkload = em.merge(workloadCollectionNewWorkload);
                    if (oldTaskOfWorkloadCollectionNewWorkload != null && !oldTaskOfWorkloadCollectionNewWorkload.equals(task)) {
                        oldTaskOfWorkloadCollectionNewWorkload.getWorkloadCollection().remove(workloadCollectionNewWorkload);
                        oldTaskOfWorkloadCollectionNewWorkload = em.merge(oldTaskOfWorkloadCollectionNewWorkload);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TaskPK id = task.getTaskPK();
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

    public void destroy(TaskPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task task;
            try {
                task = em.getReference(Task.class, id);
                task.getTaskPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The task with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Workload> workloadCollectionOrphanCheck = task.getWorkloadCollection();
            for (Workload workloadCollectionOrphanCheckWorkload : workloadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Task (" + task + ") cannot be destroyed since the Workload " + workloadCollectionOrphanCheckWorkload + " in its workloadCollection field has a non-nullable task field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UserStory userStory = task.getUserStory();
            if (userStory != null) {
                userStory.getTaskCollection().remove(task);
                userStory = em.merge(userStory);
            }
            User USERuserid = task.getUSERuserid();
            if (USERuserid != null) {
                USERuserid.getTaskCollection().remove(task);
                USERuserid = em.merge(USERuserid);
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
