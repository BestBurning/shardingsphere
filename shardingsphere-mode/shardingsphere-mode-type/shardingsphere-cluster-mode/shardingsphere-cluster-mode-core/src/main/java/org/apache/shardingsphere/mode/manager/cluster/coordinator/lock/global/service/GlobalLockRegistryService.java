/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.mode.manager.cluster.coordinator.lock.global.service;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.mode.manager.cluster.coordinator.lock.LockRegistryService;
import org.apache.shardingsphere.mode.manager.cluster.coordinator.lock.util.LockNodeUtil;
import org.apache.shardingsphere.mode.repository.cluster.ClusterPersistRepository;

import java.util.concurrent.TimeUnit;

/**
 * Global lock registry service.
 */
@RequiredArgsConstructor
public final class GlobalLockRegistryService implements LockRegistryService {
    
    private final ClusterPersistRepository repository;
    
    @Override
    public boolean tryLock(final String lockName, final long timeoutMilliseconds) {
        return repository.tryLock(lockName, timeoutMilliseconds, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void releaseLock(final String lockName) {
        repository.releaseLock(lockName);
    }
    
    @Override
    public void removeLock(final String lockName) {
        repository.delete(LockNodeUtil.generateGlobalLockReleasedNodePath(lockName));
    }
    
    @Override
    public void ackLock(final String lockName, final String lockValue) {
        repository.persistEphemeral(lockName, lockValue);
    }
    
    @Override
    public void releaseAckLock(final String lockName) {
        repository.delete(lockName);
    }
}
