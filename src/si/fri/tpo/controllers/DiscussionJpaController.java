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
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.Comment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.jpa.Discussion;
import si.fri.tpo.jpa.DiscussionPK;

/**
 *
 * @author Administrator
 */
public class DiscussionJpaController implements Serializable {

    public DiscussionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Discussion discussion) throws PreexistingEntityException, Exception {
        if (discussion.getDiscussionPK() == null) {
            discussion.setDiscussionPK(new DiscussionPK());
        }
        if (discussion.getCommentCollection() == null) {
            discussion.setCommentCollection(new ArrayList<Comment>());
        }
        discussion.getDiscussionPK().setPROJECTprojectid(discussion.getProject().getProjectId());
        discussion.getDiscussionPK().setUSERuserid(discussion.getUser().getUserId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = discussion.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                discussion.setProject(project);
            }
            User user = discussion.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                discussion.setUser(user);
            }
            Collection<Comment> attachedCommentCollection = new ArrayList<Comment>();
            for (Comment commentCollectionCommentToAttach : discussion.getCommentCollection()) {
                commentCollectionCommentToAttach = em.getReference(commentCollectionCommentToAttach.getClass(), commentCollectionCommentToAttach.getCommentPK());
                attachedCommentCollection.add(commentCollectionCommentToAttach);
            }
            discussion.setCommentCollection(attachedCommentCollection);
            em.persist(discussion);
            if (project != null) {
                project.getDiscussionCollection().add(discussion);
                project = em.merge(project);
            }
            if (user != null) {
                user.getDiscussionCollection().add(discussion);
                user = em.merge(user);
            }
            for (Comment commentCollectionComment : discussion.getCommentCollection()) {
                Discussion oldDiscussionOfCommentCollectionComment = commentCollectionComment.getDiscussion();
                commentCollectionComment.setDiscussion(discussion);
                commentCollectionComment = em.merge(commentCollectionComment);
                if (oldDiscussionOfCommentCollectionComment != null) {
                    oldDiscussionOfCommentCollectionComment.getCommentCollection().remove(commentCollectionComment);
                    oldDiscussionOfCommentCollectionComment = em.merge(oldDiscussionOfCommentCollectionComment);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDiscussion(discussion.getDiscussionPK()) != null) {
                throw new PreexistingEntityException("Discussion " + discussion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discussion discussion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        discussion.getDiscussionPK().setPROJECTprojectid(discussion.getProject().getProjectId());
        discussion.getDiscussionPK().setUSERuserid(discussion.getUser().getUserId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion persistentDiscussion = em.find(Discussion.class, discussion.getDiscussionPK());
            Project projectOld = persistentDiscussion.getProject();
            Project projectNew = discussion.getProject();
            User userOld = persistentDiscussion.getUser();
            User userNew = discussion.getUser();
            Collection<Comment> commentCollectionOld = persistentDiscussion.getCommentCollection();
            Collection<Comment> commentCollectionNew = discussion.getCommentCollection();
            List<String> illegalOrphanMessages = null;
            for (Comment commentCollectionOldComment : commentCollectionOld) {
                if (!commentCollectionNew.contains(commentCollectionOldComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comment " + commentCollectionOldComment + " since its discussion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                discussion.setProject(projectNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                discussion.setUser(userNew);
            }
            Collection<Comment> attachedCommentCollectionNew = new ArrayList<Comment>();
            for (Comment commentCollectionNewCommentToAttach : commentCollectionNew) {
                commentCollectionNewCommentToAttach = em.getReference(commentCollectionNewCommentToAttach.getClass(), commentCollectionNewCommentToAttach.getCommentPK());
                attachedCommentCollectionNew.add(commentCollectionNewCommentToAttach);
            }
            commentCollectionNew = attachedCommentCollectionNew;
            discussion.setCommentCollection(commentCollectionNew);
            discussion = em.merge(discussion);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getDiscussionCollection().remove(discussion);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getDiscussionCollection().add(discussion);
                projectNew = em.merge(projectNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDiscussionCollection().remove(discussion);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDiscussionCollection().add(discussion);
                userNew = em.merge(userNew);
            }
            for (Comment commentCollectionNewComment : commentCollectionNew) {
                if (!commentCollectionOld.contains(commentCollectionNewComment)) {
                    Discussion oldDiscussionOfCommentCollectionNewComment = commentCollectionNewComment.getDiscussion();
                    commentCollectionNewComment.setDiscussion(discussion);
                    commentCollectionNewComment = em.merge(commentCollectionNewComment);
                    if (oldDiscussionOfCommentCollectionNewComment != null && !oldDiscussionOfCommentCollectionNewComment.equals(discussion)) {
                        oldDiscussionOfCommentCollectionNewComment.getCommentCollection().remove(commentCollectionNewComment);
                        oldDiscussionOfCommentCollectionNewComment = em.merge(oldDiscussionOfCommentCollectionNewComment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DiscussionPK id = discussion.getDiscussionPK();
                if (findDiscussion(id) == null) {
                    throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DiscussionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion discussion;
            try {
                discussion = em.getReference(Discussion.class, id);
                discussion.getDiscussionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comment> commentCollectionOrphanCheck = discussion.getCommentCollection();
            for (Comment commentCollectionOrphanCheckComment : commentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Discussion (" + discussion + ") cannot be destroyed since the Comment " + commentCollectionOrphanCheckComment + " in its commentCollection field has a non-nullable discussion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Project project = discussion.getProject();
            if (project != null) {
                project.getDiscussionCollection().remove(discussion);
                project = em.merge(project);
            }
            User user = discussion.getUser();
            if (user != null) {
                user.getDiscussionCollection().remove(discussion);
                user = em.merge(user);
            }
            em.remove(discussion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Discussion> findDiscussionEntities() {
        return findDiscussionEntities(true, -1, -1);
    }

    public List<Discussion> findDiscussionEntities(int maxResults, int firstResult) {
        return findDiscussionEntities(false, maxResults, firstResult);
    }

    private List<Discussion> findDiscussionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discussion.class));
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

    public Discussion findDiscussion(DiscussionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Discussion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscussionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discussion> rt = cq.from(Discussion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
