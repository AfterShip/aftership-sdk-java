package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.checkpoint.GetLastCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;

/**
 * Endpoint provides the interface for all checkpoint API calls
 *
 * @author chenjunbiao
 */
public interface CheckpointEndpoint {
  /**
   * getLastCheckpoint Return the tracking information of the last checkpoint of a single tracking.
   *
   * @param param SingleTrackingParam
   * @param optionalParams GetLastCheckpointParam
   * @return DataEntity<LastCheckpoint>
   */
  DataEntity<LastCheckpoint> getLastCheckpoint(
      SingleTrackingParam param, GetLastCheckpointParam optionalParams);
}
