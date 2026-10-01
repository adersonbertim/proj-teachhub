package teachhub.com.TeachHub.core;

import jakarta.validation.constraints.NotEmpty;

public record GoogleLoginDTO(
        @NotEmpty
        String credential
){
}