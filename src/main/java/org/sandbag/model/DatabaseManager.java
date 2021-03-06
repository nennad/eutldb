package org.sandbag.model;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexCreator;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.sandbag.model.nodes.*;
import org.sandbag.model.nodes.interfaces.*;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15/03/16.
 */
public class DatabaseManager {

    public static GraphDatabaseService graphDb;
    public static Schema schema;

    public static Label COUNTRY_LABEL = DynamicLabel.label(CountryModel.LABEL);
    public static Label COMPANY_LABEL = DynamicLabel.label(CompanyModel.LABEL);
    public static Label INSTALLATION_LABEL = DynamicLabel.label(InstallationModel.LABEL);
    public static Label SECTOR_LABEL = DynamicLabel.label(SectorModel.LABEL);
    public static Label PERIOD_LABEL = DynamicLabel.label(PeriodModel.LABEL);
    public static Label AIRCRAFT_OPERATOR_LABEL = DynamicLabel.label(AircraftOperatorModel.LABEL);
    public static Label PROJECT_LABEL = DynamicLabel.label(ProjectModel.LABEL);
    public static Label OFFSET_LABEL = DynamicLabel.label(OffsetModel.LABEL);
    public static Label SANDBAG_SECTOR_LABEL = DynamicLabel.label(SandbagSectorModel.LABEL);
    public static Label NACE_CODE_LABEL = DynamicLabel.label(NACECodeModel.LABEL);
    public static Label NER300_LABEL = DynamicLabel.label(NER300Model.LABEL);
    public static Label FUEL_TYPE_LABEL = DynamicLabel.label(FuelType.LABEL);

    public static IndexDefinition installationIdIndex = null;
    public static IndexDefinition countryNameIndex = null;
    public static IndexDefinition countryIdIndex = null;
    public static IndexDefinition periodNameIndex = null;
    public static IndexDefinition sectorNameIndex = null;
    public static IndexDefinition sectorIdIndex = null;
    public static IndexDefinition companyNameIndex = null;
    public static IndexDefinition companyRegistrationNumberIndex = null;
    public static IndexDefinition projectIdIndex = null;
    public static IndexDefinition sandbagSectorNameIndex = null;
    public static IndexDefinition sandbagSectorIdIndex = null;
    public static IndexDefinition naceCodeIdIndex = null;
    public static IndexDefinition fuelTypeNameIndex = null;
    public static IndexDefinition aircraftOperatorUniqueCodeUnderCommissionIndex = null;
    public static IndexDefinition aircraftOperatorIdIndex = null;


    /**
     * Constructor
     *
     * @param dbFolder
     */
    public DatabaseManager(String dbFolder) {
        initDatabase(dbFolder);
    }

    /**
     * @return
     */
    public Transaction beginTransaction() {
        return graphDb.beginTx();
    }

    /**
     * Database initialization
     *
     * @param dbFolder
     */
    private void initDatabase(String dbFolder) {
        if (graphDb == null) {
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(dbFolder));

            try {

                Transaction tx = graphDb.beginTx();

                schema = graphDb.schema();

                System.out.println("Creating/checking indices...");

                if(!schema.getIndexes(INSTALLATION_LABEL).iterator().hasNext()){

                    System.out.println("Creating index for Installations");

                    installationIdIndex = schema.indexFor(INSTALLATION_LABEL)
                            .on(InstallationModel.id)
                            .create();
                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(installationIdIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(AIRCRAFT_OPERATOR_LABEL).iterator().hasNext()){

                    System.out.println("Creating index for Aircraft Operators");

                    aircraftOperatorIdIndex = schema.indexFor(AIRCRAFT_OPERATOR_LABEL)
                            .on(AircraftOperatorModel.id)
                            .create();
                    aircraftOperatorUniqueCodeUnderCommissionIndex = schema.indexFor(AIRCRAFT_OPERATOR_LABEL)
                            .on(AircraftOperatorModel.uniqueCodeUnderCommissionRegulation)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(aircraftOperatorIdIndex, 10, TimeUnit.SECONDS);
                    schema.awaitIndexOnline(aircraftOperatorUniqueCodeUnderCommissionIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(INSTALLATION_LABEL).iterator().hasNext()){

                    System.out.println("Creating index for Installations");

                    installationIdIndex = schema.indexFor(INSTALLATION_LABEL)
                            .on(InstallationModel.id)
                            .create();
                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(installationIdIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(COUNTRY_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for countries");

                    countryNameIndex = schema.indexFor(COUNTRY_LABEL)
                            .on(CountryModel.name)
                            .create();

                    countryIdIndex = schema.indexFor(COUNTRY_LABEL)
                            .on(CountryModel.id)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(countryNameIndex, 10, TimeUnit.SECONDS);
                    schema.awaitIndexOnline(countryIdIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(PERIOD_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for Periods");

                    periodNameIndex = schema.indexFor(PERIOD_LABEL)
                            .on(PeriodModel.name)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(periodNameIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(SECTOR_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for Sectors");

                    sectorNameIndex = schema.indexFor(SECTOR_LABEL)
                            .on(SectorModel.name)
                            .create();

                    sectorIdIndex = schema.indexFor(SECTOR_LABEL)
                            .on(SectorModel.id)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(sectorNameIndex, 10, TimeUnit.SECONDS);
                    schema.awaitIndexOnline(sectorIdIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(COMPANY_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for Companies");

                    companyNameIndex = schema.indexFor(COMPANY_LABEL)
                            .on(CompanyModel.name)
                            .create();

                    companyRegistrationNumberIndex = schema.indexFor(COMPANY_LABEL)
                            .on(CompanyModel.registrationNumber)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(companyNameIndex, 10, TimeUnit.SECONDS);
                    schema.awaitIndexOnline(companyRegistrationNumberIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(PROJECT_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for Projects");

                    projectIdIndex = schema.indexFor(PROJECT_LABEL)
                            .on(ProjectModel.id)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(projectIdIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(SANDBAG_SECTOR_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for Sandbag Sectors");

                    sandbagSectorIdIndex = schema.indexFor(SANDBAG_SECTOR_LABEL)
                            .on(SandbagSectorModel.id)
                            .create();

                    sandbagSectorNameIndex = schema.indexFor(SANDBAG_SECTOR_LABEL)
                            .on(SandbagSectorModel.name)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(sandbagSectorIdIndex, 10, TimeUnit.SECONDS);
                    schema.awaitIndexOnline(sandbagSectorNameIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(NACE_CODE_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for NACECodes");

                    naceCodeIdIndex = schema.indexFor(NACE_CODE_LABEL)
                            .on(NACECodeModel.id)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(naceCodeIdIndex, 10, TimeUnit.SECONDS);
                }

                if(!schema.getIndexes(FUEL_TYPE_LABEL).iterator().hasNext()){

                    System.out.println("Creating indices for FuelTypes");

                    fuelTypeNameIndex = schema.indexFor(FUEL_TYPE_LABEL)
                            .on(FuelTypeModel.name)
                            .create();

                    tx.success();
                    tx.close();
                    tx = graphDb.beginTx();

                    schema.awaitIndexOnline(fuelTypeNameIndex, 10, TimeUnit.SECONDS);
                }

                System.out.println("Done!");

                tx.success();
                tx.close();

                System.out.println("Done!");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    public AircraftOperator createAircraftOperator(String id,
                                                   String name,
                                                   String city,
                                                   String postCode,
                                                   String address,
                                                   String eprtrId,
                                                   String status,
                                                   String uniqueCodeUnderCommissionRegulation,
                                                   String monitoringPlanId,
                                                   String monitoringPlanYearOfApplicability,
                                                   String monitoringPlanYearOfExpiry,
                                                   String icaoDesignator,
                                                   String latitude,
                                                   String longitude,
                                                   Country country,
                                                   Company company,
                                                   Sector sector) {

        Node aircraftOperatorNode = graphDb.createNode(AIRCRAFT_OPERATOR_LABEL);

        AircraftOperator aircraftOperator = new AircraftOperator(aircraftOperatorNode);
        aircraftOperator.setId(id);
        aircraftOperator.setName(name);
        aircraftOperator.setStatus(status);
        aircraftOperator.setCity(city);
        aircraftOperator.setPostCode(postCode);
        aircraftOperator.setAddress(address);
        aircraftOperator.setEprtrId(eprtrId);
        aircraftOperator.setUniqueCodeUnderCommissionRegulation(uniqueCodeUnderCommissionRegulation);
        aircraftOperator.setMonitoringPlanId(monitoringPlanId);
        aircraftOperator.setMonitoringPlanFirstYearOfApplicability(monitoringPlanYearOfApplicability);
        aircraftOperator.setMonitoringPlanYearOfExpiry(monitoringPlanYearOfExpiry);
        aircraftOperator.setIcaoDesignator(icaoDesignator);
        aircraftOperator.setLatitude(latitude);
        aircraftOperator.setLongitude(longitude);

        if (country != null) {
            aircraftOperator.setCountry(country);
        }
        if (company != null) {
            aircraftOperator.setCompany(company);
        }
        if (sector != null) {
            aircraftOperator.setSector(sector);
        }

        return aircraftOperator;
    }

    public Offset createOffset2013Onwards(String amountSt,
                                          String unitTypeSt,
                                          String typeSt,
                                          String referenceSt,
                                          Country euCountry,
                                          Period period){

        Double amount = Double.parseDouble(amountSt);
        Node offsetNode = graphDb.createNode(OFFSET_LABEL);
        Offset offset = new Offset(offsetNode);
        offset.setAmount(amount);

        if (unitTypeSt.startsWith("CER")) {
            offset.setUnitType(OffsetModel.CER_UNIT_TYPE);
        } else if (unitTypeSt.startsWith("AAU")) {
            offset.setUnitType(OffsetModel.AAU_UNIT_TYPE);
        } else if (unitTypeSt.startsWith("ERU")) {
            offset.setUnitType(OffsetModel.ERU_UNIT_TYPE);
        } else {
            offset.setUnitType(unitTypeSt);
        }

        offset.setPeriod(period);

        euCountry.setOffsets2013Onwards(offset, typeSt, referenceSt);

        return offset;

    }

    public Offset createOffset(String amountSt,
                               String unitTypeSt,
                               Installation installation,
                               Project project,
                               Period period,
                               Country originatingCountry) {

        try {
            Double amount = Double.parseDouble(amountSt);

            Node offsetNode = graphDb.createNode(OFFSET_LABEL);
            Offset offset = new Offset(offsetNode);

            offset.setAmount(amount);

            if (unitTypeSt.startsWith("CER")) {
                offset.setUnitType(OffsetModel.CER_UNIT_TYPE);
            } else if (unitTypeSt.startsWith("AAU")) {
                offset.setUnitType(OffsetModel.AAU_UNIT_TYPE);
            } else if (unitTypeSt.startsWith("ERU")) {
                offset.setUnitType(OffsetModel.ERU_UNIT_TYPE);
            } else {
                offset.setUnitType(unitTypeSt);
            }


            if (installation != null) {
                offset.setInstallation(installation);
            }
            if (project != null) {
                offset.setProject(project);
            }
            if (period != null) {
                offset.setPeriod(period);
            }
            if (originatingCountry != null) {
                offset.setOriginatingCountry(originatingCountry);
            }

            return offset;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public Offset createOffset(String amountSt,
                               String unitTypeSt,
                               AircraftOperator aircraftOperator,
                               Project project,
                               Period period,
                               Country originatingCountry) {

        try {
            Double amount = Double.parseDouble(amountSt);

            Node offsetNode = graphDb.createNode(OFFSET_LABEL);
            Offset offset = new Offset(offsetNode);

            offset.setAmount(amount);

            if (unitTypeSt.startsWith("CER")) {
                offset.setUnitType(OffsetModel.CER_UNIT_TYPE);
            } else if (unitTypeSt.startsWith("AAU")) {
                offset.setUnitType(OffsetModel.AAU_UNIT_TYPE);
            } else if (unitTypeSt.startsWith("ERU")) {
                offset.setUnitType(OffsetModel.ERU_UNIT_TYPE);
            } else {
                offset.setUnitType(unitTypeSt);
            }


            if (aircraftOperator != null) {
                offset.setAircraftOperator(aircraftOperator);
            }
            if (project != null) {
                offset.setProject(project);
            }
            if (period != null) {
                offset.setPeriod(period);
            }
            if (originatingCountry != null) {
                offset.setOriginatingCountry(originatingCountry);
            }

            return offset;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public Installation createInstallation(String id,
                                           String name,
                                           String city,
                                           String postCode,
                                           String address,
                                           String eprtrId,
                                           String permitId,
                                           String permitEntryDate,
                                           String permitExpiryOrRevocationDate,
                                           String latitude,
                                           String longitude,
                                           Country country,
                                           Company company,
                                           Sector sector) {

        Node installationNode = graphDb.createNode(INSTALLATION_LABEL);

        Installation installation = new Installation(installationNode);
        installation.setId(id);
        installation.setName(name);
        //installation.setOpen(open.toLowerCase().equals("open"));
        installation.setCity(city);
        installation.setPostCode(postCode);
        installation.setAddress(address);
        installation.setEprtrId(eprtrId);
        installation.setPermitId(permitId);
        installation.setPermitEntryDate(permitEntryDate);
        installation.setPermitExpiryOrRevocationDate(permitExpiryOrRevocationDate);
        installation.setLatitude(latitude);
        installation.setLongitude(longitude);
        installation.setPowerFlag("false");
        installation.setPowerFlagReason("");

        if (country != null) {
            installation.setCountry(country);
        }
        if (company != null) {
            installation.setCompany(company);
        }
        if (sector != null) {
            installation.setSector(sector);
        }


        return installation;

    }

    public Country createCountry(String name, String id) {
        Node countryNode = graphDb.createNode(COUNTRY_LABEL);

        Country country = new Country(countryNode);
        country.setName(name);
        country.setId(id);

        return country;
    }

    public NACECode createNACECode(String id, String description) {
        Node naceCodeNode = graphDb.createNode(NACE_CODE_LABEL);

        NACECode naceCode = new NACECode(naceCodeNode);
        naceCode.setDescription(description);
        naceCode.setId(id);

        return naceCode;
    }

    public Period createPeriod(String value) {
        Node periodNode = graphDb.createNode(PERIOD_LABEL);

        Period period = new Period(periodNode);
        period.setName(value);

        return period;
    }

    public FuelType createFuelType(String value) {
        Node fuelTypeNode = graphDb.createNode(FUEL_TYPE_LABEL);

        FuelType fuelType = new FuelType(fuelTypeNode);
        fuelType.setName(value);

        return fuelType;
    }

    public Project createProject(String id) {
        Node projectNode = graphDb.createNode(PROJECT_LABEL);

        Project project = new Project(projectNode);
        project.setId(id);

        return project;
    }

    public Sector createSector(String id, String name) {
        Node sectorNode = graphDb.createNode(SECTOR_LABEL);

        Sector sector = new Sector(sectorNode);
        sector.setName(name);
        sector.setId(id);

        return sector;
    }

    public SandbagSector createSandbagSector(String id, String name) {
        Node sandbagSectorNode = graphDb.createNode(SANDBAG_SECTOR_LABEL);

        SandbagSector sector = new SandbagSector(sandbagSectorNode);
        sector.setName(name);
        sector.setId(id);

        return sector;
    }

    public Company createCompany(String name,
                                 String registrationNumber,
                                 String postalCode,
                                 String city,
                                 String address,
                                 String status,
                                 String subsidiaryCompany,
                                 String parentCompany) {

        Node companyNode = graphDb.createNode(COMPANY_LABEL);

        Company company = new Company(companyNode);
        company.setName(name);
        company.setAddress(address);
        company.setCity(city);
        company.setPostalCode(postalCode);
        company.setRegistrationNumber(registrationNumber);
        company.setStatus(status);
        company.setSubsidiaryCompany(subsidiaryCompany);
        company.setParentCompany(parentCompany);

        return company;
    }

    public Country getCountryByName(String value) {
        Country country = null;
        Node countryNode = graphDb.findNode(COUNTRY_LABEL, CountryModel.name, value);
        if (countryNode != null) {
            country = new Country(countryNode);
        }
        return country;

    }

    public Country getCountryById(String id) {
        Country country = null;
        Node countryNode = graphDb.findNode(COUNTRY_LABEL, CountryModel.id, id);
        if (countryNode != null) {
            country = new Country(countryNode);
        }
        return country;
    }

    public NACECode getNACECodeById(String id) {
        NACECode naceCode = null;
        Node naceCodeNode = graphDb.findNode(NACE_CODE_LABEL, NACECodeModel.id, id);
        if (naceCodeNode != null) {
            naceCode = new NACECode(naceCodeNode);
        }
        return naceCode;
    }


    public Installation getInstallationById(String id) {
        Installation installation = null;
        Node installationNode = graphDb.findNode(INSTALLATION_LABEL, InstallationModel.id, id);
        if (installationNode != null) {
            installation = new Installation(installationNode);
        }
        return installation;
    }

    public AircraftOperator getAircraftOperatorById(String id) {
        AircraftOperator aircraftOperator = null;
        Node aircraftOperatorNode = graphDb.findNode(AIRCRAFT_OPERATOR_LABEL, AircraftOperatorModel.id, id);
        if (aircraftOperatorNode != null) {
            aircraftOperator = new AircraftOperator(aircraftOperatorNode);
        }
        return aircraftOperator;
    }

    public AircraftOperator getAircraftOperatorByUniqueCodeUnderCommissionRegulation(String id) {
        AircraftOperator aircraftOperator = null;
        Node aircraftOperatorNode = graphDb.findNode(AIRCRAFT_OPERATOR_LABEL,
                AircraftOperatorModel.uniqueCodeUnderCommissionRegulation, id);
        if (aircraftOperatorNode != null) {
            aircraftOperator = new AircraftOperator(aircraftOperatorNode);
        }
        return aircraftOperator;
    }

    public Period getPeriodByName(String value) {
        Period period = null;
        Node periodNode = graphDb.findNode(PERIOD_LABEL, PeriodModel.name, value);
        if (periodNode != null) {
            period = new Period(periodNode);
        }
        return period;
    }

    public FuelType getFuelType(String value) {
        FuelType fuelType = null;
        Node fuelTypeNode = graphDb.findNode(FUEL_TYPE_LABEL, FuelTypeModel.name, value);
        if (fuelTypeNode != null) {
            fuelType = new FuelType(fuelTypeNode);
        }
        return fuelType;
    }

    public Sector getSectorById(String id) {
        Sector sector = null;
        Node sectorNode = graphDb.findNode(SECTOR_LABEL, SectorModel.id, id);
        if (sectorNode != null) {
            sector = new Sector(sectorNode);
        }
        return sector;
    }

    public SandbagSector getSandbagSectorById(String id) {
        SandbagSector sector = null;
        Node sectorNode = graphDb.findNode(SANDBAG_SECTOR_LABEL, SandbagSectorModel.id, id);
        if (sectorNode != null) {
            sector = new SandbagSector(sectorNode);
        }
        return sector;
    }

    public SandbagSector getSandbagSectorByName(String value) {
        SandbagSector sector = null;
        Node sectorNode = graphDb.findNode(SANDBAG_SECTOR_LABEL, SandbagSectorModel.name, value);
        if (sectorNode != null) {
            sector = new SandbagSector(sectorNode);
        }
        return sector;
    }

    public Project getProjectById(String id) {
        Project project = null;
        Node projectNode = graphDb.findNode(PROJECT_LABEL, ProjectModel.id, id);
        if (projectNode != null) {
            project = new Project(projectNode);
        }
        return project;
    }

    public Company getCompanyByRegistrationNumber(String registrationNumber) {
        Company company = null;
        Node companyNode = graphDb.findNode(COMPANY_LABEL, CompanyModel.registrationNumber, registrationNumber);
        if (companyNode != null) {
            company = new Company(companyNode);
        }
        return company;
    }

    public Company getCompanyByName(String value) {
        Company company = null;
        Node companyNode = graphDb.findNode(COMPANY_LABEL, CompanyModel.name, value);
        if (companyNode != null) {
            company = new Company(companyNode);
        }
        return company;
    }

    public void shutdown() {
        graphDb.shutdown();
        graphDb = null;
    }

    public NER300 getNER300Node(){
        NER300 ner300 = null;
        Iterator<Node> iterator = findNodes(NER300_LABEL);
        if(!iterator.hasNext()){
            ner300 = new NER300(graphDb.createNode(NER300_LABEL));
        }else{
            ner300 = new NER300(iterator.next());
        }
        return ner300;
    }

    public Iterator<Node> findNodes(Label label){
        return graphDb.findNodes(label);
    }
}
