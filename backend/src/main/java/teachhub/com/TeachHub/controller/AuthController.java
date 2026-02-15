package teachhub.com.TeachHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.core.*;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;
import teachhub.com.TeachHub.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login (@RequestBody @Valid UsuarioLoginDTO dto){
        // Serve para preparar as credenciais para o Spring Security
        var credenciais = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        // Vai tentar autenticar chamando o banco, valida a senha criptografada
        var auth = authenticationManager.authenticate(credenciais);
        //Vai gerar o token
        Usuario user  = (Usuario) auth.getPrincipal();
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(ApiResponse.success(LoginResponseDTO.of(user, token)));

        // Posteriormente colocar o autenticador com o google!

    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<Usuario>> registrar (@RequestBody @Valid UsuarioRegistroDTO dto){
        Usuario usuarioSalvo = usuarioService.registrar(dto);
        return ResponseEntity.ok(ApiResponse.success(usuarioSalvo));
    }
}
