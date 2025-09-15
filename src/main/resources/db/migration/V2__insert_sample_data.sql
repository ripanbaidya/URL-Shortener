-- Insert sample users
INSERT INTO users (email, password, name, role)
VALUES 
       ('admin@example.com', 'admin123', 'Super Admin', 'ROLE_ADMIN'),
       ('john.doe@example.com', 'password123', 'John Doe', 'ROLE_USER'),
       ('jane.smith@example.com', 'password456', 'Jane Smith', 'ROLE_USER');

-- Insert sample short URLs
INSERT INTO short_urls (short_key, original_url, created_by, created_at, expires_at, is_private, click_count)
VALUES 
       -- Admin created links
       ('ax12bc', 'https://spring.io/projects/spring-boot', 1, TIMESTAMP '2024-09-01', NULL, FALSE, 0),
       ('kd93ls', 'https://www.baeldung.com/guide-to-jooq', 1, TIMESTAMP '2024-09-02', TIMESTAMP '2024-12-31', FALSE, 0),
       ('mn47qp', 'https://github.com/spring-projects/spring-framework', 1, TIMESTAMP '2024-09-03', NULL, TRUE, 0),
       ('hg44df', 'https://openai.com/research/', 1, TIMESTAMP '2024-09-13', TIMESTAMP '2025-01-01', TRUE, 0),
       ('vb66wx', 'https://www.jetbrains.com/idea/', 1, TIMESTAMP '2024-09-14', NULL, FALSE, 0),
       ('zx77gh', 'https://redis.io/docs/latest/', 1, TIMESTAMP '2024-09-19', NULL, FALSE, 0),
       ('nb32vc', 'https://www.rabbitmq.com/tutorials/tutorial-one-java.html', 1, TIMESTAMP '2024-09-20', TIMESTAMP '2025-02-01', FALSE, 0),
       ('aw45tr', 'https://www.nginx.com/resources/wiki/', 1, TIMESTAMP '2024-09-21', NULL, FALSE, 0),
       ('uo89ik', 'https://grafana.com/docs/grafana/latest/', 1, TIMESTAMP '2024-09-22', NULL, TRUE, 0),
       ('ht67pl', 'https://prometheus.io/docs/introduction/overview/', 1, TIMESTAMP '2024-09-23', TIMESTAMP '2025-03-01', FALSE, 0),

       -- John Doe created links
       ('zs82yt', 'https://docs.oracle.com/javase/8/docs/api/', 2, TIMESTAMP '2024-09-04', NULL, FALSE, 0),
       ('qw90er', 'https://kubernetes.io/docs/home/', 2, TIMESTAMP '2024-09-05', NULL, FALSE, 0),
       ('pl56ok', 'https://www.docker.com/resources/what-container', 2, TIMESTAMP '2024-09-06', TIMESTAMP '2025-01-15', FALSE, 0),
       ('rt55yu', 'https://aws.amazon.com/getting-started/', 2, TIMESTAMP '2024-09-11', NULL, FALSE, 0),
       ('cv77nm', 'https://azure.microsoft.com/en-us/get-started/', 2, TIMESTAMP '2024-09-12', TIMESTAMP '2025-04-01', FALSE, 0),
       ('qs98pl', 'https://hibernate.org/orm/documentation/6.0/', 2, TIMESTAMP '2024-09-17', NULL, FALSE, 0),
       ('om45kl', 'https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html', 2, TIMESTAMP '2024-09-18', NULL, TRUE, 0),
       ('yt89ws', 'https://www.mongodb.com/docs/manual/', 2, TIMESTAMP '2024-09-24', TIMESTAMP '2025-02-28', FALSE, 0),
       ('pn56hy', 'https://www.apachekafka.com/getting-started/', 2, TIMESTAMP '2024-09-25', NULL, TRUE, 0),
       ('lm44qe', 'https://www.ansible.com/resources/get-started', 2, TIMESTAMP '2024-09-26', TIMESTAMP '2025-01-30', FALSE, 0),

       -- Jane Smith created links
       ('xy33rt', 'https://www.postgresql.org/docs/current/index.html', 3, TIMESTAMP '2024-09-07', NULL, TRUE, 0),
       ('fg11hu', 'https://react.dev/learn', 3, TIMESTAMP '2024-09-08', NULL, FALSE, 0),
       ('jk22lm', 'https://developer.mozilla.org/en-US/docs/Web/JavaScript', 3, TIMESTAMP '2024-09-09', NULL, FALSE, 0),
       ('bn88op', 'https://cloud.google.com/architecture', 3, TIMESTAMP '2024-09-10', NULL, FALSE, 0),
       ('lp21za', 'https://gradle.org/guides/', 3, TIMESTAMP '2024-09-15', TIMESTAMP '2025-01-20', FALSE, 0),
       ('yt34ed', 'https://maven.apache.org/guides/', 3, TIMESTAMP '2024-09-16', NULL, FALSE, 0),
       ('we67op', 'https://vuejs.org/guide/introduction.html', 3, TIMESTAMP '2024-09-27', NULL, FALSE, 0),
       ('ko55rt', 'https://angular.dev/overview', 3, TIMESTAMP '2024-09-28', TIMESTAMP '2025-03-15', FALSE, 0),
       ('mi99as', 'https://svelte.dev/docs/introduction', 3, TIMESTAMP '2024-09-29', NULL, TRUE, 0),
       ('de12fr', 'https://nextjs.org/docs', 3, TIMESTAMP '2024-09-30', TIMESTAMP '2025-02-20', FALSE, 0);
