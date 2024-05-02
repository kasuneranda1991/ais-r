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

    private static String ADMIN = Roles.ADMIN.getValue();

    private PersistsService() {
    }

    public static PersistsService get() {
        if (instance == null) {
            instance = new PersistsService();
        }
        return instance;
    }

    public ArrayList<User> staffData() {
        return data.get(STAFF_TABLE);
    }

    public ArrayList<User> applicantsData() {
        return data.get(APPLICANT_TABLE);
    }

    public void updateApplicant(Applicant applicant, HashMap<CSVConst, String> data) {
        if (data.containsKey(CSVConst.STATUS)) {
            applicant.setStatus(data.get(CSVConst.STATUS));
        }
        try {
            CSV.update(APPLICANT_TABLE, applicant);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStaff(Staff staff) {
        try {
            CSV.store("staff.csv", staff.getCSV());
        } catch (Exception e) {
            System.out.println(e);
        }
        staffData().add(staff);
        count();
    }

    public void addApplicant(Applicant applicant) {
        try {
            applicant.setCreatedAt(LocalDate.now().toString());
            applicant.setCreatedBranch(AuthService.get().user().getBranch());
            applicant.setCreatedBy(AuthService.get().user().getId());
            applicant.setStatus(Status.PENDING.getValue());
            CSV.store("recruits.csv", applicant.getCSV());
        } catch (Exception e) {
            System.out.println(e);
        }
        applicantsData().add(applicant);
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
                        User usr = null;
                        switch (getField(csvRecord, CSVConst.ROLE)) {
                            case "Management":
                                usr = new Management(
                                        getField(csvRecord, CSVConst.MGMT_LVL),
                                        getField(csvRecord, CSVConst.BRANCH));
                                usr.setId(getField(csvRecord, CSVConst.ID));
                                break;
                            case "Admin":
                                usr = new Administration(
                                        getField(csvRecord, CSVConst.EM_TYPE));
                                usr.setId(getField(csvRecord, CSVConst.ID));
                                usr.setBranch(getField(csvRecord, CSVConst.BRANCH));
                                break;
                            case "Applicant":
                                try {
                                    usr = new Applicant(
                                            LocalDate.parse(getField(csvRecord, CSVConst.INTW_DATE).trim()),
                                            getField(csvRecord, CSVConst.CREATED_BY),
                                            getField(csvRecord, CSVConst.CREATED_AT),
                                            getField(csvRecord, CSVConst.CREATED_BRANCH),
                                            getField(csvRecord, CSVConst.STATUS),
                                            getField(csvRecord, CSVConst.EDU));
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                break;
                            default:
                                break;
                        }
                        if (usr != null) {

                            usr.setFirstName(getField(csvRecord, CSVConst.FNAME));
                            usr.setLastName(getField(csvRecord, CSVConst.LNAME));
                            usr.setAddress(getField(csvRecord, CSVConst.ADDRESS));
                            usr.setPhone(Integer.parseInt(getField(csvRecord, CSVConst.PHONE)));
                            usr.setEmail(getField(csvRecord, CSVConst.EMAIL));
                            usr.setUsername(getField(csvRecord, CSVConst.USERNAME));
                            usr.setPassword(getField(csvRecord, CSVConst.PASSWORD));
                            usr.setRole(getField(csvRecord, CSVConst.ROLE));

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

    public String getField(CsvRecord record, CSVConst field) {
        return record.getField(CSVConst.getFieldIndex(field));
    }

    private void count() {
        System.out.println("Staff count: " + data.get(STAFF_TABLE).size());
        System.out.println("Applicant count: " + data.get(APPLICANT_TABLE).size());
    }
}
