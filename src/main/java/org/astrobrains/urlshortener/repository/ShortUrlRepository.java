package org.astrobrains.urlshortener.repository;

import org.astrobrains.urlshortener.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    // Find all public urls
    List<ShortUrl> findByIsPrivateIsFalseOrderByCreatedAtDesc();

    // Custom query to find all public urls

    /**
     * We are trying to fix the N+1 query problem. as we have made the spring.jpa.open-in-view=false.
     * so that lazy loading will not happen. and it will throw an exception. to resolve this issue we
     * have many options.
     * 1. Use @Query("select s from ShortUrl s left join fetch s.createdBy where s.isPrivate=false order by s.createdAt desc")
     * to avoid N+1 query problem.
     *
     * 2. @EntityGraph
     */
    // @Query("select s from ShortUrl s where s.isPrivate=false order by s.createdAt desc")
    // @EntityGraph(attributePaths = {"createdBy"})
    @Query("select s from ShortUrl s left join fetch s.createdBy where s.isPrivate=false order by s.createdAt desc")
    List<ShortUrl> findAllPublicShortUrls();

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shortKey);
}
