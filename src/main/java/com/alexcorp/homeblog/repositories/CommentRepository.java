package com.alexcorp.homeblog.repositories;

import com.alexcorp.homeblog.domais.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c WHERE c.topic = ?1 and c.article = ?2")
    Set<Comment> findAllByTopicAndArticle(Integer topic, Integer article);

    void deleteAllByTopicAndArticle(Integer topic, Integer article);

}
