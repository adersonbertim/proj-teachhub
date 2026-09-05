package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teachhub.com.TeachHub.model.postagem.PostagemDTO;
import teachhub.com.TeachHub.model.usuarios.PerfilResponseDTO;
import teachhub.com.TeachHub.model.usuarios.PerfilUpdateDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.List;

@Service
public class PerfilService {

    private final UsuarioService usuarioService;
    private final PostagemService postagemService;
    private final ArquivoService arquivoService;

    public PerfilService(UsuarioService usuarioService, PostagemService postagemService, ArquivoService arquivoService) {
        this.usuarioService = usuarioService;
        this.postagemService = postagemService;
        this.arquivoService = arquivoService;
    }

    public PerfilResponseDTO buscarPerfil(Long idPerfilVisitado, Usuario usuarioLogado) {
        Usuario dono = usuarioService.buscarPorId(idPerfilVisitado);
        if (dono == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        boolean souDono = usuarioLogado != null && usuarioLogado.getId().equals(dono.getId());

        if ("PRIVADO".equalsIgnoreCase(dono.getVisibilidade()) && !souDono) {
            return new PerfilResponseDTO(
                    dono.getId(), dono.getNome(), null, dono.getImagemPerfil(),
                    dono.getVisibilidade(), null, List.of(), false
            );
        }

        List<PostagemDTO> postagens = postagemService.listarMinhas(dono);

        return PerfilResponseDTO.fromEntity(dono, postagens, souDono);
    }

    public PerfilResponseDTO atualizarPerfil(Usuario usuarioLogado, PerfilUpdateDTO dto) {
        usuarioLogado.setDescricao(dto.descricao());
        usuarioLogado.setVisibilidade(dto.visibilidade());
        usuarioLogado.setRedesSociais(dto.redesSociais());

        Usuario salvo = usuarioService.salvar(usuarioLogado);

        List<PostagemDTO> postagens = postagemService.listarMinhas(salvo);
        return PerfilResponseDTO.fromEntity(salvo, postagens, true);
    }


    public PerfilResponseDTO atualizarFotoPerfil(Usuario usuarioLogado, MultipartFile arquivo) {
        String urlImagem = arquivoService.salvarImagemPerfil(
                usuarioLogado.getId(), arquivo, usuarioLogado.getImagemPerfil()
        );
        usuarioLogado.setImagemPerfil(urlImagem);

        Usuario salvo = usuarioService.salvar(usuarioLogado);

        List<PostagemDTO> postagens = postagemService.listarMinhas(salvo);
        return PerfilResponseDTO.fromEntity(salvo, postagens, true);
    }
}