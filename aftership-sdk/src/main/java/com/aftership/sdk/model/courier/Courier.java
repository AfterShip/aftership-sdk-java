package com.aftership.sdk.model.courier;

import lombok.Data;

import java.util.List;

@Data
public class Courier {
    private String slug;
    private String name;
    private String phone;
    private String otherName;
    private String webUrl;
    private List<String> requiredFields;
    private List<String> optionalFields;
//    private String defaultLanguage;
//    private String supportLanguages;
//    private String serviceFromCountryIso3;
}
