/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.groovy.markup.internal;

import groovy.xml.MarkupBuilder;
import ratpack.groovy.internal.Util;
import ratpack.groovy.markup.Markup;
import ratpack.groovy.markup.MarkupRenderer;
import ratpack.handling.Context;
import ratpack.render.RendererSupport;
import ratpack.util.internal.ByteBufWriteThroughOutputStream;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class DefaultMarkupRenderer extends RendererSupport<Markup> implements MarkupRenderer {

  @Override
  public void render(Context context, Markup markup) throws UnsupportedEncodingException {
    String encoding = markup.getEncoding() == null ? "UTF-8" : markup.getEncoding();

    ByteBufWriteThroughOutputStream out = new ByteBufWriteThroughOutputStream(context.getResponse().getBody());
    OutputStreamWriter writer = new OutputStreamWriter(out, encoding);
    MarkupBuilder markupBuilder = new MarkupBuilder(writer);

    Util.configureDelegateFirst(markupBuilder, markupBuilder, markup.getDefinition());

    context.getResponse().contentType(markup.getContentType()).send();
  }

}
