import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class Java8TimeTest {
    /**
     * 参考 https://www.toutiao.com/i6901845385870967308/
     *
     * ZoneId、ZoneOffset主要表示时区和偏移
     * Instant 表示时间戳
     * Duration、Period 表示时间差，前者表示时间差，后者表示日期差
     * LocalDate、LocalTime、LocalDateTime表示日期、时间、日期+时间
     * ZonedDateTime、OffsetDateTime含时区信息的时间
     * ----------------------------------------
     * | LocalDate  |   LocalTime  | ZoneId   |
     * | LocalDateTime             |          |
     * | ZonedDateTime                        |
     * ----------------------------------------
     * @param args 参数
     */
    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
        // Asia/Shanghai

        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        System.out.println(zoneOffset);
        // +08:00

        Instant instant = Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(zoneOffset));
        System.out.println(instant);
        // 2020-12-23T15:30:29Z

        Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        System.out.println(duration.getSeconds());
        // PT1H   3600
        Duration duration2 = Duration.between(LocalDateTime.now(), LocalDateTime.now().minusHours(1));
        System.out.println(duration2.getSeconds());
        // PT-1H  -3600

        Period period1 = Period.between(LocalDate.now(), LocalDate.now().plusDays(1));
        System.out.println(period1.getDays());
        // 1
        Period period2 = Period.between(LocalDate.now(), LocalDate.now().minusDays(1));
        System.out.println(period2.getDays());
        // -1

        System.out.println(LocalDate.now());
        // 2020-12-23
        System.out.println(LocalTime.now());
        // 23:40:16.195
        System.out.println(LocalDateTime.now());
        // 2020-12-23T23:40:16.195

        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), zoneId);
        System.out.println(zonedDateTime);
        // 2020-12-23T23:40:16.195+08:00[Asia/Shanghai]

        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        // 2020-12-23T23:40:16.196+08:00

        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(offsetDateTime);
        System.out.println(format);
        // 2020-12-23 23:43:34

        TemporalAdjuster temporalAdjuster = TemporalAdjusters.firstDayOfMonth();
        System.out.println(temporalAdjuster.adjustInto(LocalDate.now()));
        // 2020-12-23T23:40:16.196+08:00
    }
}
