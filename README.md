# WeatherService
This is a basic overview of the weatherservice project.  It is geared toward a reviewer within the
context that this project is an evaluation, and is:

-- created in a relatively short time frame
-- created solely by Mike Daigle without collaboration

In some cases, I have leveraged existing code from older code I've used for my personal projects.  In every case,
these files were originally created by me from scratch.  Note that some of these utilities still use older patterns,
(like singleton patterns) that I have yet to refactor.  I have attempted to use more current patterns in the code that
relates directly to the design goals for this project.

To get, build and run:

    git@github.com:mcdaigle1/WeatherService.git (or https://github.com/mcdaigle1/WeatherService.git)

    mvn clean package

    java -jar target/weatherservice-0.0.1-SNAPSHOT.jar

Here is a command reference:

    To query for wind data:
        url "http://localhost:8080/wind/{zipcode}"
    i.e
        curl "http://localhost:8080/wind/93101"

    To bust the cache:
        curl "http://localhost:8080/bustCache"

Please let me know if you have any questions regarding the code.

Mike Daigle
mcdaigle1@gmail.com
805-689-6264

