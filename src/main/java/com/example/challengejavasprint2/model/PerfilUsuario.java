package com.example.challengejavasprint2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "perfil_usuario")
public class PerfilUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "github_username", unique = true)
    private String githubUsername;
    
    @Column(name = "nome_completo")
    private String nomeCompleto;
    
    @Email
    @Column(name = "email")
    private String email;
    
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "cargo")
    private String cargo;
    
    // Construtores
    public PerfilUsuario() {}
    
    public PerfilUsuario(String githubUsername, String nomeCompleto) {
        this.githubUsername = githubUsername;
        this.nomeCompleto = nomeCompleto;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getGithubUsername() {
        return githubUsername;
    }
    
    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }
    
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
