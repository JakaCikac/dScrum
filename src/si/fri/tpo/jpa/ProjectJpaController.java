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

/**
 *
 * @author nanorax
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
        if (project.getDailyScrumEntries() == null) {
            project.setDailyScrumEntries(new ArrayList<DailyScrumEntry>());
        }
        if (project.getDiscussions() == null) {
            project.setDiscussions(new ArrayList<Discussion>());
        }
        if (project.getSprints() == null) {
            project.setSprints(new ArrayList<Sprint>());
        }
        if (project.getUserStories() == null) {
            project.setUserStories(new ArrayList<UserStory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team team = project.getTeam();
            if (team != null) {
                team = em.getReference(team.getClass(), team.getTeamId());
                project.setTeam(team);
            }
            List<DailyScrumEntry> attachedDailyScrumEntries = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntriesDailyScrumEntryToAttach : project.getDailyScrumEntries()) {
                dailyScrumEntriesDailyScrumEntryToAttach = em.getReference(dailyScrumEntriesDailyScrumEntryToAttach.getClass(), dailyScrumEntriesDailyScrumEntryToAttach.getId());
                attachedDailyScrumEntries.add(dailyScrumEntriesDailyScrumEntryToAttach);
            }
            project.setDailyScrumEntries(attachedDailyScrumEntries);
            List<Discussion> attachedDiscussions = new ArrayList<Discussion>();
            for (Discussion discussionsDiscussionToAttach : project.getDiscussions()) {
                discussionsDiscussionToAttach = em.getReference(discussionsDiscussionToAttach.getClass(), discussionsDiscussionToAttach.getId());
                attachedDiscussions.add(discussionsDiscussionToAttach);
            }
            project.setDiscussions(attachedDiscussions);
            List<Sprint> attachedSprints = new ArrayList<Sprint>();
            for (Sprint sprintsSprintToAttach : project.getSprints()) {
                sprintsSprintToAttach = em.getReference(sprintsSprintToAttach.getClass(), sprintsSprintToAttach.getId());
                attachedSprints.add(sprintsSprintToAttach);
            }
            project.setSprints(attachedSprints);
            List<UserStory> attachedUserStories = new ArrayList<UserStory>();
            for (UserStory userStoriesUserStoryToAttach : project.getUserStories()) {
                userStoriesUserStoryToAttach = em.getReference(userStoriesUserStoryToAttach.getClass(), userStoriesUserStoryToAttach.getStoryId());
                attachedUserStories.add(userStoriesUserStoryToAttach);
            }
            project.setUserStories(attachedUserStories);
            em.persist(project);
            if (team != null) {
                team.getProjects().add(project);
                team = em.merge(team);
            }
            for (DailyScrumEntry dailyScrumEntriesDailyScrumEntry : project.getDailyScrumEntries()) {
                Project oldProjectOfDailyScrumEntriesDailyScrumEntry = dailyScrumEntriesDailyScrumEntry.getProject();
                dailyScrumEntriesDailyScrumEntry.setProject(project);
                dailyScrumEntriesDailyScrumEntry = em.merge(dailyScrumEntriesDailyScrumEntry);
                if (oldProjectOfDailyScrumEntriesDailyScrumEntry != null) {
                    oldProjectOfDailyScrumEntriesDailyScrumEntry.getDailyScrumEntries().remove(dailyScrumEntriesDailyScrumEntry);
                    oldProjectOfDailyScrumEntriesDailyScrumEntry = em.merge(oldProjectOfDailyScrumEntriesDailyScrumEntry);
                }
            }
            for (Discussion discussionsDiscussion : project.getDiscussions()) {
                Project oldProjectOfDiscussionsDiscussion = discussionsDiscussion.getProject();
                discussionsDiscussion.setProject(project);
                discussionsDiscussion = em.merge(discussionsDiscussion);
                if (oldProjectOfDiscussionsDiscussion != null) {
                    oldProjectOfDiscussionsDiscussion.getDiscussions().remove(discussionsDiscussion);
                    oldProjectOfDiscussionsDiscussion = em.merge(oldProjectOfDiscussionsDiscussion);
                }
            }
            for (Sprint sprintsSprint : project.getSprints()) {
                Project oldProjectOfSprintsSprint = sprintsSprint.getProject();
                sprintsSprint.setProject(project);
                sprintsSprint = em.merge(sprintsSprint);
                if (oldProjectOfSprintsSprint != null) {
                    oldProjectOfSprintsSprint.getSprints().remove(sprintsSprint);
                    oldProjectOfSprintsSprint = em.merge(oldProjectOfSprintsSprint);
                }
            }
            for (UserStory userStoriesUserStory : project.getUserStories()) {
                Project oldProjectOfUserStoriesUserStory = userStoriesUserStory.getProject();
                userStoriesUserStory.setProject(project);
                userStoriesUserStory = em.merge(userStoriesUserStory);
                if (oldProjectOfUserStoriesUserStory != null) {
                    oldProjectOfUserStoriesUserStory.getUserStories().remove(userStoriesUserStory);
                    oldProjectOfUserStoriesUserStory = em.merge(oldProjectOfUserStoriesUserStory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getProjectId());
            Team teamOld = persistentProject.getTeam();
            Team teamNew = project.getTeam();
            List<DailyScrumEntry> dailyScrumEntriesOld = persistentProject.getDailyScrumEntries();
            List<DailyScrumEntry> dailyScrumEntriesNew = project.getDailyScrumEntries();
            List<Discussion> discussionsOld = persistentProject.getDiscussions();
            List<Discussion> discussionsNew = project.getDiscussions();
            List<Sprint> sprintsOld = persistentProject.getSprints();
            List<Sprint> sprintsNew = project.getSprints();
            List<UserStory> userStoriesOld = persistentProject.getUserStories();
            List<UserStory> userStoriesNew = project.getUserStories();
            if (teamNew != null) {
                teamNew = em.getReference(teamNew.getClass(), teamNew.getTeamId());
                project.setTeam(teamNew);
            }
            List<DailyScrumEntry> attachedDailyScrumEntriesNew = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntriesNewDailyScrumEntryToAttach : dailyScrumEntriesNew) {
                dailyScrumEntriesNewDailyScrumEntryToAttach = em.getReference(dailyScrumEntriesNewDailyScrumEntryToAttach.getClass(), dailyScrumEntriesNewDailyScrumEntryToAttach.getId());
                attachedDailyScrumEntriesNew.add(dailyScrumEntriesNewDailyScrumEntryToAttach);
            }
            dailyScrumEntriesNew = attachedDailyScrumEntriesNew;
            project.setDailyScrumEntries(dailyScrumEntriesNew);
            List<Discussion> attachedDiscussionsNew = new ArrayList<Discussion>();
            for (Discussion discussionsNewDiscussionToAttach : discussionsNew) {
                discussionsNewDiscussionToAttach = em.getReference(discussionsNewDiscussionToAttach.getClass(), discussionsNewDiscussionToAttach.getId());
                attachedDiscussionsNew.add(discussionsNewDiscussionToAttach);
            }
            discussionsNew = attachedDiscussionsNew;
            project.setDiscussions(discussionsNew);
            List<Sprint> attachedSprintsNew = new ArrayList<Sprint>();
            for (Sprint sprintsNewSprintToAttach : sprintsNew) {
                sprintsNewSprintToAttach = em.getReference(sprintsNewSprintToAttach.getClass(), sprintsNewSprintToAttach.getId());
                attachedSprintsNew.add(sprintsNewSprintToAttach);
            }
            sprintsNew = attachedSprintsNew;
            project.setSprints(sprintsNew);
            List<UserStory> attachedUserStoriesNew = new ArrayList<UserStory>();
            for (UserStory userStoriesNewUserStoryToAttach : userStoriesNew) {
                userStoriesNewUserStoryToAttach = em.getReference(userStoriesNewUserStoryToAttach.getClass(), userStoriesNewUserStoryToAttach.getStoryId());
                attachedUserStoriesNew.add(userStoriesNewUserStoryToAttach);
            }
            userStoriesNew = attachedUserStoriesNew;
            project.setUserStories(userStoriesNew);
            project = em.merge(project);
            if (teamOld != null && !teamOld.equals(teamNew)) {
                teamOld.getProjects().remove(project);
                teamOld = em.merge(teamOld);
            }
            if (teamNew != null && !teamNew.equals(teamOld)) {
                teamNew.getProjects().add(project);
                teamNew = em.merge(teamNew);
            }
            for (DailyScrumEntry dailyScrumEntriesOldDailyScrumEntry : dailyScrumEntriesOld) {
                if (!dailyScrumEntriesNew.contains(dailyScrumEntriesOldDailyScrumEntry)) {
                    dailyScrumEntriesOldDailyScrumEntry.setProject(null);
                    dailyScrumEntriesOldDailyScrumEntry = em.merge(dailyScrumEntriesOldDailyScrumEntry);
                }
            }
            for (DailyScrumEntry dailyScrumEntriesNewDailyScrumEntry : dailyScrumEntriesNew) {
                if (!dailyScrumEntriesOld.contains(dailyScrumEntriesNewDailyScrumEntry)) {
                    Project oldProjectOfDailyScrumEntriesNewDailyScrumEntry = dailyScrumEntriesNewDailyScrumEntry.getProject();
                    dailyScrumEntriesNewDailyScrumEntry.setProject(project);
                    dailyScrumEntriesNewDailyScrumEntry = em.merge(dailyScrumEntriesNewDailyScrumEntry);
                    if (oldProjectOfDailyScrumEntriesNewDailyScrumEntry != null && !oldProjectOfDailyScrumEntriesNewDailyScrumEntry.equals(project)) {
                        oldProjectOfDailyScrumEntriesNewDailyScrumEntry.getDailyScrumEntries().remove(dailyScrumEntriesNewDailyScrumEntry);
                        oldProjectOfDailyScrumEntriesNewDailyScrumEntry = em.merge(oldProjectOfDailyScrumEntriesNewDailyScrumEntry);
                    }
                }
            }
            for (Discussion discussionsOldDiscussion : discussionsOld) {
                if (!discussionsNew.contains(discussionsOldDiscussion)) {
                    discussionsOldDiscussion.setProject(null);
                    discussionsOldDiscussion = em.merge(discussionsOldDiscussion);
                }
            }
            for (Discussion discussionsNewDiscussion : discussionsNew) {
                if (!discussionsOld.contains(discussionsNewDiscussion)) {
                    Project oldProjectOfDiscussionsNewDiscussion = discussionsNewDiscussion.getProject();
                    discussionsNewDiscussion.setProject(project);
                    discussionsNewDiscussion = em.merge(discussionsNewDiscussion);
                    if (oldProjectOfDiscussionsNewDiscussion != null && !oldProjectOfDiscussionsNewDiscussion.equals(project)) {
                        oldProjectOfDiscussionsNewDiscussion.getDiscussions().remove(discussionsNewDiscussion);
                        oldProjectOfDiscussionsNewDiscussion = em.merge(oldProjectOfDiscussionsNewDiscussion);
                    }
                }
            }
            for (Sprint sprintsOldSprint : sprintsOld) {
                if (!sprintsNew.contains(sprintsOldSprint)) {
                    sprintsOldSprint.setProject(null);
                    sprintsOldSprint = em.merge(sprintsOldSprint);
                }
            }
            for (Sprint sprintsNewSprint : sprintsNew) {
                if (!sprintsOld.contains(sprintsNewSprint)) {
                    Project oldProjectOfSprintsNewSprint = sprintsNewSprint.getProject();
                    sprintsNewSprint.setProject(project);
                    sprintsNewSprint = em.merge(sprintsNewSprint);
                    if (oldProjectOfSprintsNewSprint != null && !oldProjectOfSprintsNewSprint.equals(project)) {
                        oldProjectOfSprintsNewSprint.getSprints().remove(sprintsNewSprint);
                        oldProjectOfSprintsNewSprint = em.merge(oldProjectOfSprintsNewSprint);
                    }
                }
            }
            for (UserStory userStoriesOldUserStory : userStoriesOld) {
                if (!userStoriesNew.contains(userStoriesOldUserStory)) {
                    userStoriesOldUserStory.setProject(null);
                    userStoriesOldUserStory = em.merge(userStoriesOldUserStory);
                }
            }
            for (UserStory userStoriesNewUserStory : userStoriesNew) {
                if (!userStoriesOld.contains(userStoriesNewUserStory)) {
                    Project oldProjectOfUserStoriesNewUserStory = userStoriesNewUserStory.getProject();
                    userStoriesNewUserStory.setProject(project);
                    userStoriesNewUserStory = em.merge(userStoriesNewUserStory);
                    if (oldProjectOfUserStoriesNewUserStory != null && !oldProjectOfUserStoriesNewUserStory.equals(project)) {
                        oldProjectOfUserStoriesNewUserStory.getUserStories().remove(userStoriesNewUserStory);
                        oldProjectOfUserStoriesNewUserStory = em.merge(oldProjectOfUserStoriesNewUserStory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = project.getProjectId();
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

    public void destroy(int id) throws NonexistentEntityException {
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
            Team team = project.getTeam();
            if (team != null) {
                team.getProjects().remove(project);
                team = em.merge(team);
            }
            List<DailyScrumEntry> dailyScrumEntries = project.getDailyScrumEntries();
            for (DailyScrumEntry dailyScrumEntriesDailyScrumEntry : dailyScrumEntries) {
                dailyScrumEntriesDailyScrumEntry.setProject(null);
                dailyScrumEntriesDailyScrumEntry = em.merge(dailyScrumEntriesDailyScrumEntry);
            }
            List<Discussion> discussions = project.getDiscussions();
            for (Discussion discussionsDiscussion : discussions) {
                discussionsDiscussion.setProject(null);
                discussionsDiscussion = em.merge(discussionsDiscussion);
            }
            List<Sprint> sprints = project.getSprints();
            for (Sprint sprintsSprint : sprints) {
                sprintsSprint.setProject(null);
                sprintsSprint = em.merge(sprintsSprint);
            }
            List<UserStory> userStories = project.getUserStories();
            for (UserStory userStoriesUserStory : userStories) {
                userStoriesUserStory.setProject(null);
                userStoriesUserStory = em.merge(userStoriesUserStory);
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

    public Project findProject(int id) {
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
