package org.newshabit.app.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
  private final HttpStatus status;

  protected BaseException(Message message) {
    super(message.getMessage());
    this.status = message.getStatus();
  }

  public interface Message {
    String getMessage();
    HttpStatus getStatus();
  }
}
