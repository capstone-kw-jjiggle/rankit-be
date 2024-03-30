package gitbal.backend.service;

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
public class GraphQlService {

    @Value("${token}")
    private String token;
    private final RestTemplate restTemplate;
    private final String GraphQL_URL = "https://api.github.com/graphql";


    public ResponseEntity<String> requestTopLanguageQuery(String username) {

        String query =
            "{ \"query\": \"query getUserLanguages($username: String!) { user(login: $username) { repositories(first: 100) { nodes { languages(first: 10) { edges { size node { name } } } } } } }\", \"variables\": { \"username\": \""
                + username + "\" } }";

        // GitHub GraphQL API 호출
        return restTemplate.exchange(
            GraphQL_URL,
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );
    }


    private HttpEntity<String> createHttpEntity(String query) {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(token);
        headers.set("Authorization", "token " + token);

        return new HttpEntity<>(query, headers);
    }

    public ResponseEntity<String> requestUserInfo(String username) {
        String query =
            "{ \"query\": \"query UserInfo($login: String!) { user(login: $login) { contributionsCollection { totalCommitContributions } repositories { totalCount } pullRequests { totalCount } following { totalCount } followers { totalCount } } }\", \"variables\": { \"login\": \""
                + username + "\" } }";

        return restTemplate.exchange(
            "https://api.github.com/graphql",
            HttpMethod.POST,
            createHttpEntity(query),
            String.class
        );

    }
}
