package com.grupo6.proyecto.grupo6.auth;

import com.grupo6.proyecto.grupo6.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService implements UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private LoginDao loginDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = loginDao.findByUsername(username);
        if(usuario == null){
            logger.error("Error en el login : no existe el usuario '"+ username +"' en el sistema");
            throw new UsernameNotFoundException("Error en el login : no existe el usuario '"+ username +"' en el sistema");
        }
        List<GrantedAuthority> authoritis = usuario.getRoles()
                .stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getNombre()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(usuario.getUsername() ,usuario.getPassword(),true,true,true,true,authoritis);
    }
}
