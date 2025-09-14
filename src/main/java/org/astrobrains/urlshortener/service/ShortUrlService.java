package org.astrobrains.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.astrobrains.urlshortener.entities.ShortUrl;
import org.astrobrains.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    public List<ShortUrl> getAllPublicShortUrls(){
        return shortUrlRepository.findAllPublicShortUrls();
    }
}
