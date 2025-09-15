package org.astrobrains.urlshortener.records;

import org.astrobrains.urlshortener.enums.Role;

public record CreateUserCmd(
        String email,
        String password,
        String name,
        Role role
)
{ }