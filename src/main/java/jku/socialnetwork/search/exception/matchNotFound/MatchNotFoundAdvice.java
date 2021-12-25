package jku.socialnetwork.search.exception.matchNotFound;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MatchNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MatchNotFoundException.class)
    @ResponseStatus
    String matchNotFoundHandler(MatchNotFoundException ex) {
        return ex.getMessage();
    }
}
