package Services;

import java.util.ArrayList;
import java.util.HashMap;

import Enum.Roles;
import Enum.StaffEnum;
import Helpers.CSV;
import Models.Administration;
import Models.Management;
import Models.Staff;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;

public class PersistsService {
    private HashMap<String, ArrayList<Staff>> data = new HashMap<>();
    private ArrayList<Staff> staff_data = new ArrayList<>();
    private static PersistsService instance;

    private static String STAFF_TABLE = "staff";

    private PersistsService() {
    }

    public ArrayList<Staff> staffData() {
        return staff_data;
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
        staff_data.add(staff);
        data.put(STAFF_TABLE, staff_data);
        // System.out.println(data.get(STAFF_TABLE).size());
    }

    public void mapStaffCSVData(String file) {

        CsvReader<CsvRecord> csv = CSV.read(file);
        boolean isFirstIteration = true;
        if (csv != null) {
            for (final CsvRecord csvRecord : csv) {
                if (isFirstIteration) {
                    isFirstIteration = false;
                    continue;
                }
                Staff st = null;
                if (csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.ROLE)).equals(Roles.MANAGEMENT.getValue())) {
                    st = new Management(
                            csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.MGMT_LVL)),
                            csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.BRANCH)));
                    st.setId(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.ID)));
                    System.out.println("Exe: MNG");
                } else if (csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.ROLE)).equals(Roles.ADMIN.getValue())) {
                    st = new Administration(
                            csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.EM_TYPE)));
                    st.setId(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.ID)));
                    System.out.println("Exe: ADMIN");
                }
                if (st != null) {
                    st.setFirstName(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.FNAME)));
                    st.setLastName(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.FNAME)));
                    st.setAddress(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.ADDRESS)));
                    st.setPhone(Integer.parseInt(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.PHONE))));
                    st.setEmail(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.EMAIL)));
                    st.setUsername(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.USERNAME)));
                    st.setPassword(csvRecord.getField(StaffEnum.getFieldIndex(StaffEnum.PASSWORD)));
                    staff_data.add(st);
                }

            }
            // System.out.println("DATA COUNT : "+staff_data.size());
            data.put(STAFF_TABLE, staff_data);
            // System.out.println("GetStaffData : "+this.staffData().size());
        }
    }
}
