package org.astrobrains.urlshortener.controller;

import jakarta.validation.Valid;
import org.astrobrains.urlshortener.exception.BusinessErrorCodes;
import org.astrobrains.urlshortener.exception.ShortUrlException;
import org.astrobrains.urlshortener.properties.ApplicationProperties;
import org.astrobrains.urlshortener.records.CreateShortUrlCmd;
import org.astrobrains.urlshortener.records.CreateShortUrlForm;
import org.astrobrains.urlshortener.records.PagedResult;
import org.astrobrains.urlshortener.dto.ShortUrlDto;
import org.astrobrains.urlshortener.service.ShortUrlService;
import org.astrobrains.urlshortener.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;
    private final SecurityUtils securityUtils;

    public HomeController(
            ShortUrlService shortUrlService,
            ApplicationProperties properties,
            SecurityUtils securityUtils
    ) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "1") Integer page,
            Model model
    ) {
        addShortUrlsDataToModel(model, page);
        model.addAttribute(
                "createShortUrlForm",
                new CreateShortUrlForm("", false, null)
        );
        return "index";
    }

    private void addShortUrlsDataToModel(Model model, int pageNo) {
        PagedResult<ShortUrlDto> shortUrls =
                shortUrlService.findAllPublicShortUrls(pageNo, properties.pageSize());

        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
    }

    @PostMapping("/short-urls")
    public String createShortUrl(
            @ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addShortUrlsDataToModel(model, 1);
            return "index";
        }

        try {
            Long userId = securityUtils.getCurrentUserId();
            CreateShortUrlCmd cmd = new CreateShortUrlCmd(
                    form.originalUrl(),
                    form.isPrivate(),
                    form.expirationInDays(),
                    userId
            );

            ShortUrlDto shortUrlDto = shortUrlService.createShortUrl(cmd);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Short URL created successfully " +
                            properties.baseUrl() + "/s/" + shortUrlDto.shortKey()
            );

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Failed to create short URL"
            );
        }

        return "redirect:/";
    }

    @GetMapping("/s/{shortKey}")
    public String redirectToOriginalUrl(@PathVariable String shortKey) {
        Long userId = securityUtils.getCurrentUserId();

        Optional<ShortUrlDto> shortUrlDtoOptional =
                shortUrlService.accessShortUrl(shortKey, userId);

        if (shortUrlDtoOptional.isEmpty()) {
            throw new ShortUrlException(
                    BusinessErrorCodes.URL_SHORT_KEY_INVALID,
                    "Short key is invalid"
            );
        }

        return "redirect:" + shortUrlDtoOptional.get().originalUrl();
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/my-urls")
    public String showUserUrls(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        Long currentUserId = securityUtils.getCurrentUserId();

        PagedResult<ShortUrlDto> myUrls =
                shortUrlService.getUserShortUrls(currentUserId, page, properties.pageSize());

        model.addAttribute("shortUrls", myUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("paginationUrl", "/my-urls");

        return "my-urls";
    }

    @PostMapping("/delete-urls")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String deleteUrls(
            @RequestParam(value = "ids", required = false) List<Long> ids,
            RedirectAttributes redirectAttributes
    ) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "No URLs selected for deletion"
            );
            return "redirect:/my-urls";
        }

        try {
            Long currentUserId = securityUtils.getCurrentUserId();
            shortUrlService.deleteUserShortUrls(ids, currentUserId);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Selected URLs have been deleted successfully"
            );

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error deleting URLs: " + e.getMessage()
            );
        }

        return "redirect:/my-urls";
    }
}
