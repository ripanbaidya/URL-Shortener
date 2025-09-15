package org.astrobrains.urlshortener.records;

import java.io.Serializable;

/**
 * DTO for {@link org.astrobrains.urlshortener.entities.User}
 */
public record UserDto(Long id, String name) implements Serializable {
}