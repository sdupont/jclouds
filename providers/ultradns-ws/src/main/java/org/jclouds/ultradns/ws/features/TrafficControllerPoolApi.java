/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.ultradns.ws.features;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.rest.ResourceNotFoundException;
import org.jclouds.ultradns.ws.UltraDNSWSExceptions.ResourceAlreadyExistsException;
import org.jclouds.ultradns.ws.domain.PoolRecordSpec;
import org.jclouds.ultradns.ws.domain.TrafficControllerPool;
import org.jclouds.ultradns.ws.domain.TrafficControllerPoolRecord;
import org.jclouds.ultradns.ws.domain.UpdatePoolRecord;

import com.google.common.collect.FluentIterable;

/**
 * @see TrafficControllerPoolAsyncApi
 * @author Adrian Cole
 */
public interface TrafficControllerPoolApi {

   /**
    * Returns all traffic controller pools in the zone.
    * 
    * @throws ResourceNotFoundException
    *            if the zone doesn't exist
    */
   FluentIterable<TrafficControllerPool> list() throws ResourceNotFoundException;

   /**
    * creates a traffic controller pool.
    * 
    * @param name
    *           {@link TrafficControllerPool#getName() name} of the TC pool
    * @param hostname
    *           {@link TrafficControllerPool#getDName() dname} of the TC pool
    *           {ex. www.jclouds.org.}
    * @return the {@code guid} of the new record
    * @throws ResourceAlreadyExistsException
    *            if a pool already exists with the same attrs
    */
   String createPoolForHostname(String name, String hostname) throws ResourceAlreadyExistsException;

   /**
    * Retrieves the name of the specified pool by dname.
    * 
    * @param dname
    *           {@see TrafficControllerPool#getDName()} ex. {@code jclouds.org.}
    * @return null if not found
    */
   @Nullable
   String getNameByDName(String dname);

   /**
    * removes a pool and all its records and probes
    * 
    * @param id
    *           the {@link TrafficControllerPool#getId() id}
    */
   void delete(String id);

   /**
    * Returns all records in the traffic controller pool.
    * 
    * @throws ResourceNotFoundException
    *            if the pool doesn't exist
    */
   FluentIterable<TrafficControllerPoolRecord> listRecords(String poolId) throws ResourceNotFoundException;

   /**
    * adds a new record to the pool with default weight.
    * 
    * @param pointsTo
    *           the ipv4 address or hostname
    * @param lbPoolID
    *           the pool to add the record to.
    * @param ttl
    *           the {@link PoolRecordSpec#getTTL ttl} of the record
    * @return the {@link TrafficControllerPoolRecord#getId() id} of the new
    *         record
    * @throws ResourceAlreadyExistsException
    *            if a record already exists with the same attrs
    */
   String addRecordToPoolWithTTL(String pointsTo, String lbPoolID, int ttl) throws ResourceAlreadyExistsException;

   /**
    * adds a new record to the pool with a specified weight.
    * 
    * @param pointsTo
    *           the ipv4 address or hostname
    * @param lbPoolID
    *           the pool to add the record to.
    * @param ttl
    *           the {@link PoolRecordSpec#getTTL ttl} of the record
    * @param weight
    *           the {@link PoolRecordSpec#getWeight() weight} of the record
    * @return the {@link TrafficControllerPoolRecord#getId() id} of the new
    *         record
    * @throws ResourceAlreadyExistsException
    *            if a record already exists with the same attrs
    */
   String addRecordToPoolWithTTLAndWeight(String pointsTo, String lbPoolID, int ttl, int weight)
         throws ResourceAlreadyExistsException;

   /**
    * @param poolRecordID
    *           {@link TrafficControllerPoolRecord#getId()}
    * @return null if not found
    */
   @Nullable
   PoolRecordSpec getRecordSpec(String poolRecordID);

   /**
    * This request updates an existing pool record.
    * 
    * @param poolRecordID
    *           {@link TrafficControllerPoolRecord#getId()}
    * @param update
    *           what to update, usually primed via
    *           {@link UpdatePoolRecord#pointingTo(PoolRecordSpec, String)} or
    *           {@link org.jclouds.ultradns.ws.domain.UpdatePoolRecord.Builder#from(PoolRecordSpec)}
    * @throws ResourceNotFoundException
    *            if the record doesn't exist
    */
   void updateRecord(String poolRecordID, UpdatePoolRecord update) throws ResourceNotFoundException;

   /**
    * deletes a specific pooled resource record
    * 
    * @param poolRecordID
    *           {@see TrafficControllerPoolRecord#getId()}
    */
   void deleteRecord(String poolRecordID);

}
