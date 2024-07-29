package dev.alexcoss.reportgenerator.mapper;

import dev.alexcoss.reportgenerator.dto.ApplicantData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ApplicantDataRowMapper implements RowMapper<ApplicantData> {
    @Override
    public ApplicantData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ApplicantData.builder()
            .applicantId(rs.getInt("ApplicantID"))
            .fullName(rs.getString("FullName"))
            .dateOfBirth(rs.getDate("DateOfBirth").toLocalDate())
            .countryName(rs.getString("CountryName"))
            .applicationId(rs.getInt("ApplicationID"))
            .statusName(rs.getString("StatusName"))
            .applicationDate(rs.getDate("ApplicationDate").toLocalDate())
            .documentTypeName(rs.getString("DocumentTypeName"))
            .build();
    }
}
