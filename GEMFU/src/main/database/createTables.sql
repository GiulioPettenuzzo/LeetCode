create table users(
   username VARCHAR(20) NOT NULL,
   password VARCHAR(20),
   name VARCHAR(100) NOT NULL,
   surname VARCHAR(40) NOT NULL,
   profileImage TEXT,
   email VARCHAR(200),
   gender INT,
   date DATE,
   PRIMARY KEY ( username )
);

create table problem(
   id SERIAL PRIMARY KEY,
   username VARCHAR(20) NOT NULL,
   title VARCHAR(100) NOT NULL,
   difficulty VARCHAR(40) NOT NULL,
   description TEXT,
   date TIMESTAMP,
   solution TEXT,
   FOREIGN KEY (username) REFERENCES users(username)
);

create table thread(
  id SERIAL PRIMARY KEY,
  username VARCHAR(20) NOT NULL,
  problem_id INT NOT NULL,
  title VARCHAR(50) NOT NULL,
  text TEXT NOT NULL,
  date TIMESTAMP,
  views INT DEFAULT 0,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (problem_id) REFERENCES problem(id)
);
CREATE TABLE company (
   name VARCHAR(50) PRIMARY KEY

);
CREATE TABLE problemcomp(
	problem INT,
	company VARCHAR(50),
	PRIMARY KEY(problem,company),
	FOREIGN KEY (company) REFERENCES company(name),
	FOREIGN KEY (problem) REFERENCES problem(id)
);
	
CREATE TABLE  category(
   name VARCHAR(20) PRIMARY KEY,
   subcategory VARCHAR(20),
   FOREIGN KEY (subcategory) REFERENCES category(name)
);

CREATE TABLE problemcat(
    problem INT,
    category VARCHAR(20),
    PRIMARY KEY(problem, category),
    FOREIGN KEY (category) REFERENCES category(name),
    FOREIGN KEY (problem) REFERENCES problem(id)
);

create table submission(
   id SERIAL PRIMARY KEY,
   username VARCHAR(20) NOT NULL,
   problem INT,
   title VARCHAR(100) NOT NULL,
   text text NOT NULL,
   date TIMESTAMP,
   valid INT,
   FOREIGN KEY (username) REFERENCES users(username),
   FOREIGN KEY (problem) REFERENCES problem(id)
);

CREATE TABLE hint(
id INT PRIMARY KEY,
description TEXT,
problem INT,
FOREIGN KEY (problem) REFERENCES problem(id)
);

create table comment(
   id SERIAL PRIMARY KEY,
   id2 INT,
   FOREIGN KEY (id2) REFERENCES comment(id), 
   text TEXT NOT NULL,
   date TIMESTAMP
);

create table votethread(
   username VARCHAR(20),
   thread INT,
   vote INT NOT NULL,
   PRIMARY KEY(username,thread),
   FOREIGN KEY (username) REFERENCES users(username),
   FOREIGN KEY (thread) REFERENCES thread(id)
);

create table addcomment(
  comment INT PRIMARY KEY,
  problem INT,
  username VARCHAR(20),
  FOREIGN KEY (comment) REFERENCES comment(id),
  FOREIGN KEY (problem) REFERENCES problem(id), 
  FOREIGN KEY (username) REFERENCES users(username)
);

create table comthread(
   comment INT PRIMARY KEY,
   thread INT,
   username VARCHAR(20),
   FOREIGN KEY (comment) REFERENCES comment(id),
   FOREIGN KEY (thread) REFERENCES thread(id), 
   FOREIGN KEY (username) REFERENCES users(username)
);