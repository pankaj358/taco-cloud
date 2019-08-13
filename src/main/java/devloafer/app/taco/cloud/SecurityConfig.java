package devloafer.app.taco.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected  void  configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder(){
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception
    {
      httpSecurity
              .authorizeRequests()
                .antMatchers("/design", "/orders")
                  .access("hasRole('ROLE_USER')")
              .antMatchers("/", "/**")
              .access("permitAll")
              .and()
                .formLogin()
                  .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .usernameParameter("user")
                     .passwordParameter("pwd")
              .and()
                .formLogin()
                  .loginPage("/login")
                    .defaultSuccessUrl("/design", true)
              .and()
                  .logout()
                    .logoutSuccessUrl("/");
    }

}
