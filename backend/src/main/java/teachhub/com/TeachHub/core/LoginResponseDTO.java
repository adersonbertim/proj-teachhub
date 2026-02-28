package teachhub.com.TeachHub.core;

import teachhub.com.TeachHub.model.usuarios.Usuario;

public record LoginResponseDTO(
        Long id,
        String nome,
        String email,
        String token
){
    public static LoginResponseDTO of(Usuario user, String token){
        return new LoginResponseDTO(user.getId(), user.getNome(), user.getEmail(), token);
    }
}
