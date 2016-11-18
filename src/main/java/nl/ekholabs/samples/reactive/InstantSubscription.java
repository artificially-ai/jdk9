package nl.ekholabs.samples.reactive;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class InstantSubscription implements Subscription {

  private static final Logger log = Logger.getLogger(InstantSubscription.class.getPackageName());

  private Future<?> future;

  private final Subscriber<? super Instant> subscriber;
  private final ExecutorService executor;

  public InstantSubscription(final Subscriber<? super Instant> subscriber, final ExecutorService executor) {
    this.subscriber = subscriber;
    this.executor = executor;
  }

  @Override
  public void request(long n) {
      if (n < 0) {
        executor.execute(() -> subscriber.onError(new IllegalArgumentException()));
      } else {
        future = executor.submit(() -> {
          subscriber.onNext(Instant.now());
          subscriber.onComplete();
        });
      }
  }

  @Override
  public void cancel() {
    if (future != null) {
      future.cancel(false);
    }
  }
}
