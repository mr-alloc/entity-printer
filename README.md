## Entity Printer

Simple print for some entities!
This printer can print any entities like bellow:

### Quick Start

```java
//Step 1. Create printer.
EntityPrinter printer=new EntityPrinter();

//Step 2. Create Entity
        Map<String, String> logMap=new HashMap<>();
        logMap.put("user","{\n\t\"name\": \"devis\",\n\t\"age\": 30\n}");
        logMap.put("date","2020-01-01");
        logMap.put("time","12:00:00");

//Step 3. Just Print Entity!
        System.out.println(printer.drawEntity(logMap));
        log.info(printer.drawEntity(logMap));
```

**Result**

```
+---------------+---------------+--------------------------------+
| date (String)   time (String)   user (String)                  |
+---------------+---------------+--------------------------------+
| 2020-01-01      12:00:00        {\n\t"name": "devis",\n\t"a... |
+---------------+---------------+--------------------------------+
```

___

### Usage

#### Create Printer

First of all, you need to create a printer.

```java
EntityPrinter printer=new EntityPrinter();
//There are 4 print method for this.
        printer.drawEntity(entity); //just print entity with default configured.
        printer.drawEntity(entity,config) // print entity with various options. 
        printer.drawCollection(entities,Entity.class); // print Collection<Entity> with default configured.
        prinetr.drawCollection(entities,config,Entity.class); // print Collection<Entity> with various options.
```

And `drawCollection` method allways need to set inner type `Class<T>` of collection as last parameter.
This means that you can't print collection of different type of entities.
if you set other type of entity differ to last parameter class, it will print like bellow.

```
+------+------+------+-------------+
| name   type   data   entityTypes |
+------+------+------+-------------+
| empty                            |
+------+------+------+-------------+
```

#### Additional Options

* Sample
  > There are also configured default options in printer. But you can configure other options like bellow.

    ```java
    Map<String, String> logMap = new HashMap<>();
    logMap.put("user", "{\n\t\"name\": \"devis\",\n\t\"age\": 30\n}");
    logMap.put("date", "2020-01-01");
    logMap.put("time", "12:00:00");
    
    //This config is configured default options in printer.
    PrintConfigurator<Integer> config = ExpandableSetting.EXPANDABLE_ENTITY_SETTING.getConfig();
    System.out.println(printer.drawEntity(logMap, config));
    ```

  **Result**
  ```
  +---------------+---------------+--------------------------------+
  | date (String)   time (String)   user (String)                  |
  +---------------+---------------+--------------------------------+
  | 2020-01-01      12:00:00        {\n\t"name": "devis",\n\t"a... |
  +---------------+---------------+--------------------------------+
  ```

* No Ellipsis for string
    ```java
    config.noEllipsis()
        .getConfig();
    ```

  **Result**
    ```
    +---------------+---------------+---------------------------------------+
    | date (String)   time (String)   user (String)                         |
    +---------------+---------------+---------------------------------------+
    | 2020-01-01      12:00:00        {\n\t"name": "devis",\n\t"age": 30\n} |
    +---------------+---------------+---------------------------------------+
    ```

* Exclude Data type like `(String)` in column name.
    ```java
    config.excludeDataType()
        .getConfig();
    ```

  **Result**
    ```
    +------------+----------+--------------------------------+
    | date         time       user                           |
    +------------+----------+--------------------------------+
    | 2020-01-01   12:00:00   {\n\t"name": "devis",\n\t"a... |
    +------------+----------+--------------------------------+
    ```

* Allow Multi Line
  ```java
  config.allowMultiLine()
      .getConfig();
  ```

  **Result**
  ```
  +---------------+---------------+--------------------------------+
  | date (String)   time (String)   user (String)                  |
  +---------------+---------------+--------------------------------+
  | 2020-01-01      12:00:00        {\n\t"name": "devis",\n\t"age" |
  |                                 : 30\n}                        |
  +---------------+---------------+--------------------------------+
  ```

* No escape
  ```java
    config.noEscape()
        .getConfig();
    ```

  **Result**
  ```
  +---------------+---------------+---------------+
  | date (String)   time (String)   user (String) |
  +---------------+---------------+---------------+
  | 2020-01-01      12:00:00        {             |
  +---------------+---------------+---------------+
  ```

* Allow multi line and No escape
  ```java
    config.allowMultiLine()
        .noEscape()
        .getConfig();
  ```
  **Result**
  ```
  +---------------+---------------+--------------------+
  | date (String)   time (String)   user (String)      |
  +---------------+---------------+--------------------+
  | 2020-01-01      12:00:00        {                  |
  |                                   "name": "devis", |
  |                                   "age": 30        |
  |                                 }                  |
  +---------------+---------------+--------------------+
  ```
* without floor
  ```java
  
  PrintConfigurator<String> config = ExpandableSetting.EXPANDABLE_MAP_SETTING
          .withoutFloor()
          .getConfig();
  
  Map<String, Object> logMap = new HashMap<>();
  logMap.put("user", "{\n\t\"name\": \"devis\",\n\t\"age\": 30\n}");
  logMap.put("date", "2020-01-01");
  logMap.put("time", "12:00:00");
  
  Map<String, Object> logMap2 = new HashMap<>();
  logMap2.put("user", "{\n\t\"name\": \"ultrarisk\",\n\t\"age\":3213\n}");
  logMap2.put("date", "2019-03-20");
  logMap2.put("time", "19:34:12");
  
  List<Map<String, Object>> mapList = Arrays.asList(logMap, logMap2);
  
  System.out.println(printer.drawCollection(mapList, config, Map.class));
  ```
  **Result**
    * Before configure `withoutFloor()`

      ```
      +---------------+---------------+--------------------------------+
      | date (String)   time (String)   user (String)                  |
      +---------------+---------------+--------------------------------+
      | 2020-01-01      12:00:00        {\n\t"name": "devis",\n\t"a... |
      +---------------+---------------+--------------------------------+
      | 2019-03-20      19:34:12        {\n\t"name": "ultrarisk",\n... |
      +---------------+---------------+--------------------------------+   
      ```
    * After configure `withoutFloor()`
      ```
      +---------------+---------------+--------------------------------+
      | date (String)   time (String)   user (String)                  |
      +---------------+---------------+--------------------------------+
      | 2020-01-01      12:00:00        {\n\t"name": "devis",\n\t"a... |
      | 2019-03-20      19:34:12        {\n\t"name": "ultrarisk",\n... |
      +---------------+---------------+--------------------------------+
      ```

* Activate specific column
  ```java
    config.activateFields("user", "time")
        .getConfig();
  ```
  **Result**
  ```
  +---------------+--------------------------------+
  | time (String)   user (String)                  |
  +---------------+--------------------------------+
  | 12:00:00        {\n\t"name": "devis",\n\t"a... |
  +---------------+--------------------------------+
  | 19:34:12        {\n\t"name": "ultrarisk",\n... |
  +---------------+--------------------------------+
  ```