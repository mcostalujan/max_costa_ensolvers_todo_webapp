package todo.webapp.api.enumeration;

import todo.webapp.api.constant.Authority;


public enum Role {
    ROLE_USER(Authority.USER_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
