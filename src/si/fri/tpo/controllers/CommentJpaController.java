/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.jpa.Comment;
import si.fri.tpo.jpa.CommentPK;
import si.fri.tpo.jpa.User;
import si.fri.tpo.jpa.Discussion;

/**
 *
 * @author Administrator
 */
public class CommentJpaController implements Serializable {

    public CommentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comment comment) throws PreexistingEntityException, Exception {
        if (comment.getCommentPK() == null) {
            comment.setCommentPK(new CommentPK());
        }
        comment.getCommentPK().setUSERuserid(comment.getUser().getUserId());
        comment.getCommentPK().setDISCUSSIONdiscussionid(comment.getDiscussion().getDiscussionPK().getDiscussionId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = comment.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                comment.setUser(user);
            }
            Discussion discussion = comment.getDiscussion();
            if (discussion != null) {
                discussion = em.getReference(discussion.getClass(), discussion.getDiscussionPK());
                comment.setDiscussion(discussion);
            }
            em.persist(comment);
            if (user != null) {
                user.getCommentCollection().add(comment);
                user = em.merge(user);
            }
            if (discussion != null) {
                discussion.getCommentCollection().add(comment);
                discussion = em.merge(discussion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComment(comment.getCommentPK()) != null) {
                throw new PreexistingEntityException("Comment " + comment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comment comment) throws NonexistentEntityException, Exception {
        comment.getCommentPK().setUSERuserid(comment.getUser().getUserId());
        comment.getCommentPK().setDISCUSSIONdiscussionid(comment.getDiscussion().getDiscussionPK().getDiscussionId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comment persistentComment = em.find(Comment.class, comment.getCommentPK());
            User userOld = persistentComment.getUser();
            User userNew = comment.getUser();
            Discussion discussionOld = persistentComment.getDiscussion();
            Discussion discussionNew = comment.getDiscussion();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                comment.setUser(userNew);
            }
            if (discussionNew != null) {
                discussionNew = em.getReference(discussionNew.getClass(), discussionNew.getDiscussionPK());
                comment.setDiscussion(discussionNew);
            }
            comment = em.merge(comment);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getCommentCollection().remove(comment);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getCommentCollection().add(comment);
                userNew = em.merge(userNew);
            }
            if (discussionOld != null && !discussionOld.equals(discussionNew)) {
                discussionOld.getCommentCollection().remove(comment);
                discussionOld = em.merge(discussionOld);
            }
            if (discussionNew != null && !discussionNew.equals(discussionOld)) {
                discussionNew.getCommentCollection().add(comment);
                discussionNew = em.merge(discussionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CommentPK id = comment.getCommentPK();
                if (findComment(id) == null) {
                    throw new NonexistentEntityException("The comment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CommentPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comment comment;
            try {
                comment = em.getReference(Comment.class, id);
                comment.getCommentPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comment with id " + id + " no longer exists.", enfe);
            }
            User user = comment.getUser();
            if (user != null) {
                user.getCommentCollection().remove(comment);
                user = em.merge(user);
            }
            Discussion discussion = comment.getDiscussion();
            if (discussion != null) {
                discussion.getCommentCollection().remove(comment);
                discussion = em.merge(discussion);
            }
            em.remove(comment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comment> findCommentEntities() {
        return findCommentEntities(true, -1, -1);
    }

    public List<Comment> findCommentEntities(int maxResults, int firstResult) {
        return findCommentEntities(false, maxResults, firstResult);
    }

    private List<Comment> findCommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comment.class));
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

    public Comment findComment(CommentPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comment.class, id);
        } finally {
            em.close();
        }
    }

    public int getCommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comment> rt = cq.from(Comment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
