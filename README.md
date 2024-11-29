# SimpleJSONSerializer

This class has a method `serialize`, which takes in an object to be serialized.
As this class is simpler to implement, it prints a json with respect to all the tabulation.
For private fields the serializer calls getters. If there is no public getter for a private field, then the serializer skips this field.

Example:

```java
public class Person {
    public String name;
    public int age;
    public String[] parents;
    public List<String> hobbies;
    public Queue<Integer> someQueue;
    public Set<Double> favouriteNumbers;
    public List<Person> friends;
    public Map<String, Person> friendType;
}

public class Main{
    public static void main() {
        var person1 = new Person(
                "Alex",
                21,
                new String[]{"Helena", "Charlie"},
                new ArrayList<>(Arrays.asList("football", "skiing")),
                new ArrayDeque<>(Arrays.asList(1, 2)),
                new HashSet<>(List.of(1.2)),
                new ArrayList<>(),
                new HashMap<>()
        );
        
        var person2 = new Person(
                "Maria",
                20,
                new String[]{"Helena", "Charlie"},
                new ArrayList<>(Arrays.asList("swimming")),
                new ArrayDeque<>(Arrays.asList(7)),
                new HashSet<>(List.of()),
                new ArrayList<>(),
                new HashMap<>()
        );
        
        var person3 = new Person(
                "Bob",
                21,
                new String[]{"Carmen", "Dominic"},
                new ArrayList<>(Arrays.asList("computer games")),
                new ArrayDeque<>(Arrays.asList(42)),
                new HashSet<>(List.of()),
                new ArrayList<>(),
                new HashMap<>()
        );

        person1.friendType = Map.of("sister", person2);
        person2.friends = List.of(person3);

        var serializer = new SimpleJSONSerializer();
        System.out.println(serializer.serialize(person1));
    } 
}
```

Result:
```json
{
  "name": "Alex",
  "age": 21,
  "parents": ["Helena", "Charlie"],
  "hobbies": ["football", "skiing"],
  "someQueue": [1, 2],
  "favouriteNumbers": [1.2],
  "friends": [],
  "friendType": 
    { 
      "sister": 
        {
          "name": "Maria",
          "age": 20,
          "parents": ["Helena", "Charlie"],
          "hobbies": ["swimming"],
          "someQueue": [7],
          "favouriteNumbers": [],
          "friends": [
            {
              "name": "Bob",
              "age": 21,
              "parents": ["Carmen", "Dominic"],
              "hobbies": ["computer games"],
              "someQueue": [42],
              "favouriteNumbers": [],
              "friends": [],
              "friendType": {}
            }
          ],
          "friendType": {}
        }
    }
}
```

**Important**: Make sure there are no cycles in your structures!

# SerializerFactory

Factory constructor takes in a class of the object to be serialized in order to generate a java code for a Serializer for this specific class. 
Name of the generated class generates as follows: `className` + `Serializer`.
Output format is the same.
For private fields the serializer calls getters. If there is no public getter for a private field, then the serializer skips this field.
