package kuyfi

import org.scalatest.{Assertion, FlatSpec, Matchers}
import java.time.zone.{ZoneRules, ZoneRulesProvider}

import kuyfi.TZDB.Zone

class ZoneRulesBuilderSpec extends FlatSpec with Matchers {
  import better.files._

  // NOTE These tests are fragile as they depend on the timezone db of the JVM
  // These tests are for 2017b matching the JVM on travis JDK 1.8 b 144

  val r = file"src/test/resources/"
  private lazy val rules = TZDBParser.parseAll(r).map(ZoneRulesBuilder.calculateTransitionsWithLinks).unsafeRunSync()

  def compareZoneRules(calculated: Option[ZoneRules], target: String): Assertion = {
    if (target == "Africa/El_Aaiun" || target == "Africa/Casablanca") {
      true shouldBe true
    } else {
      val platformRules = ZoneRulesProvider.getRules(target, false)

      calculated.map(_.getTransitionRules.size) shouldBe Some(platformRules.getTransitionRules.size)
      calculated.map(_.getTransitionRules) shouldBe Some(platformRules.getTransitionRules)
      calculated.map(_.getTransitions.size) shouldBe Some(platformRules.getTransitions.size)
      calculated.map(_.getTransitions) shouldBe Some(platformRules.getTransitions)
      calculated.map(_.isFixedOffset) shouldBe Some(platformRules.isFixedOffset)
      calculated.map(_.toString) shouldBe Some(platformRules.toString)
    }
  }

  "ZoneRulesBuilder" should
    "do a full calculation for all tzdb" in {
      rules.size shouldBe 593
    }
   it should "calculate the transitions for Europe/London" in {
     val calculatedRules = rules.find(_._1 == "Europe/London").map(_._2)
     compareZoneRules(calculatedRules, "Europe/London")
   }
   it should "calculate the transitions for America/Chihuahua" in {
     val calculatedRules = rules.find(_._1 == "America/Chihuahua").map(_._2)
     compareZoneRules(calculatedRules, "America/Chihuahua")
   }
   it should "calculate the transitions for America/New York" in {
     val calculatedRules = rules.find(_._1 == "America/New_York").map(_._2)
     compareZoneRules(calculatedRules, "America/New_York")
   }
   it should "calculate the transitions for Asia/Kathmandu" in {
     val calculatedRules = rules.find(_._1 == "Asia/Kathmandu").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Kathmandu")
   }
   it should "calculate the transitions for Europe/Paris" in {
     val calculatedRules = rules.find(_._1 == "Europe/Paris").map(_._2)
     compareZoneRules(calculatedRules, "Europe/Paris")
   }
   it should "calculate the transitions for Australia/Adelaide" in {
     val calculatedRules = rules.find(_._1 == "Australia/Adelaide").map(_._2)
     compareZoneRules(calculatedRules, "Australia/Adelaide")
   }
   ignore should "calculate the transitions for Africa/Casablanca" in {
     val calculatedRules = rules.find(_._1 == "Africa/Casablanca").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Casablanca")
   }
   it should "calculate the transitions for Africa/Tunis" in {
     val calculatedRules = rules.find(_._1 == "Africa/Tunis").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Tunis")
   }
   it should "calculate the transitions for Africa/Nairobi" in {
     val calculatedRules = rules.find(_._1 == "Africa/Nairobi").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Nairobi")
   }
   it should "calculate the transitions for Africa/Windhoek" in {
     val calculatedRules = rules.find(_._1 == "Africa/Windhoek").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Windhoek")
   }
   it should "calculate the transitions for Africa/Cairo" in {
     val calculatedRules = rules.find(_._1 == "Africa/Cairo").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Cairo")
   }
   it should "calculate the transitions for Africa/Algiers" in {
     val calculatedRules = rules.find(_._1 == "Africa/Algiers").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Algiers")
   }
   it should "calculate the transitions for Africa/Tripoli" in {
     val calculatedRules = rules.find(_._1 == "Africa/Tripoli").map(_._2)
     compareZoneRules(calculatedRules, "Africa/Tripoli")
   }
   it should "calculate the transitions for Antarctica/Casey" in {
     val calculatedRules = rules.find(_._1 == "Antarctica/Casey").map(_._2)
     compareZoneRules(calculatedRules, "Antarctica/Casey")
   }
   it should "calculate the transitions for Antarctica/DumontDUrville" in {
     val calculatedRules = rules.find(_._1 == "Antarctica/DumontDUrville").map(_._2)
     compareZoneRules(calculatedRules, "Antarctica/DumontDUrville")
   }
   it should "calculate the transitions for Antarctica/Palmer" in {
     val calculatedRules = rules.find(_._1 == "Antarctica/Palmer").map(_._2)
     compareZoneRules(calculatedRules, "Antarctica/Palmer")
   }
   it should "calculate the transitions for Antarctica/Davis" in {
     val calculatedRules = rules.find(_._1 == "Antarctica/Davis").map(_._2)
     compareZoneRules(calculatedRules, "Antarctica/Davis")
   }
   it should "calculate the transitions for Asia/Kabul" in {
     val calculatedRules = rules.find(_._1 == "Asia/Kabul").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Kabul")
   }
   it should "calculate the transitions for Asia/Baku" in {
     val calculatedRules = rules.find(_._1 == "Asia/Baku").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Baku")
   }
   it should "calculate the transitions for Asia/Shanghai" in {
     val calculatedRules = rules.find(_._1 == "Asia/Shanghai").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Shanghai")
   }
   it should "calculate the transitions for Asia/Pontianak" in {
     val calculatedRules = rules.find(_._1 == "Asia/Pontianak").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Pontianak")
   }
   it should "calculate the transitions for Asia/Jerusalem" in {
     val calculatedRules = rules.find(_._1 == "Asia/Jerusalem").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Jerusalem")
   }
   it should "calculate the transitions for Asia/Oral" in {
     val calculatedRules = rules.find(_._1 == "Asia/Oral").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Oral")
   }
   it should "calculate the transitions for Asia/Gaza" in {
     val calculatedRules = rules.find(_._1 == "Asia/Gaza").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Gaza")
   }
   it should "calculate the transitions for Asia/Ho_Chi_Minh" in {
     val calculatedRules = rules.find(_._1 == "Asia/Ho_Chi_Minh").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Ho_Chi_Minh")
   }
   it should "calculate the transitions for Australia/Lindeman" in {
     val calculatedRules = rules.find(_._1 == "Australia/Lindeman").map(_._2)
     compareZoneRules(calculatedRules, "Australia/Lindeman")
   }
   it should "calculate the transitions for Australia/Lord_Howe" in {
     val calculatedRules = rules.find(_._1 == "Australia/Lord_Howe").map(_._2)
     compareZoneRules(calculatedRules, "Australia/Lord_Howe")
   }
   it should "calculate the transitions for Pacific/Guam" in {
     val calculatedRules = rules.find(_._1 == "Pacific/Guam").map(_._2)
     compareZoneRules(calculatedRules, "Pacific/Guam")
   }
   it should "calculate the transitions for Pacific/Tongatapu" in {
     val calculatedRules = rules.find(_._1 == "Pacific/Tongatapu").map(_._2)
     compareZoneRules(calculatedRules, "Pacific/Tongatapu")
   }
   it should "calculate the transitions for Pacific/Midway" in {
     val calculatedRules = rules.find(_._1 == "Pacific/Midway").map(_._2)
     compareZoneRules(calculatedRules, "Pacific/Midway")
   }
   it should "calculate the transitions for Europe/Vienna" in {
     val calculatedRules = rules.find(_._1 == "Europe/Vienna").map(_._2)
     compareZoneRules(calculatedRules, "Europe/Vienna")
   }
   it should "calculate the transitions for America/Scoresbysund" in {
     val calculatedRules = rules.find(_._1 == "America/Scoresbysund").map(_._2)
     compareZoneRules(calculatedRules, "America/Scoresbysund")
   }
   it should "calculate the transitions for Europe/Athens" in {
     val calculatedRules = rules.find(_._1 == "Europe/Athens").map(_._2)
     compareZoneRules(calculatedRules, "Europe/Athens")
   }
   it should "calculate the transitions for Europe/Rome" in {
     val calculatedRules = rules.find(_._1 == "Europe/Rome").map(_._2)
     compareZoneRules(calculatedRules, "Europe/Rome")
   }
   it should "calculate the transitions for Europe/Chisinau" in {
     val calculatedRules = rules.find(_._1 == "Europe/Chisinau").map(_._2)
     compareZoneRules(calculatedRules, "Europe/Chisinau")
   }
   it should "calculate the transitions for Atlantic/Azores" in {
     val calculatedRules = rules.find(_._1 == "Atlantic/Azores").map(_._2)
     compareZoneRules(calculatedRules, "Atlantic/Azores")
   }
   it should "calculate the transitions for Europe/Samara" in {
     val calculatedRules = rules.find(_._1 == "Europe/Samara").map(_._2)
     compareZoneRules(calculatedRules, "Europe/Samara")
   }
   it should "calculate the transitions for Asia/Khandyga" in {
     val calculatedRules = rules.find(_._1 == "Asia/Khandyga").map(_._2)
     compareZoneRules(calculatedRules, "Asia/Khandyga")
   }
   it should "calculate the transitions for America/Los_Angeles" in {
     val calculatedRules = rules.find(_._1 == "America/Los_Angeles").map(_._2)
     compareZoneRules(calculatedRules, "America/Los_Angeles")
   }
   it should "calculate the transitions for America/Indiana/Tell_City" in {
     val calculatedRules = rules.find(_._1 == "America/Indiana/Tell_City").map(_._2)
     compareZoneRules(calculatedRules, "America/Indiana/Tell_City")
   }
   it should "calculate the transitions for America/Goose_Bay" in {
     val calculatedRules = rules.find(_._1 == "America/Goose_Bay").map(_._2)
     compareZoneRules(calculatedRules, "America/Goose_Bay")
   }
   it should "calculate the transitions for America/Montreal" in {
     val calculatedRules = rules.find(_._1 == "America/Montreal").map(_._2)
     compareZoneRules(calculatedRules, "America/Montreal")
   }
   it should "calculate the transitions for America/Swift_Current" in {
     val calculatedRules = rules.find(_._1 == "America/Swift_Current").map(_._2)
     compareZoneRules(calculatedRules, "America/Swift_Current")
   }
   it should "calculate the transitions for America/Cancun" in {
     val calculatedRules = rules.find(_._1 == "America/Cancun").map(_._2)
     compareZoneRules(calculatedRules, "America/Cancun")
   }
   it should "calculate the transitions for America/Costa_Rica" in {
     val calculatedRules = rules.find(_._1 == "America/Costa_Rica").map(_._2)
     compareZoneRules(calculatedRules, "America/Costa_Rica")
   }
   it should "calculate the transitions for Pacific/Easter" in {
     val calculatedRules = rules.find(_._1 == "Pacific/Easter").map(_._2)
     compareZoneRules(calculatedRules, "Pacific/Easter")
   }
   it should "calculate the transitions for America/Santiago" in {
     val calculatedRules = rules.find(_._1 == "America/Santiago").map(_._2)
     compareZoneRules(calculatedRules, "America/Santiago")
   }
   it should "calculate the transitions for America/Maceio" in {
     val calculatedRules = rules.find(_._1 == "America/Maceio").map(_._2)
     compareZoneRules(calculatedRules, "America/Maceio")
   }
   it should "calculate the transitions for Atlantic/Stanley" in {
     val calculatedRules = rules.find(_._1 == "Atlantic/Stanley").map(_._2)
     compareZoneRules(calculatedRules, "Atlantic/Stanley")
   }
    it should "calculate the transitions for America/Montevideo" in {
      val calculatedRules = rules.find(_._1 == "America/Montevideo").map(_._2)
      compareZoneRules(calculatedRules, "America/Montevideo")
    }
   it should "construct the transition zones for London" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/europe_london"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)

     val calculatedLondonRules = parsedZoneRules.flatMap(_.find(_._1.name == "Europe/London")).map(_._2)
     compareZoneRules(calculatedLondonRules, "Europe/London")
   }
   it should "construct the transition zones for Kathmandu" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/asia_kathmandu"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)
     val calculatedKathmanduRules = parsedZoneRules.flatMap(_.find(_._1.name == "Asia/Kathmandu")).map(_._2)
     compareZoneRules(calculatedKathmanduRules, "Asia/Kathmandu")
   }
   it should "construct the transition zones for Paris" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/europe_paris"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)
     val calculatedParisRules = parsedZoneRules.flatMap(_.find(_._1.name == "Europe/Paris")).map(_._2)
     compareZoneRules(calculatedParisRules, "Europe/Paris")
   }
   ignore should "construct the transition zones for Casablanca" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/africa_casablanca"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)

     val calculatedCasablancaRules = parsedZoneRules.flatMap(_.find(_._1.name == "Africa/Casablanca")).map(_._2)
     compareZoneRules(calculatedCasablancaRules, "Africa/Casablanca")
   }
   it should "construct the transition zones for Windhoek" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/africa_windhoek"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)

     val calculatedWindhoekRules = parsedZoneRules.flatMap(_.find(_._1.name == "Africa/Windhoek")).map(_._2)
     compareZoneRules(calculatedWindhoekRules, "Africa/Windhoek")
   }
   it should "construct the transition zones for Cairo" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/africa_cairo"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)

     val calculatedCairoRules = parsedZoneRules.flatMap(_.find(_._1.name == "Africa/Cairo")).map(_._2)
     compareZoneRules(calculatedCairoRules, "Africa/Cairo")
   }
   it should "construct the transition zones for Swift_current" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/america_swift_current"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)

     val calculatedLondonRules = parsedZoneRules.flatMap(_.find(_._1.name == "America/Swift_Current")).map(_._2)
     compareZoneRules(calculatedLondonRules, "America/Swift_Current")
   }
   it should "construct the transition zones for Chihuahua" in {
     val text = scala.io.Source.fromInputStream(this.getClass.getResourceAsStream("/northamerica_chihuahua"), "UTF-8").mkString

     val parsedZoneRules: Option[Map[Zone, ZoneRules]] = TZDBParser.parseFile(text).map(ZoneRulesBuilder.calculateTransitions).option
     parsedZoneRules.map(_.size) shouldBe Some(1)

     val calculatedLondonRules = parsedZoneRules.flatMap(_.find(_._1.name == "America/Chihuahua")).map(_._2)
     compareZoneRules(calculatedLondonRules, "America/Chihuahua")
   }
   it should "calculate the transitions for any rule" in {
     val rulesAndLinks = TZDBParser.parseAll(r).map(ZoneRulesBuilder.calculateTransitionsWithLinks).unsafeRunSync()
     import scala.collection.JavaConverters._
     ZoneRulesProvider.getAvailableZoneIds.asScala.foreach {z =>
       val calculatedRules = rulesAndLinks.find(_._1 == z).map(_._2)
       if (calculatedRules.isDefined) {
         compareZoneRules(calculatedRules, z)
       } else {
         // There a few rules not found
         // SystemV/AST4
         // SystemV/MST7
         // SystemV/CST6
         // SystemV/YST9
         // SystemV/HST10
         // SystemV/EST5
         // SystemV/PST8
         // SystemV/CST6CDT
         // SystemV/MST7MDT
         // SystemV/YST9YDT
         // SystemV/PST8PDT
         // SystemV/AST4ADT
         // SystemV/EST5EDT
       }
     }
   }

}
