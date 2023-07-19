CREATE TABLE IF NOT EXISTS user (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    authorizationToken VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS itemcategory (
	id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS item (
	itemId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    category INT NOT NULL,
    enabled BOOLEAN NOT NULL,
    FOREIGN KEY(category) REFERENCES itemcategory(id)
);

CREATE TABLE IF NOT EXISTS history (
	historyId INT PRIMARY KEY AUTO_INCREMENT,
    itemId INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    category INT NOT NULL,
    enabled BOOLEAN NOT NULL,
    date DATETIME NOT NULL DEFAULT now(),
    FOREIGN KEY(itemId) REFERENCES item(itemId)
);

DELIMITER $$
CREATE TRIGGER addNewItemToHistory AFTER INSERT ON item
FOR EACH ROW
BEGIN
	INSERT INTO history (itemId, name, description, quantity, category, enabled) VALUES (new.itemId, new.name, new.description, new.quantity, new.category, new.enabled);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER addUpdatedItemToHistory AFTER UPDATE ON item
FOR EACH ROW
BEGIN
	INSERT INTO history (itemId, name, description, quantity, category, enabled) VALUES (new.itemId, new.name, new.description, new.quantity, new.category, new.enabled);
END $$
DELIMITER ;

CREATE VIEW historyview AS
SELECT history.historyId, history.itemId, history.name, history.description, history.quantity, itemcategory.category, history.enabled, history.date FROM history
INNER JOIN item ON history.itemId = item.itemId
INNER JOIN itemcategory ON itemcategory.id = history.category ORDER BY history.historyId ASC;

CREATE VIEW itemview AS
SELECT item.itemId, item.name, item.description, item.quantity, itemcategory.category, item.enabled FROM item
INNER JOIN itemcategory ON item.category = itemcategory.id;
