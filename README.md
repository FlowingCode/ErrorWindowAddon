# Error Window Add-on for Vaadin

Error Window Add-on is a UI component add-on for Vaadin Framework versions 7 and 8.

## Features

This add-on allows to add a sub-window to inform that an error was cause by an exception.

The sub-window shows different information depending on the running mode of the application:

- if the application is running in debug mode, it shows the stack trace of the exception.
- if the applicacion is running in production mode, it shows a code to report that can be found in log files.

The sub-window (ErrorWindow) can be invoke through an error handler (WindowErrorHandler) or just by itself calling the "open" method.

It is posible to customize the message shown. 

This add-on is a server-side-only component, so there's no need to recompile the widgetset.

## Online demo

Not available yet.
<Try the add-on demo at>

## Download release

Official releases of this add-on will be available at Vaadin Directory soon. 

## Building and running demo

git clone https://github.com/FlowingCode/error-window-addon
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

### Version 1.0.0-SNAPSHOT
- First Version

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

Error Window Add-on is written by 
- Paola De Bartolo
- Felipe Lang




