-- -----------------------------------------------------------
-- Rowdy Ticket Reservation System SQL Script
-- -----------------------------------------------------------
-- Drop the database if it exists, then create and use it.
DROP DATABASE IF EXISTS UTSA_Ticket_Reservation_System;
CREATE DATABASE UTSA_Ticket_Reservation_System;
USE UTSA_Ticket_Reservation_System;

-- -----------------------------------------------------------
-- 1. Table Creation
-- -----------------------------------------------------------
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
                               RefundStatus VARCHAR(20) NOT NULL,
                               FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID),
                               FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create the Seats table
CREATE TABLE Seats (
                       SeatID INT AUTO_INCREMENT PRIMARY KEY,
                       SeatNumber VARCHAR(10) NOT NULL UNIQUE,
                       Availability ENUM('Available', 'Unavailable') NOT NULL DEFAULT 'Available'
);

-- -----------------------------------------------------------
-- 2. Insert Seat Data from Convocation Center
-- -----------------------------------------------------------
DELIMITER //
CREATE PROCEDURE populateSeats()
BEGIN
	DECLARE i INT DEFAULT 1;
    WHILE i <= 500 DO
		INSERT INTO Seats (SeatNumber, Availability)
        VALUES (
			CASE
				WHEN i <= 50 THEN CONCAT('A', LPAD(i, 2, '0'))
                WHEN i <= 100 THEN CONCAT('B', LPAD(i - 50, 2, '0'))
                WHEN i <= 150 THEN CONCAT('C', LPAD(i - 100, 2, '0'))
                WHEN i <= 200 THEN CONCAT('D', LPAD(i - 150, 2, '0'))
                WHEN i <= 250 THEN CONCAT('E', LPAD(i - 200, 2, '0'))
                WHEN i <= 300 THEN CONCAT('F', LPAD(i - 250, 2, '0'))
                WHEN i <= 350 THEN CONCAT('G', LPAD(i - 300, 2, '0'))
                WHEN i <= 400 THEN CONCAT('H', LPAD(i - 350, 2, '0'))
                WHEN i <= 450 THEN CONCAT('I', LPAD(i - 400, 2, '0'))
                WHEN i <= 500 THEN CONCAT('J', LPAD(i - 450, 2, '0'))
			END,
            'Available'
            );
		SET i = i+1;
END WHILE;
END//
DELIMITER ;

CALL populateSeats();
-- -----------------------------------------------------------
-- 3. Trigger Creation
-- -----------------------------------------------------------
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

-- -----------------------------------------------------------
-- 4. Stored Procedure Creation
-- -----------------------------------------------------------
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