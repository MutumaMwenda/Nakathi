package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContributionTypes {
    public ContributionTypes(String member_id, String reg_no, String id, String isfixed, String isshare, String frequency, String minimum, String maximum, String amount, String contribution_source, String name) {
        this.member_id = member_id;
        this.reg_no = reg_no;
        this.id = id;
        this.isfixed = isfixed;
        this.isshare = isshare;
        this.frequency = frequency;
        this.minimum = minimum;
        this.maximum = maximum;
        this.amount = amount;
        this.contribution_source = contribution_source;
        this.name = name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(String isfixed) {
        this.isfixed = isfixed;
    }

    public String getIsshare() {
        return isshare;
    }

    public void setIsshare(String isshare) {
        this.isshare = isshare;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContribution_source() {
        return contribution_source;
    }

    public void setContribution_source(String contribution_source) {
        this.contribution_source = contribution_source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("member_id")
    @Expose
    public String member_id;
    @SerializedName("reg_no")
    @Expose
    public String reg_no;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("isfixed")
    @Expose
    public String isfixed;
    @SerializedName("isshare")
    @Expose
    public String isshare;
    @SerializedName("frequency")
    @Expose
    public String frequency;
    @SerializedName("minimum")
    @Expose
    public String minimum;
    @SerializedName("maximum")
    @Expose
    public String maximum;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("contribution_source")
    @Expose
    public String contribution_source;
    @SerializedName("name")
    @Expose
    public String name;

}
