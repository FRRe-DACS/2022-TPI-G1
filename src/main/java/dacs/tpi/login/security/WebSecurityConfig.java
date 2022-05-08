package dacs.tpi.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.DELETE;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity //Habilita la seguridad en springboot
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    //TODO USER SERVICE AND FILTERS
    //Manager de autenticacion. Todos los intentos de autenticacion pasan por él
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder);
    }

    //Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**"
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login"); //EndPoint donde se da la autenticacion
        //STATELESS (Principio de REST) quiere decir que ante una peticion a la proxima no recuerdo que lo hayas hecho. 
        httpSecurity.csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                        .antMatchers("/auth/register").permitAll()
                        .antMatchers("/auth/login").permitAll()
                        .antMatchers("/auth/token/refresh").permitAll()
                        .antMatchers(GET,"/auth/users").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated() //No hace falta ROL. Con Token valido puede hacer lo quiera
                        .and()
                        .addFilter(customAuthenticationFilter)  //Filtro de autenticacion
                        .addFilterBefore(new CustomAuthorizationFilter(), 
                                        UsernamePasswordAuthenticationFilter.class);
                        //Filtro de autorizacion. Aqui validamos que el token sea correcto
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}