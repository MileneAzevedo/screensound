package br.com.alura.Screensound.Principal;

import br.com.alura.Screensound.Repository.ArtistaRepository;
import br.com.alura.Screensound.Repository.MusicasRepository;
import br.com.alura.Screensound.model.Artista;
import br.com.alura.Screensound.model.Musica;
import br.com.alura.Screensound.model.TipoArtista;
import br.com.alura.Screensound.service.ConsultaGemini;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal {

    private final ArtistaRepository repositorio;
    private final MusicasRepository repositorioMusicas;
    Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio, MusicasRepository repositorioMusicas) {
        this.repositorio = repositorio;
        this.repositorioMusicas = repositorioMusicas;
    }

    public void exibeMenu() {

        System.out.println("****ScreenSound****" +
        "1- Cadastrar artistas\n" +
                "\n" +
                "2- Cadastrar músicas\n" +
                "\n" +
                "3- Listar músicas\n" +
                "\n" +
                "4- Buscar músicas por artistas\n" +
                "\n" +
                "5- Pesquisar dados sobre um artista\n" +
                "\n" +
                "8- Sair");

       int opcao = leitura.nextInt();
        leitura.nextLine();

       switch (opcao) {
           case 1:
               cadastrarArtistas();
               break;
           case 2:
               cadastrarMusicas();
               break;
           case 3:
               listarMusicas();
               break;
           case 4:
               musicasPorArtistas();
               break;
           case 5:
               dadosSobreArtista();
               break;
           case 8:
               System.out.println("Encerrando...");
               break;
           default:
               System.out.println("Opção inválida.");


       }
    }

    private void cadastrarArtistas() {

        String opcao = "s";

        while(opcao.equalsIgnoreCase("s")){
            System.out.println("Qual artista gostaria de cadastrar? ");
            var nome = leitura.nextLine();
            System.out.println("Qual é o tipo de artista? (Solo, dupla ou banda)");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);
            System.out.println("Gostaria de cadastrar outro artista? (S/N)");
             opcao = leitura.nextLine();

        }
    }

    private void cadastrarMusicas() {
        System.out.println("Qual é o nome da música?");
        String musicaNome = leitura.nextLine();
        System.out.println("Qual é o gênero?");
        var generoMusica = leitura.nextLine();

        List<Artista> artistas = new ArrayList<>();
        repositorio.findAll().stream()
                .forEach(a -> System.out.println(a.getId() + " - " + a.getNome()));

        System.out.println("Informe de quem é a música pelo ID: ");
        Long id = (long) leitura.nextInt();
        leitura.nextLine();

        Optional<Artista> artistaOptional = repositorio.findById(id);

        if (artistaOptional.isPresent()) {
            Artista artista = artistaOptional.get();

            Musica musica = new Musica();
            musica.setTitulo(musicaNome);
            musica.setGenero(generoMusica);
            musica.setArtista(artista);

            artista.getMusicas().add(musica);
            repositorioMusicas.save(musica);

        } else {
            System.out.println("Artista não encontrado!");
        }
    }

    private void listarMusicas() {
        repositorioMusicas.findAll().stream()
                .forEach(m -> System.out.println(String.format("%s - %s", m.getTitulo(), m.getArtista().getNome())));
    }

    private void musicasPorArtistas() {
        System.out.println("Qual artista?");
        String nomeArtista = leitura.nextLine();

        repositorio.findAll().stream()
                .filter(artista -> artista.getNome().equalsIgnoreCase(nomeArtista))
                .forEach(artista -> {
                    if (artista.getMusicas().isEmpty()) {
                        System.out.println(artista.getNome() + " - [sem músicas]");
                    } else {
                        artista.getMusicas().forEach(musica -> {
                            System.out.println(artista.getNome() + " - " + musica.getTitulo());
                        });
                    }
                });
    }

    private void dadosSobreArtista() {
        System.out.println("Sobre qual artista gostaria de saber?");
        var artistaEscolhido = leitura.nextLine();
        var resposta = ConsultaGemini.obterSobre(artistaEscolhido);
        System.out.println(resposta.trim());
    }
}


