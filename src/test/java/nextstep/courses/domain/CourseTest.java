package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CourseTest {

  public static final Course C1 = new Course("ssafy", 1L);
  public static final Session S1 = new Session("tdd", LocalDateTime.now(),
      LocalDateTime.now().plusMonths(2), "tdd-img", SessionType.PAID, 80);
  public static final Session S2 = new Session("atdd", LocalDateTime.now(),
      LocalDateTime.now().plusMonths(1), "atdd-img", SessionType.PAID, 30);

  @DisplayName("과정(Course)은 기수(Batch)을 개설할 수 있다.")
  @Test
  public void addBatch() {
    Batch batch = C1.createdBatch(1L);

    assertThat(batch.checkBatchNo(1)).isTrue();
  }

  @DisplayName("과정(Course)은 기수(Batch) 단위로 여러 개의 강의(Session)를 가질 수 있다.")
  @Test
  public void addSession() {
    Batch batch1 = C1.createdBatch(1L);
    Batch batch2 = C1.createdBatch(1L);
    C1.addSession(1, S1);
    C1.addSession(2, S2);

    assertThat(batch1.hasSession(S1)).isTrue();
    assertThat(batch2.hasSession(S2)).isTrue();
  }
}