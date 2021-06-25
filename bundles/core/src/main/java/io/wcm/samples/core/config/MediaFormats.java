/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
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
package io.wcm.samples.core.config;

import static io.wcm.handler.media.format.MediaFormatBuilder.create;

import io.wcm.handler.media.format.MediaFormat;

/**
 * Media formats
 */
public final class MediaFormats {

  private MediaFormats() {
    // constants only
  }

  /**
   * Stage header
   */
  public static final MediaFormat STAGE_HEADER = create("stageHeader")
      .label("Stage header")
      .fixedDimension(1000, 300)
      .extensions("gif", "jpg", "jpeg", "png")
      .build();

  /**
   * Content
   */
  public static final MediaFormat CONTENT_480 = create("content_480")
      .label("Content")
      .fixedDimension(480, 270)
      .extensions("gif", "jpg", "jpeg", "png", "mp4", "svg")
      .build();

  /**
   * Sponsor Logo
   */
  public static final MediaFormat SPONSOR_TEASER = create("sponsorTeaser")
      .label("Sponsor Logo")
      .maxWidth(400)
      .maxHeight(400)
      .extensions("gif", "jpg", "png")
      .build();

  /**
   * Socialteaser Logo
   */
  public static final MediaFormat SOCIAL_TEASER = create("socialTeaser")
      .label("Socialteaser Logo")
      .fixedDimension(120, 120)
      .extensions("gif", "jpg", "png")
      .build();

  /**
   * Gallery Small
   */
  public static final MediaFormat GALLERY_SMALL = create("gallerySmall")
      .label("Gallery Small")
      .fixedDimension(100, 50)
      .extensions("gif", "jpg", "png")
      .build();

  /**
   * Gallery Medium
   */
  public static final MediaFormat GALLERY_MEDIUM = create("galleryMedium")
      .label("Gallery Medium")
      .fixedDimension(980, 490)
      .extensions("gif", "jpg", "png")
      .build();

  /**
   * Gallery Large
   */
  public static final MediaFormat GALLERY_LARGE = create("galleryLarge")
      .label("Gallery Large")
      .minWidth(1000)
      .minHeight(500)
      .ratio(1600, 800)
      .extensions("gif", "jpg", "png")
      .build();

  /**
   * Download
   */
  public static final MediaFormat DOWNLOAD = create("download")
      .label("Download")
      .extensions("pdf", "zip", "ppt", "pptx", "doc", "docx")
      .download(true)
      .build();

}
