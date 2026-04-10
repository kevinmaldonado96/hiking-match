package org.example.restclient.utils;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    public String getToken(){
        var context = SecurityContextHolder.getContext();
        if(context.getAuthentication()==null) {
            throw new AuthenticationCredentialsNotFoundException("No auth context");
        }

        Object credentials = context.getAuthentication().getCredentials();
        if(credentials instanceof String token) {
            return token;
        }

        return null;
    }
}
