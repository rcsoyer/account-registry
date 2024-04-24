package org.acme.accountregistry.web.errorhandling;

import java.sql.SQLIntegrityConstraintViolationException;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestControllerAdvice
class ExceptionHandlerAdvice implements ProblemHandling {

    /**
     * Handles exceptions related to data integrity violations that may happen concurrently.
     * <br/> Mainly is expected here to catch attempts to duplicate data on insert or update operations.
     */
    @ExceptionHandler({
      ConstraintViolationException.class,
      DataIntegrityViolationException.class,
      SQLIntegrityConstraintViolationException.class
    })
    ResponseEntity<Problem> dataIntegrityViolationHandler(final Exception error) {
        log.warn("There was an attempt to create or update data that violates integrity constraints", error);
        final var problem = Problem.builder()
                                   .withCause(toProblem(error))
                                   .withStatus(Status.CONFLICT);

        if (error instanceof DataIntegrityViolationException dataIntegrityException) {
            problem.withDetail(dataIntegrityException.getMostSpecificCause().getMessage());
        } else {
            problem.withDetail("Invalid duplicated data provided");
        }

        return ResponseEntity
                 .status(CONFLICT)
                 .body(problem.build());
    }

    @Override
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Problem> handleResponseStatusException(
      @Nonnull final ResponseStatusException error, @Nonnull final NativeWebRequest request) {
        log.warn("There was an error in the application", error);
        final var status = Status.valueOf(error.getStatusCode().value());
        final var problem = Problem.builder()
                                   .withStatus(status)
                                   .withDetail(error.getReason())
                                   .withTitle(status.getReasonPhrase())
                                   .build();
        return ResponseEntity
                 .status(error.getStatusCode())
                 .body(problem);
    }
}
