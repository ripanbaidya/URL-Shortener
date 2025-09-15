package org.astrobrains.urlshortener.records;

import org.astrobrains.urlshortener.model.Role;

public record CreateUserCmd(
        String email,
        String password,
        String name,
        Role role) {
}