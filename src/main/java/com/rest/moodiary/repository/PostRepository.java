package com.rest.moodiary.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rest.moodiary.dto.PageDto;
import com.rest.moodiary.dto.PostRequestAllDto;
import com.rest.moodiary.dto.PostRequestSearchDto;
import com.rest.moodiary.entity.Post;
import com.rest.moodiary.entity.PostMood;
import com.rest.moodiary.entity.QPost;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.rest.moodiary.entity.QPost.post;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    // 글 작성
    public Long save(Post post){

        em.persist(post);

        return post.getPostId();
    }

    // 글 삭제
    public void delete(Post post){
        em.remove(post);
    }

    // 글 단건 조회
    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    // 회원 전체 글 리스트 조회
    public List<Post> findAll(String userName){
        return em.createQuery(
                "select p from Post p" +
                        " join fetch p.user" +
                        " where p.user.userName = :userName", Post.class)
                .setParameter("userName", userName)
                .getResultList();
    }

    // 날짜별 글 리스트 조회(페이징)
    public List<Post> findDateAll(PostRequestAllDto request, PageDto page){
        return em.createQuery(
                "select p from Post p " +
                        " join fetch p.user " +
                        " where p.postDate = :postDate and p.user.userName = :userName ", Post.class)
                .setParameter("postDate", request.getDate())
                .setParameter("userName", request.getUsername())
                .setFirstResult(page.getOffset())
                .setMaxResults(page.getLimit())
                .getResultList();
    }

    // 글 내용, 기분 조회(페이징)
    public List<Post> findAllMood(PostRequestSearchDto postSearchDto, PageDto page){

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QPost post = QPost.post;

        return jpaQueryFactory
                .select(post)
                .from(post)
                .where(contentsLike(postSearchDto.getTitle()), moodEq(postSearchDto.getMood()))
                .offset(page.getOffset())
                .limit(page.getLimit())
                .fetch();
    }

    private BooleanExpression moodEq(PostMood postMood){
        if (postMood == null){
            return null;
        }
        return post.mood.eq(postMood);
    }

    private BooleanExpression contentsLike(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return post.title.like("%"+title+"%");
    }
}
