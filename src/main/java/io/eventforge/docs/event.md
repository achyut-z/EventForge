# Event
***

## Concepts exercised
- Immutability using final fields
- Constructor invariants to avoid invalid object states
- Defensive copying using Map.copyOf
- Factory methods to control object creation

## Design decisions

### Timestamp representation
- Chose `Instant` over `long` or `DateTime` or `LocalDateTime`.
- Rationale: timezone safety and event consistency.

### Versioning
- Used primitive `int` over `Integer`
- Rationale: Avoid nullability and signals mandatory presence.

### Mutable collections
- Used `Map<String, Object>` for payload to support flexible event schemas
- Used `Map<String, String>` for metadata to constrain auxiliary information.

### Factory methods
- Created two factory methods to not force the client to send metadata

## Common mistakes avoided

- Avoided validating business logic in the constructor.
- Avoided exposing mutable collections or fields
- Avoided keeping the payload type loose by not using Object to store payload

## Key takeaways

- Use `of()` when dealing with specified elements or you know the elements you want.
- Use `copyOf()` when need to return defensive (shallow) copies of an existing collection.
- Use multiple static factories for object creation (via constructor) to avoid having overloaded constructors. And can keep the constructors private to restrict questionable object creation.
- Validate invariants in constructor to ensure the object always exist in a valid state.

***
- Static factory method is preferred when needed instance control (cached objects), or the ability to return subtype, or to avoid confusing multiple overloaded constructors. Particularly valuable in domains where the creation logic conveys important meaning.
- Constructors are preferred when there is no or minimal construction logic, when needed a straightforward simple object creation approach to instantiate an object. Particularly useful for creating DTOs or POJOs, or to use Dependency Injection.