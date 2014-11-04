/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.cache;

import com.hazelcast.core.ICompletableFuture;

import javax.cache.expiry.ExpiryPolicy;
import java.util.Map;
import java.util.Set;

/**
 * Hazelcast {@link javax.cache.Cache} extension
 *<p>
 *     Hazelcast provides extension methods to {@link javax.cache.Cache}.
 * </p>
 * <p>
 *     There are three set of extensions:
 *     <ul>
 *         <li>asynchronous version of all cache operations.</li>
 *         <li>cache operations with custom ExpiryPolicy parameter to apply on that specific operation.</li>
 *         <li>uncategorized like {@link #size()}.</li>
 *     </ul>
 *</p>
 *<p>
 *     A method ending with Async is the asynchronous version of that method (for example {@link #getAsync(K)},
 *     {@link #replaceAsync(K,V)} ).<br/>
 *     These methods return a Future where you can get the result or wait for the operation to be completed.
 *
 *     <pre>
 *         <code>ICache&lt;String , SessionData&gt; icache =  cache.unwrap( ICache.class );
 *         Future&lt;SessionData&gt; future = icache.getAsync(&quot;key-1&quot; ) ;
 *         SessionData sessionData = future.get();
 *         </code>
 *     </pre>
 *</p>
 *<p>
 *     This interface can be accessed through {@link javax.cache.Cache#unwrap(Class)}.
 *</p>
 * @param <K> the type of key.
 * @param <V> the type of value.
 * @see javax.cache.Cache
 * @since 3.3.1
 */
public interface ICache<K, V>
        extends javax.cache.Cache<K, V> {

    /**
     * Asynchronously gets an entry from cache.
     *
     * @param key the key whose associated value is to be returned
     *
     * @return ICompletableFuture to retrieve the value assigned to the given key
     *
     * @throws java.lang.NullPointerException if given key is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#get(K)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAsync(K key);

    /**
     * Asynchronously gets an entry from cache with a provided expiry policy.
     *
     * @param key the key whose associated value is to be returned
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #getAsync(Object)}
     *
     * @return ICompletableFuture to retrieve the value assigned to the given key
     *
     * @throws java.lang.NullPointerException if given key is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#get(K)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAsync(K key, ExpiryPolicy expiryPolicy);

    /**
     * Asynchronously associates the specified value with the specified key in the cache.
     *
     * @param key the key whose associated value is to be returned
     * @param value the value to be associated with the specified key
     *
     * @return ICompletableFuture to get notified when the operation succeed
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#put(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Void> putAsync(K key, V value);

    /**
     * Asynchronously associates the specified value with the specified key in the cache using a
     * custom expiry policy.
     *
     * @param key the key whose associated value is to be returned
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #putAsync(Object, Object)}
     *
     * @return ICompletableFuture to get notified when the operation succeed
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#put(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Void> putAsync(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Asynchronously associates the specified value with the specified key in the cache if not already exist.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     *
     * @return ICompletableFuture to retrieve if a previous value was assigned with the key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#putIfAbsent(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> putIfAbsentAsync(K key, V value);

    /**
     * Asynchronously associates the specified value with the specified key in the cache if not already exist,
     * using a custom expiry policy.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to
     *                     {@link #putIfAbsentAsync(Object, Object, javax.cache.expiry.ExpiryPolicy)}
     *
     * @return ICompletableFuture to retrieve if a previous value was assigned with the key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#putIfAbsent(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> putIfAbsentAsync(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Asynchronously associates the specified value with the specified key in this cache,
     * returning an existing value if one existed.
     *
     * @param key the key whose associated value is to be returned
     * @param value the value to be associated with the specified key
     *
     * @return ICompletableFuture to retrieve a possible previously assigned value for the given key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndPut(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAndPutAsync(K key, V value);

    /**
     * Asynchronously associates the specified value with the specified key in this cache,
     * returning an existing value if one existed using a custom expiry policy.
     *
     * @param key the key whose associated value is to be returned.
     * @param value the value to be associated with the specified key.
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #getAndPutAsync(Object, Object, javax.cache.expiry.ExpiryPolicy)}
     *
     * @return ICompletableFuture to retrieve a possible previously assigned value for the given key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndPut(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAndPutAsync(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Asynchronously removes the mapping for a key from this cache if it is present.
     *
     * @param key the key whose associated value is to be returned
     *
     * @return ICompletableFuture to retrieve if value could be removed or not
     *
     * @throws java.lang.NullPointerException if given key is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#remove(K)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> removeAsync(K key);

    /**
     * Asynchronously removes the mapping for a key only if it is currently mapped to the
     * given value.
     *
     * @param key the key whose associated value is to be returned.
     * @param oldValue the value expected to be associated with the specified key.
     *
     * @return ICompletableFuture to retrieve if value could be removed or not
     *
     * @throws java.lang.NullPointerException if given key or oldValue is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#remove(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> removeAsync(K key, V oldValue);

    /**
     * Asynchronously removes the entry for a key returning the removed value if one existed.
     *
     * @param key the key whose associated value is to be returned
     *
     * @return ICompletableFuture to retrieve a possible previously assigned value for the removed key
     *
     * @throws java.lang.NullPointerException if given key is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndRemove(K)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAndRemoveAsync(K key);

    /**
     * Asynchronously replaces the entry for a key.
     *
     * @param key the key whose associated value is to be returned
     * @param value the value to be associated with the specified key
     *
     * @return ICompletableFuture to get notified if the operation succeed or not
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#replace(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> replaceAsync(K key, V value);

    /**
     * Asynchronously replaces the entry for a key only if it is currently mapped to some
     * value.
     *
     * @param key  the key with which the specified value is associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #replaceAsync(Object, Object)}
     *
     * @return ICompletableFuture to get notified if the operation succeed or not
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#replace(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> replaceAsync(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Asynchronously replaces the entry for a key only if it is currently mapped to a
     * given value.
     *
     * @param key     the key with which the specified value is associated
     * @param oldValue the value expected to be associated with the specified key
     * @param newValue the value to be associated with the specified key
     *
     * @return ICompletableFuture to get notified if the operation succeed or not
     *
     * @throws java.lang.NullPointerException if given key, oldValue or newValue is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#replace(K,V,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> replaceAsync(K key, V oldValue, V newValue);

    /**
     * Asynchronously replaces the entry for a key only if it is currently mapped to a
     * given value using custom expiry policy.
     *
     * @param key      the key with which the specified value is associated
     * @param oldValue the value expected to be associated with the specified key
     * @param newValue the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #replaceAsync(Object, Object, Object)}
     *
     * @return ICompletableFuture to get notified if the operation succeed or not
     *
     * @throws java.lang.NullPointerException if given key, oldValue or newValue is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#replace(K,V,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, ExpiryPolicy expiryPolicy);


    /**
     * Asynchronously replaces the entry for a key only if it is currently mapped to some value.
     *
     * @param key  the key with which the specified value is associated
     * @param value the value to be associated with the specified key
     *
     * @return ICompletableFuture to retrieve a possible previously assigned value for the given key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndReplace(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAndReplaceAsync(K key, V value);

    /**
     * Asynchronously replaces the entry for a key only if it is currently mapped to some value
     * using custom expiry policy.
     *
     * @param key  the key with which the specified value is associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #getAndReplace(Object, Object)}
     *
     * @return ICompletableFuture to retrieve a possible previously assigned value for the given key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndReplace(K,V)
     * @see com.hazelcast.core.ICompletableFuture
     */
    ICompletableFuture<V> getAndReplaceAsync(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Gets a key with custom expiry policy.
     *
     * @param key the key whose associated value is to be returned
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #get(Object)}
     *
     * @return returns the value assigned to the given key or null if not assigned
     *
     * @throws java.lang.NullPointerException if given key is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#get(K)
     */
    V get(K key, ExpiryPolicy expiryPolicy);

    /**
     * Gets a collection of entries from the cache with custom expiry policy, returning them as
     * {@link Map} of the values associated with the set of keys requested.
     *
     * @param keys the keys whose associated values are to be returned
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #getAll(java.util.Set)}
     *
     * @return A map of entries that were found for the given keys. Keys not found
     *         in the cache are not in the returned map.
     *
     * @throws java.lang.NullPointerException if given keys is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAll(java.util.Set)
     */
    Map<K, V> getAll(Set<? extends K> keys, ExpiryPolicy expiryPolicy);

    /**
     * Associates the specified value with the specified key in the cache with custom expiry policy.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #put(Object, Object)}
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#put(K,V)
     */
    void put(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Associates the specified value with the specified key in this cache with custom expiry policy,
     * returning an existing value if one existed.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #getAndPut(Object, Object)}
     *
     * @return returns the value previously assigned to the given key or null if not assigned
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndPut(K,V)
     */
    V getAndPut(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Copies all of the entries from the specified map to the cache with custom expiry policy.
     *
     * @param map the mappings to be stored in this cache
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #putAll(java.util.Map)}
     *
     * @throws java.lang.NullPointerException if given map is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#putAll(java.util.Map)
     */
    void putAll(java.util.Map<? extends K, ? extends V> map, ExpiryPolicy expiryPolicy);

    /**
     * Atomically associates the specified key with the given value (with custom expiry policy) if it is
     * not already associated with a value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #putIfAbsent(Object, Object)}
     *
     * @return true if a value was set
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#putIfAbsent(K,V)
     */
    boolean putIfAbsent(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Atomically replaces the entry for a key (with custom expiry policy) only if currently mapped to a
     * given value.
     *
     * @param key      the key with which the specified value is associated
     * @param oldValue the value expected to be associated with the specified key
     * @param newValue the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #replace(Object, Object, Object)}
     *
     * @return true if a value was replaced
     *
     * @throws java.lang.NullPointerException if given key, oldValue or newValue is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#replace(K,V,V)
     */
    boolean replace(K key, V oldValue, V newValue, ExpiryPolicy expiryPolicy);

    /**
     * Atomically replaces the entry for a key (with custom expiry policy) with given value.
     *
     * @param key  the key with which the specified value is associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #replace(Object, Object)}
     *
     * @return true if a value was replaced
     *
     * @throws java.lang.NullPointerException if given key, oldValue or newValue is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#replace(K,V)
     */
    boolean replace(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Atomically replaces the value for a given key (with custom expiry policy) if and only if there is a
     * value currently mapped by the key.
     *
     * @param key   the key with which the specified value is associated
     * @param value the value to be associated with the specified key
     * @param expiryPolicy custom expiry policy for this operation,
     *                     a null value is equivalent to {@link #getAndReplace(Object, Object)}
     *
     * @return the value previously assigned to the given key
     *
     * @throws java.lang.NullPointerException if given key or value is null
     * @throws javax.cache.CacheException if anything exceptional
     *         happens while invoking the request, other exceptions are wrapped
     *
     * @see javax.cache.Cache#getAndReplace(K,V)
     */
    V getAndReplace(K key, V value, ExpiryPolicy expiryPolicy);

    /**
     * Total entry count.
     *
     * @return total entry count
     */
    int size();

    /**
     * Closes the cache, clears the internal content and releases any resource.
     *
     * @see javax.cache.CacheManager#destroyCache(String)
     */
    void destroy();

    /**
     * Directly access to local Cache Statistics.
     *
     * @return CacheStatistics instance or an empty statistics if not enabled
     */
    CacheStatistics getLocalCacheStatistics();

}
