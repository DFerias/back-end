package br.com.dferias.api.model;

import br.com.dferias.api.model.DTO.FuncionarioDTO;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
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
        this.saldo_ferias = 0;
        this.pass = new BCryptPasswordEncoder().encode(funcionarioDTO.getPass());
        this.modalidade = funcionarioDTO.getModalidade();
        this.cidade = funcionarioDTO.getCidade();
        this.uf = funcionarioDTO.getUf();
    }

    @Override
    public String toString() {
        return ("{" +
                " id='" +
                getId() +
                "'" +
                ", idEquipe='" +
                getIdEquipe() +
                "'" +
                ", nome='" +
                getNome() +
                "'" +
                ", dataAdmissao='" +
                getDataAdmissao() +
                "'" +
                ", email='" +
                getEmail() +
                "'" +
                ", saldoFerias='" +
                getSaldo_ferias() +
                "'" +
                ", pass='" +
                getPass() +
                "'" +
                ", modalidade='" +
                getModalidade() +
                "'" +
                ", cidade='" +
                getCidade() +
                "'" +
                ", uf='" +
                getUf() +
                "'" +
                ", perfis='" +
                getPerfis() +
                "'" +
                "}");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idEquipe;
    private String nome;
    private Date dataAdmissao;
    private String email;
    private int saldo_ferias;
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