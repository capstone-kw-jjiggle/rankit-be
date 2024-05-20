package gitbal.backend.domain.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GraphQLService {

    @Value("${token}")
    private String token;
    private final RestTemplate restTemplate;
    private final String GraphQL_URL = "https://api.github.com/graphql";


    public ResponseEntity<String> requestTopLanguageQuery(String username) {

        String query = getTopLanguageQueryh(username);

        // GitHub GraphQL API 호출
        return restTemplate.exchange(
            GraphQL_URL,
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );
    }

    private static String getTopLanguageQueryh(String username) {
        String query =
            "{ \"query\": \"query getUserLanguages($username: String!) { user(login: $username) { repositories(first: 100) { nodes { languages(first: 10) { edges { size node { name } } } } } } }\", \"variables\": { \"username\": \""
                + username + "\" } }";
        return query;
    }


    private HttpEntity<String> createHttpEntity(String query) {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(token);
        headers.set("Authorization", "token " + token);

        return new HttpEntity<>(query, headers);
    }

    public ResponseEntity<String> requestUserInfo(String username) {
        LocalDate yesterday = LocalDate.now(ZoneId.of("UTC")).minusDays(1);
        String from = yesterday.format(DateTimeFormatter.ISO_DATE) + "T00:00:00Z";

        LocalDate now = LocalDate.now(ZoneId.of("UTC"));
        String to = now.format(DateTimeFormatter.ISO_DATE) + "T00:00:00Z";

        String query = getUserInfoQuery(username, from, to);

        return restTemplate.exchange(
            "https://api.github.com/graphql",
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );

    }

    private static String getUserInfoQuery(String username, String from, String to) {
        return "{" +
            "\"query\": \"query GetUserContributions($username: String!, $from: DateTime!, $to: DateTime!) {"
            +
            "  user(login: $username) {" +
            "    contributionsCollection {" +
            "      totalCommitContributions" +
            "    }" +
            "    yesterdayCommits: contributionsCollection(from: $from, to: $to) { " +
            "      totalCommitContributions " +
            "    } " +
            "    repositories {" +
            "      totalCount" +
            "    }" +
            "    pullRequests {" +
            "      totalCount" +
            "    }" +
            "    issues {" +
            "      totalCount" +
            "    }" +
            "    followers {" +
            "      totalCount" +
            "    }" +
            "  }" +
            "}\"," +
            "\"variables\": {" +
            "  \"username\": \"" + username + "\"," +
            "  \"from\": \"" + from + "\"," +
            "  \"to\": \"" + to + "\"" +
            "}" +
            "}";
    }
}
