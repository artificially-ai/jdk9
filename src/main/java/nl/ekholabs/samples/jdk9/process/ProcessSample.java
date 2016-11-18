package nl.ekholabs.samples.jdk9.process;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;

public class ProcessSample {

  public static ProcessHandle execute(final String command) throws IOException {
    final Process sleeper = Runtime.getRuntime().exec(command);

    ProcessHandle sleeperHandle = ProcessHandle.of(sleeper.getPid()).orElseThrow(IllegalStateException::new);
    return sleeperHandle;
  }

  public static CompletableFuture<Void> onCompleted(final ProcessHandle handle) {
    return handle.onExit().thenRun(() -> {
      out.printf("Process with details -> '[%d] %s - %s', is terminated.\n", handle.getPid(), handle.info().user().orElse("unknown"),
        handle.info().commandLine().orElse("none"));
    });
  }

  public static void destroy(final ProcessHandle handle) {
    handle.destroy();
  }
}