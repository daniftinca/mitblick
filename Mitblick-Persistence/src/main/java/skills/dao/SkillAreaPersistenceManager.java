package skills.dao;

import skills.entities.Skill;
import skills.entities.SkillArea;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class SkillAreaPersistenceManager {
    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;


    public SkillArea create(SkillArea skillArea) {
        em.persist(skillArea);
        em.flush();
        return skillArea;
    }

    public void update(SkillArea skillArea) {
        em.merge(skillArea);
    }

    public void delete(SkillArea skillArea) {
        em.remove(skillArea);
    }

    public Optional<SkillArea> getByName(String name) {
        TypedQuery<SkillArea> q = em.createNamedQuery(SkillArea.GET_SKILLAREA_BY_NAME, SkillArea.class)
                .setParameter("name", name);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public void deleteSkill(Skill skill) {
        TypedQuery<SkillArea> q = em.createNamedQuery(SkillArea.GET_ALL_SKILLAREAS, SkillArea.class);

        try {
            Optional<List<SkillArea>> skillAreas = Optional.of(q.getResultList());
            if (!skillAreas.isEmpty())
                skillAreas.get().forEach(skillArea -> skillArea.getSkills().remove(skill));
        } catch (NoResultException ex) {

        }
    }
}
