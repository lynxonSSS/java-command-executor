package terminalExecutor;

import java.io.Serializable;

public class Output implements Serializable {
  Boolean status;

  String description;

  Integer exitValue;

  long pid;

  public Output(Boolean status, String description, Integer exitValue, long pid) {
    this.status = status;
    this.description = description;
    this.exitValue = exitValue;
    this.pid = pid;
  }

  public Boolean isSuccess() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public Integer getExitValue() {
    return exitValue;
  }

  public long getPid() {
    return pid;
  }

  @Override
  public String toString() {
    return "Output{" +
        "status=" + status +
        ", description='" + description + '\'' +
        ", exitValue=" + exitValue +
        ", pid=" + pid +
        '}';
  }
}
