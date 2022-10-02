package com.avergreen.grasshopper;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
@Transactional
public class UserRepository {

    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public int insertUserToDb(String imie, String nazwisko) {
        //w tym momencie tworzymy nowe userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(imie);
        userEntity.setLastName(nazwisko);
        em.persist(userEntity);
        return userEntity.getId();
    }

    public UserEntity retriveUser(int id) {
        return em.find(UserEntity.class, id);
    }

    public List<UserEntity> userEntityList() {
        CriteriaQuery<UserEntity> criteria = em.getCriteriaBuilder().createQuery(UserEntity.class);
        criteria.select(criteria.from(UserEntity.class));
        List<UserEntity> listOfUserEntities = em.createQuery(criteria).getResultList();
        return listOfUserEntities;
    }

}
