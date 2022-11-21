package br.com.dferias.api.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class Role implements GrantedAuthority {

    private String nome;

    private Role(String perfil) {
        this.nome = perfil;
    }

    public static Role getInstance(boolean adm) {

        if (adm) {

            return new Role("ADM");
        }

        return new Role("USER");
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }

}
