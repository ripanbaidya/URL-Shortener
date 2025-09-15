package org.astrobrains.urlshortener.service;

import org.astrobrains.urlshortener.exception.BusinessErrorCodes;
import org.astrobrains.urlshortener.exception.ShortUrlException;
import org.astrobrains.urlshortener.exception.UserException;
import org.astrobrains.urlshortener.model.ShortUrl;
import org.astrobrains.urlshortener.mapper.EntityMapper;
import org.astrobrains.urlshortener.model.User;
import org.astrobrains.urlshortener.properties.ApplicationProperties;
import org.astrobrains.urlshortener.records.CreateShortUrlCmd;
import org.astrobrains.urlshortener.records.PagedResult;
import org.astrobrains.urlshortener.dto.ShortUrlDto;
import org.astrobrains.urlshortener.repository.ShortUrlRepository;
import org.astrobrains.urlshortener.repository.UserRepository;
import org.astrobrains.urlshortener.util.UrlExistenceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.*;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlService.class);

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_KEY_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;
    private final ApplicationProperties properties;
    private final UserRepository userRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository,
                           EntityMapper entityMapper,
                           ApplicationProperties properties, UserRepository userRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
        this.properties = properties;
        this.userRepository = userRepository;
    }

    /**
     * find all public short-urls.
     */
    public PagedResult<ShortUrlDto> findAllPublicShortUrls(int pageNo, int pageSize) {
        Pageable pageable = getPageable(pageNo, pageSize);
        Page<ShortUrlDto> shortUrlDtoPage = shortUrlRepository.findPublicShortUrls(pageable)
                .map(entityMapper::toShortUrlDto);

        return PagedResult.from(shortUrlDtoPage);
    }

    /**
     * find all short-urls created by a user.
     */
    public PagedResult<ShortUrlDto> getUserShortUrls(Long userId, int page, int pageSize) {
        Pageable pageable = getPageable(page, pageSize);
        var shortUrlsPage = shortUrlRepository.findByCreatedById(userId, pageable)
                .map(entityMapper::toShortUrlDto);

        return PagedResult.from(shortUrlsPage);
    }

    /**
     * Delete shorl-urls based on there ids and user id.
     */
    @Transactional
    public void deleteUserShortUrls(List<Long> ids, Long userId) {
        if (ids != null && !ids.isEmpty() && userId != null) {
            shortUrlRepository.deleteByIdInAndCreatedById(ids, userId);
        }
    }

    /**
     * Find all Short urls.
     */
    public PagedResult<ShortUrlDto> findAllShortUrls(int page, int pageSize) {
        Pageable pageable = getPageable(page, pageSize);
        var shortUrlsPage =  shortUrlRepository.findPublicShortUrls(pageable)
                .map(entityMapper::toShortUrlDto);

        return PagedResult.from(shortUrlsPage);
    }

    /**
     * Create Short url.
     */
    @Transactional
    public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {
        validateOriginalUrlIfRequired(cmd.originalUrl());

        Instant now = Instant.now();
        ShortUrl shortUrl = buildShortUrl(cmd, now);

        shortUrlRepository.save(shortUrl);
        log.info("Short url created with shortKey {}", shortUrl.getShortKey());
        return entityMapper.toShortUrlDto(shortUrl);
    }

    @Transactional
    public Optional<ShortUrlDto> accessShortUrl(String shortKey, Long userId) {
        Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByShortKey(shortKey);
        if(shortUrlOptional.isEmpty()) {
            return Optional.empty();
        }

        ShortUrl shortUrl = shortUrlOptional.get();
        if(shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
            return Optional.empty();
        }
        if(shortUrl.getIsPrivate() != null && shortUrl.getIsPrivate()
                && shortUrl.getCreatedBy() != null
                && !Objects.equals(shortUrl.getCreatedBy().getId(), userId)) {
            return Optional.empty();
        }
        shortUrl.setClickCount(shortUrl.getClickCount()+1);
        shortUrlRepository.save(shortUrl);
        return shortUrlOptional.map(entityMapper::toShortUrlDto);
    }

    // ------------- Helper Method -------------
    private Pageable getPageable(int page, int size) {
        page = page > 1 ? page - 1: 0;
        return PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    }

    /**
     * Generate a six digit random string.
     * @return
     */
    public static String generateRandomShortKey() {
        StringBuilder sb = new StringBuilder(SHORT_KEY_LENGTH);
        for (int i = 0; i < SHORT_KEY_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate a unique short key.
     */
    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = generateRandomShortKey();
        } while (shortUrlRepository.existsByShortKey(shortKey));
        return shortKey;
    }

    /**
     * Check whether the given orl is valid or not.
     */
    private void validateOriginalUrlIfRequired(String originalUrl) {
        if (properties.validateOriginalUrl() && !UrlExistenceValidator.isUrlExists(originalUrl)){
            throw new ShortUrlException(
                    BusinessErrorCodes.URL_NOT_FOUND,
                    "Url " + originalUrl + " not found!"
            );
        }
    }

    /**
     * This method builds a ShortUrl object based on the provided command and current time.
     */
    private ShortUrl buildShortUrl(CreateShortUrlCmd cmd, Instant now) {
        var builder = ShortUrl.builder()
                .originalUrl(cmd.originalUrl())
                .shortKey(generateUniqueShortKey())
                .clickCount(0L)
                .createdAt(now);

        if (cmd.userId() == null) {
            return builder
                    .createdBy(null)
                    .isPrivate(false)
                    .expiresAt(now.plus(properties.defaultExpiryInDays(), DAYS))
                    .build();
        } else {
            User user = userRepository.findById(cmd.userId())
                    .orElseThrow(() -> new UserException(
                            BusinessErrorCodes.USER_NOT_FOUND,
                            "User not found with id: " + cmd.userId()
                    ));

            return builder
                    .createdBy(user)
                    .isPrivate(Boolean.TRUE.equals(cmd.isPrivate()))
                    .expiresAt(cmd.expirationInDays() != null
                            ? now.plus(cmd.expirationInDays(), DAYS)
                            : null)
                    .build();
        }
    }
}