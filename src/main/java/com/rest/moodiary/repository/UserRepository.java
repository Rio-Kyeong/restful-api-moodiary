package com.rest.moodiary.repository;

import com.rest.moodiary.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 가입
    public Long save(User user){
        em.persist(user);
        return user.getUserId();
    }

    // 아이디(PK)로 개별 회원 조회
    public User findOne(Long id){
        return em.find(User.class, id);
    }

    // 전체 회원 조회
    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    // 이름으로 회원 조회
    @EntityGraph(attributePaths = "authorities")
    public Optional<User> findOneWithAuthoritiesByUsername(String userName){
        return em.createQuery("select u from User u where u.userName = :userName", User.class)
                .setParameter("userName", userName)
                .getResultStream().findAny();
    }
}
