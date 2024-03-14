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

package org.apache.druid.server.lookup;

import com.amazonaws.transform.MapEntry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.common.config.NullHandling;
import org.apache.druid.server.lookup.cache.loading.LoadingCache;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class LoadingLookupTest extends InitializedNullHandlingTest
{
  DataFetcher dataFetcher = EasyMock.createMock(DataFetcher.class);
  LoadingCache lookupCache = EasyMock.createStrictMock(LoadingCache.class);
  LoadingCache reverseLookupCache = EasyMock.createStrictMock(LoadingCache.class);
  LoadingLookup loadingLookup = new LoadingLookup(dataFetcher, lookupCache, reverseLookupCache);

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testApplyEmptyOrNull() throws ExecutionException
  {
    EasyMock.expect(lookupCache.get(EasyMock.eq(""), EasyMock.anyObject(Callable.class)))
            .andReturn("empty").atLeastOnce();
    EasyMock.replay(lookupCache);
    Assert.assertEquals("empty", loadingLookup.apply(""));
    if (!NullHandling.sqlCompatible()) {
      // Nulls and empty strings should have same behavior
      Assert.assertEquals("empty", loadingLookup.apply(null));
    } else {
      Assert.assertNull(loadingLookup.apply(null));
    }
    EasyMock.verify(lookupCache);
  }

  @Test
  public void testUnapplyNull()
  {
    if (NullHandling.sqlCompatible()) {
      Assert.assertEquals(Collections.emptyList(), loadingLookup.unapply(null));
    } else {
      Assert.assertNull(loadingLookup.unapply(null));
    }
  }

  @Test
  public void testApply() throws ExecutionException
  {
    EasyMock.expect(lookupCache.get(EasyMock.eq("key"), EasyMock.anyObject(Callable.class))).andReturn("value").once();
    EasyMock.replay(lookupCache);
    Assert.assertEquals(ImmutableMap.of("key", "value"), loadingLookup.applyAll(ImmutableSet.of("key")));
    EasyMock.verify(lookupCache);
  }

  @Test
  public void testUnapplyAll() throws ExecutionException
  {
    EasyMock.expect(reverseLookupCache.get(EasyMock.eq("value"), EasyMock.anyObject(Callable.class)))
            .andReturn(Collections.singletonList("key"))
            .once();
    EasyMock.replay(reverseLookupCache);
    Assert.assertEquals(ImmutableMap.of("value", Collections.singletonList("key")), loadingLookup.unapplyAll(ImmutableSet.of("value")));
    EasyMock.verify(reverseLookupCache);
  }

  @Test
  public void testClose()
  {
    lookupCache.close();
    reverseLookupCache.close();
    EasyMock.replay(lookupCache, reverseLookupCache);
    loadingLookup.close();
    EasyMock.verify(lookupCache, reverseLookupCache);
  }

  @Test
  public void testApplyWithExecutionError() throws ExecutionException
  {
    EasyMock.expect(lookupCache.get(EasyMock.eq("key"), EasyMock.anyObject(Callable.class)))
            .andThrow(new ExecutionException(null))
            .once();
    EasyMock.replay(lookupCache);
    Assert.assertNull(loadingLookup.apply("key"));
    EasyMock.verify(lookupCache);
  }

  @Test
  public void testUnApplyWithExecutionError() throws ExecutionException
  {
    EasyMock.expect(reverseLookupCache.get(EasyMock.eq("value"), EasyMock.anyObject(Callable.class)))
            .andThrow(new ExecutionException(null))
            .once();
    EasyMock.replay(reverseLookupCache);
    Assert.assertEquals(Collections.emptyList(), loadingLookup.unapply("value"));
    EasyMock.verify(reverseLookupCache);
  }

  @Test
  public void testGetCacheKey()
  {
    Assert.assertFalse(Arrays.equals(loadingLookup.getCacheKey(), loadingLookup.getCacheKey()));
  }

  @Test
  public void testCanGetKeySet()
  {
    Assert.assertTrue(loadingLookup.canGetKeySet());
  }

  @Test
  public void testCanIterate()
  {
    Assert.assertTrue(loadingLookup.canIterate());
  }

  @Test
  public void testKeySet()
  {
    Map.Entry<String, String> fetchedData = new AbstractMap.SimpleEntry<>("dummy", "test");
    EasyMock.expect(dataFetcher.fetchAll()).andReturn(Arrays.asList(fetchedData));
    EasyMock.replay(dataFetcher);
    Assert.assertEquals(loadingLookup.keySet().size(), 1);
    EasyMock.verify(dataFetcher);
  }

  @Test
  public void testFetchAll()
  {
    Map.Entry<String, String> fetchedData = new AbstractMap.SimpleEntry<>("dummy", "test");
    EasyMock.expect(dataFetcher.fetchAll()).andReturn(Arrays.asList(fetchedData));
    EasyMock.replay(dataFetcher);
    Assert.assertEquals(getIteratorSize(loadingLookup.iterable().iterator()), 1);
    EasyMock.verify(dataFetcher);
  }

  public int getIteratorSize(Iterator<Map.Entry<String, String>> it) {
    int i = 0;
    for ( ; it.hasNext() ; ++i ) it.next();
    return i;
  }
}
