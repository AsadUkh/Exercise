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

/**
 * A service that periodically runs {@link Runnable} tasks.
 *
 * TODO: relocate this to nexus-common (copied verbatim from nexus-blobstore-file)
 */
public interface PeriodicJobService
{
  /**
   * Starts the service if it is not already started. Each successful invocation of this method must eventually be
   * paired with a corresponding invocation of {@link #stopUsing()}.
   */
  void startUsing() throws Exception;

  /**
   * Signals the caller no longer needs the service. The service is stopped if this invocation balances out all previous
   * invocations of {@link #startUsing()}.
   */
  void stopUsing() throws Exception;

  /**
   * Add a job to be periodically executed.
   *
   * @return a {@link PeriodicJob} that must be closed when the job is no longer necessary.
   */
  PeriodicJob schedule(Runnable runnable, final int repeatPeriodSeconds);

  interface PeriodicJob
  {
    void cancel();
  }
}
