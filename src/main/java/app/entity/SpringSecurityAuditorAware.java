package app.entity;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<String>{

	  @Override
	    public Optional<String> getCurrentAuditor() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication == null || !authentication.isAuthenticated()) {
	            return Optional.empty();
	        }

	        return Optional.of(authentication.getName()); // Retorna o nome de usuário
	    }
}
