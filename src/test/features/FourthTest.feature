# language: ru

@all @fourth @without_driver
Функция: Четвертый тест

  Сценарий: Тест четвертый
    Пусть отправляю GET запрос к MS Weather с регионом Belgorod
    Тогда проверяю что от MS Weather пришел не пустой ответ
    То экспортирую респонс в отдельный файл
    То вывожу респонс в консоль
