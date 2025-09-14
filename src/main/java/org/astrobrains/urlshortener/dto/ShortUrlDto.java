package org.astrobrains.urlshortener.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link org.astrobrains.urlshortener.entities.ShortUrl}
 */
public record ShortUrlDto(Long id, String shortKey, String originalUrl,
                          Boolean isPrivate, Instant expiresAt,
                          UserDto createdBy, Long clickCount,
                          Instant createdAt) implements Serializable {
}