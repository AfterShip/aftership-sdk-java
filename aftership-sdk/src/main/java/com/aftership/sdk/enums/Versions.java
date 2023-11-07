package com.aftership.sdk.enums;

public enum Versions {
  V2024_10("2024-10"),
  V2024_07("2024-07"),
  V2024_04("2024-04"),
  V2024_01("2024-01"),
  V2023_10("2023-10");

  private String value;

  private Versions(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
