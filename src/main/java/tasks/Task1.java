package tasks;

import common.Person;
import common.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    Set<Person> persons = personService.findPersons(personIds);

    Map<Integer, Person> personsMap = persons.stream()  // Создаем Map для ID ---> Person
            .collect(Collectors.toMap(Person::id, person -> person));

    return personIds.stream() // Сортируем список по порядку из personIds
            .map(personsMap::get)
            .filter(Objects::nonNull) // можно убрать, если гарантируется что все Id ---> Person
            .collect(Collectors.toList());
  }
}

/*
Оценка асимптотики:
---> Создание Map: O(n), где n — количество элементов в Set<Person>;
---> Итерация по personIds: O(m), где m — кол-во Id.
===> Асимптотика работы метода findOrderedPersons будет: == O(n + m).
 */