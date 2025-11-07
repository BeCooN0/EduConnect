package com.example.educonnect.service.security;

import com.example.educonnect.entity.common.Ownable;
import com.example.educonnect.entity.common.OwnableRepository;
import com.example.educonnect.security.TenantContext;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("authService")
@Slf4j
@RequiredArgsConstructor
public class AuthorizationService {

    private final Map<Class<?>, OwnableRepository<?, ?>> repositoryByDomain = new HashMap<>();
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        registerOwnableRepositories();
    }

    private void registerOwnableRepositories() {
        Map<String, OwnableRepository> beans = applicationContext.getBeansOfType(OwnableRepository.class);
        log.info("Found {} OwnableRepository beans.", beans.size());

        for (Map.Entry<String, OwnableRepository> entry : beans.entrySet()) {
            OwnableRepository repoBean = entry.getValue();

            try {
                Class<?> targetClass = AopUtils.getTargetClass(repoBean);
                ResolvableType type = ResolvableType.forClass(targetClass).as(OwnableRepository.class);
                Class<?> domainClass = type.getGeneric(0).resolve();

                if (domainClass != null) {
                    repositoryByDomain.put(domainClass, repoBean);
                    log.info("Registered: {} handles {}", entry.getKey(), domainClass.getSimpleName());
                }
            } catch (Exception e) {
                log.warn("Error registering repository {}: {}", entry.getKey(), e.getMessage());
            }
        }
    }

    @Transactional(readOnly = true)
    public <T extends Ownable, ID extends Serializable> boolean isOwner(
            UserDetails principal,
            ID entityId,
            Class<T> entityClass
    ) {
        if (principal == null || principal.getUsername() == null) {
            log.warn("isOwner: principal is null. Access denied.");
            return false;
        }

        OwnableRepository<T, ID> repository = resolveRepository(entityClass);
        if (repository == null) {
            log.warn("isOwner: repository not found for type {}", entityClass.getSimpleName());
            return false;
        }

        Optional<T> entityOpt = repository.findById(entityId);
        if (entityOpt.isEmpty()) {
            return false;
        }

        T entity = entityOpt.get();

        String ownerIdentifier = entity.getOwnerIdentifier();
        String currentUserIdentifier = principal.getUsername();

        boolean isOwner = currentUserIdentifier.equalsIgnoreCase(ownerIdentifier);

        if (!isOwner) {
            return false;
        }

        String ctxTenant = TenantContext.getTenantId();
        String entityTenant = entity.getTenantId();

        if (ctxTenant != null
                && !ctxTenant.equals("public")
                && entityTenant != null
                && !ctxTenant.equalsIgnoreCase(entityTenant)) {
            log.warn("isOwner: tenant mismatch (context={}, entity={}). Access denied.", ctxTenant, entityTenant);
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private <T extends Ownable, ID extends Serializable> OwnableRepository<T, ID> resolveRepository(Class<T> domainClass) {
        return (OwnableRepository<T, ID>) repositoryByDomain.get(domainClass);
    }
}
