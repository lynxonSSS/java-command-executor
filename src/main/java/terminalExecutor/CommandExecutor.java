package terminalExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandExecutor {
  public String dirToExecute;
  private static final Logger logger = Logger.getLogger(CommandExecutor.class.getName());

  /**
   * You have to init the TerminalExecutor constructor every time you want to execute command to a specific path.
   * Any time you can change the execution path by doing new TerminalExecutor("yourPathToExecute")
   */
  public CommandExecutor(String directoryToExecute) {
    this.dirToExecute = directoryToExecute;
    ProcessBuilder p = i("perl -v");
    try {
      Process pr = p.start();
      int n = pr.waitFor();
      if (n != 0) {
        i("npm i perl");
      }
    } catch (IOException | InterruptedException e) {
      logger.log(Level.SEVERE, "TerminalExecutorException :-" + e.getCause());
    }
  }

  public CommandExecutor() {
  }

  /**
   * Execute bat or shell files by specifying fileName and get Output of the command
   */
  public Output executeShellOrBat(String fileName) {
    ProcessBuilder p = i();
    String c = "/";
    if (OsIdentifier.isWindows())
      c = "\\";
    p.command(dirToExecute + (dirToExecute.endsWith(c) ? "" : c) + fileName);
    return h(p, fileName);
  }

  /**
   * Execute terminal command and get Output of the command
   */
  public Output executeCommand(String command) {
    return h(i(command), command);
  }

  private ProcessBuilder i() {
    ProcessBuilder p = new ProcessBuilder();
    if (dirToExecute != null && !dirToExecute.isEmpty())
      p.directory(new File(dirToExecute));
    return p;
  }

  private ProcessBuilder i(String command) {
    return i().command(OsIdentifier.isWindows() ? "cmd.exe" : "bash", OsIdentifier.isWindows() ? "/c" : "-c", command);
  }

  private Output h(ProcessBuilder p, String c) {
    Output r = null;
    try {
      Process pr = p.start();
      StringBuilder o = new StringBuilder();
      BufferedReader re = new BufferedReader(
          new InputStreamReader(pr.getInputStream()));
      String l;
      while ((l = re.readLine()) != null) {
        o.append(l).append("\n");
      }
      int e = pr.waitFor();
      r = new Output(e == 0, outputJsonFromTable(o.toString()), e, pr.pid());
      logger.log(Level.INFO, "Command Status :{} " + r.isSuccess() +
          " " + c + " pid " + r.getPid() + "\noutPut :- \n" + outputJsonFromTable(o.toString()) +
          "\n____end of output____\n");
    } catch (IOException | InterruptedException e) {
      logger.log(Level.SEVERE, "HException :-" + e.getCause());
    }
    return r;
  }

  private String outputJsonFromTable(String i) {
    StringBuilder s = new StringBuilder();
    try {
      if (identifyTableContent(i)) {
        ProcessBuilder p = i("echo \"" + i + "\" | perl -MJSON -lane " +
            "'if (!@keys) { @keys = @F } else { my %h = map {($keys[$_], $F[$_])} " +
            "0..$#keys; push @data, \\%h } END { print encode_json \\@data }'");
        Process prog = p.start();
        BufferedReader br = new BufferedReader(
            new InputStreamReader(prog.getInputStream()));
        String l;
        while ((l = br.readLine()) != null) {
          s.append(l).append("\n");
        }
        return s.toString();
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "OutputJsonException :-" + e.getCause());
      return i;
    }
    return i;
  }

  private boolean identifyTableContent(String output) {
    List<String> outputSplit = Arrays.asList(output.split("\n"));
    double r = 0;
    double c = 0;
    for (String s : outputSplit) {
      c += s.split("\\s+").length;
      r += 1;
    }
    double a = c / r;
    c = 0;
    for (int i = 0; i < r; i++) {
      c += a / outputSplit.get(i).split("\\s+").length;
    }
    return c / r < 1.041667 && c / r >= 1;
  }
}
