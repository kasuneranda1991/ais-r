package Controllers.Services;

import java.util.ArrayList;
import java.util.function.Predicate;

import Controllers.Enum.CSVConst;
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
        if (field.equals(CSVConst.EMAIL)) {
            return find(staff -> staff.getEmail().equals(value));
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
