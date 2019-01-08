package skills.dao;


import skills.entities.Skill;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;
@Stateless
public class SkillPersistenceManager {

    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;


    public Skill create(Skill skill) {
        em.persist(skill);
        em.flush();
        return skill;
    }

    public void update(Skill skill) {
        em.merge(skill);
    }

    public void delete(Skill skill) {
        em.remove(skill);
    }

    public Optional<Skill> getByName(String name) {
        TypedQuery<Skill> q = em.createNamedQuery(Skill.GET_SKILL_BY_NAME, Skill.class)
                .setParameter("name", name);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


}
