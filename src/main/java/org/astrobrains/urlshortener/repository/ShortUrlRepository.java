package org.astrobrains.urlshortener.repository;

import org.astrobrains.urlshortener.model.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    // Find all public urls
    List<ShortUrl> findByIsPrivateIsFalseOrderByCreatedAtDesc();

    /**
     * We are trying to fix the N+1 query problem. as we have made the spring.jpa.open-in-view=false.
     * so that lazy loading will not happen. and it will throw an exception. to resolve this issue we
     * have many options.
     * 1. Use @Query("your_query") to avoid N+1 query problem.
     * 2. @EntityGraph to avoid N+1 query problem.
     */
    // @Query("select s from ShortUrl s where s.isPrivate=false order by s.createdAt desc")
    // @EntityGraph(attributePaths = {"createdBy"})
    @Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false")
    Page<ShortUrl> findPublicShortUrls(Pageable pageable);

    // Check if short key exists in the database
    boolean existsByShortKey(String shortKey);

    // Find short url by short key
    Optional<ShortUrl> findByShortKey(String shortKey);

    // Find short urls by user id
    Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

    // Delete short urls by user id
    @Modifying
    void deleteByIdInAndCreatedById(List<Long> ids, Long userId);

    // Find all short urls
    @Query("select u from ShortUrl u left join fetch u.createdBy")
    Page<ShortUrl> findAllShortUrls(Pageable pageable);
}
