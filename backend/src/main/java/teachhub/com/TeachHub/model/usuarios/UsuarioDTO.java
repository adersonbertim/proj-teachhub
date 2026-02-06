package teachhub.com.TeachHub.model.usuarios;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder

public record UsuarioDTO (
        Long id,
        String email,
        String nome,
        LocalDateTime dataCadastro,
        String areaEnsino,
        Integer score
){
    public static UsuarioDTO usuario(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getEmail(),
                u.getNome(),
                u.getDataCadastro(),
                u.getAreaEnsino(),
                u.getScore()
        );
    }
}