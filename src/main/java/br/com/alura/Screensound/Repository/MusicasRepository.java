package br.com.alura.Screensound.Repository;

import br.com.alura.Screensound.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicasRepository extends JpaRepository <Musica, Long> {

}
