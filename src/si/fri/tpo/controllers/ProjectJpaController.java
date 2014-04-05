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
import si.fri.tpo.jpa.Team;
import si.fri.tpo.jpa.Sprint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.UserStory;
import si.fri.tpo.jpa.Discussion;
import si.fri.tpo.jpa.DailyScrumEntry;
import si.fri.tpo.jpa.Project;

/**
 *
 * @author Administrator
 */
public class ProjectJpaController implements Serializable {

    public ProjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) {
        if (project.getSprintCollection() == null) {
            project.setSprintCollection(new ArrayList<Sprint>());
        }
        if (project.getUserStoryCollection() == null) {
            project.setUserStoryCollection(new ArrayList<UserStory>());
        }
        if (project.getDiscussionCollection() == null) {
            project.setDiscussionCollection(new ArrayList<Discussion>());
        }
        if (project.getDailyScrumEntryCollection() == null) {
            project.setDailyScrumEntryCollection(new ArrayList<DailyScrumEntry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team TEAMteamid = project.getTEAMteamid();
            if (TEAMteamid != null) {
                TEAMteamid = em.getReference(TEAMteamid.getClass(), TEAMteamid.getTeamId());
                project.setTEAMteamid(TEAMteamid);
            }
            Collection<Sprint> attachedSprintCollection = new ArrayList<Sprint>();
            for (Sprint sprintCollectionSprintToAttach : project.getSprintCollection()) {
                sprintCollectionSprintToAttach = em.getReference(sprintCollectionSprintToAttach.getClass(), sprintCollectionSprintToAttach.getSprintPK());
                attachedSprintCollection.add(sprintCollectionSprintToAttach);
            }
            project.setSprintCollection(attachedSprintCollection);
            Collection<UserStory> attachedUserStoryCollection = new ArrayList<UserStory>();
            for (UserStory userStoryCollectionUserStoryToAttach : project.getUserStoryCollection()) {
                userStoryCollectionUserStoryToAttach = em.getReference(userStoryCollectionUserStoryToAttach.getClass(), userStoryCollectionUserStoryToAttach.getStoryId());
                attachedUserStoryCollection.add(userStoryCollectionUserStoryToAttach);
            }
            project.setUserStoryCollection(attachedUserStoryCollection);
            Collection<Discussion> attachedDiscussionCollection = new ArrayList<Discussion>();
            for (Discussion discussionCollectionDiscussionToAttach : project.getDiscussionCollection()) {
                discussionCollectionDiscussionToAttach = em.getReference(discussionCollectionDiscussionToAttach.getClass(), discussionCollectionDiscussionToAttach.getDiscussionPK());
                attachedDiscussionCollection.add(discussionCollectionDiscussionToAttach);
            }
            project.setDiscussionCollection(attachedDiscussionCollection);
            Collection<DailyScrumEntry> attachedDailyScrumEntryCollection = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryCollectionDailyScrumEntryToAttach : project.getDailyScrumEntryCollection()) {
                dailyScrumEntryCollectionDailyScrumEntryToAttach = em.getReference(dailyScrumEntryCollectionDailyScrumEntryToAttach.getClass(), dailyScrumEntryCollectionDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryCollection.add(dailyScrumEntryCollectionDailyScrumEntryToAttach);
            }
            project.setDailyScrumEntryCollection(attachedDailyScrumEntryCollection);
            em.persist(project);
            if (TEAMteamid != null) {
                TEAMteamid.getProjectCollection().add(project);
                TEAMteamid = em.merge(TEAMteamid);
            }
            for (Sprint sprintCollectionSprint : project.getSprintCollection()) {
                Project oldProjectOfSprintCollectionSprint = sprintCollectionSprint.getProject();
                sprintCollectionSprint.setProject(project);
                sprintCollectionSprint = em.merge(sprintCollectionSprint);
                if (oldProjectOfSprintCollectionSprint != null) {
                    oldProjectOfSprintCollectionSprint.getSprintCollection().remove(sprintCollectionSprint);
                    oldProjectOfSprintCollectionSprint = em.merge(oldProjectOfSprintCollectionSprint);
                }
            }
            for (UserStory userStoryCollectionUserStory : project.getUserStoryCollection()) {
                Project oldPROJECTprojectidOfUserStoryCollectionUserStory = userStoryCollectionUserStory.getPROJECTprojectid();
                userStoryCollectionUserStory.setPROJECTprojectid(project);
                userStoryCollectionUserStory = em.merge(userStoryCollectionUserStory);
                if (oldPROJECTprojectidOfUserStoryCollectionUserStory != null) {
                    oldPROJECTprojectidOfUserStoryCollectionUserStory.getUserStoryCollection().remove(userStoryCollectionUserStory);
                    oldPROJECTprojectidOfUserStoryCollectionUserStory = em.merge(oldPROJECTprojectidOfUserStoryCollectionUserStory);
                }
            }
            for (Discussion discussionCollectionDiscussion : project.getDiscussionCollection()) {
                Project oldProjectOfDiscussionCollectionDiscussion = discussionCollectionDiscussion.getProject();
                discussionCollectionDiscussion.setProject(project);
                discussionCollectionDiscussion = em.merge(discussionCollectionDiscussion);
                if (oldProjectOfDiscussionCollectionDiscussion != null) {
                    oldProjectOfDiscussionCollectionDiscussion.getDiscussionCollection().remove(discussionCollectionDiscussion);
                    oldProjectOfDiscussionCollectionDiscussion = em.merge(oldProjectOfDiscussionCollectionDiscussion);
                }
            }
            for (DailyScrumEntry dailyScrumEntryCollectionDailyScrumEntry : project.getDailyScrumEntryCollection()) {
                Project oldProjectOfDailyScrumEntryCollectionDailyScrumEntry = dailyScrumEntryCollectionDailyScrumEntry.getProject();
                dailyScrumEntryCollectionDailyScrumEntry.setProject(project);
                dailyScrumEntryCollectionDailyScrumEntry = em.merge(dailyScrumEntryCollectionDailyScrumEntry);
                if (oldProjectOfDailyScrumEntryCollectionDailyScrumEntry != null) {
                    oldProjectOfDailyScrumEntryCollectionDailyScrumEntry.getDailyScrumEntryCollection().remove(dailyScrumEntryCollectionDailyScrumEntry);
                    oldProjectOfDailyScrumEntryCollectionDailyScrumEntry = em.merge(oldProjectOfDailyScrumEntryCollectionDailyScrumEntry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getProjectId());
            Team TEAMteamidOld = persistentProject.getTEAMteamid();
            Team TEAMteamidNew = project.getTEAMteamid();
            Collection<Sprint> sprintCollectionOld = persistentProject.getSprintCollection();
            Collection<Sprint> sprintCollectionNew = project.getSprintCollection();
            Collection<UserStory> userStoryCollectionOld = persistentProject.getUserStoryCollection();
            Collection<UserStory> userStoryCollectionNew = project.getUserStoryCollection();
            Collection<Discussion> discussionCollectionOld = persistentProject.getDiscussionCollection();
            Collection<Discussion> discussionCollectionNew = project.getDiscussionCollection();
            Collection<DailyScrumEntry> dailyScrumEntryCollectionOld = persistentProject.getDailyScrumEntryCollection();
            Collection<DailyScrumEntry> dailyScrumEntryCollectionNew = project.getDailyScrumEntryCollection();
            List<String> illegalOrphanMessages = null;
            for (Sprint sprintCollectionOldSprint : sprintCollectionOld) {
                if (!sprintCollectionNew.contains(sprintCollectionOldSprint)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sprint " + sprintCollectionOldSprint + " since its project field is not nullable.");
                }
            }
            for (UserStory userStoryCollectionOldUserStory : userStoryCollectionOld) {
                if (!userStoryCollectionNew.contains(userStoryCollectionOldUserStory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserStory " + userStoryCollectionOldUserStory + " since its PROJECTprojectid field is not nullable.");
                }
            }
            for (Discussion discussionCollectionOldDiscussion : discussionCollectionOld) {
                if (!discussionCollectionNew.contains(discussionCollectionOldDiscussion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discussion " + discussionCollectionOldDiscussion + " since its project field is not nullable.");
                }
            }
            for (DailyScrumEntry dailyScrumEntryCollectionOldDailyScrumEntry : dailyScrumEntryCollectionOld) {
                if (!dailyScrumEntryCollectionNew.contains(dailyScrumEntryCollectionOldDailyScrumEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DailyScrumEntry " + dailyScrumEntryCollectionOldDailyScrumEntry + " since its project field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (TEAMteamidNew != null) {
                TEAMteamidNew = em.getReference(TEAMteamidNew.getClass(), TEAMteamidNew.getTeamId());
                project.setTEAMteamid(TEAMteamidNew);
            }
            Collection<Sprint> attachedSprintCollectionNew = new ArrayList<Sprint>();
            for (Sprint sprintCollectionNewSprintToAttach : sprintCollectionNew) {
                sprintCollectionNewSprintToAttach = em.getReference(sprintCollectionNewSprintToAttach.getClass(), sprintCollectionNewSprintToAttach.getSprintPK());
                attachedSprintCollectionNew.add(sprintCollectionNewSprintToAttach);
            }
            sprintCollectionNew = attachedSprintCollectionNew;
            project.setSprintCollection(sprintCollectionNew);
            Collection<UserStory> attachedUserStoryCollectionNew = new ArrayList<UserStory>();
            for (UserStory userStoryCollectionNewUserStoryToAttach : userStoryCollectionNew) {
                userStoryCollectionNewUserStoryToAttach = em.getReference(userStoryCollectionNewUserStoryToAttach.getClass(), userStoryCollectionNewUserStoryToAttach.getStoryId());
                attachedUserStoryCollectionNew.add(userStoryCollectionNewUserStoryToAttach);
            }
            userStoryCollectionNew = attachedUserStoryCollectionNew;
            project.setUserStoryCollection(userStoryCollectionNew);
            Collection<Discussion> attachedDiscussionCollectionNew = new ArrayList<Discussion>();
            for (Discussion discussionCollectionNewDiscussionToAttach : discussionCollectionNew) {
                discussionCollectionNewDiscussionToAttach = em.getReference(discussionCollectionNewDiscussionToAttach.getClass(), discussionCollectionNewDiscussionToAttach.getDiscussionPK());
                attachedDiscussionCollectionNew.add(discussionCollectionNewDiscussionToAttach);
            }
            discussionCollectionNew = attachedDiscussionCollectionNew;
            project.setDiscussionCollection(discussionCollectionNew);
            Collection<DailyScrumEntry> attachedDailyScrumEntryCollectionNew = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryCollectionNewDailyScrumEntryToAttach : dailyScrumEntryCollectionNew) {
                dailyScrumEntryCollectionNewDailyScrumEntryToAttach = em.getReference(dailyScrumEntryCollectionNewDailyScrumEntryToAttach.getClass(), dailyScrumEntryCollectionNewDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryCollectionNew.add(dailyScrumEntryCollectionNewDailyScrumEntryToAttach);
            }
            dailyScrumEntryCollectionNew = attachedDailyScrumEntryCollectionNew;
            project.setDailyScrumEntryCollection(dailyScrumEntryCollectionNew);
            project = em.merge(project);
            if (TEAMteamidOld != null && !TEAMteamidOld.equals(TEAMteamidNew)) {
                TEAMteamidOld.getProjectCollection().remove(project);
                TEAMteamidOld = em.merge(TEAMteamidOld);
            }
            if (TEAMteamidNew != null && !TEAMteamidNew.equals(TEAMteamidOld)) {
                TEAMteamidNew.getProjectCollection().add(project);
                TEAMteamidNew = em.merge(TEAMteamidNew);
            }
            for (Sprint sprintCollectionNewSprint : sprintCollectionNew) {
                if (!sprintCollectionOld.contains(sprintCollectionNewSprint)) {
                    Project oldProjectOfSprintCollectionNewSprint = sprintCollectionNewSprint.getProject();
                    sprintCollectionNewSprint.setProject(project);
                    sprintCollectionNewSprint = em.merge(sprintCollectionNewSprint);
                    if (oldProjectOfSprintCollectionNewSprint != null && !oldProjectOfSprintCollectionNewSprint.equals(project)) {
                        oldProjectOfSprintCollectionNewSprint.getSprintCollection().remove(sprintCollectionNewSprint);
                        oldProjectOfSprintCollectionNewSprint = em.merge(oldProjectOfSprintCollectionNewSprint);
                    }
                }
            }
            for (UserStory userStoryCollectionNewUserStory : userStoryCollectionNew) {
                if (!userStoryCollectionOld.contains(userStoryCollectionNewUserStory)) {
                    Project oldPROJECTprojectidOfUserStoryCollectionNewUserStory = userStoryCollectionNewUserStory.getPROJECTprojectid();
                    userStoryCollectionNewUserStory.setPROJECTprojectid(project);
                    userStoryCollectionNewUserStory = em.merge(userStoryCollectionNewUserStory);
                    if (oldPROJECTprojectidOfUserStoryCollectionNewUserStory != null && !oldPROJECTprojectidOfUserStoryCollectionNewUserStory.equals(project)) {
                        oldPROJECTprojectidOfUserStoryCollectionNewUserStory.getUserStoryCollection().remove(userStoryCollectionNewUserStory);
                        oldPROJECTprojectidOfUserStoryCollectionNewUserStory = em.merge(oldPROJECTprojectidOfUserStoryCollectionNewUserStory);
                    }
                }
            }
            for (Discussion discussionCollectionNewDiscussion : discussionCollectionNew) {
                if (!discussionCollectionOld.contains(discussionCollectionNewDiscussion)) {
                    Project oldProjectOfDiscussionCollectionNewDiscussion = discussionCollectionNewDiscussion.getProject();
                    discussionCollectionNewDiscussion.setProject(project);
                    discussionCollectionNewDiscussion = em.merge(discussionCollectionNewDiscussion);
                    if (oldProjectOfDiscussionCollectionNewDiscussion != null && !oldProjectOfDiscussionCollectionNewDiscussion.equals(project)) {
                        oldProjectOfDiscussionCollectionNewDiscussion.getDiscussionCollection().remove(discussionCollectionNewDiscussion);
                        oldProjectOfDiscussionCollectionNewDiscussion = em.merge(oldProjectOfDiscussionCollectionNewDiscussion);
                    }
                }
            }
            for (DailyScrumEntry dailyScrumEntryCollectionNewDailyScrumEntry : dailyScrumEntryCollectionNew) {
                if (!dailyScrumEntryCollectionOld.contains(dailyScrumEntryCollectionNewDailyScrumEntry)) {
                    Project oldProjectOfDailyScrumEntryCollectionNewDailyScrumEntry = dailyScrumEntryCollectionNewDailyScrumEntry.getProject();
                    dailyScrumEntryCollectionNewDailyScrumEntry.setProject(project);
                    dailyScrumEntryCollectionNewDailyScrumEntry = em.merge(dailyScrumEntryCollectionNewDailyScrumEntry);
                    if (oldProjectOfDailyScrumEntryCollectionNewDailyScrumEntry != null && !oldProjectOfDailyScrumEntryCollectionNewDailyScrumEntry.equals(project)) {
                        oldProjectOfDailyScrumEntryCollectionNewDailyScrumEntry.getDailyScrumEntryCollection().remove(dailyScrumEntryCollectionNewDailyScrumEntry);
                        oldProjectOfDailyScrumEntryCollectionNewDailyScrumEntry = em.merge(oldProjectOfDailyScrumEntryCollectionNewDailyScrumEntry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = project.getProjectId();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getProjectId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sprint> sprintCollectionOrphanCheck = project.getSprintCollection();
            for (Sprint sprintCollectionOrphanCheckSprint : sprintCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Sprint " + sprintCollectionOrphanCheckSprint + " in its sprintCollection field has a non-nullable project field.");
            }
            Collection<UserStory> userStoryCollectionOrphanCheck = project.getUserStoryCollection();
            for (UserStory userStoryCollectionOrphanCheckUserStory : userStoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the UserStory " + userStoryCollectionOrphanCheckUserStory + " in its userStoryCollection field has a non-nullable PROJECTprojectid field.");
            }
            Collection<Discussion> discussionCollectionOrphanCheck = project.getDiscussionCollection();
            for (Discussion discussionCollectionOrphanCheckDiscussion : discussionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Discussion " + discussionCollectionOrphanCheckDiscussion + " in its discussionCollection field has a non-nullable project field.");
            }
            Collection<DailyScrumEntry> dailyScrumEntryCollectionOrphanCheck = project.getDailyScrumEntryCollection();
            for (DailyScrumEntry dailyScrumEntryCollectionOrphanCheckDailyScrumEntry : dailyScrumEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the DailyScrumEntry " + dailyScrumEntryCollectionOrphanCheckDailyScrumEntry + " in its dailyScrumEntryCollection field has a non-nullable project field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Team TEAMteamid = project.getTEAMteamid();
            if (TEAMteamid != null) {
                TEAMteamid.getProjectCollection().remove(project);
                TEAMteamid = em.merge(TEAMteamid);
            }
            em.remove(project);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
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

    public Project findProject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
