-- -----------------------------------------------------------
-- Rowdy Ticket Reservation System SQL Script
-- -----------------------------------------------------------
-- Drop the database if it exists, then create and use it.
DROP DATABASE IF EXISTS UTSA_Ticket_Reservation_System;
CREATE DATABASE UTSA_Ticket_Reservation_System;
USE UTSA_Ticket_Reservation_System;

-- -----------------------------------------------------------
-- Table Creation
-- -----------------------------------------------------------
-- Create the Users table
CREATE TABLE Users (
                       UserID INT AUTO_INCREMENT PRIMARY KEY,
                       FirstName VARCHAR(100) NOT NULL,
                       LastName VARCHAR(100) NOT NULL,
                       Email VARCHAR(100) NOT NULL UNIQUE,
                       PhoneNumber VARCHAR(20),
                       Password VARCHAR (255) NOT NULL
);

-- Create the Seats table
CREATE TABLE Seats (
                       SeatID INT AUTO_INCREMENT PRIMARY KEY,
                       SeatNumber VARCHAR(10) NOT NULL UNIQUE,
                       seatRow VARCHAR(5) NOT NULL,
                       Availability ENUM('Available', 'Unavailable') NOT NULL DEFAULT 'Available'
);

-- Create the Games table
CREATE TABLE Games (
                       GameID         INT AUTO_INCREMENT PRIMARY KEY,
                       Team1          VARCHAR(100) NOT NULL,
                       Team2          VARCHAR(100) NOT NULL,
                       GameDate       DATE         NOT NULL UNIQUE,
                       TotalSeats     INT          NOT NULL DEFAULT 500,
                       AvailableSeats INT          NOT NULL DEFAULT 500
);

-- Create the Bookings table
CREATE TABLE Bookings (
                          BookingID INT AUTO_INCREMENT PRIMARY KEY,
                          UserID INT NOT NULL,
                          GameID INT NOT NULL,
                          SeatID INT NOT NULL,
                          Status VARCHAR(20) NOT NULL,
                          Date DATE NOT NULL,
                          FOREIGN KEY (UserID) REFERENCES Users(UserID),
                          FOREIGN KEY (GameID) REFERENCES Games(GameID),
                          FOREIGN KEY (SeatID) REFERENCES Seats(SeatID),
                          UNIQUE KEY uq_game_seat (GameID, SeatID)
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

-- -----------------------------------------------------------
-- Stored Procedures
-- -----------------------------------------------------------
-- Generate seats
DELIMITER //
CREATE PROCEDURE populateSeats()
BEGIN
	DECLARE i INT DEFAULT 1;
    WHILE i <= 500 DO
		INSERT INTO Seats (SeatNumber, seatRow, Availability)
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
            CHAR(65 + FLOOR((i-1)/50)),
            'Available'
            );
		SET i = i+1;
END WHILE;
END//

/*
Purpose: The CancelBooking stored procedure allows a booking to be cancelled
by updating its Status to 'Cancelled' for a given BookingID and UserID. Once the
status is updated, the trigger (trg_after_booking_cancelled) automatically creates
a cancellation record in the Cancellations table.
Parameters:
    in_BookingID - The identifier for the booking to cancel.
    in_UserID    - The user associated with the booking.
*/
CREATE PROCEDURE CancelBooking(IN in_BookingID INT, IN in_UserID INT)
BEGIN
UPDATE Bookings
SET Status = 'Cancelled'
WHERE BookingID = in_BookingID AND UserID = in_UserID;
END//
DELIMITER ;

-- -----------------------------------------------------------
-- Triggers
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
        INSERT INTO Cancellations (BookingID, UserID, RefundStatus)
        VALUES (NEW.BookingID, NEW.UserID, 'Pending');
END IF;
END$$

-- On booking insert -> decrement available seats & mark seat unavailable
CREATE TRIGGER trg_after_booking_confirm
    AFTER INSERT ON Bookings
    FOR EACH ROW
BEGIN
    IF NEW.Status = 'Confirmed' THEN
    UPDATE Games
    SET AvailableSeats = AvailableSeats - 1
    WHERE GameID = NEW.GameID;
    UPDATE Seats
    SET Availability = 'Unavailable'
    WHERE SeatID = NEW.SeatID;
END IF;
END$$

-- On booking cancel -> restore available seats & mark seat available
CREATE TRIGGER trg_after_booking_cancel
    AFTER UPDATE ON Bookings
    FOR EACH ROW
BEGIN
    IF NEW.Status = 'Cancelled' AND OLD.Status <> 'Cancelled' THEN
    UPDATE Games
    SET AvailableSeats = AvailableSeats + 1
    WHERE GameID = NEW.GameID;
    UPDATE Seats
    SET Availability = 'Available'
    WHERE SeatID = OLD.SeatID;
END IF;
END$$
DELIMITER ;
-- -----------------------------------------------------------
-- Initialization
-- -----------------------------------------------------------
CALL populateSeats();

INSERT INTO Games(Team1, Team2, GameDate) VALUES
                                              ('UTSA', 'Trinity', '2024-11-04'),
                                              ('USTA', 'Little Rock', '2024-11-16'),
                                              ('UTSA', 'Merrimack', '2024-11-27'),
                                              ('UTSA', 'Houston Christian', '2024-11-30'),
                                              ('UTSA', 'North Dakota', '2024-12-13'),
                                              ('UTSA', 'Southwestern Adventist', '2024-12-19'),
                                              ('UTSA', 'Tulsa', '2025-01-07'),
                                              ('UTSA', 'Wichita State', '2025-01-11'),
                                              ('UTSA', 'North Texas', '2025-01-18'),
                                              ('UTSA', 'Temple', '2025-01-25'),
                                              ('UTSA', 'Tulane', '2025-02-05'),
                                              ('UTSA', 'East Carolina', '2025-02-08'),
                                              ('UTSA', 'South Florida', '2025-02-19'),
                                              ('UTSA', 'Rice', '2025-03-02'),
                                              ('UTSA', 'Memphis', '2025-03-04');

-- -----------------------------------------------------------
-- Queries
-- -----------------------------------------------------------
-- Counts how many seats are available for a game
SELECT AvailableSeats
FROM Games
WHERE GameID = 1;

-- Shows each seat that is available for a certain game
SELECT
    s.SeatID,
    s.SeatNumber,
    s.SeatRow
FROM Seats s
         LEFT JOIN Bookings b
                   ON  b.SeatID   = s.SeatID
                       AND b.GameID   = 1
                       AND b.Status   = 'Confirmed'
WHERE s.Availability = 'Available'
  AND b.BookingID IS NULL;

-- Create sample user
INSERT INTO Users(FirstName, LastName, Email, PhoneNumber, Password) VALUES
    ('Emilio', 'Hernandez', 'emilio01@gmail.com', '210-394-3030', 'UTSA');

-- find User based on password
-- SELECT * FROM Users WHERE Password = SHA2('UTSA', 256);
