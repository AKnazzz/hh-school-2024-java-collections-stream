package tasks;

import common.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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

  // Удалено поле count, т.к. оно не нужно для подсчета четных чисел используем метод count()
  // Конвертируем начиная со второго элемента списка

  // Вместо удаления элемента мы пропускаем элемент стриме. Это сохраняет исходный список неизменным и более эффективно.
  // Удалил проверку на пустой список - тк если на входе пустой список по стрим вернёт пустой список + в данной проверке возвращается иммутабельный список,
  //  а я предполагаю что с ним продолжится работа
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Получаем уникальные имена персон без учета первого элемента
  // На выходе должен быть Сет, дистинкт не нужен - делаем LinkedHashSet (+ сохраним порядок входящего списка)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new LinkedHashSet<>(getNames(persons));
  }

  // Склеиваем ФИО, убираем лишние проверки
  // Большой блок кода с опечаткой (person.secondName() - дважды) изменен на стрим - сразу видна логика, уменьшена вероятность ошибки
  //
  public String convertPersonToString(Person person) {
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // Создаем словарь id персоны -> ее имя
  // Изначально map создаётся размером 1 - лучше сделать без указания начального размера
  // Если придёт совпадающий id персоны код упадёт -> добавляем значение только в том случае, если в мапе ключа еще нет
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>();
    for (Person person : persons) {
      map.putIfAbsent(person.id(), convertPersonToString(person));
    }
    return map;
  }

  // Проверяем на совпадение персон в двух коллекциях
  // Проверяем в стриме есть ли хоть одно совпадение элемента, и вернём соответствующее условие - асимптотика должна быть O(n+m) вместо O(n*m)
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> set1 = new HashSet<>(persons1);
    return persons2.stream().anyMatch(set1::contains);
  }

  // Посчитать число четных чисел
  //  Вместо переменной count и итерацию через forEach возвращаем результат вызова метода count()
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
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
    В списке snapshot находятся все числа от 1 до 10000 в исходном порядке.
    В множестве set все элементы хранятся по бакетам, номер бакета определяется хэш функцией для каждого элемента
    Но для целых чисел хэш-код является самим числом - поэтому они будут располагаться по возрастанию в самом HashSet независимо от порядка в котором добавлялись.
    В итоге toString() пойдёт по бакетам и даст одинаковый результат как для упорядоченной коллекции так и для множества, т.к. они с целыми числами.

    P.S. Проверил это дополнительно в тестах =)
     */
  }
}
