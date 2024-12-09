package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Set<String> descriptions = new HashSet<>();

    Map<Integer, String> areaIdToNameMap = areas.stream() // Создаем мапу для быстрого поиска региона по его ID
            .collect(Collectors.toMap(Area::getId, Area::getName));

    for (Person person : persons) {
      Integer personId = person.id();
      Set<Integer> areaIds = personAreaIds.get(personId);

      if (areaIds != null) {
        String personName = person.firstName();

        for (Integer areaId : areaIds) {
          String areaName = areaIdToNameMap.get(areaId);
          if (areaName != null) {
            descriptions.add(personName + " - " + areaName);
          }
        }
      }
    }
    return descriptions;
  }
}
