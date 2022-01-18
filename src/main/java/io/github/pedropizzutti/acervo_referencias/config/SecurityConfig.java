package io.github.pedropizzutti.acervo_referencias.config;

import io.github.pedropizzutti.acervo_referencias.service.implemantationService.UserDetailsServiceImplemantation;
import io.github.pedropizzutti.acervo_referencias.service.implemantationService.UsuarioServiceImplemantation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImplemantation userDetails;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/usuario/newUser")
                        .permitAll()
                    .antMatchers("/api/usuario/att/**")
                        .hasRole("USER")
                    .antMatchers("/api/usuario/admin/**")
                        .hasRole("ADMIN")
                    .anyRequest()
                        .authenticated()
                .and()
                .httpBasic();
    }
}
