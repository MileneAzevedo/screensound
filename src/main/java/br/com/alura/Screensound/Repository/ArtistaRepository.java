package br.com.alura.Screensound.Repository;

import br.com.alura.Screensound.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository <Artista, Long> {

}
