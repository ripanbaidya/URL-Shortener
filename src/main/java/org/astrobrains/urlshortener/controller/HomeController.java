package org.astrobrains.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import org.astrobrains.urlshortener.entities.ShortUrl;
import org.astrobrains.urlshortener.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ShortUrlService shortUrlService;

    @GetMapping("/")
    public String home(Model model){
        List<ShortUrl> shortUrls = shortUrlService.getAllPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", "http://localhost:8000");
        return "index";
    }
}
