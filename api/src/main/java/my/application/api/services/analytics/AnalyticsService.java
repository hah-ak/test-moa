package my.application.api.services.analytics;

import com.google.analytics.data.v1beta.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final ApplicationContext applicationContext;
    private static final BetaAnalyticsDataSettings betaAnalyticsDataSettings;


    static {
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(AnalyticsService.class.getResourceAsStream("/ga_service_account.json")))
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/analytics.readonly"));

            betaAnalyticsDataSettings =
                    BetaAnalyticsDataSettings.newBuilder()
                            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                            .build();
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public String getRunReport() {
        try (BetaAnalyticsDataClient betaAnalyticsDataClient = BetaAnalyticsDataClient.create(betaAnalyticsDataSettings)) {
            LocalDate utc = LocalDate.now(ZoneId.of("UTC"));
//            347020421 여보야
//            451919449 내꺼
            RunReportRequest eventCount = RunReportRequest.newBuilder()
                    .setProperty("properties/" + "451919449")
                    .addDimensions(Dimension.newBuilder().setName("dateHour").build())
                    .addDimensions(Dimension.newBuilder().setName("eventName").build())
                    .addDimensions(Dimension.newBuilder().setName("operatingSystem").build())
                    .addMetrics(Metric.newBuilder().setName("eventCount").build())
                    .setDimensionFilter(FilterExpression.newBuilder()
                            .setFilter(Filter.newBuilder()
                                    .setFieldName("eventName")
                                    .setStringFilter(Filter.StringFilter.newBuilder().setMatchType(Filter.StringFilter.MatchType.EXACT).setValue("session_start").build()).build()).build())
                    .addDateRanges(DateRange.newBuilder()
                            .setStartDate("2024-07-25")
                            .setEndDate("2024-07-27")
                            .build())
                    .build();

            RunReportResponse runReportResponse = betaAnalyticsDataClient.runReport(eventCount);

            List<Row> rowsList = runReportResponse.getRowsList();
            for (int i = 0; i < rowsList.size(); i++) {
                System.out.println(rowsList.get(i));
            }
//            GetMetadataRequest request =
//                    GetMetadataRequest.newBuilder().setName("properties/" + 347020421 + "/metadata").build();
//
//            // Make the request.
//            Metadata response = betaAnalyticsDataClient.getMetadata(request);
//            System.out.println(response);
            return runReportResponse.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
