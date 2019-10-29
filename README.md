# Ravn GraphQL Code Challenge
Mobile application that allows searching Github repositories by user using GraphQL and Apollo.
To get started with the Android integration, go to [Apollo GraphQL Client for Android and the JVM](https://github.com/apollographql/apollo-tooling#apollo-clientdownload-schema-output).

## Prerequisites
1. Java 8
2. Node.js(12.3.0)(npm)
3. Curl 7.58.0
4. GitHub API Key


### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Functionalities
1. Get a user's repositories.
2. Get contributors from a repository.
3. Save the results of the queries in cache to use them in offline mode.
4. Set api key.


## Running the tests

Explain how to run the automated tests for this system

<img src="/images/1.jpeg" alt="3DPrinter" height="640" width="360" align="middle"> 

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Restriction

1. Github API v4 only allows you to see the collaborators of a repository, if it was created by the user who generates the API key.
2. Its not possible get the URL of each user's avatar, when the query is made the api returns null.

## Built With

* [GraphQL](https://graphql.org) - Query language for APIs.
* [Apollo](https://www.apollographql.com/) - Compliant client that generates Java models.
* [Android Studio](https://developer.android.com/studio) - Official integrated development environment (IDE) for Android application development.

## Authors

* **Juan Jose Leon Camilo** - *Entire project* - [juanjoseleca](https://github.com/juanjoseleca)

