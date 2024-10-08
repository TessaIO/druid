/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.indexing;

import org.apache.druid.indexing.overlord.supervisor.NoopSupervisorSpec;
import org.apache.druid.indexing.overlord.supervisor.Supervisor;
import org.apache.druid.indexing.overlord.supervisor.autoscaler.SupervisorTaskAutoScaler;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class NoopSupervisorSpecTest
{
  @Test
  public void testNoopSupervisorSpecWithAutoscaler()
  {
    Exception e = null;
    try {
      NoopSupervisorSpec noopSupervisorSpec = new NoopSupervisorSpec(null, Collections.singletonList("datasource1"));
      Supervisor supervisor = noopSupervisorSpec.createSupervisor();
      SupervisorTaskAutoScaler autoscaler = noopSupervisorSpec.createAutoscaler(supervisor);
      Assert.assertNull(autoscaler);
    }
    catch (Exception ex) {
      e = ex;
    }
    Assert.assertNull(e);
  }

  @Test
  public void testInputSourceResources()
  {
    NoopSupervisorSpec noopSupervisorSpec = new NoopSupervisorSpec(null, Collections.singletonList("datasource1"));
    Assert.assertTrue(noopSupervisorSpec.getInputSourceResources().isEmpty());
  }
}
