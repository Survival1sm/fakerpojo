package survival1sm.fakerpojo.exceptions;

public class NoAllArgsConstructorException extends NoSuchMethodException {

  public NoAllArgsConstructorException(String errorMessage) {
    super(errorMessage);
  }
}
