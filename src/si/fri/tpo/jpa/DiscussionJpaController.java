/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.jpa.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author nanorax
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
        if (discussion.getId() == null) {
            discussion.setId(new DiscussionPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = discussion.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                discussion.setUser(user);
            }
            Project project = discussion.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                discussion.setProject(project);
            }
            em.persist(discussion);
            if (user != null) {
                user.getDiscussions().add(discussion);
                user = em.merge(user);
            }
            if (project != null) {
                project.getDiscussions().add(discussion);
                project = em.merge(project);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDiscussion(discussion.getId()) != null) {
                throw new PreexistingEntityException("Discussion " + discussion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discussion discussion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion persistentDiscussion = em.find(Discussion.class, discussion.getId());
            User userOld = persistentDiscussion.getUser();
            User userNew = discussion.getUser();
            Project projectOld = persistentDiscussion.getProject();
            Project projectNew = discussion.getProject();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                discussion.setUser(userNew);
            }
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                discussion.setProject(projectNew);
            }
            discussion = em.merge(discussion);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDiscussions().remove(discussion);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDiscussions().add(discussion);
                userNew = em.merge(userNew);
            }
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getDiscussions().remove(discussion);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getDiscussions().add(discussion);
                projectNew = em.merge(projectNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DiscussionPK id = discussion.getId();
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

    public void destroy(DiscussionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion discussion;
            try {
                discussion = em.getReference(Discussion.class, id);
                discussion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.", enfe);
            }
            User user = discussion.getUser();
            if (user != null) {
                user.getDiscussions().remove(discussion);
                user = em.merge(user);
            }
            Project project = discussion.getProject();
            if (project != null) {
                project.getDiscussions().remove(discussion);
                project = em.merge(project);
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
