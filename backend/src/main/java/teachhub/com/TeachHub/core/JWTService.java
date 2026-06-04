package teachhub.com.TeachHub.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class JWTService {

    private final String SECRET_KEY;
    private final Algorithm algorithm;

    public JWTService() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.SECRET_KEY = dotenv.get("JWT_SECRET", null);

        if(this.SECRET_KEY == null || this.SECRET_KEY.isBlank()) {
            throw new IllegalStateException("SECRET_KEY não configurada, conferir a .env");
        }
        this.algorithm = Algorithm.HMAC256(this.SECRET_KEY);
    }

    public String generateToken(Usuario usuario){

        return JWT.create()
                .withIssuer("TeachHub")
                .withSubject(usuario.getEmail())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);

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
