# JReusable Components - AI Coding Agent Instructions

## Project Overview

**Purpose**: A CRUD framework library that eliminates repetitive boilerplate code across Spring, Jakarta EE, Quarkus, Helidon, and other Java frameworks.

**Architecture**: Multi-layered, technology-agnostic facade pattern that provides standardized CRUD operations (Create, Read, Update, Delete) with pre/post/error function hooks for extensibility.

## Core Architecture Patterns

### Facade Pattern with Builder

- **Base Layer**: `BaseFacade<Entity, Id>` - sealed class defining operation execution pipeline with pre/main/post/error function stages
- **Builders**: `*FacadeBuilder` classes use Consumer-based configuration pattern to inject operation functions
  - Example: `CommandFacadeBuilder` configured via `new CommandFacadeBuilder<>$ -> { $.saveFunction = ...; })`
- **Sealed Hierarchies**: Strict inheritance using Java sealed classes to control extension points:
  - `BaseFacade` permits: `AbstractCommandFacade`, `AbstractQueryFacade`, `AbstractQuerySpecificationFacade`, `AbstractQueryPaginationFacade`, `AbstractQueryPaginationSpecificationFacade`

### Three Core Facades

1. **CommandFacade**: save, saveAll, update, updateAll, delete, deleteAll, deleteById, deleteByIds
2. **QueryFacade**: findById, findAll, countAll, existsById, existsAll
3. **QueryPaginationFacade**: paginated/sorted queries with pagination support

### Cross-Cutting Infrastructure

All facades delegate to injected services (configured in `BaseFacadeBuilder`):

- `InterfaceSecurityService`: Session/user context management (default: `DefaultSecurityService`)
- `InterfaceI18nService`: Internationalization (default: `JavaSEI18nService`)
- `InterfaceExceptionAdapterService`: Exception translation (default: `DefaultExceptionAdapterService`)

## Project Structure

```
jreusable-components-api/          # Framework-agnostic abstractions
├── jreusable-components-base/     # Core facades, domain, utilities
│   └── jreusable-components-base-core/  # Facades, AbstractEntity, exception handling
├── jreusable-components-rest/     # REST/HTTP abstractions
└── jreusable-components-cqrs/     # CQRS pattern support

jreusable-components-technology/    # Framework-specific implementations
├── jreusable-components-spring/   # Spring Boot implementations (3.2.6+, Java 21+)
├── jreusable-components-jakarta/  # Jakarta EE implementations
├── jreusable-components-quarkus/  # Quarkus implementations
├── jreusable-components-helidon/  # Helidon implementations
└── jreusable-components-microprofile/ # MicroProfile support
```

## Critical Patterns & Conventions

### Domain Model

- All entities must extend `AbstractEntity<Id>` which enforces validation and equality contracts
- Supports logical deletion via `InterfaceEntityLogicalDeletion`
- Entities use generic type parameters for ID type flexibility (not just `Long`)

### Operation Function Signature Pattern

Operations are structured with 4 function types (applied in order):

```java
preFunction(input1, input2, directives)    // Validation, state check
mainFunction(input1, input2, directives)   // Core operation
posFunction(output, directives)             // Transformation, logging
errorFunction(exception, input1, input2, directives) // Error handling
```

### Framework Implementation Pattern

Technology-specific facades (e.g., `SpringCommandFacade`) extend generic `CommandFacade` and inject repository-specific lambda functions into builder:

```java
new CommandFacadeBuilder<>($ -> {
    $.saveFunction = (entity, directives) -> repository.save(entity);
    $.deleteFunction = (entity, directives) -> { repository.delete(entity); return null; }
    // ... etc
})
```

### Exception Handling

- Custom exception hierarchy in `org.reusablecomponents.base.core.infra.exception.common`
- Exceptions are translated via `InterfaceExceptionAdapterService` for API consistency
- All exceptions support internationalization through `InterfaceI18nService`

### Generic Type Parameter Naming Conventions

The builders use extensive generic parameters with strict naming conventions. These parameters drive type safety across the operation pipeline.

**CommandFacadeBuilder pattern** (8 operation types × 2 in/out parameters):

```java
CommandFacadeBuilder<
  Entity extends AbstractEntity<Id>, Id,      // core: entity type & id type
  SaveEntityIn, SaveEntityOut,                // save single
  SaveEntitiesIn, SaveEntitiesOut,            // save batch
  UpdateEntityIn, UpdateEntityOut,            // update single
  UpdateEntitiesIn, UpdateEntitiesOut,        // update batch
  DeleteEntityIn, DeleteEntityOut,            // delete single
  DeleteEntitiesIn, DeleteEntitiesOut,        // delete batch
  DeleteIdIn, DeleteIdOut,                    // delete by id single
  DeleteIdsIn, DeleteIdsOut                   // delete by id batch
>
```

**QueryFacadeBuilder pattern** (5 result types):

```java
QueryFacadeBuilder<
  Entity extends AbstractEntity<Id>, Id,      // core: entity type & id type
  QueryIdIn,                                  // input type for id queries
  OneResult,                                  // single entity result
  MultipleResult,                             // collection result
  CountResult,                                // count result
  ExistsResult                                // existence check result
>
```

**Key naming semantics**:

- `*In` suffix: Input parameter type (what the method receives)
- `*Out` suffix: Output/return type (what the operation produces)
- Single operations: `Entity`, `Id` (e.g., `SaveEntityIn = Entity`)
- Batch operations: `Entities`, `Ids` (e.g., `SaveEntitiesIn = List<Entity>`)
- Result types: Named after semantic meaning (`OneResult`, `MultipleResult`, `CountResult`, `ExistsResult`)

**Technology-specific simplifications**:
Spring/Jakarta implementations often collapse these to concrete types:

```java
// Spring example: concrete types instead of generics
SpringCommandFacade<Entity extends AbstractEntity<Id>, Id>
  extends CommandFacade<Entity, Id,
    Entity, Entity,              // SaveEntityIn/Out = Entity
    List<Entity>, List<Entity>,  // SaveEntitiesIn/Out = List<Entity>
    // ... rest follow same pattern
  >
```

**When extending or implementing**:

- Always preserve the generic parameter ordering—builders depend on positional type arguments
- Use same `*In`/`*Out` suffix naming when adding new operation types
- For Spring implementations, it's safe to collapse `*In` and `*Out` to the same concrete type when the operation uses the same type for input and output

## Build & Development

### Maven Multi-Module Build

- **Root POM**: Aggregates 3 modules (jreusable-components-api, jreusable-components-technology, jreusable-components-apptest)
- **Parent Dependencies**: Extends `allset-java` parent POM for centralized plugin/dependency management
- **Quality Tools**: Maven plugins configured for PMD, SpotBugs, Checkstyle, JaCoCo code coverage, and Javadoc generation

### Build Commands

```bash
mvn clean install          # Full build with tests
mvn clean install -DskipTests  # Build without tests
mvn test -pl <module>      # Test specific module
mvn checkstyle:check       # Code style validation
```

### Module Dependencies

- **jreusable-components-api** provides abstractions
- **jreusable-components-technology** implementations depend on jreusable-components-api
- **jreusable-components-apptest** provides integration tests and example usage

## Key Files & Locations

| File                                                                                                                                                                                                                                                                                                                                   | Purpose                                              |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------- |
| [jreusable-components-api/jreusable-components-base/jreusable-components-base-core/src/main/java/org/reusablecomponents/base/core/application/base/BaseFacade.java](jreusable-components-api/jreusable-components-base/jreusable-components-base-core/src/main/java/org/reusablecomponents/base/core/application/base/BaseFacade.java) | Core facade execution engine with operation pipeline |
| [jreusable-components-api/jreusable-components-base/jreusable-components-base-core/src/main/java/org/reusablecomponents/base/core/domain/AbstractEntity.java](jreusable-components-api/jreusable-components-base/jreusable-components-base-core/src/main/java/org/reusablecomponents/base/core/domain/AbstractEntity.java)             | Base domain entity enforcing validation & equality   |
| [jreusable-components-technology/jreusable-components-spring/jreusable-components-spring-core/src/main/java/org/reusablecomponents/spring/core/](jreusable-components-technology/jreusable-components-spring/jreusable-components-spring-core/src/main/java/org/reusablecomponents/spring/core/)                                       | Spring Boot facade implementations                   |

## Common Development Tasks

### Adding New Operation Type

1. Create function interface in `jreusable-components-api/..../base/core/application/*/function/`
2. Add to appropriate `*FacadeBuilder` class with pre/pos/error variants
3. Add sealed permit to base facade
4. Implement in each technology module

### Extending for New Framework

1. Implement `SpringCommandFacade`-style class that extends generic `CommandFacade`
2. Create builder with lambda injection for repository operations
3. Add framework module to `jreusable-components-technology`
4. Register in parent POM modules list

### Testing Pattern

- Use Mockito for repository mocking in builder lambdas
- Facades provide validation via `Validator` parameter in `AbstractEntity.validate()`
- Verify security context capture via `securityService.getSession()`

## Technology Versions & Requirements

- **Java**: >= 21 (using sealed classes, records, pattern matching)
- **Maven**: >= 3.8.8
- **Spring Boot**: 3.2.6+ (if using Spring implementations)
- **Jakarta EE**: Latest versions for Jakarta implementations
- **Test Frameworks**: JUnit 5, Mockito 5.x with BOM dependency management

## Important Notes

- Project is in **Maintenance** status - used primarily for learning new Java technologies
- Heavily uses Java 21+ features (sealed classes, records, text blocks, pattern matching)
- Event-driven architecture implied by `BaseFacade` comments - research security session capture and post-function hooks
- Generic type parameters are extensive - preserve IDE/compiler type inference when refactoring
