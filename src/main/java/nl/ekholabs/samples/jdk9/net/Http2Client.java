package nl.ekholabs.samples.jdk9.net;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;
import static java.net.URI.create;
import static java.net.http.HttpClient.getDefault;
import static java.net.http.HttpResponse.asString;

public class Http2Client {

  public static CompletableFuture<Void> open(final String url) throws ExecutionException, InterruptedException {
    CompletableFuture<Void> completableFuture = getDefault()
      .request(create(url))
      .GET()
      .responseAsync()
      .thenAccept(httpResponse -> out.println(httpResponse.body(asString())));

    return completableFuture;
  }
}
