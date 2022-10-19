package com.flowingcode.vaadin.addons.errorwindow;

import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import java.io.IOException;
import java.io.OutputStream;

public class NullMemoryBuffer extends MemoryBuffer {

  @Override
  public OutputStream receiveUpload(String fileName, String MIMEType) {
    super.receiveUpload(fileName, MIMEType);
    return new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        // do nothing
      }

      @Override
      public void write(byte[] b, int off, int len) throws IOException {
        // do nothing
      }
    };
  }
}
