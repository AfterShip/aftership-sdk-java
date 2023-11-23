package com.aftership.sdk.enums;

public enum Versions {
  V2023_10("2023-10");

  private String value;

  private Versions(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
