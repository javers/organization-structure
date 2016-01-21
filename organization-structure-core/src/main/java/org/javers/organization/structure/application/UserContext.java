package org.javers.organization.structure.application;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    public String getLoggedInUser() {
        return "mr Bean";
    }
}
