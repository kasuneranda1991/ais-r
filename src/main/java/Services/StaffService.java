package Services;

import java.util.ArrayList;
import java.util.function.Predicate;

import Enum.CSVConst;
import Models.Staff;
import Models.User;

public class StaffService {

    private static ArrayList<User> data = PersistsService.get().staffData();

    /**
     * Find a staff using id
     * 
     * @param id
     * @return
     */
    public static Staff findById(String id) {
        return find(staff -> staff.getId().equals(id));
    }

    public static Staff findByFieldName(CSVConst field, String value) {
        switch (field) {
            case CSVConst.EMAIL:
                return find(staff -> staff.getEmail().equals(value));
            default:
                break;
        }
        return null;
    }

    private static Staff find(Predicate<Staff> logic) {
        for (User stf : data) {
            if (logic.test((Staff) stf)) {
                return (Staff) stf;
            }
        }
        return null;
    }

}