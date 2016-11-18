package nl.ekholabs.samples.reactive;

import java.time.Instant;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

public class InstantReactiveSubscriber implements Subscriber<Instant> {

  private static final  Logger log = getLogger(InstantReactiveSubscriber.class.getPackageName());

  private Subscription subscription;
  private long count;

  private final Consumer<? super Instant> consumer;
  private final long bufferSize;

  public InstantReactiveSubscriber(Consumer<? super Instant> consumer, long bufferSize) {
    this.consumer = consumer;
    this.bufferSize = bufferSize;
    this.count = bufferSize;
  }

  @Override public void onSubscribe(final Subscription subscription) {
    (this.subscription = subscription).request(bufferSize);
  }

  @Override public void onNext(final Instant item) {
    if (count > 0) {
      consumer.accept(item);
      count--;
    }
  }

  @Override public void onError(final Throwable e) {
    log.log(SEVERE, format("Error occurred with message -> %s", e.getMessage()));
  }

  @Override public void onComplete() {
  }
}
