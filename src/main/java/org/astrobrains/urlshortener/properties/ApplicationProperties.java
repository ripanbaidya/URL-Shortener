package org.astrobrains.urlshortener.properties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Validated
public record ApplicationProperties(
        @NotBlank
        @DefaultValue("http://localhost:8000")
        String baseUrl,

        @Min(1) @Max(180)
        @DefaultValue("30")
        int defaultExpiryInDays,

        @DefaultValue("true")
        boolean validateOriginalUrl
)
{ }
