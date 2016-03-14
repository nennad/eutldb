package org.sandbag.model;

import org.neo4j.graphdb.Label;

/**
 * Created by pablo on 15/02/16.
 */
public interface InstallationModel extends Label{

    String LABEL = "INSTALLATION";
    String id = "id";
    String name = "name";
    String city = "city";
    String postCode = "post_code";
    String open = "open";
    String address = "address";
    String eprtrId = "EPRTR_ID";
    String permitId = "permit_id";
    String permitEntryDate = "permit_entry_date";
    String permitExpiryOrRevocationDate = "permit_expiry_or_revocation_date";

    String getId();
    Country getCountry();
    Company getCompany();
    String getPostCode();
    boolean getOpen();
    String getName();
    String getCity();
    String getAddress();
    String getEprtrId();
    String getPermitId();
    String getPermitEntryDate();
    String getPermitExpiryOrRevocationDate();
    Sector getSector();



    void setId(String id);
    void setCountry(Country country);
    void setCompany(Company company);
    void setOpen(boolean open);
    void setCity(String city);
    void setPostCode(String postCode);
    void setName(String name);
    void setAddress(String address);
    void setEprtrId(String eprtrId);
    void setPermitId(String permitId);
    void setPermitEntryDate(String date);
    void setPermitExpiryOrRevocationDate(String date);
    void setSector(Sector sector);
    void setVerifiedEmissionsForPeriod(Period period, double value);
    void setFreeAllocationForPeriod(Period period, double value, String type);
    void setOffsetsForPeriod(Period period, double value, String type);
    void setOffsetEntitlementForPeriod(Period period, double value, String type);

}
