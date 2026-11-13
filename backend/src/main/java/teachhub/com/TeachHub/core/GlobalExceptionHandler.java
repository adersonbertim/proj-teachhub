package teachhub.com.TeachHub.core;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;


// Intercepta os erros e devolve no ApiResponse, sempre com mensagem em português
@RestControllerAdvice
public class GlobalExceptionHandler {

    // regra de negócio: a mensagem já foi escrita para o usuário
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
    }

    // validações dos DTOs (@NotBlank, @Email, @Size...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException ex){
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(". "));
        return ResponseEntity.badRequest().body(ApiResponse.error(mensagem));
    }

    // Erro de credencias
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("E-mail ou senha inválidos"));
    }

    //Erro de acesso negado (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDenied(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(ex.getMessage() != null ? ex.getMessage() : "Você não tem permissão para fazer isso."));
    }

    //Erro de recurso não encontrado (404)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage() != null ? ex.getMessage() : "O recurso não foi encontrado ou não existe!"));
    }

    // tentar apagar algo que outros registros ainda usam, ou salvar um valor repetido
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error("Não foi possível concluir: existem informações ligadas a este registro."));
    }

    // arquivo maior que o limite do spring.servlet.multipart
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<String>> handleArquivoGrande() {
        return ResponseEntity.badRequest().body(ApiResponse.error("A imagem precisa ter no máximo 5MB"));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse<String>> handleArquivoAusente() {
        return ResponseEntity.badRequest().body(ApiResponse.error("Nenhum arquivo enviado"));
    }

    // corpo da requisição vazio ou com JSON inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleCorpoInvalido() {
        return ResponseEntity.badRequest().body(ApiResponse.error("Os dados enviados estão em um formato inválido."));
    }

    // ex.: /perfil/abc quando o esperado era um número
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleParametroInvalido() {
        return ResponseEntity.badRequest().body(ApiResponse.error("Parâmetro inválido na requisição."));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleParametroAusente(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error("Informe o parâmetro obrigatório: " + ex.getParameterName()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<String>> handleMetodoNaoSuportado() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.error("Operação não permitida neste endereço."));
    }

    //Rota 404
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResource(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Rota não encontrada: " + ex.getResourcePath()));
    }

    // erro que ninguém previu: mostra no console e não manda a mensagem técnica (em inglês) para o usuário
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Ocorreu um erro inesperado. Tente novamente em instantes."));
    }
}