package de.caritas.cob.uploadservice.api.service;

import de.caritas.cob.uploadservice.config.CacheManagerConfig;
import de.caritas.cob.uploadservice.config.TenantServiceApiControllerFactory;
import de.caritas.cob.uploadservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final @NonNull TenantServiceApiControllerFactory tenantServiceApiControllerFactory;

  @Cacheable(cacheNames = CacheManagerConfig.TENANT_CACHE, key = "#subdomain")
  public RestrictedTenantDTO getRestrictedTenantDataBySubdomain(String subdomain) {
    return getRestrictedTenantDTO(subdomain);
  }

  public RestrictedTenantDTO getRestrictedTenantDataBySubdomainNonCached(String subdomain) {
    return getRestrictedTenantDTO(subdomain);
  }

  private RestrictedTenantDTO getRestrictedTenantDTO(String subdomain) {
    log.debug("Calling tenant service to get tenant data for subdomain {}", subdomain);
    return tenantServiceApiControllerFactory
        .createControllerApi()
        .getRestrictedTenantDataBySubdomainWithHttpInfo(subdomain, null)
        .getBody();
  }

  private RestrictedTenantDTO getRestrictedTenantDTO(Long tenantId) {
    log.debug("Calling tenant service to get tenant data for tenantId {}", tenantId);
    return tenantServiceApiControllerFactory
        .createControllerApi()
        .getRestrictedTenantDataByTenantId(tenantId);
  }

  public RestrictedTenantDTO getRestrictedTenantDataNonCached(Long tenantId) {
    return getRestrictedTenantDTO(tenantId);
  }
}
