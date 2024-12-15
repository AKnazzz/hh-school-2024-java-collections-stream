package tasks;

import common.Person;
import common.PersonService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {

    Map<Integer, Person> personsMap = personService.findPersons(personIds).stream()
            .collect(toMap(Person::id, person -> person));

    return personIds.stream()
            .map(personsMap::get)
            .toList();
  }
}

/*
Оценка асимптотики:
---> Создание Map: O(n)
---> Итерация по personIds: O(n)
---> Итоговая асимптотическая сложность будет: O(n).
 */