package service;

import model.Error;

import java.util.ArrayList;
import java.util.List;

public class ListSplitService {

    /**
     * Splits the error list based on the email address.
     *
     * @return the splited lists
     */
    public static List<List<Error>> splitList(List<Error> errorList) {
        List<List<Error>> lists = new ArrayList<>();
        boolean found;
        for (Error e : errorList) {
            found = false;
            // check if list for this mail exists
            for(List<Error> list : lists) {
                 if(list.get(0).getMail().equals(e.getMail())) {
                     // yes, add error to the list
                     list.add(e);
                     found = true;
                     break;
                 }
            }
            if (!found) {
                // not found, create new list and add the error
                List<Error> newList = new ArrayList<>();
                newList.add(e);
                lists.add(newList);
            }
        }
        return lists;
    }
}
