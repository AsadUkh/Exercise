<!--

    Sonatype Nexus (TM) Open Source Version
    Copyright (c) 2017-present Sonatype, Inc.
    All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.

    This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
    which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.

    Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
    of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
    Eclipse Foundation. All other trademarks are the property of their respective owners.

-->
Nexus Repository Google Cloud Storage Blobstore
==============================

[![Join the chat at https://gitter.im/sonatype/nexus-developers](https://badges.gitter.im/sonatype/nexus-developers.svg)](https://gitter.im/sonatype/nexus-developers?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This project adds [Google Cloud Object Storage](https://cloud.google.com/storage/) backed blobstores to Sonatype Nexus 
Repository 3.  It allows Nexus Repository to store the components and assets in Google Cloud instead of a
local filesystem.

Contribution Guidelines
-----------------------

Go read [our contribution guidelines](/.github/CONTRIBUTING.md) to get a bit more familiar with how
we would like things to flow.

Requirements
------------

* [Apache Maven 3.3.3+](https://maven.apache.org/install.html)
* [Java 8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Network access to https://repository.sonatype.org/content/groups/sonatype-public-grid

Also, there is a good amount of information available at [Bundle Development Overview](https://help.sonatype.com/display/NXRM3/Bundle+Development#BundleDevelopment-BundleDevelopmentOverview)

Building
--------

To build the project and generate the bundle use Maven

    mvn clean install

If everything checks out, the nexus-blobstore-s3 bundle  should be available in the `target` folder

Google Cloud Storage Authentication
-----------------------------------

Per the [Google Cloud documentation](https://github.com/GoogleCloudPlatform/google-cloud-java#authentication):

1. [Generate a JSON Service Account key](https://cloud.google.com/storage/docs/authentication?hl=en#service_accounts)
2. Store this file on the filesystem with appropriate permissions for the user running Nexus to read it.
3. Set the `GOOGLE_APPLICATION_CREDENTIALS` environment variable for the user running Nexus:

```
export GOOGLE_APPLICATION_CREDENTIALS=/path/to/my/key.json

```

Alternatively, if you have not set this environment variable, the blobstore configuration screen within the 
Nexus Repository Manager administrative user interface will allow you to specify the path to the file.

Installing
----------

After you have built the jar, copy the plugin (jar+feature) into the nexus install (rooted at `NEXUS_HOME` with `NEXUS_VERSION` being 3.6.0 or later):

```bash
mkdir -p ${NEXUS_HOME}/system/org/sonatype/nexus/plugins/nexus-blobstore-google-cloud/0.0.1-SNAPSHOT
cp ./target/feature/feature.xml \
  ${NEXUS_HOME}/system/org/sonatype/nexus/plugins/nexus-blobstore-google-cloud/0.0.1-SNAPSHOT/nexus-blobstore-google-cloud-0.0.1-SNAPSHOT-features.xml
cp ../nexus-blobstore-google-cloud/target/nexus-blobstore-google-cloud-0.0.1-SNAPSHOT.jar 
  ${NEXUS_HOME}/system/org/sonatype/nexus/plugins/nexus-blobstore-google-cloud/0.0.1-SNAPSHOT/
```
   
Edit `${NEXUS_HOME}/etc/karaf/org.ops4j.pax.url.mvn.cfg` and change the last line so it can fetch dependencies from central:

```
org.ops4j.pax.url.mvn.repositories=https://repo1.maven.org/maven2@id=central
```

Edit `${NEXUS_HOME}/etc/karaf/org.apache.karaf.features.cfg` and register the new plugin feature by adding this entry to the end of the comma-separated list under featuresRepositories:

```bash
mvn:org.sonatype.nexus.plugins/nexus-blobstore-google-cloud/0.0.1-SNAPSHOT/xml/features
```
   
Edit `${NEXUS_HOME}/system/org/sonatype/nexus/assemblies/nexus-core-feature/${NEXUS_VERSION}/nexus-core-feature-${NEXUS_VERSION}-features.xml`:

```xml
<feature version="0.0.1.SNAPSHOT" prerequisite="false" dependency="false">nexus-blobstore-google-cloud</feature>
```
   

This line should be added at about line 14, directly after:

```xml
<feature version="3.6.0" prerequisite="false" dependency="false">nexus-task-log-cleanup</feature>
```

Configuration
-------------

Log in as admin and create a new blobstore, selecting 'Google Cloud Storage' as the type.

The Fine Print
--------------

It is worth noting that this is **NOT SUPPORTED** by Sonatype, and is a contribution of ours
to the open source community (read: you!)

Remember:

* Use this contribution at the risk tolerance that you have
* Do NOT file Sonatype support tickets related to Google Cloud support
* DO file issues here on GitHub, so that the community can pitch in

Phew, that was easier than I thought. Last but not least of all:

Have fun creating and using this plugin and the Nexus platform, we are glad to have you here!

Getting help
------------

Looking to contribute to our code but need some help? There's a few ways to get information:

* Chat with us on [Gitter](https://gitter.im/sonatype/nexus-developers)
* Check out the [Nexus3](http://stackoverflow.com/questions/tagged/nexus3) tag on Stack Overflow
* Check out the [Nexus Repository User List](https://groups.google.com/a/glists.sonatype.com/forum/?hl=en#!forum/nexus-users)
