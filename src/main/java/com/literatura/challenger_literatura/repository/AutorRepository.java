package com.literatura.challenger_literatura.repository;

import com.literatura.challenger_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    List<Autor> findByFechaNacimientoBeforeAndFechaMuerteAfter(int fechaInicio, int fechaFin);
}
