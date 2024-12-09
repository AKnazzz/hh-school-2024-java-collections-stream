package tasks;

import common.Person;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объединить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    if (persons1 == null || persons2 == null || limit < 0) {
      throw new IllegalArgumentException("Коллекции Person не должны быть null, а величина limit должна быть неотрицательна.");
    }

    return Stream.concat(persons1.stream(), persons2.stream())
            .sorted(Comparator.comparing(Person::createdAt))
            .limit(limit)
            .collect(Collectors.toList());     // Объединяем, сортируем и ограничиваем количество результатов
  }
}
