# JResuable Components

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Project status](https://img.shields.io/badge/Project%20status-Maintenance-orange.svg)](https://img.shields.io/badge/Project%20status-Maintenance-orange.svg)

# Project status

I use this project to learn new technologies related to Java, Spring Frameworks, Jakarta EE, etc.<br />
I change this project constantly improving and adding new features, click [here](docs/STATUS.md) to follow up.

# The Problem

We could say 90% of our code are CRUD (Create, Read, Update, and Delete), so I realized (and the universe) these operations are repeated on projects, and they do the same thing. Example:  <br />

```text
PersonControler (post, put, delete, patch, get)
     |
PersonService (save, delete, find, etc)
     |
PersonRepository (okay, here we have great solutions like Spring Data, Jakarta Data, etc, just use it)
     |
WhatEverDataBase
```
It works for other entities, so we tend to repeat this code.

# About

My intention on this project is to creat a CRUD maker using as background well known frameworks (Spring Frameworks
# Technologies

- Java
- Maven

# Requirements

These are the requirements:

- Git

```bash
# check the git version
git --version
```

- Java version >= 21 

```bash
# check the Java version
java --version
```

- Maven version >= 3.8.8

```bash
# check the Maven version
mvn --version
```

## To install

Please get this project and install it on your repository before continuing.

To start, clone it:

```bash
git clone https://github.com/fernando-romulo-silva/image-converter-service
```

Just import it as a maven project on your IDE.
