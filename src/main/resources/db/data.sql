CREATE TABLE Countries
(
    CountryID   SERIAL PRIMARY KEY,
    CountryName VARCHAR(255) NOT NULL
);

CREATE TABLE Applicants
(
    ApplicantID SERIAL PRIMARY KEY,
    FirstName   VARCHAR(255) NOT NULL,
    LastName    VARCHAR(255) NOT NULL,
    DateOfBirth DATE         NOT NULL,
    Gender      CHAR(1)      NOT NULL,
    CountryID   INT REFERENCES Countries (CountryID)
);

CREATE TABLE ApplicationStatuses
(
    StatusID   SERIAL PRIMARY KEY,
    StatusName VARCHAR(255) NOT NULL
);

CREATE TABLE Applications
(
    ApplicationID   SERIAL PRIMARY KEY,
    ApplicantID     INT REFERENCES Applicants (ApplicantID),
    ApplicationDate DATE NOT NULL,
    StatusID        INT REFERENCES ApplicationStatuses (StatusID)
);

CREATE TABLE DocumentTypes
(
    DocumentTypeID   SERIAL PRIMARY KEY,
    DocumentTypeName VARCHAR(255) NOT NULL
);

CREATE TABLE Documents
(
    DocumentID     SERIAL PRIMARY KEY,
    ApplicationID  INT REFERENCES Applications (ApplicationID),
    DocumentTypeID INT REFERENCES DocumentTypes (DocumentTypeID),
    DocumentPath   VARCHAR(255) NOT NULL
);