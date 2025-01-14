package nextstep.qna.domain;

import java.time.LocalDateTime;
import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class Answer {

  private Long id;

  private NsUser writer;

  private Question question;

  private String contents;

  private boolean deleted = false;

  private LocalDateTime createdDate = LocalDateTime.now();

  private LocalDateTime updatedDate;

  public Answer() {
  }

  public Answer(NsUser writer, Question question, String contents) {
    this(null, writer, question, contents);
  }

  public Answer(Long id, NsUser writer, Question question, String contents) {
    this.id = id;
    validateWriter(writer);
    this.writer = writer;
    validateQuestion(question);
    this.question = question;
    this.contents = contents;
  }

  private void validateWriter(NsUser writer) {
    if (writer == null) {
      throw new UnAuthorizedException();
    }
  }

  private void validateQuestion(Question question) {
    if (question == null) {
      throw new NotFoundException();
    }
  }

  public boolean isDeleted() {
    return deleted;
  }

  public DeleteHistory delete(NsUser loginUser) {
    validateAnswerOwner(loginUser);
    return makeDeleted();
  }

  public void validateAnswerOwner(NsUser loginUser) {
    if (!writer.equals(loginUser)) {
      throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
  }

  private DeleteHistory makeDeleted() {
    this.deleted = true;
    return toDeleteHistory();
  }

  public DeleteHistory toDeleteHistory() {
    return new DeleteHistory(ContentType.ANSWER, id, writer, LocalDateTime.now());
  }

  public void toQuestion(Question question) {
    this.question = question;
  }

  @Override
  public String toString() {
    return "Answer [id=" + id + ", writer=" + writer + ", contents=" + contents + "]";
  }
}
