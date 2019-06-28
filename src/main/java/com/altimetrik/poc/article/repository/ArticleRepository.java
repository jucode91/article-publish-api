package com.altimetrik.poc.article.repository;

import com.altimetrik.poc.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByTitle(String title);

    void deleteByTitle(String title);

    @Override
    @Query(value = "SELECT * from ARTICLE A " +
            "WHERE (A.STATUS = 'ACTIVE') " +
            "ORDER BY A.VOTE_COUNT DESC",nativeQuery = true)
    List<ArticleEntity> findAll();
}
