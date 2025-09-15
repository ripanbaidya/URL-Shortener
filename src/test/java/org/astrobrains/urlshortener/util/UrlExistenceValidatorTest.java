package org.astrobrains.urlshortener.util;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlExistenceValidatorTest {
    private static final Logger log = LoggerFactory.getLogger(UrlExistenceValidatorTest.class);
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        // Start a mock web server for testing
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Shut down the mock server after each test
        mockWebServer.shutdown();
    }

    @Test
    void isUrlExists_shouldReturnTrue_whenUrlReturns200() {
        // Arrange
        String testUrl = mockWebServer.url("/test-200").toString();
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        
        // Act
        boolean result = UrlExistenceValidator.isUrlExists(testUrl);
        
        // Assert
        assertTrue(result, "Should return true for 200 status code");
    }

    @Test
    void isUrlExists_shouldReturnTrue_whenUrlReturns301() {
        // Arrange
        String testUrl = mockWebServer.url("/test-301").toString();
        mockWebServer.enqueue(new MockResponse().setResponseCode(301));
        
        // Act
        boolean result = UrlExistenceValidator.isUrlExists(testUrl);
        
        // Assert
        assertTrue(result, "Should return true for 301 status code");
    }

    @Test
    void isUrlExists_shouldReturnFalse_whenUrlReturns404() {
        // Arrange
        String testUrl = mockWebServer.url("/test-404").toString();
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));
        
        // Act
        boolean result = UrlExistenceValidator.isUrlExists(testUrl);
        
        // Assert
        assertFalse(result, "Should return false for 404 status code");
    }

    @Test
    void isUrlExists_shouldReturnFalse_whenConnectionTimesOut() {
        // Arrange
        String testUrl = mockWebServer.url("/test-timeout").toString();
        // No response enqueued, so it will time out
        
        // Act
        boolean result = UrlExistenceValidator.isUrlExists(testUrl);
        
        // Assert
        assertFalse(result, "Should return false on connection timeout");
    }

    @Test
    void isUrlExists_shouldReturnFalse_whenInvalidUrl() {
        // Arrange
        String invalidUrl = "htp:/invalid-url";
        
        // Act
        boolean result = UrlExistenceValidator.isUrlExists(invalidUrl);
        
        // Assert
        assertFalse(result, "Should return false for invalid URL format");
    }

    @Test
    void isUrlExists_shouldReturnFalse_whenMalformedUrl() {
        // Arrange
        String malformedUrl = "not-a-valid-url";
        
        // Act
        boolean result = UrlExistenceValidator.isUrlExists(malformedUrl);
        
        // Assert
        assertFalse(result, "Should return false for malformed URL");
    }
}
