# springboot-web-oauth
A Springboot application that secures an html/js front end behind and oauth login.
Additionally a `HandlerMethodArgumentResolver` is also implemented so that user information can be injected into the rest endpoint based on the user that is currently accessing the api.

## `/src/main/java/.../Api.java`
This is our endpoint where the UserInfo object is injected.

## `/src/main/java/.../UserInfo.java`

## `/src/main/java/.../UserInfoResolver.java`

## `/src/main/java/.../WebConfig.java`

## `/src/main/java/.../Application.java`
In this application we use the `@EnableOAuth2Sso` to enable spring's oauth2 support (provided by the auth autoconfigure dependency).

## `/src/main/resources/application.yml`
here the settings under `security.oauth2.client:` and `security.oauth2.resource:` are springboot's auto configured properties for setting up authentication against an oauth2 provider.
- `userInfoUri` must be specified or the application will not start.
- the scope value must contain `openid` or else you will not recieve an id token (jwt) from the oauth2 authenticator.
- the username and password for the test account configured is saved here. (`test@example.com` : `P4ssw0rd`)
