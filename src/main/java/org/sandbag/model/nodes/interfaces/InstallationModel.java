package org.sandbag.model.nodes.interfaces;

import org.neo4j.graphdb.Label;
import org.sandbag.model.nodes.*;

/**
 * Created by pablo on 15/02/16.
 */
public interface InstallationModel extends Label{

    public static String POWER_FLAG_REASON_ARTICLE10C = "Article10c";
    public static String POWER_FLAG_REASON_NACE_CODES = "Has one of these NACE codes: 35.00, 35.10, 35.11, 35.12, 35.13, 35.14, 35.30";

    String LABEL = "INSTALLATION";
    String id = "id";
    String name = "name";
    String city = "city";
    String postCode = "post_code";
    String address = "address";
    String eprtrId = "EPRTR_ID";
    String permitId = "permit_id";
    String permitEntryDate = "permit_entry_date";
    String permitExpiryOrRevocationDate = "permit_expiry_or_revocation_date";
    String latitude = "latitude";
    String longitude = "longitude";
    String powerFlag = "power_flag";
    String powerFlagReason = "power_flag_reason";

    String getId();
    Country getCountry();
    Company getCompany();
    String getPostCode();
    String getName();
    String getCity();
    String getAddress();
    String getEprtrId();
    String getPermitId();
    String getPermitEntryDate();
    String getPermitExpiryOrRevocationDate();
    String getLatitude();
    String getLongitude();
    String getPowerFlag();
    String getPowerFlagReason();
    Sector getSector();
    NACECode getNACECode();



    void setId(String id);
    void setCountry(Country country);
    void setCompany(Company company);
    void setCity(String city);
    void setPostCode(String postCode);
    void setName(String name);
    void setAddress(String address);
    void setEprtrId(String eprtrId);
    void setPermitId(String permitId);
    void setPermitEntryDate(String date);
    void setPermitExpiryOrRevocationDate(String date);
    void setLatitude(String latitude);
    void setLongitude(String longitude);
    void setPowerFlag(String value);
    void setPowerFlagReason(String value);
    void setSector(Sector sector);
    void setVerifiedEmissionsForPeriod(Period period, double value);
    void setOffsetEntitlementForPeriod(Period period, String value);
    void setSurrenderedUnitsForPeriod(Period period, double value);
    void setComplianceForPeriod(Period period, String value);
    void setAllowancesInAllocationForPeriod(Period period, double value, String type);
    void setNACECode(NACECode naceCode);
    void setFuelType(FuelType fuelType, String note, String source);

    String getOffsetEntitlementForPeriod(Period period);

}
