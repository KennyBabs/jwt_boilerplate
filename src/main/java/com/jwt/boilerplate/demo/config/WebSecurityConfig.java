package com.jwt.boilerplate.demo.config;

import com.jwt.boilerplate.demo.security.jwt.JwtAuthenticationEntryPoint;
import com.jwt.boilerplate.demo.security.jwt.JwtTokenFilter;
import com.jwt.boilerplate.demo.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailService jwtUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtRequestFilter;

//    @Autowired
//    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
//                             JwtUserDetailService jwtUserDetailsService,
//                             JwtTokenFilter jwtRequestFilter){
//        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//        this.jwtRequestFilter = jwtRequestFilter;
//        this.jwtUserDetailsService = jwtUserDetailsService;
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/authenticate", "/register").permitAll()
        // all other requests need to be authenticated
        // anyRequest().authenticated()
        ;

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
