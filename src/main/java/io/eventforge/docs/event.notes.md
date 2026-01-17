# Event
***

> A scratchpad to scribble thoughts processes and annotate questions, and key takeaways from the learning

## Main concept learned and exercised
- Immutability
  - Immutability using private final fields
  - Defensive copying for mutable fields/collections
  - Private constructor to restrict object creation outside the class
  - Public getter methods and no use of setter methods

## ⚠️ Initial Event structure
- Using wrapped fields instead of primitives
- Used `Long` to store timestamp
- Used `Integer` for version
- Used `Object` for generic payload
- Returning a new `Object` for payload and returning a new `Map` object for metadata
- No usage of factory methods for object creation and creating it using constructor without validations

## 🧠 Thought process behind these decisions 
- Initially thought using Wrapper classes instead of primitives (using `Long` or `Integer` over `long` or `int`) is a better choice always since it would be helpful if needed to deal with collections to avoid autoboxing.
- No particular reasoning behind using `Long` for the timestamp since that was the only thing I had encountered until today to use when dealing with time related fields.
- Also thought to use `DateTime` or `LocalDateTime` over `Long` to store the timestamp more "professionally" but went with `Long` finally because not that much comfortable with `DateTime` API and its method.
- Similarly for the version field, using `Integer` felt like a better option and future-proof.
- Using `Object` for payload seemed like a right choice because the payload is supposed to be dynamic and not fixed to any particular schema so that any service or client can use `EventForge` for storing events taking place in their application.
- Also used `Object` for payload instead of *generics* because it felt like that would have been complicated.
- Returned a new object for their type for each mutable fields thinking it would keep the original value safe since any modifications happening would affect only the newly returned object and not the original object.
- Did not think of factory pattern because was not aware about their use case first of all and so felt like constructor was an obvious choice to create the object instance.
- Did not think of putting the basic validations inside the constructor itself so that the object created would always be in a valid state and thought that validations can be done using separate Validator classes for each of the field necessary to validate.


## 👀 New Insights gained
- Using primitive types is actually better where having null values makes no sense and where clarity is needed for the purpose.
- Using fields like `Long` or `LocalDateTime` for timestamp related fields is generally discouraged because otherwise we would have to deal with timezone, and it can affect consistency. 
- Using `Object` for payload (or any dynamic mutable field in general) is too risky and lossy, because of zero compile-time safety and having to go through the trouble of casting everywhere and more prone to runtime errors.
- Returning new object for defensive copy does not make it immutable.
- ***Invariants*** should be defined/declared in the constructor itself so a valid object instance is guaranteed everytime.
- Learned the usage of static factory methods for instance creation and how it differs from constructor initialisation.
- Get to know the difference between `of()` and `copyOf()` methods and their use cases.

## 💡Updated structure after the learning
- Used `Instant` to store the timestamp to maintain consistency and timezone safety.
- Used `int` for version to avoid nullability and signal mandatory presence.
- Used `Map<String, Object>` for payload to maintain flexible schema design with explicit keys related to the payload data.
- Returned safe defensive copies for the mutable objects using the `copyOf` method (to freeze the value/s received for the mutable field/s).
- Added public static factory methods to create objects with validation regarding the invariants is handled in the constructor itself.
- Used the method returning defensive copy to initialise mutable fields in the constructor.
- Two factory methods to avoid forcing metadata when not required.

## 🤔 Dumb Questions I had (and their answers)
**Q:** Why not initialise a default map object for metadata in the constructor itself to handle the optional absence?
<br> **A:** Because then it would make no difference if metadata is present or not  (resulting in ignoring the metadata even if it is present) which can lead to unforeseen bugs and bad design.

**Q:** Why not apply the `copyOf` method just like when returning defensive copies while providing a default map instance when metadata is not available?
<br>**A:** Because `copyOf` takes an existing `Collection` (or `Map`) as an argument. So, using `of()` with no arguments can be used as a default value.

**Q:** Why use `Instant` over `LocalDateTime` even if it is readable?
<br>**A:** Because `Instant` gives us an absolute timestamp for the event and we do not have to deal with or manage timezone related issues as we would had to with `LocalDateTime`.

**Q:** Why not validate everything in the constructor itself so we don't have to create custom validators?
<br>**A:** Because constructors should always enforce only the Invariants and business logic can change based on context so it is not managed in the constructors.

**Q:** Why not move the validation to the factory methods itself if it ultimately calls the constructor to create an object instance?
<br>**A:** Because creation of a new factory method would require duplicated validation and can lead to inconsistent object states if validation is missed.
<br> Moreover, when design pattern like Dependency Injection are introduced, object creation is generally handled via a constructor only and having validation in a constructor rather than a factory method ensure that the object always exist in a valid state.

## 📝 Key takeaways
- ***Invariants*** are logical conditions or rules that must always hold true for every instance of a class throughout its life cycle.
- `.of()`:
  - Returns a new unmodifiable (immutable) collection/map of specified elements.
  - Can generally be used to set default values to avoid null.
  - Used when we know the elements we want in the collection.
  - `null` explodes loudly which is good (throws `NullPointerException` immediately)

- `.copyOf()`:
  - Returns a new unmodifiable (immutable) collection/map of existing elements.
  - Used to provide defensive copies to maintain immutability.
  - Preserves source ordering (stores the collection as it is from the source).
  - Throws `NullPointerException` if the collection is `null` or any element in it is `null`.

- Validating invariants in the constructor ensures that the object created always exist in a valid state so they can be used without needing to validate them at every step of the way.
- When needed to create objects differently based on some condition or on optional data, it is better to use multiple static factories rather than overloaded constructors because factory can clarify intent and can even return cached objects or subtypes if needed

***
🏆 At this point, I realised that immutability is not only about safety, it is about **trust**.
***