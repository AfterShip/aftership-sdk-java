package com.aftership.sdk.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HMACSigner extends AbstractSigner {

  public HMACSigner(String secret) {
    super(secret);
  }

  public static final String HeaderASSignatureHMAC = "as-signature-hmac-sha256";

  @Override
  protected SignHeader getHeader(String signString) {
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key = new SecretKeySpec(this.getSecret().getBytes(), "HmacSHA256");
      sha256_HMAC.init(secret_key);
      return new SignHeader(HeaderASSignatureHMAC, Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(signString.getBytes())));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
