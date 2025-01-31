package de.caritas.cob.uploadservice;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UploadServiceApplicationTests {

  @MockBean UploadServiceApplication uploadServiceApplication;

  @Test
  public void getAuthenticatedUser_Should_ReturnNullWhenNoUserSessionActive() {
    assertNull(uploadServiceApplication.getAuthenticatedUser());
  }

  @Test
  void name() {}
}
