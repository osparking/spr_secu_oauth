package com.bumsoap.spr_secu_oauth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    //일반 로그인 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //OAuth 로그인 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes ) {
        this.user = user;
        this.attributes = attributes;
    }
    /**
     * OAuth2User 인터페이스 메소드
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * UserDetails 인터페이스 메소드
     * 해당 User의 role 을 리턴. SecurityFilterChain 권한 체크 때 쓰임
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return String.valueOf(user.getRole());
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
