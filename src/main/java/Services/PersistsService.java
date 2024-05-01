package Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import Enum.CSVConst;
import Enum.Roles;
import Enum.Status;
import Helpers.CSV;
import Models.Administration;
import Models.Applicant;
import Models.Management;
import Models.Staff;
import Models.User;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;

public class PersistsService {
    private HashMap<String, ArrayList<User>> data = new HashMap<>();
    private static PersistsService instance;

    private static String STAFF_TABLE = "staff";
    private static String APPLICANT_TABLE = "‘recruits";

    private PersistsService() {
    }

    public ArrayList<User> staffData() {
        return data.get(STAFF_TABLE);
    }

    public ArrayList<User> applicantsData() {
        return data.get(APPLICANT_TABLE);
    }

    public static PersistsService get() {
        if (instance == null) {
            instance = new PersistsService();
        }
        return instance;
    }

    public void addStaff(Staff staff) {
        try {
            CSV.store("staff.csv", staff.getCSV());
        } catch (Exception e) {
            System.out.println(e);
        }
        staffData().add(staff);

        // System.out.println("CHeck here");
        data.put(STAFF_TABLE, staffData());
        count();
    }

    public void addApplicant(Applicant applicant) {
        try {
            applicant.setCreatedAt(LocalDate.now().toString());
            applicant.setCreatedBranch(AuthService.get().user().getBranch());
            applicant.setCreatedBy(AuthService.get().user().getId());
            applicant.setStatus(Status.PENDING.getValue());
            System.out.println(AuthService.get().user().getBranch());
            CSV.store("recruits.csv", applicant.getCSV());
        } catch (Exception e) {
            System.out.println(e);
        }
        applicantsData().add(applicant);

        // System.out.println("CHeck here");
        data.put(APPLICANT_TABLE, applicantsData());
        count();
    }

    public void mapCSVData(String... files) {

        for (String file : files) {
            CsvReader<CsvRecord> csv = CSV.read(file);
            boolean isFirstIteration = true;

            if (csv != null) {
                ArrayList<User> users = new ArrayList<>();
                try {
                    for (final CsvRecord csvRecord : csv) {
                        if (isFirstIteration) {
                            isFirstIteration = false;
                            continue;
                        }

                        System.out.println("records :" + file);
                        User usr = null;
                        if (csvRecord.getField(CSVConst.getFieldIndex(CSVConst.ROLE))
                                .equals(Roles.MANAGEMENT.getValue())) {
                            usr = new Management(
                                    csvRecord.getField(CSVConst.getFieldIndex(CSVConst.MGMT_LVL)),
                                    csvRecord.getField(CSVConst.getFieldIndex(CSVConst.BRANCH)));
                            usr.setId(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.ID)));
                        } else if (csvRecord.getField(CSVConst.getFieldIndex(CSVConst.ROLE))
                                .equals(Roles.ADMIN.getValue())) {
                            usr = new Administration(
                                    csvRecord.getField(CSVConst.getFieldIndex(CSVConst.EM_TYPE)));
                            usr.setId(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.ID)));
                            usr.setBranch(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.BRANCH)));
                        } else if (csvRecord.getField(CSVConst.getFieldIndex(CSVConst.ROLE))
                                .equals(Roles.APPLICANT.getValue())) {
                            try {

                                usr = new Applicant(
                                        LocalDate.parse(csvRecord.getField(CSVConst
                                                .getFieldIndexForHeading(CSVConst.INTW_DATE,
                                                        CSVConst.RECRUITS_CSV_HEADING))),
                                        csvRecord.getField(CSVConst
                                                .getFieldIndexForHeading(CSVConst.CREATED_BY,
                                                        CSVConst.RECRUITS_CSV_HEADING)),
                                        csvRecord.getField(CSVConst
                                                .getFieldIndexForHeading(CSVConst.CREATED_AT,
                                                        CSVConst.RECRUITS_CSV_HEADING)),
                                        csvRecord.getField(CSVConst
                                                .getFieldIndexForHeading(CSVConst.CREATED_BRANCH,
                                                        CSVConst.RECRUITS_CSV_HEADING)),
                                        csvRecord.getField(CSVConst
                                                .getFieldIndexForHeading(CSVConst.STATUS,
                                                        CSVConst.RECRUITS_CSV_HEADING)));
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        if (usr != null) {

                            usr.setFirstName(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.FNAME)));
                            usr.setLastName(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.FNAME)));
                            usr.setAddress(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.ADDRESS)));
                            usr.setPhone(Integer.parseInt(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.PHONE))));
                            usr.setEmail(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.EMAIL)));
                            usr.setUsername(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.USERNAME)));
                            usr.setPassword(csvRecord.getField(CSVConst.getFieldIndex(CSVConst.PASSWORD)));

                            users.add(usr);
                        }
                    }
                } catch (Exception e) {

                    System.out.println(e);
                }

                switch (file) {
                    case "staff.csv":
                        data.put(STAFF_TABLE, users);
                        break;
                    case "recruits.csv":
                        data.put(APPLICANT_TABLE, users);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private void count() {
        System.out.println("Staff count: " + data.get(STAFF_TABLE).size());
        System.out.println("Applicant count: " + data.get(APPLICANT_TABLE).size());
    }
}
