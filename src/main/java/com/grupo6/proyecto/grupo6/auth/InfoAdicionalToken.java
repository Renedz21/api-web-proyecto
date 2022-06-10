package com.grupo6.proyecto.grupo6.auth;


import com.grupo6.proyecto.grupo6.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private LoginDao loginService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		User usuario = loginService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("info_adicional", "Hola que tal " .concat(usuario.getName()));
		info.put("nombre_usuario",usuario.getUsername());
		info.put("id",usuario.getId());
		info.put("age",usuario.getAge());
		info.put("genero",usuario.getGenero());
		info.put("phone",usuario.getPhone());
		info.put("workarea",usuario.getWorkArea());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
