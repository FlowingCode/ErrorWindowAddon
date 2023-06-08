/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2023 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
