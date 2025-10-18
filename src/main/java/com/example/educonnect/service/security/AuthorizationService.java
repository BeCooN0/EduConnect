package com.example.educonnect.service.security;

import com.example.educonnect.entity.common.Ownable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.Optional;

@Service("authService")
@RequiredArgsConstructor
public class AuthorizationService {
    private final ApplicationContext applicationContext;
    public <T extends Ownable, ID extends Serializable> boolean isOwner(
            UserDetails principal, ID entityId, Class<T> entityClass
    ){
        String repositoryName = entityClass.getSimpleName().toLowerCase() + "Repository";
        JpaRepository<T, ID> repository = (JpaRepository<T ,ID>) applicationContext.getBean(repositoryName);
        Optional<T> entityOptional = repository.findById(entityId);
        if (entityOptional.isEmpty()){
            return false;
        }
        String currentUserIdentifier = principal.getUsername();
        String ownerIdentifier = entityOptional.get().getOwnerIdentifier();

        return ownerIdentifier != null && ownerIdentifier.equalsIgnoreCase(currentUserIdentifier);
    }
}