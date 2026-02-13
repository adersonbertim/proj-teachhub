package teachhub.com.TeachHub.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/***
 * @author: adersonbertim
 * @since 13/02/2026
 */


    //This class capture the validation errors @Valid in put in the pattern ApiResponse
    // They capture all error mensages defined in DTO and return the default ApiResponse.error
@RestControllerAdvice
public class ExceptionTratament {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": "+ error.getDefaultMessage())
                .collect(Collectors.joining(", "));
                return ResponseEntity.badRequest().body(ApiResponse.error("Erro de validaçã: " + errors));

    }

    // Capture other generics errors!
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ex.getMessage()));
    }
}
