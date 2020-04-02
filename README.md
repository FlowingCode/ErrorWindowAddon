[![Build Status](https://jenkins.flowingcode.com/job/ErrorWindow-addon/badge/icon)](https://jenkins.flowingcode.com/job/ErrorWindow-addon)

# Error Window Add-on for Vaadin

Error Window Add-on is a UI component add-on for Vaadin 14+

## Features

This add-on shows a dialog to inform that an error was cause by an exception.

The dialog shows different information depending on the running mode of the application:

- if the application is running in development mode, it shows the stack trace of the exception.
- if the applicacion is running in production mode, it shows a code to report that can be found in log files.

The dialog (ErrorWindow) is invoked automatically through a com.vaadin.flow.server.VaadinServiceInitListener (WindowErrorHandler) or just by itself calling the "open" method.

It is posible to customize the message shown. 

This add-on is built using server side API

## Online demo

Try the add-on demo at http://addonsv14.flowingcode.com/error-window

## Download release

Official releases of this add-on are available through Vaadin Directory. 

## Building and running demo

git clone https://github.com/FlowingCode/error-window-addon
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

### Version 3.0.0
- First Vaadin 14+ release

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases. 

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:

- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

RssItems Addon is written by Flowing Code S.A.



