package nl.ekholabs.samples.reactive;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InstantPublisher implements Publisher<Instant> {

  private static InstantPublisher instance;

  static {
    instance = new InstantPublisher();
  }

  private final ExecutorService executor = ForkJoinPool.commonPool();

  private InstantPublisher() {
  }

  public static InstantPublisher getInstance() {
    return instance;
  }

  @Override
  public void subscribe(Subscriber<? super Instant> subscriber) {

    final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(() -> subscriber.onSubscribe(new InstantSubscription(subscriber, executor)),
      0L, 1L, TimeUnit.SECONDS);
  }
}