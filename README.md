# Buddy
A fast and powerful EventManager that avoids Reflection.

## Another one..?

**EventManager**, **EventBus**, **Pubsub** are various names used by libraries to describe a service of event posting to listeners yet
Buddy is another one of them...

*Why would I use Buddy?*  
Buddy is a fast, lightweight and highly customizable EventManager. It provides really powerful implementations by default but they can be improved
over time and to your conveniance if you modify them.  

## Performances

One of the main focus of most event libraries is performance. Buddy is probably a great solution for most circumstances. It uses
Java's LambdaMetaFactory to provide high performances.

_**TODO: Benchmark**_

## Creating your first EventManager

To use the default EventManager implementation, it's as easy as it should be:
```java
Buddy.newEventManager();
```
Keep in mind, this forces the EventManager to use default's factories but you can provide the one you prefere using the
```java
Buddy.newEventManager(ListenerFactory...);
```

## The future of this library

Currently, this library is in some parts incompletes but you can contribute to fix this!

**TODO-LIST**:
- README
- Benchmarks
- Filters? (Shyrogan: Not sure if that's a good idea, would add additional operations to post method)
