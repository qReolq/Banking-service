package qreol.project.bankingservice.web.security.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import qreol.project.bankingservice.web.security.JwtEntity;

@Component
public class CustomSecurityExpression {

    public boolean canAccessUserByLogin(String login) {
        JwtEntity user = getJwtEntity();
        return user.getUsername().equals(login);
    }

    public boolean canAccessUserById(Long id) {
        JwtEntity user = getJwtEntity();
        return user.getId().equals(id);
    }

    private JwtEntity getJwtEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }

}
