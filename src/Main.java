import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class Employee {
    String name;
    String dept;
    double salary;

    public Employee(String name, String dept, double salary) {
        this.dept = dept;
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", salary=" + salary +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {

        //set ignore duplicates based on hashcode and equals method for specific  field (only name)
        //and consider two object to be single
        Set<Employee> set = new HashSet<>();
        Employee e1 = new Employee("Amit", "IT", 30000);
        Employee e2 = new Employee("Amit", "bio", 30000);


        boolean objCheck = e1.equals(e2);
        System.out.println("Object matches?" + objCheck);

        //above 2 objects are equal based on name attribute which is overriden in
        // hascode and equals methods

        set.add(e1);
        set.add(e2);

        System.out.println(set.size());
        System.out.println("Value::" + set);


        // Hashmap replaces the value with with latest record if key are duplicate
        HashMap<Employee, String> map = new HashMap<>();
        map.put(e1, "Amit");
        map.put(e2, "Sumit");

        System.out.println("Hashmap size " + map.size() + "Details::" + map);


/// /////////////////////////////////////////////////////////////////////////////

        //Program to find character count in a string -use hashmap

        String input = "abccdd";
        Map<Character, Integer> freqmap = new HashMap<>();

        for (Character c : input.toCharArray()) {
            freqmap.put(c, freqmap.getOrDefault(c, 0) + 1);
        }
        System.out.println("Frequency of characters" + freqmap);

        ////////////////////////////////////////////////////////////////////////////////////
        //Find department with highest salary
        List<Employee> employeeList = List.of(
                new Employee("Amit", "IT", 30000),
                new Employee("Anil", "IT", 29000),
                new Employee("Ashok", "IT", 21000),
                new Employee("Sumit", "bio", 4000),
                new Employee("rohan", "Compute science", 90000)
        );
        Map<String, Optional<Employee>> result =
                employeeList.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDept,
                                Collectors.maxBy(Comparator.comparing(Employee::getSalary))
                        ));


        for (Map.Entry<String, Optional<Employee>> mapValues : result.entrySet()) {
            System.out.println("Map::" + mapValues.getKey() + "Values " + mapValues.getValue());
        }

/// //////////////////////////////////////////////////////////////////////////////


        // find the total number of employees

        Map<String, Long> countDept = employeeList.stream().collect(
                Collectors.groupingBy(
                        Employee::getDept,
                        Collectors.counting()
                ));

        for (Map.Entry<String, Long> f : countDept.entrySet()) {
            System.out.println(" Departments Name" + f.getKey() + "Total" + f.getValue());
        }

        //2. find out common prefix character in string array

        String[] inputString = {"Amit", "Ammu", "Amil"};

        String prefix = inputString[0];

        for (int i = 1; i < inputString.length; i++) {

            while (!inputString[i].startsWith(prefix)) {

                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }

        System.out.println("Common Prefix" + prefix);

/////////////////////////////////////////////////////////////////////////////////////////////


        //find out top 3 name of employee basis of salary using stream api.

        List<String> list1 = employeeList.stream().
                sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3).map(Employee::getName)
                .toList();

        System.out.println("Top 3 Highest salary employee" + list1);


        ///////////////////////////////////////////////////////////////////////////////////////////
        //Creating a immutable list in streams

        //var result= Stream.of("null","Amit","IT").toList();
        // result.add("Jake Gilly"); //cannot be done

        //create mutable list in streams

        var resultMutable = Stream.of("amit", "IT", 1000).collect(Collectors.toList());
        resultMutable.add("Jake");
        System.out.println("Result Mutable" + resultMutable);


        //Java program to find the index of a particular string in a List

        //create 2 List Find the index of Hello and Hi

        //create 2 List Find the index of Hello and Hi
        List<String> a = Arrays.asList("Eshita", "Hello", "Hi");
        List<String> b = Arrays.asList("Hello", "Hi");

        System.out.println("Index fetch" + a.indexOf("Hello"));


        //How to find duplicate elements in a string using streams

        String check = "programming";

        Set<Character> seen = new HashSet<>();
        Set<Character> duplicates =
                check.chars().
                        mapToObj(e -> (char) e).
                        filter(e -> !seen.add(e))
                        .collect(Collectors.toSet());

        System.out.println("Duplicates in String" + duplicates);
        System.out.println("Non duplicates in String" + seen);


        //First non repeated character in a string

//        String checkFirstRepated = "Ammit";
//
//        HashMap<Character,Integer> countMap =new HashMap<>();
//
//        for(Character e :checkFirstRepated.toCharArray()){
//            countMap.put(e,countMap.getOrDefault(e,0)+1);
//        }
//
//        System.out.println(countMap);
//        for(Map.Entry<Character,Integer> entry :countMap.entrySet()){
//
//            if(entry.getValue()==1){
//                System.out.println("Non Repeating first character"+entry.getKey());
//                return;
//            }
//
//        }


        //Fist non-repated character using streams


        String input1 = "Ammitt";
        Character result1 = input1.chars()                      // IntStream
                .mapToObj(c -> (char) c)                      // Convert to Character
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,                  // Maintain order
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)      // non-repeated
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        System.out.println("First non-repeated character: " + result1);


        //Find  occurences of a string using streams not getOrDefault function


        String input2 = "Ammittt";
        Map<Character, Long> result2 = input2.chars()                      // IntStream
                .mapToObj(c -> (char) c)                      // Convert to Character
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,                  // Maintain order
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("String Occurences Count: " + result2);
        //sum of digits  where target sum =10

        int[] digits = {1, 2, 5, 12, 20, 9};
        int sum = 10;

        List<List<List<Integer>>> pair = new ArrayList<>();
        List<List<Integer>> resPair = new ArrayList<>();


        for (int i = 0; i < digits.length - 1; i++) {

            for (int j = 1; j < digits.length; j++) {
                if (digits[i] + digits[j] == sum) {
                    resPair.add(Arrays.asList(digits[i], digits[j]));
                }
            }

            if (!pair.contains(resPair)) {
                pair.add(resPair);
            }

        }
        System.out.println("Sum of digits" + pair);


        // Given a list of integers: List<Integer> list = Arrays.asList(2, 4, 5, 7, 9, 4, 5, 2, 6);
        // how would you find all duplicate elements using Java 8 Streams?


        List<Integer> listInteger = Arrays.asList(2, 4, 5, 7, 9, 4, 5, 2, 6);

        Set<Integer> seenInteger = new HashSet<>();
        Set<Integer> duplicatesInteger = listInteger.stream().filter(
                        e -> !seenInteger.add(e))
                .collect(Collectors.toSet());

        System.out.println("Duplicate Integers" + duplicatesInteger);


        //2.Program to get 3rd Max salary in java 8


        List<Employee> empl = List.of(
                new Employee("Amit", "IT", 30000),
                new Employee("Sumit", "bio", 40000),
                new Employee("rohan", "Compute science", 90000),
                new Employee("rohan", "Compute science", 20000)
        );

        List<Double> Highest3Sal = empl.stream().
                sorted(Comparator.comparing(Employee::getSalary).reversed())
                .map(Employee::getSalary).
                skip(2).
                limit(1).
                toList();

        System.out.println("Third Highest Salary" + Highest3Sal);


        //3.reverse String using Java 8 - String buffer

        StringBuffer sb = new StringBuffer("Amit");
        System.out.println("Reversed String using StringBuffer" + " : " + sb.reverse());


        //Get the employee name by deparment wise, using stream api?

        Map<String, List<String>> e =
                employeeList.stream().
                        collect(Collectors.groupingBy(
                                Employee::getDept,
                                Collectors.mapping(Employee::getName,
                                        Collectors.toList())));
        System.out.println("Name department wise" + e);


        //total salary department wise
        Map<String, Double> f =
                employeeList.stream().
                        collect(Collectors.groupingBy(
                                Employee::getDept,
                                Collectors.summingDouble(Employee::getSalary)));

        System.out.println("total salary department wise" + f);


        // Get the employee by name starts with "A" and In department "HR"

        List<String> res = employeeList.stream()
                .filter(c -> c.getName().startsWith("A")
                        && c.getDept().equalsIgnoreCase("IT")).
                map(Employee::getName)
                .toList();

        System.out.println("Employe in particular Department " + res);


        // second highest salary in each dept


        Map<String, Optional<Employee>> m = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Employee::getSalary).reversed())
                                        .skip(1)
                                        .findFirst())
                ));

        System.out.println("Second highest salary in each department : " + m);


        //find the largest and second largest in an array without using sort


        int[] num = {5, 15, 20, 19, 7};

        int largestNumber = Integer.MIN_VALUE;
        int secondLargestNumber = Integer.MIN_VALUE;

        for (int no : num) {

            if (no > largestNumber) {
                secondLargestNumber = largestNumber;
                largestNumber = no;
            } else if (no > secondLargestNumber && no != largestNumber) {
                secondLargestNumber = no;
            }
            System.out.println("largest Number" + largestNumber);
            System.out.println("Second Largest Number" + secondLargestNumber);

        }


        //3. Given a list of integers, generate all unique pairs (a, b)
        // such that the sum of a + b equals a specified target value (e.g., 5).
        // The result should be a list of string-formatted pairs.


//
//        Algorithm Question: Find the Next Greater Number Using Digits of a Given Number
//        Example: For input 218765, the next greater number using the same digits is 251678.


        //Given the following code
        //Map<Employee, Integer> map = new HashMap<>();
        // Employee e3 = new Employee("John", 101);
        //map.put(e3, 3);
        // map.put(e3, 4);
        //Size of map with below scenarios? - compile time error as same reference object
        //


        // ********************* BASIC STREAMS PROBLEMS  *****************************************


        //OUT PUT LIKE :
        //  {One=1, Two=2, Three=3}

        Map<Integer, String> map1 = Map.of
                (1, "One",
                        2, "Two",
                        3, "Three");
        Map<String, Integer> transformed = map1.entrySet()
                .stream()
                .collect(Collectors.toMap
                        (entry -> entry.getValue(), entry -> entry.getKey()));
        System.out.println("Transformed Map " + transformed);

        //OUTPUT :    // {One- 1, Two- 2, Three- 3}

        String resultMap = transformed.entrySet()
                .stream()
                .map(g -> g.getValue() + "- " + g.getKey())
                .collect(Collectors.joining(", ", "{", "}"));

        System.out.println("Transformed Map with String" + resultMap);


        // OUTPUT

//        Output Uppercase -> [A]
//        Lowercase -> [b, x, y]
//        Digits -> [3]
//        Others -> [#]


        List<Character> chars = Arrays.asList('A', 'b', 'x', 'y', '#', '3');
        Map<String, List<Character>> grouped = chars.
                stream()
                .collect
                        (Collectors.groupingBy(c ->
                        {
                            if (Character.isUpperCase(c)) return "Uppercase";
                            else if (Character.isLowerCase(c)) return "Lowercase";
                            else if (Character.isDigit(c)) return "Digits";
                            else return "Others";
                        }));
        grouped.forEach((k, v) -> System.out.println(k + " -> " + v));


        // Reverse a String s ="Hello World "

        //Output - olleh  dlrow

        String s = "Hello World ";
        String[] splitString = s.split(" ");
        String resultCheck = "";


        for (String words : splitString) {
            String rev = new StringBuilder(words).reverse().toString();
            resultCheck = resultCheck + rev.toLowerCase();
        }

        resultCheck = resultCheck.trim();

        System.out.println("Reversed Sentence" + " " + resultCheck);
    }

		
	//Given a decimal Array print elements highest to lowest.
	double[] arr = {12.45, 6.9 , 23.58, 17.13,  42.89, 33.78, 71.85};
	
		Arrays.stream(arr)
      .boxed()  // double → Double
      .sorted(Comparator.reverseOrder()).forEach(System.out::println);
      
      
     // Print only numbers by eliminating all other characters from String.
      	String s1 = "1,1,4,5@3,1{6,7,4,8,9,3,4:5:7:?5?9?3?5?6,2";
      
               String result = s1.chars()
                  .filter(Character::isDigit)
                  .mapToObj(c -> String.valueOf((char) c))
                  .collect(Collectors.joining());

                System.out.println(result);

        String d = "Hello World",rev="";
        String [] words =d.split(" ");


         for(String w : words){
                StringBuilder s = new StringBuilder(w);
                rev=rev+s.reverse().toString();
                }

                System.out.println("Reversed String"+rev);
                }
                }


        //reverse a string after removing the vowels.
        String str "abkhhoui";
        String vowels = "aeiouAEIOU";
        StringBuilder sb = new StringBuilder();

        // Remove vowels
        for (char c : str.toCharArray()) {
            if (vowels.indexOf(c) == -1) {
                sb.append(c);
            }
        }

        // Reverse result
        System.out.println("Reversed String "+ sb.reverse().toString());


        // find the number Starts with 1

        List<Integer>number = List. Of(10,50,12,34,78);
          number.stream()
              .map(String::valueOf)        // convert Integer → String
              .filter(n -> n.startsWith("1")) // check starts with '1'
              .forEach(System.out::println);


        //sum of 2 largest number in a list 

        List<Integer> number1 = List.of(1, 5, 3, 4, 7);

         List<Integer> top2 = number1.stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .toList();

        int sum = top2.get(0) + top2.get(1);

        System.out.println("Top 2: " + top2);
        System.out.println("Sum: " + sum);


        //Find 3 Maximum and 3 Minimum number from given list - [45, 12, 56, 15, 24, 75, 31, 89]

        List<Integer> numbers = List.of(45, 12, 56, 15, 24, 75, 31, 89);

        // 3 Minimum numbers
        List<Integer> min3 = numbers.stream()
                .sorted()
                .limit(3)
                .toList();

        // 3 Maximum numbers
        List<Integer> max3 = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();

        System.out.println("Min 3: " + min3);
        System.out.println("Max 3: " + max3);


        // Find the longest word

         List<String> words = Arrays.asList("apple", "banana", "kiwi", "grapefruit");

        String longest = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        System.out.println("Longest word: " + longest);

        //Predict the output

        String s1="Sachin";
        String s2="Sachin";  
        String s3="Umang"; 
        String s4= new String("Varun"); 

        System.out.println(s1.compareTo(s2)); // 0
        System.out.println(s1.compareTo(s3)); // -2
        System.out.println(s4.compareTo(s1)); // 3


        //Sort employee name and employee department

         List<Employee> sorted = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getName)
                        .thenComparing(Employee::getDept))
                .toList();

        sorted.forEach(System.out::println)


        // Demo code for functional Interface Supplier ,consumer ,predicate 

        Supplier<String> supplier = () -> "Hello Amit";

        System.out.println(supplier.get());


        Consumer<String> consumer=(name)->
               consumer.accept("Amit");
               System.out.println(  "Hello"+name);
        

        Predicate<Integer> isEven = n-> n % 2 == 0;
        System.out.println(isEven.test(10));
        System.out.println(isEven.test(3));



      




//public class  Main {
//
//    static int number = 1;
//    static final int MAX = 10;
//
//    static Semaphore s1 = new Semaphore(1); // t1 starts
//    static Semaphore s2 = new Semaphore(0);
//    static Semaphore s3 = new Semaphore(0);
//
//    public static void main(String[] args) {
//
//        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
//
//            executor.submit(() -> print("t1", s1, s2));
//            executor.submit(() -> print("t2", s2, s3));
//            executor.submit(() -> print("t3", s3, s1));
//
//            executor.shutdown();
//        }
//    }
//
//    private static void print(String name, Semaphore current, Semaphore next) {
//        while (true) {
//            try {
//                current.acquire();
//
//                if (number > MAX) {
//                    next.release(); // prevent deadlock
//                    break;
//                }
//
//                System.out.println(name + "-" + number++);
//                next.release(); // passes the control to next thread in sequence
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//}


// Threading check
// class Main {
//
//        static int number = 1;
//        static final int MAX = 10;
//
//        public static void main(String[] args) throws InterruptedException {
//
//            while (number <= MAX) {
//
//                Thread t1 = new Thread(() -> {
//                    if (number <= MAX) {
//                        System.out.println("t1-" + number++);
//                    }
//                });
//
//                Thread t2 = new Thread(() -> {
//                    if (number <= MAX) {
//                        System.out.println("t2-" + number++);
//                    }
//                });
//
//                Thread t3 = new Thread(() -> {
//                    if (number <= MAX) {
//                        System.out.println("t3-" + number++);
//                    }
//                });
//
//                t1.start();
//                t1.join();   // wait till t1 finishes
//
//                t2.start();
//                t2.join();   // wait till t2 finishes
//
//                t3.start();
//                t3.join();   // wait till t3 finishes
//            }
//        }
//    }




