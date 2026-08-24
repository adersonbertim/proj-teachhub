package teachhub.com.TeachHub.model.usuarios;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedesSociais {
    private String instagram;
    private String facebook;
    private String twitter;
    private String linkedin;
}