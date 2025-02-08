package de.caritas.cob.uploadservice.api;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiDefaultResponseEntityExceptionHandler {

  /**
   * "Catch all" respectively fallback for all controller error messages that are not specifically
   * retained by {@link ApiResponseEntityExceptionHandler}. For the caller side does not need to
   * know the exact error stack trace, this method catches the trace and logs it.
   *
   * @param ex RuntimeException
   * @param request WebRequest
   * @return
   */
}
