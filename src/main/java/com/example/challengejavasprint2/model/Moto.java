package com.example.challengejavasprint2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "motos")
@Data
public class Moto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;
    
    @NotBlank(message = "Placa é obrigatória")
    @Column(nullable = false, unique = true)
    private String placa;
    
    @Column(nullable = false)
    private String status = "DISPONIVEL"; // DISPONIVEL, EM_USO, MANUTENCAO
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private String observacoes;
}
