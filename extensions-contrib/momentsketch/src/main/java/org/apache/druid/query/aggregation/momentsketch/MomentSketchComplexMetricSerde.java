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

package org.apache.druid.query.aggregation.momentsketch;

import org.apache.druid.data.input.InputRow;
import org.apache.druid.query.aggregation.momentsketch.aggregator.MomentSketchAggregatorFactory;
import org.apache.druid.segment.data.ObjectStrategy;
import org.apache.druid.segment.serde.ComplexMetricExtractor;
import org.apache.druid.segment.serde.ComplexMetricSerde;

public class MomentSketchComplexMetricSerde extends ComplexMetricSerde
{
  private static final MomentSketchObjectStrategy STRATEGY = new MomentSketchObjectStrategy();

  @Override
  public String getTypeName()
  {
    return MomentSketchAggregatorFactory.TYPE_NAME;
  }

  @Override
  public ComplexMetricExtractor getExtractor()
  {
    return new ComplexMetricExtractor()
    {
      @Override
      public Class<?> extractedClass()
      {
        return MomentSketchWrapper.class;
      }

      @Override
      public Object extractValue(final InputRow inputRow, final String metricName)
      {
        return (MomentSketchWrapper) inputRow.getRaw(metricName);
      }
    };
  }

  @Override
  public ObjectStrategy<MomentSketchWrapper> getObjectStrategy()
  {
    return STRATEGY;
  }
}
