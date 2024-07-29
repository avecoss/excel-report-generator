-- Insert Applicants
DO
$$
    BEGIN
        FOR i IN 1..1000000
            LOOP
                INSERT INTO Applicants (FirstName, LastName, DateOfBirth, Gender, CountryID)
                VALUES ('FirstName' || i,
                        'LastName' || i,
                        '1980-01-01'::DATE + (i % 365),
                        CASE WHEN i % 2 = 0 THEN 'M' ELSE 'F' END,
                        (i % 10) + 1);
            END LOOP;
    END
$$;

-- Insert Applications
DO
$$
    BEGIN
        FOR i IN 1..1000000
            LOOP
                INSERT INTO Applications (ApplicantID, ApplicationDate, StatusID)
                VALUES (i,
                        '2020-01-01'::DATE + (i % 365),
                        (i % 3) + 1);
            END LOOP;
    END
$$;

-- Insert Documents
DO
$$
    BEGIN
        FOR i IN 1..100000
            LOOP
                INSERT INTO Documents (ApplicationID, DocumentTypeID, DocumentPath)
                VALUES (i,
                        (i % 4) + 1,
                        '/path/to/document' || i || '.pdf');
            END LOOP;
    END
$$;