# Amulet Mod (Fabric 1.20.1)

Мод добавляет амулеты, которые прокачиваются за алмазы и работают в **левой руке (off-hand)**.

## Что добавлено

- **Стол амулетов** (`amuletmod:amulet_table`) — крафт: 4 аметиста по углам, 4 алмаза по краям, верстак в центре.
- **Амулет** (`amuletmod:amulet`) — крафт: золотые слитки в форме ожерелья + аметист.
- **Амулет-тотем** (`amuletmod:totem_amulet`) — крафт **в наковальне**: обычный амулет + тотем бессмертия. Все прокачки переносятся.

## Прокачка

Положите амулет в левый слот стола амулетов, алмазы — в правый, и нажимайте кнопки. Каждое нажатие = +1 уровень за 1 алмаз.

| Стат               | Эффект за 1 уровень                          |
|---|---|
| Скорость           | +2% к скорости передвижения                  |
| Урон               | +0.5 к атаке                                 |
| Броня              | +0.5 к броне                                 |
| Здоровье           | +1 сердце (2 HP)                             |
| Дальность          | Кратковременная Спешка (placeholder)         |
| Скорость атаки     | +0.1                                         |
| Регенерация        | Хил 1 HP всё реже = быстрее с лвлом          |
| Снижение голода    | Тиковая сатурация                            |

> **Побочка:** если регенерация прокачана выше 10, амулет периодически повышает голод (как и просил автор).

## Тотем-эффект

Когда вы умираете, амулет-тотем работает как тотем бессмертия:
- Вы остаётесь живы, эффекты, частицы, звук — всё как у ванильного.
- **Сам амулет НЕ исчезает**, но получает кулдаун 5 минут (видно в подсказке).

## Структура

```
amuletmod/
├── build.gradle, settings.gradle, gradle.properties
└── src/main/
    ├── java/com/example/amuletmod/
    │   ├── AmuletMod.java               (entrypoint)
    │   ├── client/AmuletModClient.java  (client entrypoint)
    │   ├── registry/                    (регистрация всего)
    │   ├── item/                        (амулеты + AmuletData NBT хелпер)
    │   ├── block/                       (блок и BE стола)
    │   ├── screen/                      (GUI)
    │   ├── event/AmuletEvents.java      (тики, тотем)
    │   └── mixin/AnvilCraftMixin.java   (крафт в наковальне)
    └── resources/
        ├── fabric.mod.json
        ├── amuletmod.mixins.json
        ├── assets/amuletmod/...         (модели, языки)
        └── data/amuletmod/recipes/      (рецепты)
```

## Сборка

1. Установите JDK 17.
2. В корне проекта:
   ```
   ./gradlew build
   ```
3. Готовый `.jar` появится в `build/libs/`.
4. Закиньте в `mods/` рядом с **Fabric Loader** + **Fabric API**.

## Что нужно дорисовать самому

В папках `src/main/resources/assets/amuletmod/textures/item/` и `.../block/` нужно положить PNG-файлы (16×16 для предметов, 16×16 для блоков):

- `item/amulet.png`
- `item/totem_amulet.png`
- `block/amulet_table_top.png`
- `block/amulet_table_side.png`
- `block/amulet_table_bottom.png`

Без текстур мод запустится, но иконки будут чёрно-фиолетовыми «отсутствующая текстура».

## Известные ограничения

- Стат **«Дальность»** в 1.20.1 даёт временный эффект Haste как заглушку, потому что у ванильной 1.20.1 нет атрибута reach. Для настоящей прокачки дальности добавьте мод **Reach Entity Attributes** и поправьте `AmuletEvents.tickPlayer` — добавьте модификатор на `ReachEntityAttributes.REACH` и `ATTACK_RANGE`.
- GUI стола использует простой залитый прямоугольник вместо текстуры. Если нарисуете `assets/amuletmod/textures/gui/amulet_table.png` (176×186), замените `drawBackground` в `AmuletTableScreen` на `ctx.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight)`.
