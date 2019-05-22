package git.bng.springbootoauthresolver;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;

import java.util.Map;

public class UserInfo {
    private final Jwt<Header, Map<String, String>> jwt;
    private final String accessToken;

    public UserInfo(Jwt jwt, String accessToken) {
        this.jwt = jwt;
        this.accessToken = accessToken;
    }

    public String getId() {
        return jwt.getBody().get("sub");
    }

    public String getEmail() {
        return jwt.getBody().get("email");
    }

    public Map<String, String> getBody() {
        return jwt.getBody();
    }

    public String getAccessToken() {
        return accessToken;
    }
}
