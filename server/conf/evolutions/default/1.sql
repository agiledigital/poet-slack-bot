# Users schema

# --- !Ups

CREATE TABLE questionsasked (
    questionNo SERIAL PRIMARY KEY,
    question TEXT NOT NULL
);
