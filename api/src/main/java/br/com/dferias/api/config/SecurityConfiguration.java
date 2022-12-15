package br.com.dferias.api.config;

import br.com.dferias.api.service.AuthenticationService;
import br.com.dferias.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private TokenAuthenticationFilter tokenAuthenticationFilter;

  @Autowired
  private TokenService tokenService;

  // Configurations for authentication
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(authenticationService)
      .passwordEncoder(new BCryptPasswordEncoder());
  }

  // Configuration for authorization
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/api/auth")
      .permitAll()
      .antMatchers(HttpMethod.POST, "/api/user")
      .hasAuthority("funcionario")
      .antMatchers(HttpMethod.POST, "/api/new")
      .permitAll()
      .antMatchers(HttpMethod.GET, "/api/equipe")
      .authenticated()
      .antMatchers(HttpMethod.POST, "/api/equipe/**")
      .hasAuthority("RH")
      .anyRequest()
      .authenticated()
      .and()
      .csrf()
      .disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      // Configuração do Filtro
      .and()
      .addFilterBefore(
        tokenAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
      );
  }

  // Configuration for static resources
  @Override
  public void configure(WebSecurity web) throws Exception {}

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
