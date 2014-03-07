#!/bin/bash
mvn clean compile assembly:single
chmod +x target/*.jar
