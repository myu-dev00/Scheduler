use schedule;
CREATE TABLE IF NOT EXISTS schedule (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        contents VARCHAR(100) NOT NULL,
                                        username VARCHAR(100) NOT NULL,
                                        password VARCHAR(10) NOT NULL,
                                        date VARCHAR(10) NOT NULL,
                                        modified_date VARCHAR(10)
);