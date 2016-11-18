package nl.ekholabs.samples.main;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ekholabs.samples.reactive.InstantReactiveSubscriber;
import nl.ekholabs.samples.reactive.InstantPublisher;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import static java.lang.Integer.parseInt;

public class JDK9Reactive {

  private static final Logger log = Logger.getLogger(JDK9Reactive.class.getPackageName());

  public static final String BUFFER = "buffer";

  final static Supplier<Options> createOptions = () -> {
    final Options options = new Options();
    options.addOption(BUFFER, true, "Buffer size for consumption.");

    return options;
  };

  final static Function<Options, Function<List<String>, Optional<CommandLine>>> createCommandLine = createOptions -> args -> {
    final CommandLineParser parser = new DefaultParser();

    final String[] arguments = new String[args.size()];
    args.toArray(arguments);

    try {
      return Optional.of(parser.parse(createOptions, arguments));
    } catch (final ParseException e) {
      return Optional.empty();
    }
  };

  public static void main(final String... args) throws IOException, ExecutionException, InterruptedException {
    Optional<CommandLine> commandLine = createCommandLine.apply(createOptions.get()).apply(List.of(args));
    commandLine.ifPresent(cmd -> {
      final int buffer = parseInt(cmd.getOptionValue(BUFFER));

      final InstantReactiveSubscriber subscriber = new InstantReactiveSubscriber(instant -> {
        log.log(Level.INFO, String.format("Logging instant '%s' from Thread '%s'", instant, Thread.currentThread()));
      }, buffer);

      final InstantPublisher publisher = new InstantPublisher();
      publisher.subscribe(subscriber);
    });
  }
}
