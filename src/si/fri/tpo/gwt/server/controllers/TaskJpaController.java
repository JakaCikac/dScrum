/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.gwt.server.jpa.UserStory;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.jpa.Workload;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.gwt.server.jpa.Task;
import si.fri.tpo.gwt.server.jpa.TaskPK;

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
        if (task.getWorkloadList() == null) {
            task.setWorkloadList(new ArrayList<Workload>());
        }
        task.getTaskPK().setUserStoryStoryId(task.getUserStory().getStoryId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserStory userStory = task.getUserStory();
            if (userStory != null) {
                userStory = em.getReference(userStory.getClass(), userStory.getStoryId());
                task.setUserStory(userStory);
            }
            User userUserId = task.getUserUserId();
            if (userUserId != null) {
                userUserId = em.getReference(userUserId.getClass(), userUserId.getUserId());
                task.setUserUserId(userUserId);
            }
            List<Workload> attachedWorkloadList = new ArrayList<Workload>();
            for (Workload workloadListWorkloadToAttach : task.getWorkloadList()) {
                workloadListWorkloadToAttach = em.getReference(workloadListWorkloadToAttach.getClass(), workloadListWorkloadToAttach.getWorkloadPK());
                attachedWorkloadList.add(workloadListWorkloadToAttach);
            }
            task.setWorkloadList(attachedWorkloadList);
            em.persist(task);
            if (userStory != null) {
                userStory.getTaskList().add(task);
                userStory = em.merge(userStory);
            }
            if (userUserId != null) {
                userUserId.getTaskList().add(task);
                userUserId = em.merge(userUserId);
            }
            for (Workload workloadListWorkload : task.getWorkloadList()) {
                Task oldTaskOfWorkloadListWorkload = workloadListWorkload.getTask();
                workloadListWorkload.setTask(task);
                workloadListWorkload = em.merge(workloadListWorkload);
                if (oldTaskOfWorkloadListWorkload != null) {
                    oldTaskOfWorkloadListWorkload.getWorkloadList().remove(workloadListWorkload);
                    oldTaskOfWorkloadListWorkload = em.merge(oldTaskOfWorkloadListWorkload);
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
        task.getTaskPK().setUserStoryStoryId(task.getUserStory().getStoryId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task persistentTask = em.find(Task.class, task.getTaskPK());
            UserStory userStoryOld = persistentTask.getUserStory();
            UserStory userStoryNew = task.getUserStory();
            User userUserIdOld = persistentTask.getUserUserId();
            User userUserIdNew = task.getUserUserId();
            List<Workload> workloadListOld = persistentTask.getWorkloadList();
            List<Workload> workloadListNew = task.getWorkloadList();
            List<String> illegalOrphanMessages = null;
            for (Workload workloadListOldWorkload : workloadListOld) {
                if (!workloadListNew.contains(workloadListOldWorkload)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workload " + workloadListOldWorkload + " since its task field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userStoryNew != null) {
                userStoryNew = em.getReference(userStoryNew.getClass(), userStoryNew.getStoryId());
                task.setUserStory(userStoryNew);
            }
            if (userUserIdNew != null) {
                userUserIdNew = em.getReference(userUserIdNew.getClass(), userUserIdNew.getUserId());
                task.setUserUserId(userUserIdNew);
            }
            List<Workload> attachedWorkloadListNew = new ArrayList<Workload>();
            for (Workload workloadListNewWorkloadToAttach : workloadListNew) {
                workloadListNewWorkloadToAttach = em.getReference(workloadListNewWorkloadToAttach.getClass(), workloadListNewWorkloadToAttach.getWorkloadPK());
                attachedWorkloadListNew.add(workloadListNewWorkloadToAttach);
            }
            workloadListNew = attachedWorkloadListNew;
            task.setWorkloadList(workloadListNew);
            task = em.merge(task);
            if (userStoryOld != null && !userStoryOld.equals(userStoryNew)) {
                userStoryOld.getTaskList().remove(task);
                userStoryOld = em.merge(userStoryOld);
            }
            if (userStoryNew != null && !userStoryNew.equals(userStoryOld)) {
                userStoryNew.getTaskList().add(task);
                userStoryNew = em.merge(userStoryNew);
            }
            if (userUserIdOld != null && !userUserIdOld.equals(userUserIdNew)) {
                userUserIdOld.getTaskList().remove(task);
                userUserIdOld = em.merge(userUserIdOld);
            }
            if (userUserIdNew != null && !userUserIdNew.equals(userUserIdOld)) {
                userUserIdNew.getTaskList().add(task);
                userUserIdNew = em.merge(userUserIdNew);
            }
            for (Workload workloadListNewWorkload : workloadListNew) {
                if (!workloadListOld.contains(workloadListNewWorkload)) {
                    Task oldTaskOfWorkloadListNewWorkload = workloadListNewWorkload.getTask();
                    workloadListNewWorkload.setTask(task);
                    workloadListNewWorkload = em.merge(workloadListNewWorkload);
                    if (oldTaskOfWorkloadListNewWorkload != null && !oldTaskOfWorkloadListNewWorkload.equals(task)) {
                        oldTaskOfWorkloadListNewWorkload.getWorkloadList().remove(workloadListNewWorkload);
                        oldTaskOfWorkloadListNewWorkload = em.merge(oldTaskOfWorkloadListNewWorkload);
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
            List<Workload> workloadListOrphanCheck = task.getWorkloadList();
            for (Workload workloadListOrphanCheckWorkload : workloadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Task (" + task + ") cannot be destroyed since the Workload " + workloadListOrphanCheckWorkload + " in its workloadList field has a non-nullable task field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UserStory userStory = task.getUserStory();
            if (userStory != null) {
                userStory.getTaskList().remove(task);
                userStory = em.merge(userStory);
            }
            User userUserId = task.getUserUserId();
            if (userUserId != null) {
                userUserId.getTaskList().remove(task);
                userUserId = em.merge(userUserId);
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
