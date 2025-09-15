package org.astrobrains.urlshortener.mapper;

import org.astrobrains.urlshortener.records.ShortUrlDto;
import org.astrobrains.urlshortener.records.UserDto;
import org.astrobrains.urlshortener.entities.ShortUrl;
import org.astrobrains.urlshortener.entities.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

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

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }
}
