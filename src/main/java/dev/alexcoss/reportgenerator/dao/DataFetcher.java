package dev.alexcoss.reportgenerator.dao;

import dev.alexcoss.reportgenerator.dto.ApplicantData;
import dev.alexcoss.reportgenerator.mapper.ApplicantDataRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataFetcher {
    private static final String QUERY = """
            SELECT a.ApplicantID,
                   CONCAT(a.FirstName, ' ', a.LastName) AS FullName, 
                   a.DateOfBirth, 
                   c.CountryName, 
                   ap.ApplicationID, 
                   aps.StatusName, 
                   ap.ApplicationDate, 
                   dt.DocumentTypeName 
            FROM Applicants a
            JOIN Countries c ON a.CountryID = c.CountryID
            JOIN Applications ap ON a.ApplicantID = ap.ApplicantID
            JOIN ApplicationStatuses aps ON ap.StatusID = aps.StatusID
            JOIN Documents d ON ap.ApplicationID = d.ApplicationID
            JOIN DocumentTypes dt ON d.DocumentTypeID = dt.DocumentTypeID
            LIMIT ? OFFSET ?""";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM Applicants";

    private final JdbcTemplate jdbcTemplate;
    private final ApplicantDataRowMapper rowMapper;

    public List<ApplicantData> fetchData(int offset, int batchSize) {
        return jdbcTemplate.query(QUERY, rowMapper, batchSize, offset);
    }

    public int fetchTotalRecords() {
        Integer count = jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class);
        if (count == null)
            throw new IllegalStateException("Count query returned null");
        return count;
    }
}
