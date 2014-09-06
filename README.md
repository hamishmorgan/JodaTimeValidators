# JodaTime Validators

A collection of `javax.validation.ConstraintValidator` implementations that fully support [JodaTime](http://www.joda.org/joda-time/) concrete types and interfaces.


## Annotations

 * `@Past`
 * `@Future`
 * `@Before`
 * `@After`

## Temporal Types

 * `org.joda.time.ReadableInstant`
   * `org.joda.time.DateTime`
   * `org.joda.time.DateMidnight`
   * `org.joda.time.Instant`
   * `org.joda.time.MutableDateTime`
 * `org.joda.time.ReadablePartial`
   * `org.joda.time.LocalDate`
   * `org.joda.time.LocalDateTime`
   * `org.joda.time.LocalTime`
   * `org.joda.time.MonthDay`
   * `org.joda.time.Partial`
   * `org.joda.time.TimeOfDay`
   * `org.joda.time.YearMonth`
   * `org.joda.time.YearMonthDay`
 * `org.joda.time.ReadableInterval`
   * `org.joda.time.Interval`
   * `org.joda.time.MutableInterval`

## Support

|           | `ReadableInstant` | `ReadablePartial` | `ReadableInterval` |
| --------- |:-----------------:|:-----------------:|:------------------:|
| `@Past`   | ✔                 | ✔                 | ✔                  |
| `@Future` | ✔                 | ✔                 | ✔                  |
| `@Before` |                   |                   |                    |
| `@After`  |                   |                   |                    |


