# Ravn GraphQL Code Challenge
Mobile application that allows searching Github repositories by user using GraphQL and Apollo.
To get started with the Android integration, go to [Apollo GraphQL Client for Android and the JVM](https://github.com/apollographql/apollo-tooling#apollo-clientdownload-schema-output).

## Prerequisites
1. Java 8
2. Node.js(12.3.0)(npm)
3. Curl 7.58.0
4. GitHub API Key


### Architecture
The application makes the connection with the Github API through Apollo, using GraphQL to obtain the data that it requires (query), then processes the information in the controllers to finally display it in the interface.

<img src="/images/architecture.png" alt="3DPrinter" height="176" width="313" align="middle"> 

## Design

The main interface consists of a navigation bar, a text editor and a list view where we will show the results obtained. Then we have the interface to save the API key, which consists of a text editor, a save button and a Github image that redirects you to the page where you can generate your API key.

## Functionalities
1. Get a user's repositories.
2. Get contributors from a repository.
3. Save the results of the queries in cache to use them in offline mode.
4. Set api key.


## Running the application

```
When starting the application, it will show us the main interface where we can search for repositories by user.
```

<img src="/images/1.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 

```
Before performing a search we must enter our Github api key
```

<img src="/images/6.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 

```
Before performing a search we must enter our Github api key
```

<img src="/images/7.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 
<img src="/images/8.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 

<img src="/images/2.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 
<img src="/images/3.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 
<img src="/images/4.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 
<img src="/images/5.jpeg" alt="3DPrinter" height="320" width="180" align="middle"> 



## Restrictions

1. Github API v4 only allows you to see the collaborators of a repository, if it was created by the user who generates the API key.
2. Its not possible get the URL of each user's avatar, when the query is made the api returns null.

## Built With

* [GraphQL](https://graphql.org) - Query language for APIs.
* [Apollo](https://www.apollographql.com/) - Compliant client that generates Java models.
* [Android Studio](https://developer.android.com/studio) - Official integrated development environment (IDE) for Android application development.

## Authors

* **Juan Jose Leon Camilo** - *Entire project* - [juanjoseleca](https://github.com/juanjoseleca)

