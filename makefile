# Makefile for Spring Boot (Maven) Project

# Variables
MVN = mvn
JAR_FILE = target/e-commerce-0.0.1-SNAPSHOT.jar  # change to your actual artifact name
PROFILE ?= default

# Default target
.PHONY: help
help:
	@echo "Makefile commands for Spring Boot Maven project"
	@echo "-----------------------------------------------"
	@echo "make clean        : Clean project"
	@echo "make compile      : Compile project"
	@echo "make package      : Package project (jar)"
	@echo "make install      : Install package to local Maven repository"
	@echo "make run          : Run Spring Boot application"
	@echo "make run-profile  : Run with specific Spring profile (PROFILE=<profile>)"
	@echo "make test         : Run tests"
	@echo "make rebuild      : Clean + package"

# Clean project
.PHONY: clean
clean:
	$(MVN) clean

# Compile project
.PHONY: compile
compile:
	$(MVN) compile

# Package project
.PHONY: package
package:
	$(MVN) package

# Install to local Maven repository
.PHONY: install
install:
	$(MVN) install 

# Run Spring Boot project
.PHONY: run
run:
	$(MVN) spring-boot:run

# Run with specific Spring profile
.PHONY: run-profile
run-profile:
	SPRING_PROFILES_ACTIVE=$(PROFILE) $(MVN) spring-boot:run

# Run tests
.PHONY: test
test:
	$(MVN) test

# Clean + Package
.PHONY: rebuild
rebuild: clean package

