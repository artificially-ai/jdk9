package nl.ekholabs.samples.jdk9.stack;

import java.util.List;
import java.util.stream.Collectors;

public class Walker {

  public static List<String> walkAndFilterStackframe() {
    return StackWalker.getInstance()
      .walk(s -> s.map(frame -> frame.getClassName() + "/" + frame.getMethodName())
      .filter(name -> name.startsWith("nl.ekholabs"))
      .limit(10).collect(Collectors.toList()));
  }
}
