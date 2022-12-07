package br.com.dferias.api.model;

import java.sql.Date;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dferias.api.model.DTO.FuncionarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario implements UserDetails {

    private static final long serialVersionUID = 1L;

    public Funcionario(FuncionarioDTO funcionarioDTO) {
        this.id = funcionarioDTO.getId();
        this.idEquipe = funcionarioDTO.getIdEquipe();
        this.nome = funcionarioDTO.getNome();
        this.dataAdmissao = funcionarioDTO.getDataAdmissao();
        this.email = funcionarioDTO.getEmail();
        this.saldoFerias = 0;
        this.pass = funcionarioDTO.getSenha();
        this.modalidade = funcionarioDTO.getModalidade();
        this.cidade = funcionarioDTO.getCidade();
        this.uf = funcionarioDTO.getUf();

    }

    @Id
    private Long id;
    private Long idEquipe;
    private String nome;
    private Date dataAdmissao;
    private String email;
    private int saldoFerias;
    private String pass;
    private String modalidade;
    private String cidade;
    private String uf;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Perfil> perfis;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.pass;
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
