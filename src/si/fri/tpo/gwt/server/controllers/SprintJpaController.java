/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.controllers;

import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Sprint;
import si.fri.tpo.gwt.server.jpa.SprintPK;
import si.fri.tpo.gwt.server.jpa.UserStory;

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
public class SprintJpaController implements Serializable {

    public SprintJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public SprintJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int create(Sprint sprint) throws PreexistingEntityException, Exception {
        int insertedSprintID = -1;
        if (sprint.getSprintPK() == null) {
            sprint.setSprintPK(new SprintPK());
        }
        if (sprint.getUserStoryList() == null) {
            sprint.setUserStoryList(new ArrayList<UserStory>());
        }
        sprint.getSprintPK().setProjectProjectId(sprint.getProject().getProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = sprint.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                sprint.setProject(project);
            }
            List<UserStory> attachedUserStoryList = new ArrayList<UserStory>();
            for (UserStory userStoryListUserStoryToAttach : sprint.getUserStoryList()) {
                userStoryListUserStoryToAttach = em.getReference(userStoryListUserStoryToAttach.getClass(), userStoryListUserStoryToAttach.getStoryId());
                attachedUserStoryList.add(userStoryListUserStoryToAttach);
            }
            sprint.setUserStoryList(attachedUserStoryList);
            em.persist(sprint);
            if (project != null) {
                project.getSprintList().add(sprint);
                project = em.merge(project);
            }
            for (UserStory userStoryListUserStory : sprint.getUserStoryList()) {
                Sprint oldSprintOfUserStoryListUserStory = userStoryListUserStory.getSprint();
                userStoryListUserStory.setSprint(sprint);
                userStoryListUserStory = em.merge(userStoryListUserStory);
                if (oldSprintOfUserStoryListUserStory != null) {
                    oldSprintOfUserStoryListUserStory.getUserStoryList().remove(userStoryListUserStory);
                    oldSprintOfUserStoryListUserStory = em.merge(oldSprintOfUserStoryListUserStory);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSprint(sprint.getSprintPK()) != null) {
                throw new PreexistingEntityException("Sprint " + sprint + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                insertedSprintID = sprint.getSprintPK().getSprintId();
                em.close();
            }
        }
        return insertedSprintID;
    }

    public void edit(Sprint sprint) throws NonexistentEntityException, Exception {
        sprint.getSprintPK().setProjectProjectId(sprint.getProject().getProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sprint persistentSprint = em.find(Sprint.class, sprint.getSprintPK());
            Project projectOld = persistentSprint.getProject();
            Project projectNew = sprint.getProject();
            List<UserStory> userStoryListOld = persistentSprint.getUserStoryList();
            List<UserStory> userStoryListNew = sprint.getUserStoryList();
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                sprint.setProject(projectNew);
            }
            List<UserStory> attachedUserStoryListNew = new ArrayList<UserStory>();
            for (UserStory userStoryListNewUserStoryToAttach : userStoryListNew) {
                userStoryListNewUserStoryToAttach = em.getReference(userStoryListNewUserStoryToAttach.getClass(), userStoryListNewUserStoryToAttach.getStoryId());
                attachedUserStoryListNew.add(userStoryListNewUserStoryToAttach);
            }
            userStoryListNew = attachedUserStoryListNew;
            sprint.setUserStoryList(userStoryListNew);
            sprint = em.merge(sprint);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getSprintList().remove(sprint);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getSprintList().add(sprint);
                projectNew = em.merge(projectNew);
            }
            for (UserStory userStoryListOldUserStory : userStoryListOld) {
                if (!userStoryListNew.contains(userStoryListOldUserStory)) {
                    userStoryListOldUserStory.setSprint(null);
                    userStoryListOldUserStory = em.merge(userStoryListOldUserStory);
                }
            }
            for (UserStory userStoryListNewUserStory : userStoryListNew) {
                if (!userStoryListOld.contains(userStoryListNewUserStory)) {
                    Sprint oldSprintOfUserStoryListNewUserStory = userStoryListNewUserStory.getSprint();
                    userStoryListNewUserStory.setSprint(sprint);
                    userStoryListNewUserStory = em.merge(userStoryListNewUserStory);
                    if (oldSprintOfUserStoryListNewUserStory != null && !oldSprintOfUserStoryListNewUserStory.equals(sprint)) {
                        oldSprintOfUserStoryListNewUserStory.getUserStoryList().remove(userStoryListNewUserStory);
                        oldSprintOfUserStoryListNewUserStory = em.merge(oldSprintOfUserStoryListNewUserStory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SprintPK id = sprint.getSprintPK();
                if (findSprint(id) == null) {
                    throw new NonexistentEntityException("The sprint with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SprintPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sprint sprint;
            try {
                sprint = em.getReference(Sprint.class, id);
                sprint.getSprintPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sprint with id " + id + " no longer exists.", enfe);
            }
            Project project = sprint.getProject();
            if (project != null) {
                project.getSprintList().remove(sprint);
                project = em.merge(project);
            }
            List<UserStory> userStoryList = sprint.getUserStoryList();
            for (UserStory userStoryListUserStory : userStoryList) {
                userStoryListUserStory.setSprint(null);
                userStoryListUserStory = em.merge(userStoryListUserStory);
            }
            em.remove(sprint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sprint> findSprintEntities() {
        return findSprintEntities(true, -1, -1);
    }

    public List<Sprint> findSprintEntities(int maxResults, int firstResult) {
        return findSprintEntities(false, maxResults, firstResult);
    }

    private List<Sprint> findSprintEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sprint.class));
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

    public Sprint findSprint(SprintPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sprint.class, id);
        } finally {
            em.close();
        }
    }

    public int getSprintCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sprint> rt = cq.from(Sprint.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
