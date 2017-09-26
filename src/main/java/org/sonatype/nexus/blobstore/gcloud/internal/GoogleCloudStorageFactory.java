/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2017-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.blobstore.gcloud.internal;

import java.io.FileInputStream;

import javax.inject.Named;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.shiro.util.StringUtils;

import static org.sonatype.nexus.blobstore.gcloud.internal.GoogleCloudBlobStore.CONFIG_KEY;
import static org.sonatype.nexus.blobstore.gcloud.internal.GoogleCloudBlobStore.CREDENTIAL_FILE_KEY;

@Named
public class GoogleCloudStorageFactory
{

  Storage create(final BlobStoreConfiguration configuration) throws Exception {
    String environmentVariable = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
    if (StringUtils.hasText(environmentVariable)) {
      // if this is set, the google library will pick it up automatically
      return StorageOptions.getDefaultInstance().getService();
    }

    String credentialFile = configuration.attributes(CONFIG_KEY).get(CREDENTIAL_FILE_KEY, String.class);
    if (credentialFile != null ) {
      return StorageOptions.newBuilder()
          .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialFile)))
          .build()
          .getService();
    }

    throw new IllegalStateException("either GOOGLE_APPLICATION_CREDENTIALS must be set or credential file provided");
  }
}
