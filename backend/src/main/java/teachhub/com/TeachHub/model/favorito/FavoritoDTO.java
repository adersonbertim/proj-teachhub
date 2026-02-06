package teachhub.com.TeachHub.model.favorito;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {
    private Long idFavorito;
    private LocalDateTime dataFavorito;
    private String nomeUsuario;
    private String tituloPostagem;


    public static FavoritoDTO fromEntity(Favorito favorito) {
        if  (favorito == null) {
            return null;
        }
        return FavoritoDTO.builder()
                .idFavorito(favorito.getId())
                .dataFavorito(favorito.getData())
                .nomeUsuario(favorito.getUsuario() != null ? favorito.getUsuario().getNome() : null)
                .tituloPostagem(favorito.getPostagem() != null ? favorito.getPostagem().getTitulo() : null)
                .build();
    }

}
