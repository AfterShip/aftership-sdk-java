package com.aftership.sample.checkpoint;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.FieldsKind;
import com.aftership.sdk.model.LangKind;
import com.aftership.sdk.model.checkpoint.GetCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;

/** Sample of getLastCheckpoint method in CheckpointEndpoint */
public class GetLastCheckpointSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getLastCheckpoint(afterShip);
  }

  public static void getLastCheckpoint(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_LAST_CHECKPOINT);

    String id = "vebix4hfu3sr3kac0epve01n";
    GetCheckpointParam optionalParam = new GetCheckpointParam();
    optionalParam.setFields(FieldsKind.combine(FieldsKind.TAG));
    optionalParam.setLang(LangKind.CHINA_EMS);

    try {
      LastCheckpoint lastCheckpoint =
          afterShip.getCheckpointEndpoint().getLastCheckpoint(id, optionalParam);
      System.out.println(lastCheckpoint.getSlug());
      System.out.println(lastCheckpoint.getTrackingNumber());
      System.out.println(lastCheckpoint.getCheckpoint());
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
