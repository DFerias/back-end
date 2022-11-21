package br.com.dferias.api.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class Login implements UserDetails {

    private Long id;
    private String email;
    private String senha;
    private List<Role> perfis;

    public Login(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.email = funcionario.getEmail();
        this.senha = funcionario.getSenha();
        perfis = new ArrayList<>();
        perfis.add(Role.getInstance(funcionario.isAdministrador()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
