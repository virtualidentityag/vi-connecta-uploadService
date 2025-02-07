package de.caritas.cob.uploadservice.api.tenant;

import com.google.common.collect.Lists;
import de.caritas.cob.uploadservice.api.authorization.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.keycloak.representations.AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class TechnicalUserTenantResolver implements TenantResolver {

  @Override
  public Optional<Long> resolve(HttpServletRequest request) {
    return isTechnicalUserRole(request) ? Optional.of(0L) : Optional.empty();
  }

  public Collection<String> extractRealmRoles(Jwt jwt) {
    Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
    if (realmAccess != null) {
      var roles = (List<String>) realmAccess.get("roles");
      System.out.println("Extracted roles: " + roles); // Debug logging
      if (roles != null) {
        return roles;
      }
    }
    return Lists.newArrayList();
  }

  private boolean containsAnyRole(HttpServletRequest request, String... expectedRoles) {
    JwtAuthenticationToken token = ((JwtAuthenticationToken) request.getUserPrincipal());
    var roles = extractRealmRoles(token.getToken());
    if (!roles.isEmpty()) {
      return containsAny(roles, expectedRoles);
    } else {
      return false;
    }
  }

  private boolean containsAny(Collection<String> roles, String... expectedRoles) {
    return Arrays.stream(expectedRoles).anyMatch(roles::contains);
  }

  private boolean isTechnicalUserRole(HttpServletRequest request) {
    return containsAnyRole(request, UserRole.TECHNICAL.getValue());
  }

  private boolean hasRoles(AccessToken accessToken) {
    return accessToken.getRealmAccess() != null && accessToken.getRealmAccess().getRoles() != null;
  }

  @Override
  public boolean canResolve(HttpServletRequest request) {
    return resolve(request).isPresent();
  }
}
