package lambda;

import org.nutz.json.Json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ruizhou on 15/10/24.
 */
public class FirstLambda {
    public static void main(String[] args){
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("rui", "zhou"));
        personList.add(new Person("erge", "guan"));
        personList.add(new Person("sange", "xiao"));
        personList.add(new Person("wu", "wang"));

        Collections.sort(personList, (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()));

        System.out.print(Json.toJson(personList));

    }
}
