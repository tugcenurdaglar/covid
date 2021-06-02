package com.tugcenurdaglar.retro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryModel {
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Slug")
    @Expose
    private String slug;
    @SerializedName("ISO2")
    @Expose
    private String iso2;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }
}
