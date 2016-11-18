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

  private final ExecutorService executor = ForkJoinPool.commonPool();

  @Override
  public void subscribe(Subscriber<? super Instant> subscriber) {

    final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      subscriber.onSubscribe(new InstantSubscription(subscriber, executor));
    }, 1l, 1l, TimeUnit.SECONDS);
  }
}