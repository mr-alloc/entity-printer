## Entity Printer는 무엇인가요?

엔터티 프린터는 `JVM`어플리케이션이라면 어디서든 사용가능하며, 간단한 조작으로 직관적인 Java 개체를 볼 수 있는 유틸리티 라이브러리입니다.
`EntityPrinter` 객체를 사용하여 간단하게 아래 예시처럼 출력할 수 있습니다.

```java
EntityPrinter printer = new EntityPrinter();
Coordinates coordinates = new Coordinates(21.239832931, 293.29391239);

String result = printer.drawEntity(coordinates);
System.out.

println(result);
/* 출력 결과
 * +--------------+--------------+
 * | x (double)     y (double)   |
 * +--------------+--------------+
 * | 21.239832931   293.29391239 |
 * +--------------+--------------+
 * */
```

이는 로컬에서 잡을수없는 이슈를 배포 환경에서 매우 간편한 디버깅으로 해결할 수도 있습니다.
또한 여러가지 옵션을 제공하며 작은 코드로 다양한 컬렉션 및 객체를 사용자친화 시각화를 지원합니다.

___

## 프린터를 사용하는 방법

엔터티를 출력하려면, 프린터를 생성해야합니다. 프린터는 `EntityPrinter`를 사용하여 생성할 수 있으며, 일반 엔터티는 `drawEntity` 메서드를 이용하고
컬렉션은 `drawCollection`메서드를 이용하여, 출력가능합니다.  
컬렉션을 출력하는경우, 다른 요소들과 열정보가 일치 해야하기 때문에, 파라미터로 타입 클래스를 받습니다.

```java
List<Coordinate> coordinates = new ArrayList<>();
coordinates.

add(new Coordinate(21.239832931, 293.29391239));
        coordinates.

add(new Coordinate(732.2182192, 706.293213243));

String result = printer.drawCollection(coordinates, Coordinate.class);
/** 출력 결과
 * +--------------+---------------+
 * | x (double)     y (double)    |
 * +--------------+---------------+
 * | 21.239832931   293.29391239  |
 * +--------------+---------------+
 * | 732.2182192    706.293213243 |
 * +--------------+---------------+
 */
```

### 프린터 옵션

`drawEntity` 또는 `drawCollection`메서드를 사용하여, 간단한 출력을 할 수도 있지만, 여러가지 옵션을 사용할 수도 있습니다.  
프린터 입장에서는 `Map`인지 또는 `POJO`인지만 확인하여 취급 합니다. 예를들어 `activateFields()`메서드는 활성화시킬 필드를 지정하는 옵션인데,
`Map<String, Object>`타입인경우 activateFields("KEY1","KEY2") 처럼 키로 인덱스를 지정하여 두가지 컬럼만 활성화 시킬수 있습니다.  
반대로 `POJO`인경우 `activateFields(1, 2)`와 같이 필드 순서로 제외가 가능합니다.

> `POJO`의 경우 필드명으로 활성화 또는 비활성화 하는 기능은 추후 추가될 예정입니다.

각 요소별 타입에대한 옵션을 사용하기 위해 아래 준비된 설정을 이용할 수 있습니다.

```java
// POJO를 출력하는 경우
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
// Map을 출력하는 경우
ExpandableMapSetting mapSetting = ExpandableSetting.EXPANDABLE_MAP_SETTING;
```

엔터티프린터는 좀 더 **사용자 친화적인 시각화**를 위해 아래와 같은 옵션들을 사용가능 합니다.

#### 필드 활성화: activateFields

```java
ExpandableMapSetting entitySetting = ExpandableSetting.EXPANDABLE_MAP_SETTING;
entitySetting.

activateFields(1);
printer.

drawCollection(coordinates, entitySetting.getConfig(),Coordinate.class)
/** 출력 결과
 * +--------------+
 * | x (double)   |
 * +--------------+
 * | 21.239832931 |
 * +--------------+
 * | 732.2182192  |
 * +--------------+
 */
```

이 옵션은 위 결과처럼 1번 필드(Coordinate.x)만 활성화하여, 원하는 열만 출력할 수 있는 기능입니다.
`Map<String, Object>`의 경우, `activateFields("KEY")`처럼 키를 지정하는 방식으로 사용가능합니다.

#### 데이터타입 제외: excludeDataType

```java
ExpandableMapSetting entitySetting = ExpandableSetting.EXPANDABLE_MAP_SETTING;
entitySetting.

excludeDataType();
printer.

drawCollection(coordinates, entitySetting.getConfig(),Coordinate.class);
/** 출력결과
 * +--------------+---------------+
 * | x              y             |
 * +--------------+---------------+
 * | 21.239832931   293.29391239  |
 * +--------------+---------------+
 * | 732.2182192    706.293213243 |
 * +--------------+---------------+
 */
```

이 옵션은 열이름 옆에 표시되던 데이터타입 정보를 출력하지 않습니다.

#### 행의 바닥제거: withoutFloor()

```java
ExpandableMapSetting entitySetting = ExpandableSetting.EXPANDABLE_MAP_SETTING;
entitySetting.

withoutFloor();
printer.

drawCollection(coordinates, entitySetting.getConfig(),Coordinate.class);
/**
 * +--------------+---------------+
 * | x (double)     y (double)    |
 * +--------------+---------------+
 * | 21.239832931   293.29391239  |
 * | 732.2182192    706.293213243 |
 * +--------------+---------------+
 */
```

이옵션은 불필요한 행간 내부 테두리를 지워, 축약된 시각화를 지원합니다.

> 이제부터는 조금더 데이터에대해 사용자 친화적인 옵션들이 소개됩니다.

간단한 설명을 위해 예제 클래스 `Person`을 구현합니다.

```java
class Person {
  String name;
  String email;
  LocalDateTime birthday;
  String jsonCache;

  public Person(String name, String email, LocalDateTime birthday) {
    this.name = name;
    this.email = email;
    this.birthday = birthday;
    this.jsonCache = "{\n" +
            "\t\"name\": \"" + this.name + "\",\n" +
            "\t\"email\": \"" + this.email + "\",\n" +
            "\t\"birthday\":" + this.birthday + "\"\n" +
            "}";
  }
}
```

출력확인이 필요한 데이터도 준비합니다.
```java
List<Person> blackpink = new ArrayList<>();
blackpink.

add(new Person("Jennie", "jennie@black.pink",LocalDateTime.of(1996, 1,16,12,20)));
        blackpink.

add(new Person("Jisoo", "jisoo@black.pink",LocalDateTime.of(1995, 1,3,17,53)));
        blackpink.

add(new Person("Rosé", "roseanne@black.pink",LocalDateTime.of(1997, 2,11,7,12)));
        blackpink.

add(new Person("Lisa", "risa@black.pink",LocalDateTime.of(1997, 3,27,2,9)));
```

아무 옵션을 지정하지 않은 경우 아래와같이 출력됩니다.
```
+---------------+---------------------+--------------------------+--------------------------------+
| name (String)   email (String)        birthday (LocalDateTime)   jsonCache (String)             |
+---------------+---------------------+--------------------------+--------------------------------+
| Jennie          jennie@black.pink     1996-01-16                 {\n\t"name": "Jennie",\n\t"... |
+---------------+---------------------+--------------------------+--------------------------------+
| Jisoo           jisoo@black.pink      1995-01-03                 {\n\t"name": "Jisoo",\n\t"e... |
+---------------+---------------------+--------------------------+--------------------------------+
| Rosé            roseanne@black.pink   1997-02-11                 {\n\t"name": "Rosé",\n\t"em... |
+---------------+---------------------+--------------------------+--------------------------------+
| Lisa            risa@black.pink       1997-03-27                 {\n\t"name": "Lisa",\n\t"em... |
+---------------+---------------------+--------------------------+--------------------------------+
```

#### 날짜형식 지정: dateformat(String pattern)

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

dateformat("yyyy-MM-dd hh:mm:ss");
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class);
/**
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | name (String)   email (String)        birthday (LocalDateTime)   jsonCache (String)             |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Jennie          jennie@black.pink     1996-01-16 12:20:00        {\n\t"name": "Jennie",\n\t"... |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Jisoo           jisoo@black.pink      1995-01-03 05:53:00        {\n\t"name": "Jisoo",\n\t"e... |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Rosé            roseanne@black.pink   1997-02-11 07:12:00        {\n\t"name": "Rosé",\n\t"em... |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Lisa            risa@black.pink       1997-03-27 02:09:00        {\n\t"name": "Lisa",\n\t"em... |
 * +---------------+---------------------+--------------------------+--------------------------------+
 */
```

이 옵션은 내부적으로 자바의 `DateTimeFormatter`를 사용하며, 포매팅 형식을 그대로 사용할 수 있습니다.

#### 문자열 이스케이프 해제: noEscape()

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

noEscape();
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class);
/**
 * +---------------+---------------------+--------------------------+--------------------+
 * | name (String)   email (String)        birthday (LocalDateTime)   jsonCache (String) |
 * +---------------+---------------------+--------------------------+--------------------+
 * | Jennie          jennie@black.pink     1996-01-16                 {                  |
 * +---------------+---------------------+--------------------------+--------------------+
 * | Jisoo           jisoo@black.pink      1995-01-03                 {                  |
 * +---------------+---------------------+--------------------------+--------------------+
 * | Rosé            roseanne@black.pink   1997-02-11                 {                  |
 * +---------------+---------------------+--------------------------+--------------------+
 * | Lisa            risa@black.pink       1997-03-27                 {                  |
 * +---------------+---------------------+--------------------------+--------------------+
 */
```

이 옵션은 escape 처리된 문자열을 그대로 보여지도록 랜더링 합니다. jsonCache는 `\n`문자열이 랜더링되어 다음줄로 넘어갔기 떄문에, 첫번째 줄만 출력됩니다.

#### 멀티라인 허용: allowMultiLine()

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

allowMultiLine();
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class);
/**
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | name (String)   email (String)        birthday (LocalDateTime)   jsonCache (String)             |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Jennie          jennie@black.pink     1996-01-16                 {\n\t"name": "Jennie",\n\t"ema |
 * |                                                                  il": "jennie@black.pink",\n\t" |
 * |                                                                  birthday": "1996-01-16T12:20"\ |
 * |                                                                  n}                             |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Jisoo           jisoo@black.pink      1995-01-03                 {\n\t"name": "Jisoo",\n\t"emai |
 * |                                                                  l": "jisoo@black.pink",\n\t"bi |
 * |                                                                  rthday": "1995-01-03T17:53"\n} |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Rosé            roseanne@black.pink   1997-02-11                 {\n\t"name": "Rosé",\n\t"email |
 * |                                                                  ": "roseanne@black.pink",\n\t" |
 * |                                                                  birthday": "1997-02-11T07:12"\ |
 * |                                                                  n}                             |
 * +---------------+---------------------+--------------------------+--------------------------------+
 * | Lisa            risa@black.pink       1997-03-27                 {\n\t"name": "Lisa",\n\t"email |
 * |                                                                  ": "risa@black.pink",\n\t"birt |
 * |                                                                  hday": "1997-03-27T02:09"\n}   |
 * +---------------+---------------------+--------------------------+--------------------------------+
 */
```

이 옵션은 열이 가지고있는 정보를 모두 보여줍니다.   
기본적으로 최대 30자까지만 보여지지만, 30자 단위로 라인을 만들어서 가장 긴 열을 기준으로 최대라인수 까지 출력합니다.

`noEscape()` 옵션과 함께 사용하면, 더 나은 시각하로 사용이가능하며, 이는 NoSQL과 같은 문서기반 데이터에서 더나은 시너지가 나옵니다.

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

allowMultiLine().

allowMultiLine();
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class);
/**
 * +---------------+---------------------+--------------------------+-----------------------------------+
 * | name (String)   email (String)        birthday (LocalDateTime)   jsonCache (String)                |
 * +---------------+---------------------+--------------------------+-----------------------------------+
 * | Jennie          jennie@black.pink     1996-01-16                 {                                 |
 * |                                                                    "name": "Jennie",               |
 * |                                                                    "email": "jennie@black.pink",   |
 * |                                                                    "birthday": "1996-01-16T12:20"  |
 * |                                                                  }                                 |
 * +---------------+---------------------+--------------------------+-----------------------------------+
 * | Jisoo           jisoo@black.pink      1995-01-03                 {                                 |
 * |                                                                    "name": "Jisoo",                |
 * |                                                                    "email": "jisoo@black.pink",    |
 * |                                                                    "birthday": "1995-01-03T17:53"  |
 * |                                                                  }                                 |
 * +---------------+---------------------+--------------------------+-----------------------------------+
 * | Rosé            roseanne@black.pink   1997-02-11                 {                                 |
 * |                                                                    "name": "Rosé",                 |
 * |                                                                    "email": "roseanne@black.pink", |
 * |                                                                    "birthday": "1997-02-11T07:12"  |
 * |                                                                  }                                 |
 * +---------------+---------------------+--------------------------+-----------------------------------+
 * | Lisa            risa@black.pink       1997-03-27                 {                                 |
 * |                                                                    "name": "Lisa",                 |
 * |                                                                    "email": "risa@black.pink",     |
 * |                                                                    "birthday": "1997-03-27T02:09"  |
 * |                                                                  }                                 |
 * +---------------+---------------------+--------------------------+-----------------------------------+
 */
```

#### `...` 생략 해제: noEllipsis()

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

noEllipsis();
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class);
/**
 * +---------------+---------------------+--------------------------+----------------------------------------------------------------------------------------------+
 * | name (String)   email (String)        birthday (LocalDateTime)   jsonCache (String)                                                                           |
 * +---------------+---------------------+--------------------------+----------------------------------------------------------------------------------------------+
 * | Jennie          jennie@black.pink     1996-01-16                 {\n\t"name": "Jennie",\n\t"email": "jennie@black.pink",\n\t"birthday": "1996-01-16T12:20"\n} |
 * +---------------+---------------------+--------------------------+----------------------------------------------------------------------------------------------+
 * | Jisoo           jisoo@black.pink      1995-01-03                 {\n\t"name": "Jisoo",\n\t"email": "jisoo@black.pink",\n\t"birthday": "1995-01-03T17:53"\n}   |
 * +---------------+---------------------+--------------------------+----------------------------------------------------------------------------------------------+
 * | Rosé            roseanne@black.pink   1997-02-11                 {\n\t"name": "Rosé",\n\t"email": "roseanne@black.pink",\n\t"birthday": "1997-02-11T07:12"\n} |
 * +---------------+---------------------+--------------------------+----------------------------------------------------------------------------------------------+
 * | Lisa            risa@black.pink       1997-03-27                 {\n\t"name": "Lisa",\n\t"email": "risa@black.pink",\n\t"birthday": "1997-03-27T02:09"\n}     |
 * +---------------+---------------------+--------------------------+----------------------------------------------------------------------------------------------+
 */
```

이 옵션은 기본으로 지정되어있는 최대길이 30자에대해 생략하는 `...`기호 처리를 비활성화 합니다.
해당 열이 가지고있는 값을 한개의 라인으로 볼 수 있습니다.

### 다양한 활용

위에서 소개되었는 옵션들을 일괄적으로 적용하면 다음과 같이 시각화 할 수 있습니다.

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

activateFields(1,2,3)
    .

dateformat("yyyy/MM/dd HH:mm")
    .

excludeDataType()
    .

withoutFloor();
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class);
/**
 * +--------+---------------------+------------------+
 * | name     email                 birthday         |
 * +--------+---------------------+------------------+
 * | Jennie   jennie@black.pink     1996/01/16 12:20 |
 * | Jisoo    jisoo@black.pink      1995/01/03 17:53 |
 * | Rosé     roseanne@black.pink   1997/02/11 07:12 |
 * | Lisa     risa@black.pink       1997/03/27 02:09 |
 * +--------+---------------------+------------------+
 */
```

```java
ExpandableEntitySetting entitySetting = ExpandableSetting.EXPANDABLE_ENTITY_SETTING;
entitySetting.

activateFields(4)
    .

excludeDataType()
    .

allowMultiLine()
    .

noEscape();
printer.

drawCollection(blackpink, entitySetting.getConfig(),Person.class)
/**
 * +-----------------------------------+
 * | jsonCache                         |
 * +-----------------------------------+
 * | {                                 |
 * |   "name": "Jennie",               |
 * |   "email": "jennie@black.pink",   |
 * |   "birthday": "1996-01-16T12:20"  |
 * | }                                 |
 * | {                                 |
 * |   "name": "Jisoo",                |
 * |   "email": "jisoo@black.pink",    |
 * |   "birthday": "1995-01-03T17:53"  |
 * | }                                 |
 * | {                                 |
 * |   "name": "Rosé",                 |
 * |   "email": "roseanne@black.pink", |
 * |   "birthday": "1997-02-11T07:12"  |
 * | }                                 |
 * | {                                 |
 * |   "name": "Lisa",                 |
 * |   "email": "risa@black.pink",     |
 * |   "birthday": "1997-03-27T02:09"  |
 * | }                                 |
 * +-----------------------------------+
 */
```

### 예외 상황

`drawCollection` 메서드를 사용할때 컬렉션의 데이터가 없거나 지정한 타입과 요소의 타입이 다른경우 아래와 같이 출력됩니다.

```
+------+------+------+-------------+
| name   type   data   entityTypes |
+------+------+------+-------------+
| empty                            |
+------+------+------+-------------+
```