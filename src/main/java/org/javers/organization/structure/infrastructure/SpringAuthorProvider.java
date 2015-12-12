package org.javers.organization.structure.infrastructure;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringAuthorProvider implements AuthorProvider{

    private final UserContext userContext;

    @Autowired
    public SpringAuthorProvider(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public String provide() {
        return userContext.getLoggedInUser();
    }
}
