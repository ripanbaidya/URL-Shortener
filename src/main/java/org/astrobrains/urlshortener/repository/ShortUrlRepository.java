package org.astrobrains.urlshortener.repository;

import org.astrobrains.urlshortener.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    // Find all public urls
    List<ShortUrl> findByIsPrivateIsFalseOrderByCreatedAtDesc();

    // Custom query to find all public urls
    @Query("select s from ShortUrl s where s.isPrivate=false order by s.createdAt desc")
    List<ShortUrl> findAllPublicShortUrls();

}
