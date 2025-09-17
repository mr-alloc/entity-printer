# Entity Printer

![Build Status](https://github.com/mr-alloc/entity-printer/actions/workflows/release.yml/badge.svg)
![Maven Central](https://img.shields.io/maven-central/v/io.alloc.utils/entity-printer)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)

**Gradle**
```groovy
dependencies {
    implementation 'io.alloc.utils.entity-printer:0.1.3'
}
```
**Maven**
```xml
<dependencies>
    <dependency>
        <groupId>io.alloc.utils</groupId>
        <artifactId>entity-printer</artifactId>
        <version>0.1.3</version>
    </dependency>
</dependencies>
```

## What is Entity Printer?

Entity Printer is a utility library that can be used anywhere in JVM applications, allowing you to visualize Java objects intuitively with simple operations.
You can easily output objects using the `EntityPrinter` object as shown in the example below.

```java
EntityPrinter printer = new EntityPrinter();
Coordinates coordinates = new Coordinates(21.239832931, 293.29391239);

String result = printer.drawEntity(coordinates);
System.out.println(result);
/* Output
 * +--------------+--------------+
 * | x (double)     y (double)   |
 * +--------------+--------------+
 * | 21.239832931   293.29391239 |
 * +--------------+--------------+
 * */
```

This can help solve issues that cannot be caught locally through very convenient debugging in production environments.
It also provides various options and supports user-friendly visualization of various collections and objects with minimal code.

___

## How to Use the Printer

To print entities, you need to create a printer. The printer can be created using `EntityPrinter`, and regular entities can be output using the `drawEntity` method,
while collections can be output using the `drawCollection` method.  
When outputting collections, since column information must match with other elements, it takes a type class as a parameter.

```java
List<Coordinate> coordinates = new ArrayList<>();
coordinates.add(new Coordinate(21.239832931, 293.29391239));
coordinates.add(new Coordinate(732.2182192, 706.293213243));

String result = printer.drawCollection(coordinates, Coordinate.class);
/** Output
 * +--------------+---------------+
 * | x (double)     y (double)    |
 * +--------------+---------------+
 * | 21.239832931   293.29391239  |
 * +--------------+---------------+
 * | 732.2182192    706.293213243 |
 * +--------------+---------------+
 */
```

### Printer Options

While you can perform simple output using the `drawEntity` or `drawCollection` methods, you can also use various options.  
From the printer's perspective, it only checks whether it's a `Map` or a `POJO` and handles them accordingly. For example, the `activateFields()` method is an option to specify fields to activate.
For `Map<String, Object>` types, you can specify indices by keys like `activateFields("KEY1","KEY2")` to activate only two columns.  
Conversely, for `POJO` types, exclusion is possible by field order like `activateFields(1, 2)`.

> For `POJO` types, the ability to activate or deactivate by field name will be added in the future.

To use options for each element type, you can use the following prepared settings:

```java
// For outputting POJO
ExpandableEntitySetting entitySetting = ExpandableSetting.entity();
// For outputting Map
ExpandableMapSetting mapSetting = ExpandableSetting.map();
```

Entity Printer provides the following options for more **user-friendly visualization**:

#### Field Activation: activateFields

When you want to activate only specific fields, you can filter by providing field names.
If empty values are provided, nothing will be output.

```java
ExpandableMapSetting setting = ExpandableSetting.map()
        .activateFields("x");
printer.drawCollection(coordinates, setting.getConfig(), Coordinate.class)
/** Output
 * +--------------+
 * | x (double)   |
 * +--------------+
 * | 21.239832931 |
 * +--------------+
 * | 732.2182192  |
 * +--------------+
 */
ExpandableMapSetting setting = ExpandableSetting.map()
        .activateFields();
printer.drawCollection(coordinates, setting.getConfig(), Coordinate.class)
/** Output
 * No activated field. Please check optional method called by "PrintConfigurator.activateFields()".
 */
```

This option activates only the 1st field (Coordinate.x) as shown in the result above, allowing you to output only the desired columns.
For `Map<String, Object>`, it can be used by specifying keys like `activateFields("KEY")`.

#### Exclude Data Type: excludeDataType

```java
ExpandableMapSetting setting = ExpandableSetting.map()
        .excludeDataType();
printer.drawCollection(coordinates, setting.getConfig(), Coordinate.class);
/** Output
 * +--------------+---------------+
 * | x              y             |
 * +--------------+---------------+
 * | 21.239832931   293.29391239  |
 * +--------------+---------------+
 * | 732.2182192    706.293213243 |
 * +--------------+---------------+
 */
```

This option does not output the data type information displayed next to column names.

#### Remove Row Floor: withoutFloor()

```java
ExpandableMapSetting setting = ExpandableSetting.map()
        .withoutFloor();
printer.drawCollection(coordinates, setting.getConfig(),Coordinate.class);
/**
 * +--------------+---------------+
 * | x (double)     y (double)    |
 * +--------------+---------------+
 * | 21.239832931   293.29391239  |
 * | 732.2182192    706.293213243 |
 * +--------------+---------------+
 */
```

This option removes unnecessary internal borders between rows, supporting condensed visualization.

> From now on, more user-friendly options for data will be introduced.

Let's implement an example class `Person` for simple explanation.

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

Let's prepare data that needs output verification.
```java
List<Person> blackpink = new ArrayList<>();
blackpink.add(new Person("Jennie", "jennie@black.pink",LocalDateTime.of(1996, 1,16,12,20)));
blackpink.add(new Person("Jisoo", "jisoo@black.pink",LocalDateTime.of(1995, 1,3,17,53)));
blackpink.add(new Person("Rosé", "roseanne@black.pink",LocalDateTime.of(1997, 2,11,7,12)));
blackpink.add(new Person("Lisa", "risa@black.pink",LocalDateTime.of(1997, 3,27,2,9)));
```

When no options are specified, it outputs as follows:
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

#### Date Format Specification: dateformat(String pattern)

```java
ExpandableEntitySetting setting = ExpandableSetting.entity()
        .dateformat("yyyy-MM-dd hh:mm:ss");
printer.drawCollection(blackpink, setting.getConfig(), Person.class);
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

This option internally uses Java's `DateTimeFormatter` and can use formatting patterns as-is.

#### Disable String Escape: noEscape()

```java
ExpandableEntitySetting setting = ExpandableSetting.entity();
        .noEscape();
printer.drawCollection(blackpink, setting.getConfig(), Person.class);
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

This option renders escaped strings to be displayed as-is. Since jsonCache has `\n` strings rendered to move to the next line, only the first line is output.

#### Allow Multi-line: allowMultiLine()

```java
ExpandableEntitySetting setting = ExpandableSetting.entity();
        .allowMultiLine();
printer.drawCollection(blackpink, setting.getConfig(), Person.class);
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

This option shows all information that a column has.   
By default, only up to 30 characters are shown, but it creates lines in 30-character units and outputs up to the maximum number of lines based on the longest column.

When used with the `noEscape()` option, it can be used for better visualization, which creates better synergy with document-based data like NoSQL.

```java
ExpandableEntitySetting setting = ExpandableSetting.entity();
        .allowMultiLine().noEscape();
printer.drawCollection(blackpink, setting.getConfig(),Person.class);
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

#### Disable `...` Ellipsis: noEllipsis()

```java
ExpandableEntitySetting setting = ExpandableSetting.entity();
        .noEllipsis();
printer.drawCollection(blackpink, setting.getConfig(), Person.class);
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

This option disables the `...` symbol processing that truncates the default maximum length of 30 characters.
You can see the values that the column has in a single line.

### Various Applications

By applying the options introduced above collectively, you can visualize as follows:

```java
ExpandableEntitySetting setting = ExpandableSetting.entity();
        .activateFields("name", "email", "birthday")
        .dateformat("yyyy/MM/dd HH:mm")
        .excludeDataType()
        .withoutFloor();
printer.drawCollection(blackpink, setting.getConfig(), Person.class);
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
ExpandableEntitySetting setting = ExpandableSetting.entity();
        .activateFields("jsonCache")
        .excludeDataType()
        .allowMultiLine()
        .noEscape();
printer.drawCollection(blackpink, setting.getConfig(),Person.class)
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

### Exception Cases

When using the `drawCollection` method, if the collection has no data or if the specified type differs from the element type, it outputs as follows:

```
+------+------+------+-------------+
| name   type   data   entityTypes |
+------+------+------+-------------+
| empty                            |
+------+------+------+-------------+
```