/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.hotthreads;

import org.elasticsearch.action.support.nodes.BaseNodesRequest;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.monitor.jvm.HotThreads;

import java.io.IOException;

public class NodesHotThreadsRequest extends BaseNodesRequest<NodesHotThreadsRequest> {

    final HotThreads.RequestOptions requestOptions;

    public NodesHotThreadsRequest(StreamInput in) throws IOException {
        super(in);
        requestOptions = HotThreads.RequestOptions.readFrom(in);
    }

    /**
     * Get hot threads from nodes based on the nodes ids specified. If none are passed, hot
     * threads for all nodes is used.
     */
    public NodesHotThreadsRequest(String[] nodesIds, HotThreads.RequestOptions requestOptions) {
        super(nodesIds);
        this.requestOptions = requestOptions;
    }

    /**
     * Get hot threads from the given node, for use if the node isn't a stable member of the cluster.
     */
    public NodesHotThreadsRequest(DiscoveryNode node, HotThreads.RequestOptions requestOptions) {
        super(node);
        this.requestOptions = requestOptions;
    }

    public int threads() {
        return requestOptions.threads();
    }

    public boolean ignoreIdleThreads() {
        return requestOptions.ignoreIdleThreads();
    }

    public HotThreads.ReportType type() {
        return requestOptions.reportType();
    }

    public HotThreads.SortOrder sortOrder() {
        return requestOptions.sortOrder();
    }

    public TimeValue interval() {
        return requestOptions.interval();
    }

    public int snapshots() {
        return requestOptions.snapshots();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        requestOptions.writeTo(out);
    }
}
