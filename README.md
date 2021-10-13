# points-tracker-cats-effect

An alternative implementation of https://github.com/ellenglight/points-tracker. Originally I handled mutable state using Akka actors. This implementation will
use Cats Effect [Ref](https://typelevel.org/cats-effect/docs/std/ref) instead.
