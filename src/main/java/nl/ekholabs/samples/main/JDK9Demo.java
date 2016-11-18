package nl.ekholabs.samples.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Map.entry;
import static java.util.Map.of;
import static java.util.Map.ofEntries;
import static nl.ekholabs.samples.jdk9.io.IOStream.readStream;
import static nl.ekholabs.samples.jdk9.io.IOStream.transfer;
import static nl.ekholabs.samples.jdk9.net.Http2Client.open;
import static nl.ekholabs.samples.jdk9.process.ProcessSample.destroy;
import static nl.ekholabs.samples.jdk9.process.ProcessSample.execute;
import static nl.ekholabs.samples.jdk9.process.ProcessSample.onCompleted;
import static nl.ekholabs.samples.jdk9.stack.Walker.walkAndFilterStackframe;

public class JDK9Demo {

  private static final Logger log = Logger.getLogger(JDK9Demo.class.getPackageName());

  public static  void main(String ... args) throws IOException, ExecutionException, InterruptedException {
    // Process

    ProcessHandle handle = execute("sleep 120");

    CompletableFuture<Void> processFuture = onCompleted(handle);

    destroy(handle);

    // Do not use Thread.sleep() in order to wait for the execution and avoid the Java program to continue (and probably finish
    // before you see anything). Just call get on the CompletableFuture and it will block.

    processFuture.get();

    // Immutable Collections

    Map<String, String> map = of("a", "b", "c", "d");

    map.forEach((k, v) -> out.println(format("key %s and value %s", k, v)));

    Map<String, String> map2 = ofEntries(entry("e", "f"), entry("g", "h"));

    map2.forEach((k, v) -> out.println(format("key %s and value %s", k, v)));

    List<String> list = List.of("a", "b", "c", "d");

    list.forEach(s -> out.println(format("list item %s", s)));

    Set<String> set = Set.of("a", "b", "c", "d");

    set.forEach(s -> out.println(format("set item %s", s)));

    // Stack Walker

    walkAndFilterStackframe().forEach(s -> out.println(format("clas is %s", s)));

    // Stream

    byte[] bytes = readStream("/test");
    transfer(bytes);

    // HHTP/2

    CompletableFuture<Void> httpFuture = open("https://www.google.com");

    // Do not use Thread.sleep() in order to wait for the execution and avoid the Java program to continue (and probably finish
    // before you see anything). Just call get on the CompletableFuture and it will block.

    httpFuture.get();

    // Package Name

    out.println(format("Package name -> %s", JDK9Demo.class.getPackageName()));
  }
}
