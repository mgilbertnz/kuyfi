package kuyfi

import java.time.{LocalTime, LocalDateTime, Month, ZoneOffset, DayOfWeek}
import java.time.zone.ZoneOffsetTransitionRule
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition

import kuyfi.TZDB._
import org.scalatest.{FlatSpec, Matchers}

class TZDBOptimizedCodeGeneratorSpec extends FlatSpec with Matchers {
  import TZDBCodeGenerator.OptimizedTreeGenerator._

  val zone1 = Zone("Europe/Belfast", List(
                ZoneTransition(GmtOffset(0, -23, -40), NullRule,                        "LMT",     Some(Until(1880, Some(Month.AUGUST),  Some(DayOfTheMonth(2)),  None))),
                ZoneTransition(GmtOffset(0, -25, -21), NullRule,                        "DMT",     Some(Until(1916, Some(Month.MAY),     Some(DayOfTheMonth(21)), Some(AtWallTime(LocalTime.of(2, 0)))))),
                ZoneTransition(GmtOffset(0, -25, -21), FixedOffset(GmtOffset(1, 0, 0)), "IST",     Some(Until(1916, Some(Month.OCTOBER), Some(DayOfTheMonth(1)),  Some(AtStandardTime(LocalTime.of(2, 0)))))),
                ZoneTransition(GmtOffset(0,   0,   0), RuleId("GB-Eire"),               "%s",      Some(Until(1968, Some(Month.OCTOBER), Some(DayOfTheMonth(27)), None))),
                ZoneTransition(GmtOffset(1,   0,   0), NullRule,                        "BST",     Some(Until(1971, Some(Month.OCTOBER), Some(DayOfTheMonth(31)), Some(AtUniversalTime(LocalTime.of(2, 0)))))),
                ZoneTransition(GmtOffset(0,   0,   0), RuleId("GB-Eire"),               "%s",      Some(Until(1996, None,                None,                    None))),
                ZoneTransition(GmtOffset(0,   0,   0), RuleId("EU"),                    "GMT/BST", None)
            ))

  val zoneFixed = Zone("Etc/GMT+1", List(ZoneTransition(GmtOffset(-1,0,0), NullRule, "-01",None)))

  val zone2 = Zone("Africa/Tripoli", List(
      ZoneTransition(GmtOffset( 0, 52, 44), NullRule,        "LMT",   Some(Until(1920, None,                  None,                    None))),
      ZoneTransition(GmtOffset( 1,  0,  0), RuleId("Libya"), "CE%sT", Some(Until(1959, None,                  None,                    None))),
      ZoneTransition(GmtOffset( 2,  0,  0), NullRule,        "EET",   Some(Until(1982, None,                  None,                    None))),
      ZoneTransition(GmtOffset( 1,  0,  0), RuleId("Libya"), "CE%sT", Some(Until(1990, Some(Month.MAY),       Some(DayOfTheMonth(4)),  None))),
      ZoneTransition(GmtOffset( 2,  0,  0), NullRule,        "EET",   Some(Until(1996, Some(Month.SEPTEMBER), Some(DayOfTheMonth(30)), None))),
      ZoneTransition(GmtOffset( 1,  0,  0), RuleId("Libya"), "CE%sT", Some(Until(1997, Some(Month.OCTOBER),   Some(DayOfTheMonth(4)),  None))),
      ZoneTransition(GmtOffset( 2,  0,  0), NullRule,        "EET",   Some(Until(2012, Some(Month.NOVEMBER),  Some(DayOfTheMonth(10)), Some(AtWallTime(LocalTime.of(2, 0)))))),
      ZoneTransition(GmtOffset( 1,  0,  0), RuleId("Libya"), "CE%sT", Some(Until(2013, Some(Month.OCTOBER),   Some(DayOfTheMonth(25)), Some(AtWallTime(LocalTime.of(2, 0)))))),
      ZoneTransition(GmtOffset( 2,  0,  0), NullRule,        "EET",   None)
  ))

  val link1 = Link("Europe/Belfast", "Europe/Ireland")
  val link2 = Link("America/Curacao", "America/Aruba")

  import TZDBCodeGenerator._
  import treehugger.forest._

  "TZDB Code generator" should
    "generate a name from a Zone" in {
      treeToString(TreeGenerator[Zone].generateTree(zone1)) shouldBe "(\"Europe/Belfast\", rules.Europe_Belfast)"
    }
    it should "generate a name from a Fixed offset Zone" in {
      treeToString(TreeGenerator[Zone].generateTree(zoneFixed)) shouldBe "(\"Etc/GMT+1\", rules.Etc_GMT_plus_1)"
    }
    it should "generate a tuple from a Link" in {
      treeToString(TreeGenerator[Link].generateTree(link2)) shouldBe "(\"America/Aruba\", \"America/Curacao\")"
    }
    it should "clean dangling links" in {
      val rows1 = link1.liftC[Row] :: link2.liftC[Row] :: Nil
      cleanLinks(rows1) shouldBe empty

      val rows2 = link1.liftC[Row] :: link2.liftC[Row] :: zone1.liftC[Row] :: Nil

      cleanLinks(rows2) should have size 2
    }
    it should "generate from zone offset transition rule" in {
      val rule = ZoneOffsetTransitionRule.of(Month.JANUARY, 3, DayOfWeek.MONDAY, LocalTime.of(12, 0), false, TimeDefinition.UTC, ZoneOffset.ofHours(0), ZoneOffset.ofHours(1), ZoneOffset.ofHours(2))
      treeToString(TreeGenerator[ZoneOffsetTransitionRule].generateTree(rule)) shouldBe s"scala.scalajs.js.Array[Int](1, 3, 1, 43200, 0, 0, 0, 3600, 7200)"
    }
    it should "generate from zone offset transition" in {
      treeToString(TreeGenerator[ZoneOffsetTransitionParams].generateTree(ZoneOffsetTransitionParams(LocalDateTime.of(2017, Month.FEBRUARY, 1, 10, 15), ZoneOffset.ofHours(1), ZoneOffset.ofHours(2)))) shouldBe s"scala.scalajs.js.Array[Int](2017032, 36900, 3600, 7200)"
    }
    it should "generate from Month" in {
      treeToString(TreeGenerator[Month].generateTree(Month.JANUARY)) shouldBe s"1"
      treeToString(TreeGenerator[Month].generateTree(Month.DECEMBER)) shouldBe s"12"
    }
    it should "generate from LocalDateTime" in {
      treeToString(TreeGenerator[LocalDateTime].generateTree(LocalDateTime.of(2017, Month.FEBRUARY, 1, 10, 15, 25))) shouldBe s"scala.scalajs.js.Array[Int](2017, 32, 36925)"
    }
    it should "generate from offset" in {
      treeToString(TreeGenerator[ZoneOffset].generateTree(ZoneOffset.ofHoursMinutesSeconds(1, 2, 3))) shouldBe s"val zo_3723: Int = 3723"
    }
    it should "import a top level package" in {
      treeToString(exportTzdb(TzdbVersion("2018a"), "org.threeten.bp", "org.threeten.bp", link1.liftC[Row] :: link2.liftC[Row] :: zone1.liftC[Row] :: Nil, _ => true)) should include ("import scala.scalajs.js")
    }
    it should "include the version" in {
      treeToString(exportTzdb(TzdbVersion("2018a"), "org.threeten.bp", "org.threeten.bp", link1.liftC[Row] :: link2.liftC[Row] :: zone1.liftC[Row] :: Nil, _ => true)) should include ("2018a")
    }
    it should "generate from zone rules param" in {
      val standardTransitions = List(ZoneOffsetTransitionParams(LocalDateTime.of(2017, Month.FEBRUARY, 1, 10, 15), ZoneOffset.ofHours(1), ZoneOffset.ofHours(2)))
      val transitions = List(ZoneOffsetTransitionParams(LocalDateTime.of(2005, Month.NOVEMBER, 3, 0, 0), ZoneOffset.ofHours(0), ZoneOffset.ofHours(2)))
      val rule = List(ZoneOffsetTransitionRule.of(Month.JANUARY, 3, DayOfWeek.MONDAY, LocalTime.of(12, 0), false, TimeDefinition.UTC, ZoneOffset.ofHours(0), ZoneOffset.ofHours(1), ZoneOffset.ofHours(2)))
      val params = StandardRulesParams(ZoneOffset.ofHours(1), ZoneOffset.ofHours(0), standardTransitions, transitions, rule)
      treeToString(TreeGenerator[ZoneRulesParams].generateTree(params)).trim shouldBe s"""js.Dynamic.literal(("s", 3600), ("w", 0), ("t", scala.scalajs.js.Array[scala.scalajs.js.Array[Int]](scala.scalajs.js.Array[Int](2017032, 36900, 3600, 7200))), ("l", scala.scalajs.js.Array[scala.scalajs.js.Array[Int]](scala.scalajs.js.Array[Int](2005307, 0, 0, 7200))), ("r", scala.scalajs.js.Array[scala.scalajs.js.Array[Int]](scala.scalajs.js.Array[Int](1, 3, 1, 43200, 0, 0, 0, 3600, 7200))))"""
      treeToString(exportTzdb(TzdbVersion("2018a"), "org.threeten.bp", "org.threeten.bp", link1.liftC[Row] :: link2.liftC[Row] :: zone1.liftC[Row] :: Nil, _ => true)) should include ("import scala.scalajs.js")
    }
}
