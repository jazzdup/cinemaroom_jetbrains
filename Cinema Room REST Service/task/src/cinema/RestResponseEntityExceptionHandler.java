package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = SeatException.class)
    public ResponseEntity<ErrorMessage> handleSeatException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex);
        return ResponseEntity.badRequest().body(message);
    }

    /**
     * this is an alternative, probably better, annotated version:
     * @param ex
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleUnauthorizedException(Exception ex, WebRequest request) {
        return new ErrorMessage(ex);
    }

}