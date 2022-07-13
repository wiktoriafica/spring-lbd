package pl.fis.springlbdday2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SecurityContextController {
    @GetMapping
    public StringBuilder getLoggedInUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        StringBuilder loggedInUser = new StringBuilder("You logged in as: " + authentication.getName());
        for(var authority : authentication.getAuthorities())
            loggedInUser.append("\nRole: ").append(authority.getAuthority());
        return loggedInUser;
    }
}
