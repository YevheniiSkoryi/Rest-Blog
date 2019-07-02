package com.jenia.blog.config;


import com.jenia.blog.config.service.AuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityWeb extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/register/**"
    };

    private final AuthFailedHandler restAuthenticationEntryPoint;

    private final AuthUserDetailService userService;

    @Autowired
    public SecurityWeb(AuthFailedHandler restAuthenticationEntryPoint, AuthUserDetailService userService) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userService = userService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().hasRole("PUBLISHER");

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint);

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
