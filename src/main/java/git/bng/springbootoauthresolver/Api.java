package git.bng.springbootoauthresolver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller public class Api {
    @GetMapping("/me") public ResponseEntity<UserInfo> me(UserInfo userInfo) { return ResponseEntity.ok(userInfo); }
}
