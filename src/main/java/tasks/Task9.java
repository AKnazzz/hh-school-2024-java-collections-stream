package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  // Удалено поле count, так как оно не нужно
  // Можно использовать метод count() для подсчета четных чисел

  // Конвертируем начиная со второго элемента списка
  /*
  Удаление первого элемента с помощью persons.remove(0) изменяет исходный список, что может быть нежелательно.
  Проверка на пустоту списка выполняется, но удаление элемента все равно происходит, если список не пуст.
  Удаление элемента из начала списка неэффективно для больших списков.
   */
  public List<String> getNames(List<Person> persons) {
    if (persons == null || persons.isEmpty()) {   // изменено для улучшения читаемости
      return Collections.emptyList();
    }
    return persons.stream()
            .skip(1) // Вместо удаления элемента мы пропускаем элемент в стриме. Это сохраняет исходный список неизменным и более эффективно.
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Получаем уникальные имена персон без учета первого элемента
  public Set<String> getDifferentNames(List<Person> persons) {
    if (persons == null) {
      throw new IllegalArgumentException("Список persons не должен быть null");
    }
    return getNames(persons).stream()
            .filter(name -> name != null && !name.isEmpty()) // Фильтруем null и пустые строки
            .collect(Collectors.toSet()); // Собираем в Set
  }

  // Склеиваем ФИО, убираем лишние проверки
  public String convertPersonToString(Person person) {
    if (person == null) {
      return ""; // можно выбросить исключение, если это более уместно
    }
    return String.join(" ",
            Optional.ofNullable(person.secondName()).orElse(""),
            Optional.ofNullable(person.firstName()).orElse(""),
            Optional.ofNullable(person.middleName()).orElse("") // ФИО
    ).trim();
  }

  // Создаем словарь id персоны -> ее имя
  /*
    Преобразуем коллекцию в стрим, что позволяет использовать функциональные операции.
    Убирает дубликаты, если это необходимо (если ожидаются дубликаты).
    Создаем Map, где ключом является id человека, а значением — строковое представление ФИО Person.
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    if (persons == null) {
      throw new IllegalArgumentException("Collection не должна быть null");
    }
    return persons.stream()
            .distinct() // если нужно убрать дубликаты по объектам Person
            .collect(Collectors.toMap(Person::id, this::convertPersonToString
            ));
  }

  // Проверяем на совпадение персон в двух коллекциях
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    if (persons1 == null || persons2 == null) {
      throw new IllegalArgumentException("Collections не должны быть null"); // можно вернуть false
    }
    Set<Person> set1 = new HashSet<>(persons1); // Создаем HashSet для первой коллекции
    set1.retainAll(persons2); // Оставляем только те элементы, которые есть в persons2
    return !set1.isEmpty(); // Если остались элементы, значит есть совпадения
  }

  // Посчитать число четных чисел
  /*
    Вместо переменной count и итерацию через forEach, сразу возвращаем результат вызова метода count(),
    который подсчитывает количество элементов в потоке.
   */
  public long countEven(Stream<Integer> numbers) {
    if (numbers == null) {
      throw new IllegalArgumentException("Stream must not be null");
    }
    return numbers.filter(num -> num % 2 == 0).count(); // Используем count() для подсчета
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());

    /*
    Утверждение assert всегда верно благодаря особенностям работы HashSet и методу toString().
    1. В списке snapshot находятся все числа от 1 до 10000 в исходном порядке.
    2. В множестве set также есть все те же числа, но они могут быть в случайном порядке, так как для множества порядок не имеет значения.
    3. Когда мы вызываем toString() на списке, он возвращает строку с элементами в том порядке, в котором они были добавлены: [1, 2, 3, ..., 10000].
    4. Когда мы вызываем toString() на множестве, оно возвращает строку с элементами, отсортированными по возрастанию: [1, 2, 3, ..., 10000].

    Таким образом, хотя порядок элементов в snapshot и set отличается, их содержимое одинаково.
    Поэтому вызов метода toString() на обоих объектах вернет одну и ту же строку: все числа от 1 до 10000 в отсортированном виде.
     */
  }
}
