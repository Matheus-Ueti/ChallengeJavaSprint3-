package com.example.challengejavasprint2.repository;

import com.example.challengejavasprint2.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    
    // Buscar motos por status
    List<Moto> findByStatus(String status);
    
    // Buscar motos por modelo
    List<Moto> findByModeloContainingIgnoreCase(String modelo);
    
    // Contar motos por status
    @Query("SELECT COUNT(m) FROM Moto m WHERE m.status = ?1")
    long countByStatus(String status);
}
