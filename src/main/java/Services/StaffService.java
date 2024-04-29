package Services;

import java.util.ArrayList;
import java.util.function.Predicate;

import Enum.StaffEnum;
import Models.Staff;

public class StaffService {

    private static ArrayList<Staff> data = PersistsService.get().staffData();

    /**
     * Find a staff using id
     * 
     * @param id
     * @return
     */
    public static Staff findById(String id) {
        return find(staff -> staff.getId().equals(id));
    }

    public static Staff findByFieldName(StaffEnum field, String value) {
        switch (field) {
            case StaffEnum.EMAIL:
                return find(staff -> staff.getEmail().equals(value));
            default:
                break;
        }
        return null;
        // for (Staff staff : data) {
        // switch (field) {
        // case StaffEnum.ADDRESS:
        // if (staff.getEmail().equals(value)) {
        // System.out.println(staff.getCSV());
        // }
        // break;

        // default:
        // break;
        // }
        // }
        // return null;
    }

    private static Staff find(Predicate<Staff> logic) {
        for (Staff stf : data) {
            if (logic.test(stf)) {
                return stf;
            }
        }
        return null;
    }

}
