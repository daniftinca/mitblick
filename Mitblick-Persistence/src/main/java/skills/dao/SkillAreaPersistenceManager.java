package skills.dao;

import skills.entities.Skill;
import skills.entities.SkillArea;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
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

    public Optional<List<SkillArea>> getAll() {
        TypedQuery<SkillArea> q = em.createNamedQuery(SkillArea.GET_ALL_SKILLAREAS, SkillArea.class);
        try {
            Optional<List<SkillArea>> skillAreas = Optional.of(q.getResultList());
            return skillAreas;
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    public List<Skill> getSkillsFromSkillArea(String skillAreaName) {
        Optional<SkillArea> skillArea = getByName(skillAreaName);
        if (!skillArea.isPresent())
            return skillArea.get().getSkills();
        else
            return null;
    }

//    public void deleteSkill(Skill skill) {
//        TypedQuery<SkillArea> q = em.createNamedQuery(SkillArea.GET_ALL_SKILLAREAS, SkillArea.class);
//
//        try {
//            Optional<List<SkillArea>> skillAreas = Optional.of(q.getResultList());
//            if (!(skillAreas.get().isEmpty()))
//                skillAreas.get().forEach(skillArea -> skillArea.getSkills().remove(skill));
//            //skillAreas.get();
//        } catch (NoResultException ex) {
//
//        }
//    }
}
