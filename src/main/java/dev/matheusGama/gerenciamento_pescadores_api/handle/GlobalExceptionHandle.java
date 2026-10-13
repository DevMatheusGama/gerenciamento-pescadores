package dev.matheusGama.gerenciamento_pescadores_api.handle;

import dev.matheusGama.gerenciamento_pescadores_api.dto.response.ErroResponse;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingMonthException;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingPescadorByCpfException;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingPescadorByIDException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ErrorLocatingPescadorByIDException.class)
    public ResponseEntity<ErroResponse> ErrorLocatingPescadorByID(ErrorLocatingPescadorByIDException exception) {
        ErroResponse erroResponse = new ErroResponse(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(erroResponse);
    }

    @ExceptionHandler(ErrorLocatingPescadorByCpfException.class)
    public ResponseEntity<ErroResponse> ErrorLocatingPescadorByCpfException(ErrorLocatingPescadorByCpfException exception) {
        ErroResponse erroResponse = new ErroResponse(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(erroResponse);
    }

    @ExceptionHandler(ErrorLocatingMonthException.class)
    public ResponseEntity<ErroResponse> ErrorLocatingMonthException(ErrorLocatingMonthException exception) {
        ErroResponse erroResponse = new ErroResponse(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(erroResponse);
    }
}
