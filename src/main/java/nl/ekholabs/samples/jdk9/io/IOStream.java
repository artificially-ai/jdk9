package nl.ekholabs.samples.jdk9.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static java.lang.System.out;

public class IOStream {

  public static byte [] readStream(final String fileName) throws IOException {
    return IOStream.class.getResourceAsStream(fileName).readAllBytes();
  }

  public static void transfer(final byte [] buffer) throws IOException {
    new ByteArrayInputStream(buffer).transferTo(out);
  }
}
