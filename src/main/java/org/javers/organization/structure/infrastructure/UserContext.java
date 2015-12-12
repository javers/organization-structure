package org.javers.organization.structure.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    public String getLoggedInUser() {
        return "mr Bean";
    }
}
