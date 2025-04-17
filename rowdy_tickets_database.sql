-----------------------------------------------------------
-- Rowdy Ticket Reservation System SQL Script
-----------------------------------------------------------
-- Drop the database if it exists, then create and use it.
DROP DATABASE IF EXISTS UTSA_Ticket_Reservation_System;
CREATE DATABASE UTSA_Ticket_Reservation_System;
USE UTSA_Ticket_Reservation_System;

-----------------------------------------------------------
-- 1. Table Creation
-----------------------------------------------------------
-- Create the Users table
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    PhoneNumber VARCHAR(20)
);

-- Create the Bookings table
CREATE TABLE Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    SeatNumber VARCHAR(10) NOT NULL,
    ConfirmationCode VARCHAR(20) NOT NULL,
    Status VARCHAR(20) NOT NULL,
    Date DATE NOT NULL,
    UserID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create the Cancellations table
CREATE TABLE Cancellations (
    CancellationID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT NOT NULL,
    UserID INT NOT NULL,
    CancellationCode VARCHAR(20) NOT NULL,
    RefundStatus VARCHAR(20) NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-----------------------------------------------------------
-- 2. Insert Sample Data
-----------------------------------------------------------
-- Insert sample data into Users table
INSERT INTO Users (FirstName, LastName, Email, PhoneNumber)
VALUES 
    ('John', 'Doe', 'john.doe@example.com', '555-1234'),
    ('Jane', 'Smith', 'jane.smith@example.com', '555-5678'),
    ('Alice', 'Johnson', 'alice.johnson@example.com', '555-9012');

-- Insert sample data into Bookings table
INSERT INTO Bookings (SeatNumber, ConfirmationCode, Status, Date, UserID)
VALUES 
    ('A1', 'CONF001', 'Confirmed', '2025-05-20', 1),
    ('A2', 'CONF002', 'Confirmed', '2025-05-20', 2),
    ('B1', 'CONF003', 'Pending', '2025-05-20', 3);

-- Note: Initially, no cancellations exist. They will be automatically created 
-- when a booking’s status changes to 'Cancelled' via the trigger (see below).

-----------------------------------------------------------
-- 3. Trigger Creation
-----------------------------------------------------------
/*
Purpose: This AFTER UPDATE trigger on the Bookings table automatically
inserts a record into the Cancellations table whenever a booking’s status
is changed to 'Cancelled' (and it wasn’t previously cancelled). A cancellation
record is created using the BookingID and UserID from the booking, along with
a generated CancellationCode and a default RefundStatus 'Pending'.
*/
DELIMITER $$
CREATE TRIGGER trg_after_booking_cancelled
AFTER UPDATE ON Bookings
FOR EACH ROW
BEGIN
    IF NEW.Status = 'Cancelled' AND OLD.Status <> 'Cancelled' THEN
        INSERT INTO Cancellations (BookingID, UserID, CancellationCode, RefundStatus)
        VALUES (NEW.BookingID, NEW.UserID, CONCAT('CANC', NEW.BookingID), 'Pending');
    END IF;
END$$
DELIMITER ;

-----------------------------------------------------------
-- 4. Stored Procedure Creation
-----------------------------------------------------------
/*
Purpose: The CancelBooking stored procedure allows a booking to be cancelled 
by updating its Status to 'Cancelled' for a given BookingID and UserID. Once the
status is updated, the trigger (trg_after_booking_cancelled) automatically creates
a cancellation record in the Cancellations table.
Parameters:
    in_BookingID - The identifier for the booking to cancel.
    in_UserID    - The user associated with the booking.
*/
DELIMITER $$
CREATE PROCEDURE CancelBooking(IN in_BookingID INT, IN in_UserID INT)
BEGIN
    UPDATE Bookings
    SET Status = 'Cancelled'
    WHERE BookingID = in_BookingID AND UserID = in_UserID;
END$$
DELIMITER ;

-----------------------------------------------------------
-- 5. Sample Queries Demonstrating CRUD Operations, Joins, and Aggregates
-----------------------------------------------------------

-- 1. Create (Insert): Insert a new User
INSERT INTO Users (FirstName, LastName, Email, PhoneNumber)
VALUES ('Bob', 'Williams', 'bob.williams@example.com', '555-3456');

-- 2. Create (Insert): Insert a new Booking for an existing user (UserID 1)
INSERT INTO Bookings (SeatNumber, ConfirmationCode, Status, Date, UserID)
VALUES ('C3', 'CONF004', 'Confirmed', '2025-05-21', 1);

-- 3. Read (Select with Join): Retrieve all booking details along with user information
SELECT 
    b.BookingID,
    b.SeatNumber,
    b.ConfirmationCode,
    b.Status,
    b.Date,
    u.FirstName,
    u.LastName,
    u.Email
FROM Bookings b
JOIN Users u ON b.UserID = u.UserID;

-- 4. Read (Select with Aggregate): Count the number of bookings by Status
SELECT Status, COUNT(*) AS TotalBookings
FROM Bookings
GROUP BY Status;

-- 5. Update: Update a user's phone number (e.g., for UserID 2)
UPDATE Users
SET PhoneNumber = '555-7777'
WHERE UserID = 2;

-- 6. Update via Stored Procedure: Cancel a booking using the CancelBooking procedure
-- This updates the booking's Status to 'Cancelled' and triggers the automatic insertion 
-- of a corresponding cancellation record.
CALL CancelBooking(1, 1);  -- Cancelling booking with BookingID 1 for UserID 1

-- 7. Delete: Delete a booking record (e.g., delete the booking with BookingID 3 which is 'Pending')
DELETE FROM Bookings
WHERE BookingID = 3;

-- 8. Delete: Delete a user (e.g., the user we just inserted with UserID 4 - Bob Williams)
-- Note: Deleting users may require careful handling if there are foreign key constraints.
DELETE FROM Users
WHERE UserID = 4;

-- 9. Read (Join with Cancellation): Retrieve all cancellations along with related booking and user details
SELECT 
    c.CancellationID,
    c.CancellationCode,
    c.RefundStatus,
    b.SeatNumber,
    b.ConfirmationCode,
    u.FirstName,
    u.LastName
FROM Cancellations c
JOIN Bookings b ON c.BookingID = b.BookingID
JOIN Users u ON c.UserID = u.UserID;

-- 10. Read: Retrieve all bookings for a specific date (e.g., '2025-05-20')
SELECT * FROM Bookings
WHERE Date = '2025-05-20';