import java.util.UUID

import cats.effect._
import cats.implicits.{catsSyntaxApplicative, catsSyntaxTuple2Parallel}

import scala.concurrent.duration.DurationInt

case class User(id: UUID, points: Int)

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = for {
    user <- Ref[IO].of(User(UUID.randomUUID(), 0))
    _ <- (incrementPoints(1, user).replicateA(3), incrementPoints(2, user).replicateA(3)).parTupled.void
    finalValue <- user.get
  } yield if (finalValue == 6) ExitCode.Success else ExitCode.Error

  def incrementPoints(taskId: Int, user: Ref[IO, User]): IO[Unit] = for {
    _ <- IO.sleep(1.second)
    before <- user.get
    _ = println(s"$taskId before: ${before.points}")
    _ <- user.update(u => u.copy(points = u.points + 1))
    after <- user.get
    _ = println(s"$taskId after: ${after.points}")
//    _ <- incrementPoints(taskId, user)
  } yield ()
}