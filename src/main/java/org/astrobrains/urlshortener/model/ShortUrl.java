package org.astrobrains.urlshortener.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "short_urls")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "short_urls_id_gen")
    @SequenceGenerator(name = "short_urls_id_gen", sequenceName = "short_urls_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "short_key", nullable = false, length = 10)
    private String shortKey;

    @Column(name = "original_url", nullable = false, length = Integer.MAX_VALUE)
    private String originalUrl;

    @ColumnDefault("false")
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate = false;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ColumnDefault("0")
    @Column(name = "click_count", nullable = false)
    private Long clickCount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}