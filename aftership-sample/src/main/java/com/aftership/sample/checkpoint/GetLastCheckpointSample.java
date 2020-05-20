package com.aftership.sample.checkpoint;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.checkpoint.GetLastCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.tracking.MultiTrackingsParams.FieldsKind;
import com.aftership.sdk.model.tracking.MultiTrackingsParams.LangKind;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;

/** Sample of getLastCheckpoint method in CheckpointEndpoint */
public class GetLastCheckpointSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getLastCheckpoint(afterShip);
  }

  public static void getLastCheckpoint(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_LAST_CHECKPOINT);

    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wcwy86mie4o17kadedkcw029");
    GetLastCheckpointParam optionalParams = new GetLastCheckpointParam();
    optionalParams.setFields(FieldsKind.combine(FieldsKind.TAG, FieldsKind.ORDER_ID));
    optionalParams.setLang(LangKind.CHINA_EMS);

    DataEntity<LastCheckpoint> entity =
        afterShip.getCheckpointEndpoint().getLastCheckpoint(param, optionalParams);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      LastCheckpoint lastCheckpoint = entity.getData();
      System.out.println(lastCheckpoint.getSlug());
      System.out.println(lastCheckpoint.getTrackingNumber());
      System.out.println(lastCheckpoint.getCheckpoint());
    }
  }
}
