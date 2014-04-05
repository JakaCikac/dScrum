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
import si.fri.tpo.gwt.server.jpa.Team;
import si.fri.tpo.gwt.server.jpa.Discussion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.jpa.DailyScrumEntry;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Sprint;
import si.fri.tpo.gwt.server.jpa.UserStory;

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
        if (project.getDiscussionList() == null) {
            project.setDiscussionList(new ArrayList<Discussion>());
        }
        if (project.getDailyScrumEntryList() == null) {
            project.setDailyScrumEntryList(new ArrayList<DailyScrumEntry>());
        }
        if (project.getSprintList() == null) {
            project.setSprintList(new ArrayList<Sprint>());
        }
        if (project.getUserStoryList() == null) {
            project.setUserStoryList(new ArrayList<UserStory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team teamTeamId = project.getTeamTeamId();
            if (teamTeamId != null) {
                teamTeamId = em.getReference(teamTeamId.getClass(), teamTeamId.getTeamId());
                project.setTeamTeamId(teamTeamId);
            }
            List<Discussion> attachedDiscussionList = new ArrayList<Discussion>();
            for (Discussion discussionListDiscussionToAttach : project.getDiscussionList()) {
                discussionListDiscussionToAttach = em.getReference(discussionListDiscussionToAttach.getClass(), discussionListDiscussionToAttach.getDiscussionPK());
                attachedDiscussionList.add(discussionListDiscussionToAttach);
            }
            project.setDiscussionList(attachedDiscussionList);
            List<DailyScrumEntry> attachedDailyScrumEntryList = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryListDailyScrumEntryToAttach : project.getDailyScrumEntryList()) {
                dailyScrumEntryListDailyScrumEntryToAttach = em.getReference(dailyScrumEntryListDailyScrumEntryToAttach.getClass(), dailyScrumEntryListDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryList.add(dailyScrumEntryListDailyScrumEntryToAttach);
            }
            project.setDailyScrumEntryList(attachedDailyScrumEntryList);
            List<Sprint> attachedSprintList = new ArrayList<Sprint>();
            for (Sprint sprintListSprintToAttach : project.getSprintList()) {
                sprintListSprintToAttach = em.getReference(sprintListSprintToAttach.getClass(), sprintListSprintToAttach.getSprintPK());
                attachedSprintList.add(sprintListSprintToAttach);
            }
            project.setSprintList(attachedSprintList);
            List<UserStory> attachedUserStoryList = new ArrayList<UserStory>();
            for (UserStory userStoryListUserStoryToAttach : project.getUserStoryList()) {
                userStoryListUserStoryToAttach = em.getReference(userStoryListUserStoryToAttach.getClass(), userStoryListUserStoryToAttach.getStoryId());
                attachedUserStoryList.add(userStoryListUserStoryToAttach);
            }
            project.setUserStoryList(attachedUserStoryList);
            em.persist(project);
            if (teamTeamId != null) {
                teamTeamId.getProjectList().add(project);
                teamTeamId = em.merge(teamTeamId);
            }
            for (Discussion discussionListDiscussion : project.getDiscussionList()) {
                Project oldProjectOfDiscussionListDiscussion = discussionListDiscussion.getProject();
                discussionListDiscussion.setProject(project);
                discussionListDiscussion = em.merge(discussionListDiscussion);
                if (oldProjectOfDiscussionListDiscussion != null) {
                    oldProjectOfDiscussionListDiscussion.getDiscussionList().remove(discussionListDiscussion);
                    oldProjectOfDiscussionListDiscussion = em.merge(oldProjectOfDiscussionListDiscussion);
                }
            }
            for (DailyScrumEntry dailyScrumEntryListDailyScrumEntry : project.getDailyScrumEntryList()) {
                Project oldProjectOfDailyScrumEntryListDailyScrumEntry = dailyScrumEntryListDailyScrumEntry.getProject();
                dailyScrumEntryListDailyScrumEntry.setProject(project);
                dailyScrumEntryListDailyScrumEntry = em.merge(dailyScrumEntryListDailyScrumEntry);
                if (oldProjectOfDailyScrumEntryListDailyScrumEntry != null) {
                    oldProjectOfDailyScrumEntryListDailyScrumEntry.getDailyScrumEntryList().remove(dailyScrumEntryListDailyScrumEntry);
                    oldProjectOfDailyScrumEntryListDailyScrumEntry = em.merge(oldProjectOfDailyScrumEntryListDailyScrumEntry);
                }
            }
            for (Sprint sprintListSprint : project.getSprintList()) {
                Project oldProjectOfSprintListSprint = sprintListSprint.getProject();
                sprintListSprint.setProject(project);
                sprintListSprint = em.merge(sprintListSprint);
                if (oldProjectOfSprintListSprint != null) {
                    oldProjectOfSprintListSprint.getSprintList().remove(sprintListSprint);
                    oldProjectOfSprintListSprint = em.merge(oldProjectOfSprintListSprint);
                }
            }
            for (UserStory userStoryListUserStory : project.getUserStoryList()) {
                Project oldProjectProjectIdOfUserStoryListUserStory = userStoryListUserStory.getProjectProjectId();
                userStoryListUserStory.setProjectProjectId(project);
                userStoryListUserStory = em.merge(userStoryListUserStory);
                if (oldProjectProjectIdOfUserStoryListUserStory != null) {
                    oldProjectProjectIdOfUserStoryListUserStory.getUserStoryList().remove(userStoryListUserStory);
                    oldProjectProjectIdOfUserStoryListUserStory = em.merge(oldProjectProjectIdOfUserStoryListUserStory);
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
            Team teamTeamIdOld = persistentProject.getTeamTeamId();
            Team teamTeamIdNew = project.getTeamTeamId();
            List<Discussion> discussionListOld = persistentProject.getDiscussionList();
            List<Discussion> discussionListNew = project.getDiscussionList();
            List<DailyScrumEntry> dailyScrumEntryListOld = persistentProject.getDailyScrumEntryList();
            List<DailyScrumEntry> dailyScrumEntryListNew = project.getDailyScrumEntryList();
            List<Sprint> sprintListOld = persistentProject.getSprintList();
            List<Sprint> sprintListNew = project.getSprintList();
            List<UserStory> userStoryListOld = persistentProject.getUserStoryList();
            List<UserStory> userStoryListNew = project.getUserStoryList();
            List<String> illegalOrphanMessages = null;
            for (Discussion discussionListOldDiscussion : discussionListOld) {
                if (!discussionListNew.contains(discussionListOldDiscussion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discussion " + discussionListOldDiscussion + " since its project field is not nullable.");
                }
            }
            for (DailyScrumEntry dailyScrumEntryListOldDailyScrumEntry : dailyScrumEntryListOld) {
                if (!dailyScrumEntryListNew.contains(dailyScrumEntryListOldDailyScrumEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DailyScrumEntry " + dailyScrumEntryListOldDailyScrumEntry + " since its project field is not nullable.");
                }
            }
            for (Sprint sprintListOldSprint : sprintListOld) {
                if (!sprintListNew.contains(sprintListOldSprint)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sprint " + sprintListOldSprint + " since its project field is not nullable.");
                }
            }
            for (UserStory userStoryListOldUserStory : userStoryListOld) {
                if (!userStoryListNew.contains(userStoryListOldUserStory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserStory " + userStoryListOldUserStory + " since its projectProjectId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (teamTeamIdNew != null) {
                teamTeamIdNew = em.getReference(teamTeamIdNew.getClass(), teamTeamIdNew.getTeamId());
                project.setTeamTeamId(teamTeamIdNew);
            }
            List<Discussion> attachedDiscussionListNew = new ArrayList<Discussion>();
            for (Discussion discussionListNewDiscussionToAttach : discussionListNew) {
                discussionListNewDiscussionToAttach = em.getReference(discussionListNewDiscussionToAttach.getClass(), discussionListNewDiscussionToAttach.getDiscussionPK());
                attachedDiscussionListNew.add(discussionListNewDiscussionToAttach);
            }
            discussionListNew = attachedDiscussionListNew;
            project.setDiscussionList(discussionListNew);
            List<DailyScrumEntry> attachedDailyScrumEntryListNew = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryListNewDailyScrumEntryToAttach : dailyScrumEntryListNew) {
                dailyScrumEntryListNewDailyScrumEntryToAttach = em.getReference(dailyScrumEntryListNewDailyScrumEntryToAttach.getClass(), dailyScrumEntryListNewDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryListNew.add(dailyScrumEntryListNewDailyScrumEntryToAttach);
            }
            dailyScrumEntryListNew = attachedDailyScrumEntryListNew;
            project.setDailyScrumEntryList(dailyScrumEntryListNew);
            List<Sprint> attachedSprintListNew = new ArrayList<Sprint>();
            for (Sprint sprintListNewSprintToAttach : sprintListNew) {
                sprintListNewSprintToAttach = em.getReference(sprintListNewSprintToAttach.getClass(), sprintListNewSprintToAttach.getSprintPK());
                attachedSprintListNew.add(sprintListNewSprintToAttach);
            }
            sprintListNew = attachedSprintListNew;
            project.setSprintList(sprintListNew);
            List<UserStory> attachedUserStoryListNew = new ArrayList<UserStory>();
            for (UserStory userStoryListNewUserStoryToAttach : userStoryListNew) {
                userStoryListNewUserStoryToAttach = em.getReference(userStoryListNewUserStoryToAttach.getClass(), userStoryListNewUserStoryToAttach.getStoryId());
                attachedUserStoryListNew.add(userStoryListNewUserStoryToAttach);
            }
            userStoryListNew = attachedUserStoryListNew;
            project.setUserStoryList(userStoryListNew);
            project = em.merge(project);
            if (teamTeamIdOld != null && !teamTeamIdOld.equals(teamTeamIdNew)) {
                teamTeamIdOld.getProjectList().remove(project);
                teamTeamIdOld = em.merge(teamTeamIdOld);
            }
            if (teamTeamIdNew != null && !teamTeamIdNew.equals(teamTeamIdOld)) {
                teamTeamIdNew.getProjectList().add(project);
                teamTeamIdNew = em.merge(teamTeamIdNew);
            }
            for (Discussion discussionListNewDiscussion : discussionListNew) {
                if (!discussionListOld.contains(discussionListNewDiscussion)) {
                    Project oldProjectOfDiscussionListNewDiscussion = discussionListNewDiscussion.getProject();
                    discussionListNewDiscussion.setProject(project);
                    discussionListNewDiscussion = em.merge(discussionListNewDiscussion);
                    if (oldProjectOfDiscussionListNewDiscussion != null && !oldProjectOfDiscussionListNewDiscussion.equals(project)) {
                        oldProjectOfDiscussionListNewDiscussion.getDiscussionList().remove(discussionListNewDiscussion);
                        oldProjectOfDiscussionListNewDiscussion = em.merge(oldProjectOfDiscussionListNewDiscussion);
                    }
                }
            }
            for (DailyScrumEntry dailyScrumEntryListNewDailyScrumEntry : dailyScrumEntryListNew) {
                if (!dailyScrumEntryListOld.contains(dailyScrumEntryListNewDailyScrumEntry)) {
                    Project oldProjectOfDailyScrumEntryListNewDailyScrumEntry = dailyScrumEntryListNewDailyScrumEntry.getProject();
                    dailyScrumEntryListNewDailyScrumEntry.setProject(project);
                    dailyScrumEntryListNewDailyScrumEntry = em.merge(dailyScrumEntryListNewDailyScrumEntry);
                    if (oldProjectOfDailyScrumEntryListNewDailyScrumEntry != null && !oldProjectOfDailyScrumEntryListNewDailyScrumEntry.equals(project)) {
                        oldProjectOfDailyScrumEntryListNewDailyScrumEntry.getDailyScrumEntryList().remove(dailyScrumEntryListNewDailyScrumEntry);
                        oldProjectOfDailyScrumEntryListNewDailyScrumEntry = em.merge(oldProjectOfDailyScrumEntryListNewDailyScrumEntry);
                    }
                }
            }
            for (Sprint sprintListNewSprint : sprintListNew) {
                if (!sprintListOld.contains(sprintListNewSprint)) {
                    Project oldProjectOfSprintListNewSprint = sprintListNewSprint.getProject();
                    sprintListNewSprint.setProject(project);
                    sprintListNewSprint = em.merge(sprintListNewSprint);
                    if (oldProjectOfSprintListNewSprint != null && !oldProjectOfSprintListNewSprint.equals(project)) {
                        oldProjectOfSprintListNewSprint.getSprintList().remove(sprintListNewSprint);
                        oldProjectOfSprintListNewSprint = em.merge(oldProjectOfSprintListNewSprint);
                    }
                }
            }
            for (UserStory userStoryListNewUserStory : userStoryListNew) {
                if (!userStoryListOld.contains(userStoryListNewUserStory)) {
                    Project oldProjectProjectIdOfUserStoryListNewUserStory = userStoryListNewUserStory.getProjectProjectId();
                    userStoryListNewUserStory.setProjectProjectId(project);
                    userStoryListNewUserStory = em.merge(userStoryListNewUserStory);
                    if (oldProjectProjectIdOfUserStoryListNewUserStory != null && !oldProjectProjectIdOfUserStoryListNewUserStory.equals(project)) {
                        oldProjectProjectIdOfUserStoryListNewUserStory.getUserStoryList().remove(userStoryListNewUserStory);
                        oldProjectProjectIdOfUserStoryListNewUserStory = em.merge(oldProjectProjectIdOfUserStoryListNewUserStory);
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
            List<Discussion> discussionListOrphanCheck = project.getDiscussionList();
            for (Discussion discussionListOrphanCheckDiscussion : discussionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Discussion " + discussionListOrphanCheckDiscussion + " in its discussionList field has a non-nullable project field.");
            }
            List<DailyScrumEntry> dailyScrumEntryListOrphanCheck = project.getDailyScrumEntryList();
            for (DailyScrumEntry dailyScrumEntryListOrphanCheckDailyScrumEntry : dailyScrumEntryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the DailyScrumEntry " + dailyScrumEntryListOrphanCheckDailyScrumEntry + " in its dailyScrumEntryList field has a non-nullable project field.");
            }
            List<Sprint> sprintListOrphanCheck = project.getSprintList();
            for (Sprint sprintListOrphanCheckSprint : sprintListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Sprint " + sprintListOrphanCheckSprint + " in its sprintList field has a non-nullable project field.");
            }
            List<UserStory> userStoryListOrphanCheck = project.getUserStoryList();
            for (UserStory userStoryListOrphanCheckUserStory : userStoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the UserStory " + userStoryListOrphanCheckUserStory + " in its userStoryList field has a non-nullable projectProjectId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Team teamTeamId = project.getTeamTeamId();
            if (teamTeamId != null) {
                teamTeamId.getProjectList().remove(project);
                teamTeamId = em.merge(teamTeamId);
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
