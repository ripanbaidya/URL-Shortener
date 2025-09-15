package org.astrobrains.urlshortener.dto;

import org.astrobrains.urlshortener.model.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto(Long id, String name) implements Serializable {
}