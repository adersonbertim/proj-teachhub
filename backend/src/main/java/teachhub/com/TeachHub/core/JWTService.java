package teachhub.com.TeachHub.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class JWTService {

    //Vai ser mudado depois usando o application.properties para ser secreto!
    private final String SECRET_KEY = "aqui vai ir a chave";

    public String generateToken(Usuario usuario){
        Algorithm algoritimo = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer("TeachHub")
                .withSubject(usuario.getEmail())
                .withExpiresAt(genExpirationDate())
                .sign(algoritimo);

    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    //Vai validar o token, retorna o email guardado no token
    public String validateToken(String token){
        try{
            Algorithm algoritimo = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algoritimo)
                    .withIssuer("TeachHub")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (Exception e){
            return null; // Token invalido!
        }
    }
}
