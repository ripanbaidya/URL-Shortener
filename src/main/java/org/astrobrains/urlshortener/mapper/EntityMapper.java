package org.astrobrains.urlshortener.mapper;

import org.astrobrains.urlshortener.dto.ShortUrlDto;
import org.astrobrains.urlshortener.dto.UserDto;
import org.astrobrains.urlshortener.model.ShortUrl;
import org.astrobrains.urlshortener.model.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    /**
     * Converts a ShortUrl Object to ShortUrlDto
     */
    public ShortUrlDto toShortUrlDto(ShortUrl shortUrl) {
        UserDto userDto = null;
        if (shortUrl.getCreatedBy() != null) {
            userDto = toUserDto(shortUrl.getCreatedBy());
        }

        return new ShortUrlDto(
            shortUrl.getId(),
            shortUrl.getShortKey(),
            shortUrl.getOriginalUrl(),
            shortUrl.getIsPrivate(),
            shortUrl.getExpiresAt(),
            userDto,
            shortUrl.getClickCount(),
            shortUrl.getCreatedAt()
        );
    }

    /**
     * Converts a User Object to UserDto
     */
    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }
}
