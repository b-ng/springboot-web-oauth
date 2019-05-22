package git.bng.springbootoauthresolver;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;

@Component
public class UserInfoResolver implements HandlerMethodArgumentResolver {
    private static final Logger log = LoggerFactory.getLogger(UserInfoResolver.class);

    @Value("${b-ng.jwks.url}") String jwksUrl;

    @Autowired private OAuth2ClientContext oAuth2ClientContext;

    @Override public boolean supportsParameter(MethodParameter mp) { return mp.getParameterType().equals(UserInfo.class); }

    @Override
    public Object resolveArgument(MethodParameter mp, ModelAndViewContainer mvc, NativeWebRequest nwr, WebDataBinderFactory bf) {
        final String accessToken = oAuth2ClientContext.getAccessToken().getValue();
        final String idToken = oAuth2ClientContext.getAccessToken().getAdditionalInformation().get("id_token").toString();
        final Jwt validatedJwt;

        try {
            validatedJwt = Jwts.parser().setSigningKey(getSigningKey(JwtHelper.headers(idToken).get("kid"))).parse(idToken);
        } catch (MalformedURLException | JwkException e) {
            log.error("Error occurred while validating the JWT.", e);
            return null;
        }

        return new UserInfo(validatedJwt, accessToken);
    }

    private PublicKey getSigningKey(final String kid) throws MalformedURLException, JwkException {
        final JwkProvider jwkProvider = new UrlJwkProvider(new URL(jwksUrl));
        return jwkProvider.get(kid).getPublicKey();
    }
}
