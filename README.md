[![Published on Vaadin Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/error-window-add-on)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/error-window-add-on.svg)](https://vaadin.com/directory/component/error-window-add-on)
[![Build Status](https://jenkins.flowingcode.com/job/ErrorWindow-14-addon/badge/icon)](https://jenkins.flowingcode.com/job/ErrorWindow-14-addon)
[![Maven Central](https://img.shields.io/maven-central/v/com.flowingcode.vaadin.addons/error-window-vaadin)](https://mvnrepository.com/artifact/com.flowingcode.vaadin.addons/error-window-vaadin)
[![Javadoc](https://img.shields.io/badge/javadoc-00b4f0)](https://javadoc.flowingcode.com/artifact/com.flowingcode.vaadin.addons/error-window-vaadin)

# Error Window Add-on for Vaadin

Error Window Add-on is a UI component add-on for Vaadin Flow

## Features

This add-on shows a dialog to inform that an error was cause by an exception.

The dialog shows different information depending on the running mode of the application:

- if the application is running in development mode, it shows the stack trace of the exception.
- if the applicacion is running in production mode, it shows a code to report that can be found in log files.

The dialog (ErrorWindow) is invoked automatically through a com.vaadin.flow.server.VaadinServiceInitListener (WindowErrorHandler) or just by itself calling the "open" method.

It is posible to customize the message shown. 

This add-on is built using server side API

## Online demo

[Online demo here](http://addonsv24.flowingcode.com/error-window)

## Download release

[Available in Vaadin Directory](https://vaadin.com/directory/component/error-window-add-on)

### Maven install

Add the following dependencies in your pom.xml file:

```xml
<dependency>
   <groupId>com.flowingcode.vaadin.addons</groupId>
   <artifactId>error-window-vaadin</artifactId>
   <version>X.Y.Z</version>
</dependency>
```
Release versions are available from Maven Central repository. For SNAPSHOT versions see [here](https://maven.flowingcode.com/snapshots/).

## Building and running demo

- git clone repository
- mvn clean install jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

See [here](https://github.com/FlowingCode/ErrorWindowAddon/releases)

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome. There are two primary ways you can contribute: by reporting issues or by submitting code changes through pull requests. To ensure a smooth and effective process for everyone, please follow the guidelines below for the type of contribution you are making.

#### 1. Reporting Bugs and Requesting Features

Creating an issue is a highly valuable contribution. If you've found a bug or have an idea for a new feature, this is the place to start.

* Before creating an issue, please check the existing issues to see if your topic is already being discussed.
* If not, create a new issue, choosing the right option: "Bug Report" or "Feature Request". Try to keep the scope minimal but as detailed as possible.

> **A Note on Bug Reports**
> 
> Please complete all the requested fields to the best of your ability. Each piece of information, like the environment versions and a clear description, helps us understand the context of the issue.
> 
> While all details are important, the **[minimal, reproducible example](https://stackoverflow.com/help/minimal-reproducible-example)** is the most critical part of your report. It's essential because it removes ambiguity and allows our team to observe the problem firsthand, exactly as you are experiencing it.

#### 2. Contributing Code via Pull Requests

As a first step, please refer to our [Development Conventions](https://github.com/FlowingCode/DevelopmentConventions) page to find information about Conventional Commits & Code Style requirements.

Then, follow these steps for creating a contribution:
 
- Fork this project.
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- For commit message, use [Conventional Commits](https://github.com/FlowingCode/DevelopmentConventions/blob/main/conventional-commits.md) to describe your change.
- Send a pull request for the original project.
- Comment on the original issue that you have implemented a fix for it.

## License & Author

This add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

Error Window Add-on is written by Flowing Code S.A.

# Developer Guide

## Getting started

```java
Button errorButton =
    new Button(
        "Throw Error",
        event -> {
          Integer.parseInt("asdf");
        });
```    
    
```java
Button throwErrorWithoutErrorHandler =
    new Button(
        "Throw Error without an error handler",
        ev -> {
          try {
            Integer.parseInt("asdf");
          } catch (NumberFormatException e) {
            ErrorManager.showError(e);
          }
        });
```

## Special configuration when using Spring

By default, Vaadin Flow only includes ```com/vaadin/flow/component``` to be always scanned for UI components and views. For this reason, the add-on might need to be allowed in order to display correctly. 

To do so, just add ```com.flowingcode``` to the ```vaadin.allowed-packages``` property in ```src/main/resources/application.properties```, like:

```vaadin.allowed-packages = com.vaadin,org.vaadin,dev.hilla,com.flowingcode```
 
More information on Spring scanning configuration [here](https://vaadin.com/docs/latest/integrations/spring/configuration/#configure-the-scanning-of-packages).


