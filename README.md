# Scarlet

- Scarlet scans the classpath and uses reflection to invoke methods with `@LikeHandler`, `@DislikeHandler` annotations.
- If a user likes another person, the `@LikeHandler` annotation is scanned for and invokes the correct method handler.
- The `@DislikeHandler` annotation is the opposite principle.

## Usage: 
- Any class extending the `SentimentService` abstract base class

```
  @LikeHandler
  public void processLike() {
       System.out.println("Method is invoked by the `@LikeHandler` annotation");
  }
 ```
 
 ```
  @DislikeHandler
  public void processDislike() {
       System.out.println("Method is invoked by the `@DislikeHandler` annotation");
  }
 ```
 
    
    
