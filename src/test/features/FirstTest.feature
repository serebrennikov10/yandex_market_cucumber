# language: ru

@all @one
Функция: Первый тест

  Сценарий: Тест первый
    Пусть перехожу на страницу Яндекс Маркет
    Также меняю регион
    Когда перехожу в Все категории
    И перехожу в категорию Компьютеры
    И перехожу в подкатегорию Ноутбуки
    То открыта страница Ноутбуки

    Когда устанавливаю фильтр по цене от 0 до 30000
    И устанавливаю другие фильтры:
      | type_name  | value_name  |
      | brand      | HP          |
      | brand      | Lenovo      |
      | color      | черный      |
      | color      | белый       |
    То выполнена фильтрация/сортировка ноутбуков

    Когда устанавливаю сортировку по цене
    То выполнена фильтрация/сортировка ноутбуков

    Когда ищу самый дешевый ноутбук
    И ищу самый дорогой ноутбук
    Тогда считаю разницу между дорогим и дешевым ноутбуком

    Пусть вывожу список ноутбуков в консоль
    И вывожу ноутбуки из Мар <name, price>
    Тогда вывожу в консоль Первый тест завершен!