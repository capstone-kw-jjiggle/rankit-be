package gitbal.backend.global.util;

import gitbal.backend.domain.majorlanguage.application.TopLanguageService;
import gitbal.backend.domain.user.UserInfoService;
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
public class GithubDataService implements UserInfoService, TopLanguageService {

    @Value("${token}")
    private String token;
    private final RestTemplate restTemplate;
    private static final String GITHUB_GRAPHQL_URL = "https://api.github.com/graphql";


    @Override
    public ResponseEntity<String> requestTopLanguageQuery(String username) {
        String query = getTopLanguageQuery(username);

        // GitHub GraphQL API 호출
        return restTemplate.exchange(
            GITHUB_GRAPHQL_URL,
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );
    }


    private String getTopLanguageQuery(String username) {
        return """
        {
          "query": "query userInfo($login: String!) { user(login: $login) { repositories(ownerAffiliations: OWNER, isFork: false, first: 100) { nodes { name languages(first: 10, orderBy: {field: SIZE, direction: DESC}) { edges { size node { name } } } } } } }",
          "variables": { "login": "%s" }
        }
        """.formatted(username);
    }


    private HttpEntity<String> createHttpEntity(String query) {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(token);
        headers.set("Authorization", "token " + token);

        return new HttpEntity<>(query, headers);
    }


    // 작업 진행하기
    @Override
    public ResponseEntity<String> requestUserInfo(String username) {
        String query = getUserInfoQuery(username);

        return restTemplate.exchange(GITHUB_GRAPHQL_URL,
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );
    }


    @Override
    public ResponseEntity<String> requestUserRecentCommit(String username) {
        LocalDate yesterday = LocalDate.now(ZoneId.of("UTC")).minusDays(1);
        String from = yesterday.format(DateTimeFormatter.ISO_DATE) + "T00:00:00Z";

        LocalDate now = LocalDate.now(ZoneId.of("UTC"));
        String to = now.format(DateTimeFormatter.ISO_DATE) + "T00:00:00Z";

        String query = getYesterdayCommitsQuery(username, from, to);

        return restTemplate.exchange(GITHUB_GRAPHQL_URL,
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );
    }

    private String getUserInfoQuery(String username) {
        return """
        {
            "query": "query GetUserContributions($username: String!) { user(login: $username) { contributionsCollection { totalCommitContributions } repositories { totalCount } pullRequests { totalCount } issues { totalCount } followers { totalCount } } }",
            "variables": {
                "username": "%s"
            }
        }
        """.formatted(username);
    }






    private String getYesterdayCommitsQuery(String username, String from, String to) {
        return "{" +
            "\"query\": \"query GetUserContributions($username: String!, $from: DateTime!, $to: DateTime!) {" +
            "  user(login: $username) {" +
            "    yesterdayCommits: contributionsCollection(from: $from, to: $to) {" +
            "      totalCommitContributions" +
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
